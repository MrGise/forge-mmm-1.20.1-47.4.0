package net.MrGise.mmm.compat.jei.bowyery;

import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import net.MrGise.mmm.network.ModNetwork;
import net.MrGise.mmm.network.compat.TransferBowyeryRecipePacket;
import net.MrGise.mmm.recipe.BowyeryRecipe;
import net.MrGise.mmm.registry.variables.ModMenuTypes;
import net.MrGise.mmm.screen.bowyery_table.BowyeryTableMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.*;

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
        List<Integer> availableSlots = new ArrayList<>();
        List<Integer> missingSlots = new ArrayList<>();

        Map<Integer, Integer> consumed = new HashMap<>();

        int loop = 0;
        for (Ingredient ingredient : ingredients) {
            boolean found = false;
            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ItemStack stack = player.getInventory().getItem(i);
                int used = consumed.getOrDefault(i, 0);

                if (ingredient.test(stack) && stack.getCount() > used) {
                    consumed.put(i, used + 1);
                    availableSlots.add(i);
                    found = true;
                    break;
                }
            }
            if (!found) {
                missingSlots.add(loop);
            }
            loop++;
        }

        if (!missingSlots.isEmpty()) {
            List<IRecipeSlotView> allSlotViews = recipeSlots.getSlotViews(RecipeIngredientRole.INPUT);
            List<IRecipeSlotView> missingSlotViews = missingSlots.stream().map(allSlotViews::get).toList();
            return helper.createUserErrorForMissingSlots(Component.translatable("jei.tooltip.error.recipe.transfer.missing"),
                    missingSlotViews);
        }
        if (doTransfer) {
            ModNetwork.CHANNEL.sendToServer(new TransferBowyeryRecipePacket(availableSlots));
        }
        return null;
    }
}
