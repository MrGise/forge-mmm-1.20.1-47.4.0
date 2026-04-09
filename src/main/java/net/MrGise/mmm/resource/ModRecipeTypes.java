package net.MrGise.mmm.resource;

import mezz.jei.api.recipe.RecipeType;
import net.MrGise.mmm.MMM;
import net.MrGise.mmm.recipe.BowyeryRecipe;
import net.MrGise.mmm.recipe.ThingamajigRecipe;

public class ModRecipeTypes {
    public static final RecipeType<ThingamajigRecipe> THINGAMAJIG =
            RecipeType.create(MMM.MOD_ID, "thingamajig", ThingamajigRecipe.class);

    public static final RecipeType<BowyeryRecipe> BOWYERY =
            RecipeType.create(MMM.MOD_ID, "bowyery", BowyeryRecipe.class);
}
