package net.MrGise.mmm.compat.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.MrGise.mmm.recipe.BowyeryRecipe;
import net.MrGise.mmm.registry.content.ModBlocks;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import static net.MrGise.mmm.util.Methods.*;

public class BowyeryRecipeCategory implements IRecipeCategory<BowyeryRecipe> {
    private static final ResourceLocation UID = mmm("bowyery");
    private static final ResourceLocation TEXTURE = mmm("textures/gui/bowyery_table/bowyery_table_jei_gui.png");

    public static final RecipeType<BowyeryRecipe> BOWYERY_TYPE =
            new RecipeType(UID, BowyeryRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public BowyeryRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 83);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.BOWYERY_TABLE.get()));
    }

    @Override
    public RecipeType<BowyeryRecipe> getRecipeType() {
        return BOWYERY_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("container.mmm.bowyery");
    }

    @Override
    public @Nullable IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BowyeryRecipe recipe, IFocusGroup focuses) {
        builder.addInputSlot(26, 26).addIngredients(recipe.getIngredients().get(0));
        builder.addInputSlot(39, 55).addIngredients(recipe.getIngredients().get(1));
        builder.addInputSlot(57, 30).addIngredients(recipe.getIngredients().get(2));

        builder.addOutputSlot(116, 26).addItemStack(recipe.getResultItem(null));
    }
}
