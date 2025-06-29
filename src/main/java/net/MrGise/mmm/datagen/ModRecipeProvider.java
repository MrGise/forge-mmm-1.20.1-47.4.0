package net.MrGise.mmm.datagen;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.block.ModBlocks;
import net.MrGise.mmm.item.ModItems;
import net.MrGise.mmm.util.ModTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public static final List<ItemLike> SKIRON_SMELTABLES = List.of(ModBlocks.SKIRON_ORE.get(), ModItems.RAW_SKIRON.get());
    public static final List<ItemLike> SKOAL_SMELTABLES = List.of(ModBlocks.SKOAL_ORE.get());

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        //Recipes here

        swordRecipe(pWriter, RecipeCategory.COMBAT, ModItems.ACTINOLITE.get(), null, ModItems.ACTIONLITE_SWORD.get(), "sky_ores");

        swordRecipe(pWriter, RecipeCategory.COMBAT, ModItems.SKIRON.get(), null, ModItems.SKIRON_SWORD.get(), "sky_ores");
        pickaxeRecipe(pWriter, RecipeCategory.COMBAT, ModItems.SKIRON.get(), null, ModItems.SKIRON_PICKAXE.get(), "sky_ores");
        axeRecipe(pWriter, RecipeCategory.COMBAT, ModItems.SKIRON.get(), null, ModItems.SKIRON_AXE.get(), "sky_ores");
        shovelRecipe(pWriter, RecipeCategory.COMBAT, ModItems.SKIRON.get(), null, ModItems.SKIRON_SHOVEL.get(), "sky_ores");
        hoeRecipe(pWriter, RecipeCategory.COMBAT, ModItems.SKIRON.get(), null, ModItems.SKIRON_HOE.get(), "sky_ores");

        // Nine-to-one ratio
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItems.SKOAL.get(), RecipeCategory.MISC, ModBlocks.SKOAL_BLOCK.get(),
                "mmm:skoal_block", "sky_ores", "mmm:skoal", null);

        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItems.SKIRON.get(), RecipeCategory.MISC, ModBlocks.SKIRON_BLOCK.get(),
                "mmm:skiron_block", "sky_ores", "mmm:skiron", null);

        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItems.RAW_SKIRON.get(), RecipeCategory.MISC, ModBlocks.RAW_SKIRON_BLOCK.get(),
                "mmm:raw_skiron_block", "sky_ores", "mmm:raw_skiron", null);

        nineItemIngotRecipes(pWriter, RecipeCategory.MISC, ModItems.SKIRON_NUGGET.get(), RecipeCategory.MISC, ModItems.SKIRON.get(), "mmm:skiron_nugget_from_skiron", "sky_ores", "mmm:skiron_from_nuggets", "sky_ores");


        // Smelting and stuff
        oreSmeltingAndBlasting(pWriter, SKIRON_SMELTABLES, RecipeCategory.MISC, ModItems.SKIRON.get(), 0.15f, 0.25f,
                200, 100, "sky_ores");

        oreSmeltingAndBlasting(pWriter, SKOAL_SMELTABLES, RecipeCategory.MISC, ModItems.SKOAL.get(), 0.25f, 0.5f,
                200, 100, "sky_ores");


        // Wood
        slab(pWriter, RecipeCategory.MISC, ModBlocks.SKYWOOD_SLAB.get(), ModBlocks.SKYWOOD_PLANKS.get());
        stairs(pWriter, RecipeCategory.MISC, ModBlocks.SKYWOOD_STAIRS.get(), ModBlocks.SKYWOOD_PLANKS.get());

    }

    protected static void nineItemIngotRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pUnpackedCategory, ItemLike pUnpacked, RecipeCategory pPackedCategory, ItemLike pPacked, String pPackedName, @javax.annotation.Nullable String pPackedGroup, String pUnpackedName, @javax.annotation.Nullable String pUnpackedGroup) {
        ShapelessRecipeBuilder.shapeless(pUnpackedCategory, pUnpacked, 9).requires(pPacked).group(pUnpackedGroup).unlockedBy(getHasName(pPacked), has(pPacked)).save(pFinishedRecipeConsumer, new ResourceLocation(pUnpackedName));
        ShapedRecipeBuilder.shaped(pPackedCategory, pPacked).define('#', pUnpacked)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .group(pPackedGroup).unlockedBy(getHasName(pUnpacked), has(pUnpacked)).save(pFinishedRecipeConsumer, new ResourceLocation(pPackedName));
    }

    protected static void swordRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory, ItemLike pMaterial, ItemLike pStick, Item pResult, String pGroup) {
        if (pStick == null) {
            pStick = Items.STICK;
        }
        ShapedRecipeBuilder.shaped(pCategory, pResult).define('#', pMaterial).define('S', pStick)
                .pattern("#")
                .pattern("#")
                .pattern("S")
                .group(pGroup).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer, new ResourceLocation("mmm:" + getItemName(pResult)));
    }

    protected static void pickaxeRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory, ItemLike pMaterial, ItemLike pStick, Item pResult, String pGroup) {
        if (pStick == null) {
            pStick = Items.STICK;
        }
        ShapedRecipeBuilder.shaped(pCategory, pResult).define('#', pMaterial).define('S', pStick)
                .pattern("###")
                .pattern(" S ")
                .pattern(" S ")
                .group(pGroup).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer, new ResourceLocation("mmm:" + getItemName(pResult)));
    }

    protected static void axeRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory, ItemLike pMaterial, ItemLike pStick, Item pResult, String pGroup) {
        if (pStick == null) {
            pStick = Items.STICK;
        }
        ShapedRecipeBuilder.shaped(pCategory, pResult).define('#', pMaterial).define('S', pStick)
                .pattern("##")
                .pattern("S#")
                .pattern("S ")
                .group(pGroup).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer, new ResourceLocation("mmm:" + getItemName(pResult) + "_right"));

        ShapedRecipeBuilder.shaped(pCategory, pResult).define('#', pMaterial).define('S', pStick)
                .pattern("##")
                .pattern("#S")
                .pattern(" S")
                .group(pGroup).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer, new ResourceLocation("mmm:" + getItemName(pResult) + "_left"));
    }
    protected static void hoeRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory, ItemLike pMaterial, ItemLike pStick, Item pResult, String pGroup) {
        if (pStick == null) {
            pStick = Items.STICK;
        }
        ShapedRecipeBuilder.shaped(pCategory, pResult).define('#', pMaterial).define('S', pStick)
                .pattern("##")
                .pattern("S ")
                .pattern("S ")
                .group(pGroup).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer, new ResourceLocation("mmm:" + getItemName(pResult) + "_right"));

        ShapedRecipeBuilder.shaped(pCategory, pResult).define('#', pMaterial).define('S', pStick)
                .pattern("##")
                .pattern(" S")
                .pattern(" S")
                .group(pGroup).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer, new ResourceLocation("mmm:" + getItemName(pResult) + "_left"));
    }

    protected static void shovelRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory, ItemLike pMaterial, ItemLike pStick, Item pResult, String pGroup) {
        if (pStick == null) {
            pStick = Items.STICK;
        }
        ShapedRecipeBuilder.shaped(pCategory, pResult).define('#', pMaterial).define('S', pStick)
                .pattern("#")
                .pattern("S")
                .pattern("S")
                .group(pGroup).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer, new ResourceLocation("mmm:" + getItemName(pResult)));
    }

    protected static void stairs(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory, ItemLike pPressurePlate, ItemLike pMaterial) {
        customStairBuilder(pCategory, pPressurePlate, Ingredient.of(pMaterial)).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer);
    }

    protected static RecipeBuilder customStairBuilder(RecipeCategory pCategory, ItemLike pSlab, Ingredient pMaterial) {
        return ShapedRecipeBuilder.shaped(pCategory, pSlab, 6).define('#', pMaterial)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###");
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreSmeltingAndBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperienceSmelting, float pExperienceBlasting, int pCookingTImeSmelting, int pCookingTImeBlasting, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperienceSmelting, pCookingTImeSmelting, pGroup, "_from_smelting");
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperienceBlasting, pCookingTImeBlasting, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        Iterator var9 = pIngredients.iterator();

        while(var9.hasNext()) {
            ItemLike itemlike = (ItemLike)var9.next();
            SimpleCookingRecipeBuilder.generic(Ingredient.of(new ItemLike[]{itemlike}), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer, MMM.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }

    }

}
