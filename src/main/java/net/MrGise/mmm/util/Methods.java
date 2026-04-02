package net.MrGise.mmm.util;

import com.simibubi.create.Create;
import net.MrGise.mmm.MMM;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;

import java.util.function.Supplier;

public class Methods {

    public static ResourceLocation nAp(String namespace, String path) {
        return new ResourceLocation(namespace, path);
    }

    public static ResourceLocation mmm(String path) {
        return nAp(MMM.MOD_ID, path);
    }

    public static ResourceLocation crt(String path) {
        return nAp(Create.ID, path);
    }

    public static ResourceLocation mcr(String path) {
        return new ResourceLocation(path);
    }

    public static FoodProperties basicFoodProperty(int nutrition, float saturation) {
        return new FoodProperties.Builder().nutrition(nutrition).saturationMod(saturation).build();
    }

    public static <T> Supplier<T> s(T s) {
        return () -> s;
    }
}
