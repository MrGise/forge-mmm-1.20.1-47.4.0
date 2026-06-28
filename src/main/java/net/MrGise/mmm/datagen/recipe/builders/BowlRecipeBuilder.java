package net.MrGise.mmm.datagen.recipe.builders;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.MrGise.mmm.block.entity.BowlBlockEntity;
import net.MrGise.mmm.recipe.BowlRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.function.Consumer;

import static net.MrGise.floating.helper.Methods.*;

public class BowlRecipeBuilder implements RecipeBuilder {
    private final Item result;

    private final NonNullList<Ingredient> ingredients;
    private final FluidIngredient fluidIngredient;

    private final int count;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    private final int craftLength;

    private String group;

    public BowlRecipeBuilder(ItemLike result, int count, FluidIngredient fluidIngredient, int craftLength, @NonNull Ingredient... ingredients) {
        if (ingredients.length == 0) {
            throw new IllegalArgumentException("Provided ingredients of bowlRecipe cannot be empty");
        }

        NonNullList<Ingredient> inputItems = NonNullList.create();
        inputItems.addAll(Arrays.asList(ingredients));

        this.result = result.asItem();
        this.ingredients = inputItems;
        this.fluidIngredient = fluidIngredient;
        this.count = count;

        this.craftLength = craftLength;
    }
    public BowlRecipeBuilder(ItemLike result, int count, int craftLength, @NonNull Ingredient... ingredients) {
        this(result, count, FluidIngredient.EMPTY, craftLength, ingredients);
    }

    public BowlRecipeBuilder(ItemLike result, int count, FluidIngredient fluidIngredient, int craftLength, @NonNull ItemLike... ingredients) {
        this(result, count, fluidIngredient, craftLength, Arrays.stream(ingredients).map(Ingredient::of).toArray(Ingredient[]::new));
    }
    public BowlRecipeBuilder(ItemLike result, int count, int craftLength, ItemLike... ingredients) {
        this(result, count, FluidIngredient.EMPTY, craftLength, ingredients);
    }

    public BowlRecipeBuilder(ItemLike result, int count, FluidIngredient fluidIngredient, int craftLength, NonNullList<Ingredient> ingredients) {
        this.result = result.asItem();
        this.ingredients = ingredients;
        this.fluidIngredient = fluidIngredient;
        this.count = count;

        this.craftLength = craftLength;
    }


    @Override
    public BowlRecipeBuilder unlockedBy(String criterionName, CriterionTriggerInstance criterionTrigger) {
        this.advancement.addCriterion(criterionName, criterionTrigger);
        return this;
    }

    @Override
    public BowlRecipeBuilder group(@Nullable String groupName) {
        this.group = groupName;
        return this;
    }

    @Override
    public Item getResult() {
        return this.result;
    }

    @Override
    public void save(Consumer<FinishedRecipe> writer, ResourceLocation id) {
        this.advancement.parent(mcr("recipes/root"))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id)).requirements(RequirementsStrategy.OR);

        writer.accept(new Result(id, this.ingredients, this.fluidIngredient, this.result, this.count, this.craftLength,
                this.advancement, nAp(id.getNamespace(), "recipes/" + id.getPath())));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final NonNullList<Ingredient> ingredients;
        private final FluidIngredient fluidIngredient;
        private final Item result;
        private final int count;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final int craftLength;

        public Result(ResourceLocation id, NonNullList<Ingredient> ingredients, FluidIngredient fluidIngredient,
                      Item result, int count, int craftLength, Advancement.Builder advancement, ResourceLocation advancementId) {
            this.id = id;
            this.ingredients = ingredients;
            this.fluidIngredient = fluidIngredient;
            this.result = result;
            this.count = count;
            this.craftLength = craftLength > 0 ? craftLength : 1;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        public Result(ResourceLocation id, NonNullList<Ingredient> ingredients,
                      Item result, int count, int craftLength, Advancement.Builder advancement, ResourceLocation advancementId) {
            this(id,ingredients, FluidIngredient.EMPTY, result, count, craftLength, advancement, advancementId);
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            JsonArray ingredientJson = new JsonArray();
            for (Ingredient ingredient : this.ingredients) {
                ingredientJson.add(ingredient.toJson());
            }

            int ingredientWeight = this.ingredients.stream().mapToInt(ingredient -> {
                int max = 0;
                for (ItemStack stack : ingredient.getItems()) {
                    max = Math.max(max, BowlBlockEntity.getSingleWeight(stack));
                }
                return max;
            }).sum();
            if (ingredientWeight > BowlBlockEntity.MAX_WEIGHT) {
                throw new JsonSyntaxException("Invalid ingredient maximum weight of {" + ingredientWeight + "} for " + this.ingredients);
            }

            json.add("ingredients", ingredientJson);

            if (this.fluidIngredient != null && !this.fluidIngredient.equals(FluidIngredient.EMPTY)) {
                json.add("fluid", this.fluidIngredient.serialize());
            }

            JsonObject resultJson = new JsonObject();
            resultJson.addProperty("item", ForgeRegistries.ITEMS.getKey(this.result).toString());
            if (this.count > 1) {
                resultJson.addProperty("count", this.count);
            }

            json.add("result", resultJson);

            json.addProperty("craft_length", this.craftLength);
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return BowlRecipe.Serializer.INSTANCE;
        }

        @Override
        public @Nullable JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Override
        public @Nullable ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
