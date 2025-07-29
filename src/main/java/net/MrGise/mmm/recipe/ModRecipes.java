package net.MrGise.mmm.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, "mmm");

    public static final RegistryObject<RecipeSerializer<GlyphInscribingRecipe>> GLYPH_INSCRIBING =
            SERIALIZERS.register("glyph_inscribing", () ->
                    new SimpleCraftingRecipeSerializer<>(GlyphInscribingRecipe::new)
            );

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}