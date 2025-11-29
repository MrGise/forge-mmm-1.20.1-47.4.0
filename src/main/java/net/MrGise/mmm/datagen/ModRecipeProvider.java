package net.MrGise.mmm.datagen;

import com.simibubi.create.AllItems;
import net.MrGise.mmm.MMM;
import net.MrGise.mmm.registry.front.ModBlocks;
import net.MrGise.mmm.datagen.recipe.NBTSingularShapelessRecipeBuilder;
import net.MrGise.mmm.registry.front.item.ModItems;
import net.MrGise.mmm.registry.back.ModTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import vectorwing.farmersdelight.common.tag.ForgeTags;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

// Generates recipes
public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public static final List<ItemLike> SKIRON_SMELTABLES = List.of(ModBlocks.SKIRON_ORE.get(), ModItems.RAW_SKIRON.get());
    public static final List<ItemLike> SKOAL_SMELTABLES = List.of(ModBlocks.SKOAL_ORE.get());

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        //Recipes here

        shapedRecipe(pWriter, RecipeCategory.MISC, ModItems.GOLD_KEY.get(), Map.of('G', Ingredient.of(Tags.Items.INGOTS_GOLD)), Items.GOLD_INGOT, "  G", "GGG", "GG ");

        cuttingRecipe(pWriter, Ingredient.of(ModTags.Items.CUCUMBERS), ModItems.CUT_CUCUMBER.get(), Ingredient.of(ForgeTags.TOOLS_KNIVES),
                1, 1.0f, "cucumber_cutting",
                new resultWithChance(ModItems.CUCUMBER_SEEDS.get(), 1, 0.1f));

        cuttingRecipe(pWriter, Ingredient.of(ModTags.Items.POMEGRANATES), ModItems.POMEGRANATE_SLICE.get(), Ingredient.of(ForgeTags.TOOLS_KNIVES),
                2, 1.0f, "pomegranate_cutting");

        cuttingRecipe(pWriter, Ingredient.of(Items.APPLE), ModItems.APPLE_SLICE.get(), Ingredient.of(ForgeTags.TOOLS_KNIVES),
                8, 1.0f, "apple_slicing");
        cuttingRecipe(pWriter, Ingredient.of(AllItems.HONEYED_APPLE), ModItems.HONEYED_APPLE_SLICE.get(), Ingredient.of(ForgeTags.TOOLS_KNIVES),
                8, 1.0f, "honeyed_apple_slicing");

        exchangeRecipe(pWriter, RecipeCategory.FOOD, Ingredient.of(ModTags.Items.POMEGRANATE_SLICES), ModItems.POMEGRANATE.get(), ModItems.POMEGRANATE_SEEDS.get(), 16);

        exchangeRecipe(pWriter, RecipeCategory.FOOD, Ingredient.of(ModTags.Items.CUCUMBERS), ModItems.CUCUMBER.get(), ModItems.CUCUMBER_SEEDS.get());
        exchangeRecipe(pWriter, RecipeCategory.FOOD, Ingredient.of(ModTags.Items.STRAWBERRIES), ModItems.STRAWBERRY.get(), ModItems.STRAWBERRY_SEEDS.get());

        swordRecipe(pWriter, RecipeCategory.COMBAT, Ingredient.of(ModTags.Items.ACTINOLITE), null, ModItems.ACTIONLITE_SWORD.get(), ModItems.ACTINOLITE.get(), "sky_ores");
        pickaxeRecipe(pWriter, RecipeCategory.COMBAT, Ingredient.of(ModTags.Items.ACTINOLITE), null, ModItems.ACTIONLITE_PICKAXE.get(), ModItems.ACTINOLITE.get(), "sky_ores");


        swordRecipe(pWriter, RecipeCategory.COMBAT, Ingredient.of(ModTags.Items.SKIRON_INGOTS), null, ModItems.SKIRON_SWORD.get(), ModItems.SKIRON.get(), "sky_ores");
        pickaxeRecipe(pWriter, RecipeCategory.TOOLS, Ingredient.of(ModTags.Items.SKIRON_INGOTS), null, ModItems.SKIRON_PICKAXE.get(), ModItems.SKIRON.get(), "sky_ores");
        axeRecipe(pWriter, RecipeCategory.COMBAT, Ingredient.of(ModTags.Items.SKIRON_INGOTS), null, ModItems.SKIRON_AXE.get(), ModItems.SKIRON.get(), "sky_ores");
        shovelRecipe(pWriter, RecipeCategory.TOOLS, Ingredient.of(ModTags.Items.SKIRON_INGOTS), null, ModItems.SKIRON_SHOVEL.get(), ModItems.SKIRON.get(), "sky_ores");
        hoeRecipe(pWriter, RecipeCategory.TOOLS, Ingredient.of(ModTags.Items.SKIRON_INGOTS), null, ModItems.SKIRON_HOE.get(), ModItems.SKIRON.get(), "sky_ores");
        paxelRecipe(pWriter, RecipeCategory.TOOLS, Ingredient.of(ModItems.SKIRON_PICKAXE.get()), Ingredient.of(ModItems.SKIRON_AXE.get()), Ingredient.of(ModItems.SKIRON_SHOVEL.get()), Ingredient.of(ModItems.SKIRON_HOE.get()), null, ModItems.SKIRON.get(), ModItems.SKIRON_PAXEL.get(), "sky_ores");
        hammerRecipe(pWriter, RecipeCategory.TOOLS, Ingredient.of(ModTags.Items.SKIRON_BLOCKS), ModBlocks.SKIRON_BLOCK.get(), null, ModItems.SKIRON_HAMMER.get(), "sky_ores");

        armorRecipes(pWriter, RecipeCategory.COMBAT, Ingredient.of(ModTags.Items.SKIRON_INGOTS), ModItems.SKIRON.get(), ModItems.SKIRON_HELMET.get(), ModItems.SKIRON_CHESTPLATE.get(), ModItems.SKIRON_LEGGINGS.get(), ModItems.SKIRON_BOOTS.get(), "sky_ores");

        imbuedArmorRecipes(pWriter, RecipeCategory.COMBAT, Ingredient.of(ModTags.Items.ACTINOLITE), ModItems.ACTINOLITE.get(),
                ModItems.SKIRON_HELMET.get(), ModItems.SKIRON_CHESTPLATE.get(), ModItems.SKIRON_LEGGINGS.get(), ModItems.SKIRON_BOOTS.get(),
                ModItems.SKIRON_ACTINOLITE_HELMET.get(), ModItems.SKIRON_ACTINOLITE_CHESTPLATE.get(), ModItems.SKIRON_ACTINOLITE_LEGGINGS.get(), ModItems.SKIRON_ACTINOLITE_BOOTS.get(), "sky_ores");

        //Region Mimics
        mimicDisguise("carrot", pWriter, RecipeCategory.MISC, ModItems.MIMIC.get(), Items.CARROT, ModItems.MIMIC.get(), "mimic_carrot");

        mimicDisguise("chest", pWriter, RecipeCategory.MISC, ModItems.MIMIC.get(), Blocks.CHEST.asItem(), ModBlocks.MIMIC_BLOCK.get(), "mimic_chest");
        mimicDisguise("shulker_box", pWriter, RecipeCategory.MISC, ModItems.MIMIC.get(), Blocks.SHULKER_BOX.asItem(), ModBlocks.MIMIC_BLOCK.get(), "mimic_shulker_box");
        //End

        //-- Trims
        trimSmithing(pWriter, ModItems.GLIDE_ARMOR_TRIM_SMITHING_TEMPLATE.get(), new ResourceLocation(MMM.MOD_ID, "glide_armor_trim"));

        copySmithingTemplate(pWriter, ModItems.GLIDE_ARMOR_TRIM_SMITHING_TEMPLATE.get(), ModBlocks.BROKEN_SKYSOLID.get());

        // Nine-to-one ratio
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItems.SKOAL.get(), RecipeCategory.MISC, ModBlocks.SKOAL_BLOCK.get(),
                "mmm:skoal_block", "sky_ores", "mmm:skoal", null);

        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItems.SKIRON.get(), RecipeCategory.MISC, ModBlocks.SKIRON_BLOCK.get(),
                "mmm:skiron_block", "sky_ores", "mmm:skiron", null);

        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItems.RAW_SKIRON.get(), RecipeCategory.MISC, ModBlocks.RAW_SKIRON_BLOCK.get(),
                "mmm:raw_skiron_block", "sky_ores", "mmm:raw_skiron", null);

        nineItemIngotRecipes(pWriter, RecipeCategory.MISC, ModItems.SKIRON_NUGGET.get(), Ingredient.of(ModTags.Items.SKIRON_NUGGETS),
                RecipeCategory.MISC, ModItems.SKIRON.get(), Ingredient.of(ModTags.Items.SKIRON_INGOTS),
                "mmm:skiron_nugget_from_skiron", "sky_ores",
                "mmm:skiron_from_nuggets", "sky_ores");


        //-- Smelting and stuff
        oreSmeltingAndBlasting(pWriter, SKIRON_SMELTABLES, RecipeCategory.MISC, ModItems.SKIRON.get(), 0.15f, 0.25f,
                200, 100, "sky_ores");

        oreSmeltingAndBlasting(pWriter, SKOAL_SMELTABLES, RecipeCategory.MISC, ModItems.SKOAL.get(), 0.25f, 0.5f,
                200, 100, "sky_ores");

        smelting(pWriter, Ingredient.of(ModBlocks.BROKEN_SKYSOLID.get()), RecipeCategory.MISC, ModBlocks.SKYSOLID.get(), ModBlocks.BROKEN_SKYSOLID.get(), 100, "skyland_misc");

        //-- Wood
        exchangeRecipe(pWriter, RecipeCategory.MISC, Ingredient.of(ModBlocks.SKYWOOD_LOG.get()), ModBlocks.SKYWOOD_LOG.get(), ModBlocks.SKYWOOD_PLANKS.get(), 4);
        exchangeRecipe(pWriter, RecipeCategory.MISC, Ingredient.of(ModBlocks.STRIPPED_SKYWOOD_LOG.get()), ModBlocks.STRIPPED_SKYWOOD_LOG.get(), ModBlocks.SKYWOOD_PLANKS.get(), 4);

        slab(pWriter, RecipeCategory.MISC, ModBlocks.SKYWOOD_SLAB.get(), ModBlocks.SKYWOOD_PLANKS.get());
        stairs(pWriter, RecipeCategory.MISC, ModBlocks.SKYWOOD_STAIRS.get(), Ingredient.of(ModBlocks.SKYWOOD_PLANKS.get()), ModBlocks.SKYWOOD_PLANKS.get());

    }

    protected static void mimicDisguise(String pForm, Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory, ItemLike pMimic, ItemLike pToForm, ItemLike pResultMimic, String pName) {
        ItemStack output = new ItemStack(pResultMimic);

        CompoundTag nbt = new CompoundTag();
        nbt.putString("form", pForm);
        output.setTag(nbt);

        NBTSingularShapelessRecipeBuilder.shapeless(pCategory, output)
                .requires(pMimic)
                .requires(pToForm)
                .save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID, pName));
    }

    //. useful

    protected static void cuttingRecipe(Consumer<FinishedRecipe> finishedRecipeConsumer,
                                        Ingredient ingredient, ItemLike result, Ingredient tool,
                                        int count, float chance, String name,
                                        resultWithChance... additionalResults) {
        CuttingBoardRecipeBuilder builder = CuttingBoardRecipeBuilder.cuttingRecipe(ingredient,
                tool, result, count, (int) chance);

        if (additionalResults.length != 0) {
            for (int i = 0; i < additionalResults.length; i++) {
                builder.addResultWithChance(additionalResults[i].result, additionalResults[i].chance, additionalResults[i].count);
            }
        }

        builder.build(finishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID, name));
    }

    protected static void shapedRecipe(Consumer<FinishedRecipe> finishedRecipeConsumer,
                                        RecipeCategory category,
                                        ItemLike result, Map<Character, Ingredient> ingredients,
                                        ItemLike unlockedBy,
                                        String... pattern) {
        ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(category, result);

        // Define ingredients
        for (Map.Entry<Character, Ingredient> entry : ingredients.entrySet()) {
            builder.define(entry.getKey(), entry.getValue());
        }

        // Add pattern lines (can be 1, 2, 3 or more)
        for (String line : pattern) {
            builder.pattern(line);
        }

        // Unlock condition based on first ingredient
        builder.unlockedBy(getHasName(unlockedBy), has(unlockedBy));

        // Save recipe
        builder.save(finishedRecipeConsumer);
    }

    protected static void exchangeRecipe(Consumer<FinishedRecipe> finishedRecipeConsumer, RecipeCategory category, Ingredient ingredient, ItemLike unlockedBy, ItemLike result) {
        exchangeRecipe(finishedRecipeConsumer, category, ingredient, unlockedBy, result, 1);
    }

    protected static void exchangeRecipe(Consumer<FinishedRecipe> finishedRecipeConsumer, RecipeCategory category,
                                            Ingredient ingredient, ItemLike unlockedBy, ItemLike result, int count) {
        ShapelessRecipeBuilder.shapeless(category, result, count).requires(ingredient)
                .unlockedBy(getHasName(unlockedBy), has(unlockedBy)).save(finishedRecipeConsumer,
                        new ResourceLocation(result.asItem().toString().toLowerCase() + "_from_" + unlockedBy.asItem().toString().toLowerCase()));
    }

    protected static void nineItemIngotRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pUnpackedCategory,
                                                ItemLike pUnpacked, Ingredient unpack, RecipeCategory pPackedCategory, ItemLike pPacked, Ingredient pack, String pPackedName, @javax.annotation.Nullable String pPackedGroup, String pUnpackedName, @javax.annotation.Nullable String pUnpackedGroup) {
        ShapelessRecipeBuilder.shapeless(pUnpackedCategory, pUnpacked, 9)
                .requires(pack).group(pUnpackedGroup).unlockedBy(getHasName(pPacked), has(pPacked))
                .save(pFinishedRecipeConsumer, new ResourceLocation(pUnpackedName));
        ShapedRecipeBuilder.shaped(pPackedCategory, pPacked).define('#', unpack)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .group(pPackedGroup).unlockedBy(getHasName(pUnpacked), has(pUnpacked)).save(pFinishedRecipeConsumer, new ResourceLocation(pPackedName));
    }

    protected static void imbuedArmorRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory,
                                                Ingredient pImbued, ItemLike unlockedBy, Item pHelmet, Item pChestplate, Item pLeggings, Item pBoots, Item pImbuedHelmet, Item pImbuedChestplate, Item pImbuedLeggings, Item pImbuedBoots, String pCollectiveGroup) {

        ShapelessRecipeBuilder.shapeless(pCategory, pImbuedHelmet).requires(pImbued).requires(pHelmet)
                .group(pCollectiveGroup).unlockedBy(getHasName(unlockedBy), has(unlockedBy)).unlockedBy(getHasName(pHelmet), has(pHelmet))
                .save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID + ":" + getItemName(pImbuedHelmet)));

        ShapelessRecipeBuilder.shapeless(pCategory, pImbuedChestplate).requires(pImbued).requires(pChestplate)
                .group(pCollectiveGroup).unlockedBy(getHasName(unlockedBy), has(unlockedBy)).unlockedBy(getHasName(pChestplate), has(pChestplate))
                .save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID + ":" + getItemName(pImbuedChestplate)));

        ShapelessRecipeBuilder.shapeless(pCategory, pImbuedLeggings).requires(pImbued).requires(pLeggings)
                .group(pCollectiveGroup).unlockedBy(getHasName(unlockedBy), has(unlockedBy)).unlockedBy(getHasName(pLeggings), has(pLeggings))
                .save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID + ":" + getItemName(pImbuedLeggings)));

        ShapelessRecipeBuilder.shapeless(pCategory, pImbuedBoots).requires(pImbued).requires(pBoots)
                .group(pCollectiveGroup).unlockedBy(getHasName(unlockedBy), has(unlockedBy)).unlockedBy(getHasName(pBoots), has(pBoots))
                .save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID + ":" + getItemName(pImbuedBoots)));

    }

    protected static void armorRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory,
                                        Ingredient pMaterial, ItemLike unlockedBy, Item pHelmet, Item pChestplate, Item pLeggings, Item pBoots, String pCollectiveGroup) {
        helmetRecipe(pFinishedRecipeConsumer, pCategory, pMaterial, unlockedBy, pHelmet, pCollectiveGroup);
        chestplateRecipe(pFinishedRecipeConsumer, pCategory, pMaterial, unlockedBy, pChestplate, pCollectiveGroup);
        leggingsRecipe(pFinishedRecipeConsumer, pCategory, pMaterial, unlockedBy, pLeggings, pCollectiveGroup);
        bootsRecipe(pFinishedRecipeConsumer, pCategory, pMaterial, unlockedBy, pBoots, pCollectiveGroup);

    }

    protected static void helmetRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory,
                                        Ingredient pMaterial, ItemLike unlockedBy, Item pHelmet, String pGroup) {
        ShapedRecipeBuilder.shaped(pCategory, pHelmet).define('#', pMaterial)
                .pattern("###")
                .pattern("# #")
                .group(pGroup).unlockedBy(getHasName(unlockedBy), has(unlockedBy)).save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID + ":" + getItemName(pHelmet)));
    }

    protected static void chestplateRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory,
                                            Ingredient pMaterial, ItemLike unlockedBy, Item pChestplate, String pGroup) {
        ShapedRecipeBuilder.shaped(pCategory, pChestplate).define('#', pMaterial)
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .group(pGroup).unlockedBy(getHasName(unlockedBy), has(unlockedBy)).save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID + ":" + getItemName(pChestplate)));
    }

    protected static void leggingsRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory,
                                            Ingredient pMaterial, ItemLike unlockedBy, Item pLeggings, String pGroup) {
        ShapedRecipeBuilder.shaped(pCategory, pLeggings).define('#', pMaterial)
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .group(pGroup).unlockedBy(getHasName(unlockedBy), has(unlockedBy)).save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID + ":" + getItemName(pLeggings)));
    }

    protected static void bootsRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory, Ingredient pMaterial, ItemLike unlockedBy, Item pBoots, String pGroup) {
        ShapedRecipeBuilder.shaped(pCategory, pBoots).define('#', pMaterial)
                .pattern("# #")
                .pattern("# #")
                .group(pGroup).unlockedBy(getHasName(unlockedBy), has(unlockedBy)).save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID + ":" + getItemName(pBoots)));
    }

    protected static void swordRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory,
                                        Ingredient pMaterial, Ingredient pStick, Item pResult, ItemLike unlockedBy, String pGroup) {
        if (pStick == null) {
            pStick = Ingredient.of(Items.STICK);
        }
        ShapedRecipeBuilder.shaped(pCategory, pResult).define('#', pMaterial).define('S', pStick)
                .pattern("#")
                .pattern("#")
                .pattern("S")
                .group(pGroup).unlockedBy(getHasName(unlockedBy), has(unlockedBy)).save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID + ":" + getItemName(pResult)));
    }

    protected static void pickaxeRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory,
                                        Ingredient pMaterial, Ingredient pStick, Item pResult, ItemLike unlockedBy, String pGroup) {
        if (pStick == null) {
            pStick = Ingredient.of(Items.STICK);
        }
        ShapedRecipeBuilder.shaped(pCategory, pResult).define('#', pMaterial).define('S', pStick)
                .pattern("###")
                .pattern(" S ")
                .pattern(" S ")
                .group(pGroup).unlockedBy(getHasName(unlockedBy), has(unlockedBy)).save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID + ":" + getItemName(pResult)));
    }

    protected static void axeRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory,
                                    Ingredient pMaterial, Ingredient pStick, Item pResult, ItemLike unlockedBy, String pGroup) {
        if (pStick == null) {
            pStick = Ingredient.of(Items.STICK);
        }
        ShapedRecipeBuilder.shaped(pCategory, pResult).define('#', pMaterial).define('S', pStick)
                .pattern("##")
                .pattern("S#")
                .pattern("S ")
                .group(pGroup).unlockedBy(getHasName(unlockedBy), has(unlockedBy)).save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID + ":" + getItemName(pResult) + "_right"));

        ShapedRecipeBuilder.shaped(pCategory, pResult).define('#', pMaterial).define('S', pStick)
                .pattern("##")
                .pattern("#S")
                .pattern(" S")
                .group(pGroup).unlockedBy(getHasName(unlockedBy), has(unlockedBy)).save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID + ":" + getItemName(pResult) + "_left"));
    }

    protected static void hoeRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory,
                                    Ingredient pMaterial, Ingredient pStick, Item pResult, ItemLike unlockedBy, String pGroup) {
        if (pStick == null) {
            pStick = Ingredient.of(Items.STICK);
        }
        ShapedRecipeBuilder.shaped(pCategory, pResult).define('#', pMaterial).define('S', pStick)
                .pattern("##")
                .pattern("S ")
                .pattern("S ")
                .group(pGroup).unlockedBy(getHasName(unlockedBy), has(unlockedBy)).save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID + ":" + getItemName(pResult) + "_right"));

        ShapedRecipeBuilder.shaped(pCategory, pResult).define('#', pMaterial).define('S', pStick)
                .pattern("##")
                .pattern(" S")
                .pattern(" S")
                .group(pGroup).unlockedBy(getHasName(unlockedBy), has(unlockedBy)).save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID + ":" + getItemName(pResult) + "_left"));
    }

    protected static void shovelRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory,
                                        Ingredient pMaterial, Ingredient pStick, Item pResult, ItemLike unlockedBy, String pGroup) {
        if (pStick == null) {
            pStick = Ingredient.of(Items.STICK);
        }
        ShapedRecipeBuilder.shaped(pCategory, pResult).define('#', pMaterial).define('S', pStick)
                .pattern("#")
                .pattern("S")
                .pattern("S")
                .group(pGroup).unlockedBy(getHasName(unlockedBy), has(unlockedBy))
                .save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID + ":" + getItemName(pResult)));
    }

    protected static void knifeRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory,
                                        Ingredient pMaterial, ItemLike unlockedBy, Ingredient pStick, Item pResult, String pGroup) {
        if (pStick == null) {
            pStick = Ingredient.of(Items.STICK);
        }
        ShapedRecipeBuilder.shaped(pCategory, pResult).define('#', pMaterial).define('S', pStick)
                .pattern("#")
                .pattern("S")
                .group(pGroup).unlockedBy(getHasName(unlockedBy), has(unlockedBy)).save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID + ":" + getItemName(pResult)));
    }

    protected static void hammerRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory,
                                        Ingredient pBlockItem, ItemLike pUnlockedBy, Ingredient pStick, Item pResult, String pGroup) {
        if (pStick == null) {
            pStick = Ingredient.of(Items.STICK);
        }
        ShapedRecipeBuilder.shaped(pCategory, pResult).define('#', pBlockItem).define('S', pStick)
                .pattern("###")
                .pattern(" S ")
                .pattern(" S ")
                .group(pGroup).unlockedBy(getHasName(pUnlockedBy), has(pUnlockedBy)).save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID + ":" + getItemName(pResult)));
    }

    protected static void paxelRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory,
                                        Ingredient pPickaxe, Ingredient pAxe, Ingredient pShovel, Ingredient pHoe, Ingredient pStick,
                                        ItemLike pUnlockedBy, Item pResult, String pGroup) {
        if (pStick == null) {
            pStick = Ingredient.of(Items.STICK);
        }
        ShapedRecipeBuilder.shaped(pCategory, pResult).define('A', pPickaxe).define('B', pAxe).define('C', pShovel).define('D', pHoe).define('S', pStick)
                .pattern("ACD")
                .pattern("BS ")
                .pattern(" S ")
                .group(pGroup).unlockedBy(getHasName(pUnlockedBy), has(pUnlockedBy)).save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID + ":" + getItemName(pResult) + "_right"));

        ShapedRecipeBuilder.shaped(pCategory, pResult).define('A', pPickaxe).define('B', pAxe).define('C', pShovel).define('D', pHoe).define('S', pStick)
                .pattern("DCA")
                .pattern(" SB")
                .pattern(" S ")
                .group(pGroup).unlockedBy(getHasName(pUnlockedBy), has(pUnlockedBy)).save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID + ":" + getItemName(pResult) + "_left"));
    }

    protected static void stairs(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory,
                                    ItemLike result, Ingredient pMaterial, ItemLike unlockedBy) {
        customStairBuilder(pCategory, result, pMaterial).unlockedBy(getHasName(unlockedBy), has(unlockedBy)).save(pFinishedRecipeConsumer);
    }

    protected static RecipeBuilder customStairBuilder(RecipeCategory pCategory, ItemLike pSlab, Ingredient pMaterial) {
        return ShapedRecipeBuilder.shaped(pCategory, pSlab, 6).define('#', pMaterial)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###");
    }

    protected static void smelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, Ingredient pIngredient, RecipeCategory pCategory, ItemLike pResult, ItemLike unlockedBy,
                                    int pCookingTIme, String pGroup) {
        cooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredient, pCategory, pResult, unlockedBy, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void blasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, Ingredient pIngredient, RecipeCategory pCategory, ItemLike pResult, ItemLike unlockedBy,
                                    int pCookingTIme, String pGroup) {
        cooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredient, pCategory, pResult, unlockedBy, pCookingTIme, pGroup, "_from_blasting");
    }

    protected static void smoking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, Ingredient pIngredient, RecipeCategory pCategory, ItemLike pResult, ItemLike unlockedBy,
                                    int pCookingTIme, String pGroup) {
        cooking(pFinishedRecipeConsumer, RecipeSerializer.SMOKING_RECIPE, pIngredient, pCategory, pResult, unlockedBy, pCookingTIme, pGroup, "_from_smoking");
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

    protected static void automaticOreSmeltingAndBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                        float pExperienceSmelting, float pExperienceBlasting, int pCookingTImeSmelting, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperienceSmelting, pCookingTImeSmelting, pGroup, "_from_smelting");
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperienceBlasting, pCookingTImeSmelting - 100, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer,
                                        List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        Iterator var9 = pIngredients.iterator();

        while(var9.hasNext()) {
            ItemLike itemlike = (ItemLike)var9.next();
            SimpleCookingRecipeBuilder.generic(Ingredient.of(new ItemLike[]{itemlike}), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer, MMM.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }

    }

    protected static void cooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer,
                                    Ingredient pIngredient, RecipeCategory pCategory, ItemLike pResult, ItemLike unlockedByAndName,
                                    int pCookingTime, String pGroup, String pRecipeName) {

            SimpleCookingRecipeBuilder.generic(pIngredient, pCategory, pResult, 0, pCookingTime, pCookingSerializer).group(pGroup).unlockedBy(getHasName(unlockedByAndName), has(unlockedByAndName))
                    .save(pFinishedRecipeConsumer, MMM.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(unlockedByAndName));

    }

    private record resultWithChance(ItemLike result, int count, float chance) {}

}
