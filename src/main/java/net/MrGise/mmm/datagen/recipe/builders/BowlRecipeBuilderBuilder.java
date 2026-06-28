package net.MrGise.mmm.datagen.recipe.builders;

import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.Arrays;

public class BowlRecipeBuilderBuilder {
    private Item result;

    private NonNullList<Ingredient> ingredients;
    private FluidIngredient fluidIngredient;

    private int count;
    private Advancement.Builder advancement = Advancement.Builder.advancement();

    private int craftLength;

    public static BowlRecipeBuilderBuilder bowl(ItemLike result) {
        return new BowlRecipeBuilderBuilder().init(result, 1);
    }
    public static BowlRecipeBuilderBuilder bowl(ItemLike result, int count) {
        return new BowlRecipeBuilderBuilder().init(result, count);
    }

    private BowlRecipeBuilderBuilder init(ItemLike result, int count) {
        this.ingredients = NonNullList.create();
        this.fluidIngredient = FluidIngredient.EMPTY;
        this.craftLength = 1;
        this.result = result.asItem();
        this.count = count;
        return this;
    }

    public BowlRecipeBuilderBuilder ingredient(Ingredient ingredient) {
        checkIfInitiated();
        this.ingredients.add(ingredient);
        return this;
    }

    public BowlRecipeBuilderBuilder ingredients(Ingredient... ingredients) {
        checkIfInitiated();
        this.ingredients.addAll(Arrays.asList(ingredients));
        return this;
    }

    public BowlRecipeBuilderBuilder fluid(FluidIngredient fluidIngredient) {
        checkIfInitiated();
        this.fluidIngredient = fluidIngredient;
        return this;
    }

    public BowlRecipeBuilderBuilder craftLength(int craftLength) {
        checkIfInitiated();
        this.craftLength = craftLength;
        return this;
    }

    public BowlRecipeBuilder build() {
        checkIfInitiated();
        if (ingredients.isEmpty()) {
            throw new IllegalStateException("BowlRecipeBuilderBuilder: ingredients list cannot be empty");
        }
        return new BowlRecipeBuilder(this.result, this.count, this.fluidIngredient, this.craftLength, this.ingredients);
    }


    private void checkIfInitiated() {
        if (result == null) {
            throw new IllegalStateException("Recipe must be initiated (with BowlRecipeBuilderBuilder.bowl(ItemLike)) before any other changes are made.");
        }
    }
}
