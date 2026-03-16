package net.MrGise.mmm.datagen.recipe.custom;

import com.google.gson.JsonObject;
import net.MrGise.mmm.MMM;
import net.MrGise.mmm.recipe.BowyeryRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class BowyeryRecipeBuilder implements RecipeBuilder {
    private final Item result;
    private final Ingredient bowSlot;
    private final Ingredient slot1;
    private final Ingredient slot2;
    private final int count;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public BowyeryRecipeBuilder(Ingredient bow, Ingredient slot1, Ingredient slot2, ItemLike result, int count) {
        this.bowSlot = bow;
        this.slot1 = slot1;
        this.slot2 = slot2;
        this.result = result.asItem();
        this.count = count;
    }

    public BowyeryRecipeBuilder(ItemLike bow, ItemLike slot1, ItemLike slot2, ItemLike result, int count) {
        this(Ingredient.of(bow), Ingredient.of(slot1), Ingredient.of(slot2), result, count);
    }

    @Override
    public RecipeBuilder unlockedBy(String criterionName, CriterionTriggerInstance criterionTrigger) {
        this.advancement.addCriterion(criterionName, criterionTrigger);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return result;
    }

    @Override
    public void save(Consumer<FinishedRecipe> finishedRecipeConsumer, ResourceLocation recipeId) {
        this.advancement.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId)).requirements(RequirementsStrategy.OR);

        finishedRecipeConsumer.accept(new Result(recipeId, this.result, this.count, this.bowSlot, this.slot1, this.slot2,
                this.advancement, new ResourceLocation(recipeId.getNamespace(), "recipes/"
                + recipeId.getPath())));

    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final Ingredient bow;
        private final Ingredient slot1;
        private final Ingredient slot2;
        private final int count;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation pId, Item pResult, int pCount,
                      Ingredient bow, Ingredient slot1, Ingredient slot2,
                      Advancement.Builder pAdvancement, ResourceLocation pAdvancementId) {
            this.id = pId;
            this.result = pResult;
            this.count = pCount;
            this.bow = bow;
            this.slot1 = slot1;
            this.slot2 = slot2;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            JsonObject ingredientsJson = new JsonObject();
            ingredientsJson.add("bow", bow.toJson());
            ingredientsJson.add("first", slot1.toJson());
            ingredientsJson.add("second", slot2.toJson());

            json.add("ingredients", ingredientsJson);
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("item", ForgeRegistries.ITEMS.getKey(this.result).toString());
            if (this.count > 1) {
                jsonobject.addProperty("count", this.count);
            }

            json.add("result", jsonobject);
        }

        @Override
        public ResourceLocation getId() {
            return new ResourceLocation(MMM.MOD_ID,
                    ForgeRegistries.ITEMS.getKey(this.result).getPath() + "_from_bowyery");
        }

        @Override
        public RecipeSerializer<?> getType() {
            return BowyeryRecipe.Serializer.INSTANCE;
        }

        @javax.annotation.Nullable
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @javax.annotation.Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
