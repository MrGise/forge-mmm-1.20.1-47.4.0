package net.MrGise.mmm.compat.jei.thingamajig;

import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import net.MrGise.mmm.network.ModNetwork;
import net.MrGise.mmm.network.compat.TransferThingamajigRecipePacket;
import net.MrGise.mmm.recipe.ThingamajigRecipe;
import net.MrGise.mmm.registry.variables.ModMenuTypes;
import net.MrGise.mmm.screen.thingamajig.ThingamajigMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ThingamajigRecipeTransferHandler implements IRecipeTransferHandler<ThingamajigMenu, ThingamajigRecipe> {
    private final IRecipeTransferHandlerHelper helper;

    public ThingamajigRecipeTransferHandler(IRecipeTransferHandlerHelper helper) {
        this.helper = helper;
    }

    @Override
    public Class<? extends ThingamajigMenu> getContainerClass() {
        return ThingamajigMenu.class;
    }

    @Override
    public Optional<MenuType<ThingamajigMenu>> getMenuType() {
        return Optional.of(ModMenuTypes.THINGAMAJIG_MENU.get());
    }

    @Override
    public RecipeType<ThingamajigRecipe> getRecipeType() {
        return ThingamajigRecipeCategory.THINGAMAJIG_TYPE;
    }

    @Override
    public @Nullable IRecipeTransferError transferRecipe(ThingamajigMenu container, ThingamajigRecipe recipe,
                                                         IRecipeSlotsView recipeSlots, Player player,
                                                         boolean maxTransfer, boolean doTransfer) {
        Ingredient ingredient = recipe.getIngredients().get(0);

        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            if (ingredient.test(player.getInventory().getItem(i))) {
                if (doTransfer) {
                    ModNetwork.CHANNEL.sendToServer(new TransferThingamajigRecipePacket(i));
                }
                return null;
            }
        }

        // No matching item found - show missing ingredients error
        return helper.createUserErrorForMissingSlots(
                Component.translatable("jei.tooltip.error.recipe.transfer.missing"),
                recipeSlots.getSlotViews(RecipeIngredientRole.INPUT)
        );
    }
}