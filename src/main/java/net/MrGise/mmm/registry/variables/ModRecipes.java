package net.MrGise.mmm.registry.variables;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.recipe.BowyeryRecipe;
import net.MrGise.mmm.recipe.ThingamajigRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

// Recipe registry
public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MMM.MOD_ID);


    public static final RegistryObject<RecipeSerializer<ThingamajigRecipe>> THINGAMAJIG_SERIALIZER =
            SERIALIZERS.register("thingamajig", () -> ThingamajigRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<BowyeryRecipe>> BOWYERY_SHAPED_SERIALIZER =
            SERIALIZERS.register("bowyery_shaped", () -> BowyeryRecipe.Serializer.INSTANCE);


    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
