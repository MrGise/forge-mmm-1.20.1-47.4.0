package net.MrGise.floating.helper;

import net.MrGise.floating.ModIDs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class Methods {

    public static ResourceLocation nAp(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }

    public static ResourceLocation mmm(String path) {
        return nAp(ModIDs.mmm(), path);
    }

    public static ResourceLocation crt(String path) {
        return nAp(ModIDs.create(), path);
    }

    public static ResourceLocation mcr(String path) {
        return ResourceLocation.withDefaultNamespace(path);
    }

    public static ResourceLocation forge(String path) {
        return nAp(ModIDs.forge(), path);
    }

    public static FoodProperties basicFoodProperty(int nutrition, float saturation) {
        return new FoodProperties.Builder().nutrition(nutrition).saturationMod(saturation).build();
    }

    public static String name(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block).getPath();
    }

    public static String name(Item item) {
        return ForgeRegistries.ITEMS.getKey(item).getPath();
    }

    public static <T> Supplier<T> s(T s) {
        return () -> s;
    }
}
