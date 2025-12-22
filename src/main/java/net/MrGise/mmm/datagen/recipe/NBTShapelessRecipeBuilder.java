package net.MrGise.mmm.datagen.recipe;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.List;
import java.util.function.Consumer;

public class NBTShapelessRecipeBuilder extends ShapelessRecipeBuilder {

    private final RecipeCategory category;
    private final ItemStack result;
    private final List<Ingredient> ingredients = Lists.newArrayList();

    public NBTShapelessRecipeBuilder(RecipeCategory pCategory, ItemStack pResult, int pCount) {
        super(pCategory, pResult.getItem(), pCount);
        this.category = pCategory;
        this.result = pResult;
    }

    public static NBTShapelessRecipeBuilder shapeless(RecipeCategory pCategory, ItemStack pResult) {
        return new NBTShapelessRecipeBuilder(pCategory, pResult, 1);
    }

    public static NBTShapelessRecipeBuilder shapeless(RecipeCategory pCategory, ItemStack pResult, int pCount) {
        return new NBTShapelessRecipeBuilder(pCategory, pResult, pCount);
    }

    public NBTShapelessRecipeBuilder requires(Ingredient ingredient) {
        ingredients.add(ingredient);
        return this;
    }

    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation id) {
        pFinishedRecipeConsumer.accept(new FinishedRecipe() {
            @Override
            public void serializeRecipeData(JsonObject json) {
                JsonArray ingredientsJson = new JsonArray();
                for (Ingredient ing : ingredients) {
                    ingredientsJson.add(ing.toJson());
                }
                json.add("ingredients", ingredientsJson);
                json.addProperty("type", "minecraft:crafting_shapeless");

                JsonObject resultJson = new JsonObject();
                resultJson.addProperty("item", net.minecraftforge.registries.ForgeRegistries.ITEMS.getKey(result.getItem()).toString());
                if (result.getCount() > 1) {
                    resultJson.addProperty("count", result.getCount());
                }
                if (result.hasTag()) {
                    CompoundTag tag = result.getTag();

                    // Convert tag to SNBT and then parse as JSON
                    try {
                        String snbt = tag.toString(); // NBT to SNBT
                        JsonElement nbtJson = JsonParser.parseString(snbt.replace('\'', '"'));
                        resultJson.add("nbt", nbtJson);
                    } catch (Exception e) {
                        throw new IllegalStateException("Failed to convert NBT to JSON: " + e.getMessage(), e);
                    }
                }
                json.add("result", resultJson);
            }

            @Override
            public ResourceLocation getId() {
                return id;
            }

            @Override
            public RecipeSerializer<?> getType() {
                return RecipeSerializer.SHAPELESS_RECIPE;
            }

            @Override
            public JsonObject serializeAdvancement() {
                return null;
            }

            @Override
            public ResourceLocation getAdvancementId() {
                return id;
            }
        });
    }
}
