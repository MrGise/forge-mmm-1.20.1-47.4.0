package net.MrGise.mmm.registry;

import net.minecraft.world.food.FoodProperties;

public class ModFoodProperties {

    public static final FoodProperties BREADSTICK = new FoodProperties.Builder().nutrition(3).saturationMod(0.33f).fast().build();

    public static final FoodProperties STRAWBERRY = new FoodProperties.Builder().nutrition(4).saturationMod(3.5f).build();
    public static final FoodProperties CUCUMBER = new FoodProperties.Builder().nutrition(4).saturationMod(5.3f).build();

}
