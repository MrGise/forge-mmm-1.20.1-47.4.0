package net.MrGise.mmm.compat.jei.thingamajig;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.MrGise.mmm.recipe.ThingamajigRecipe;
import net.MrGise.mmm.registry.content.ModBlocks;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import static net.MrGise.mmm.util.Methods.*;

public class ThingamajigRecipeCategory implements IRecipeCategory<ThingamajigRecipe> {
    private static final ResourceLocation UID = mmm("thingamajig");
    private static final ResourceLocation TEXTURE = mmm("textures/gui/thingamajig/thingamajig_jei_gui.png");

    public static final RecipeType<ThingamajigRecipe> THINGAMAJIG_TYPE =
            new RecipeType(UID, ThingamajigRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable arrow;

    public ThingamajigRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 90);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.THINGAMAJIG.get()));

        IDrawableStatic arrowStatic = helper.createDrawable(TEXTURE, 176, 0, 8, 26);
        this.arrow = helper.createAnimatedDrawable(arrowStatic, 200, IDrawableAnimated.StartDirection.TOP, false);
    }

    @Override
    public void draw(ThingamajigRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        arrow.draw(graphics, 85, 36);
    }

    @Override
    public RecipeType<ThingamajigRecipe> getRecipeType() {
        return THINGAMAJIG_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.mmm.thingamajig");
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
    public void setRecipe(IRecipeLayoutBuilder builder, ThingamajigRecipe recipe, IFocusGroup focuses) {
        builder.addInputSlot(80, 17).addIngredients(recipe.getIngredients().get(0));

        builder.addOutputSlot(80, 65).addItemStack(recipe.getResultItem(null));
    }
}
