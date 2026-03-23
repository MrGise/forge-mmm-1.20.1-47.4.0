package net.MrGise.mmm.datagen.recipe.create;

import com.simibubi.create.AllTags;
import com.simibubi.create.api.data.recipe.FillingRecipeGen;
import net.MrGise.mmm.MMM;
import net.MrGise.mmm.registry.content.ModItems;
import net.minecraft.data.PackOutput;

public class MMMFillingRecipeGen extends FillingRecipeGen {

    GeneratedRecipe
    HONEYED_APPLE_SLICE = create("honeyed_apple_slice", builder ->
            builder.require(AllTags.AllFluidTags.HONEY.tag, 32).require(ModItems.APPLE_SLICE.get())
                    .output(ModItems.HONEYED_APPLE_SLICE.get()));


    public MMMFillingRecipeGen(PackOutput output) {
        super(output, MMM.MOD_ID);
    }
}
