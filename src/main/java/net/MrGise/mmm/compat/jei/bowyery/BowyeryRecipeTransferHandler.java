package net.MrGise.mmm.compat.jei.bowyery;

import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import net.MrGise.mmm.MMM;
import net.MrGise.mmm.network.ModNetwork;
import net.MrGise.mmm.network.compat.TransferBowyeryRecipePacket;
import net.MrGise.mmm.recipe.BowyeryRecipe;
import net.MrGise.mmm.registry.variables.ModMenuTypes;
import net.MrGise.mmm.screen.bowyery_table.BowyeryTableMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class BowyeryRecipeTransferHandler implements IRecipeTransferHandler<BowyeryTableMenu, BowyeryRecipe> {
    private final IRecipeTransferHandlerHelper helper;

    public BowyeryRecipeTransferHandler(IRecipeTransferHandlerHelper helper) {
        this.helper = helper;
    }

    @Override
    public Class<? extends BowyeryTableMenu> getContainerClass() {
        return BowyeryTableMenu.class;
    }

    @Override
    public Optional<MenuType<BowyeryTableMenu>> getMenuType() {
        return Optional.of(ModMenuTypes.BOWYERY_TABLE_MENU.get());
    }

    @Override
    public RecipeType<BowyeryRecipe> getRecipeType() {
        return BowyeryRecipeCategory.BOWYERY_TYPE;
    }

    @Override
    public @Nullable IRecipeTransferError transferRecipe(BowyeryTableMenu container, BowyeryRecipe recipe,
                                                         IRecipeSlotsView recipeSlots, Player player,
                                                         boolean maxTransfer, boolean doTransfer) {
        List<Ingredient> ingredients = recipe.getIngredients();
        List<Integer> missingSlots = new ArrayList<>();

        // For each ingredient index, store all matching inventory slots in order
        Map<Integer, List<Integer>> ingredientToSlots = new HashMap<>();
        Map<Integer, Integer> consumedList = new HashMap<>();

        for (int ingredientIndex = 0; ingredientIndex < ingredients.size(); ingredientIndex++) {
            Ingredient ingredient = ingredients.get(ingredientIndex);
            List<Integer> matchingSlots = new ArrayList<>();

            for (int playerSlot = 0; playerSlot < player.getInventory().getContainerSize(); playerSlot++) {
                ItemStack stack = player.getInventory().getItem(playerSlot);
                int consumed = consumedList.getOrDefault(playerSlot, 0);
                if (ingredient.test(stack) && stack.getCount() > consumed) {
                    matchingSlots.add(playerSlot);
                    consumedList.put(playerSlot, consumed + 1); // claim one item from this slot
                }
            }

            if (matchingSlots.isEmpty()) {
                missingSlots.add(ingredientIndex);
            } else {
                ingredientToSlots.put(ingredientIndex, matchingSlots);
            }
        }
        if (!missingSlots.isEmpty()) {
            List<IRecipeSlotView> allSlotViews = recipeSlots.getSlotViews(RecipeIngredientRole.INPUT);
            List<IRecipeSlotView> missingSlotViews = missingSlots.stream().map(allSlotViews::get).toList();
            return helper.createUserErrorForMissingSlots(
                    Component.translatable("jei.tooltip.error.recipe.transfer.missing"),
                    missingSlotViews);
        }

        if (doTransfer) {
            int craftCount = maxTransfer ? getMaxCraftCount(ingredients, ingredientToSlots, player, container) : 1;
            MMM.LOGGER.debug("Craft count: {}, maxTransfer: {}", craftCount, maxTransfer);

            List<Integer> packetData = new ArrayList<>();
            Map<Integer, Integer> sharedConsumed = new HashMap<>(); // shared across all ingredients

            for (int ingredientIndex = 0; ingredientIndex < ingredients.size(); ingredientIndex++) {
                List<Integer> slots = ingredientToSlots.get(ingredientIndex);
                int remaining = craftCount;

                for (int slot : slots) {
                    if (remaining <= 0) break;
                    ItemStack stack = player.getInventory().getItem(slot);
                    int alreadyTaking = sharedConsumed.getOrDefault(slot, 0);
                    int available = stack.getCount() - alreadyTaking;
                    if (available <= 0) continue;
                    int taking = Math.min(available, remaining);
                    sharedConsumed.put(slot, alreadyTaking + taking);
                    packetData.add(ingredientIndex);
                    packetData.add(slot);
                    packetData.add(taking);
                    remaining -= taking;
                }
            }

            ModNetwork.CHANNEL.sendToServer(new TransferBowyeryRecipePacket(packetData));
        }
        return null;
    }

    private int getMaxCraftCount(List<Ingredient> ingredients, Map<Integer, List<Integer>> ingredientToSlots,
                                 Player player, BowyeryTableMenu container) {
        int maxCount = Integer.MAX_VALUE;

        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient ingredient = ingredients.get(i);
            List<Integer> slots = ingredientToSlots.get(i);

            // Total available across all matching stacks
            int totalAvailable = slots.stream()
                    .mapToInt(slot -> player.getInventory().getItem(slot).getCount())
                    .sum();

            // Add space from existing matching items in the menu slot
            ItemStack menuStack = container.getSlot(i).getItem();
            if (!menuStack.isEmpty() && ingredient.test(menuStack)) {
                totalAvailable += menuStack.getMaxStackSize() - menuStack.getCount();
            }

            int timesRequired = (int) ingredients.stream().filter(o -> ingredientsMatch(ingredient, o)).count();
            if (totalAvailable == 0) return 0;
            maxCount = Math.min(maxCount, totalAvailable / timesRequired);
        }

        return maxCount == Integer.MAX_VALUE ? 1 : maxCount;
    }

    private boolean ingredientsMatch(Ingredient a, Ingredient b) {
        Set<Item> aItems = Arrays.stream(a.getItems())
                .map(ItemStack::getItem)
                .collect(Collectors.toSet());
        Set<Item> bItems = Arrays.stream(b.getItems())
                .map(ItemStack::getItem)
                .collect(Collectors.toSet());
        return aItems.equals(bItems);
    }
}
