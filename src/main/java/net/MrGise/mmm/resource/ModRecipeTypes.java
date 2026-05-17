package net.MrGise.mmm.resource;

import mezz.jei.api.recipe.RecipeType;
import net.MrGise.mmm.MMM;
import net.MrGise.mmm.recipe.BowyeryRecipe;

public class ModRecipeTypes {
    public static final RecipeType<BowyeryRecipe> BOWYERY =
            RecipeType.create(MMM.MOD_ID, "bowyery", BowyeryRecipe.class);
}
