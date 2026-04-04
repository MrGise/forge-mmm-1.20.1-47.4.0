package net.MrGise.mmm.registry.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.MrGise.mmm.MMM;
import net.MrGise.mmm.compat.jei.ThingamajigRecipeCategory;
import net.MrGise.mmm.recipe.ThingamajigRecipe;
import net.MrGise.mmm.screen.thingamajig.ThingamajigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIMMMPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(MMM.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new ThingamajigRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();

        List<ThingamajigRecipe> thingamajigRecipes = manager.getAllRecipesFor(ThingamajigRecipe.Type.INSTANCE);
        registration.addRecipes(ThingamajigRecipeCategory.THINGAMAJIG_TYPE, thingamajigRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(ThingamajigScreen.class, 85, 32, 6, 26,
                ThingamajigRecipeCategory.THINGAMAJIG_TYPE);
    }
}
