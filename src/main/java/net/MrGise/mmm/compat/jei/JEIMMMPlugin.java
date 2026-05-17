package net.MrGise.mmm.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;
import net.MrGise.mmm.MMM;
import net.MrGise.mmm.recipe.BowyeryRecipe;
import net.MrGise.mmm.registry.content.ModBlocks;
import net.MrGise.mmm.registry.variables.ModMenuTypes;
import net.MrGise.mmm.resource.ModRecipeTypes;
import net.MrGise.mmm.screen.bowyery_table.BowyeryTableMenu;
import net.MrGise.mmm.screen.bowyery_table.BowyeryTableScreen;
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
        registration.addRecipeCategories(new BowyeryRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();

        List<BowyeryRecipe> bowyeryRecipes = manager.getAllRecipesFor(BowyeryRecipe.Type.INSTANCE);
        registration.addRecipes(BowyeryRecipeCategory.BOWYERY_TYPE, bowyeryRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(BowyeryTableScreen.class, 84, 27, 22, 15,
                BowyeryRecipeCategory.BOWYERY_TYPE);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(BowyeryTableMenu.class, ModMenuTypes.BOWYERY_TABLE_MENU.get(), ModRecipeTypes.BOWYERY,
                0, 3, 3, 36);
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(ModBlocks.HEAVENLY_GRASS.get().asItem(), new CustomGrassSubtypeInterpreter());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(ModBlocks.BOWYERY_TABLE.get(), BowyeryRecipeCategory.BOWYERY_TYPE);
    }
}
