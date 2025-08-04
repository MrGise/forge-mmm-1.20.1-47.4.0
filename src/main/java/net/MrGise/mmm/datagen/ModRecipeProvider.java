package net.MrGise.mmm.datagen;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.block.ModBlocks;
import net.MrGise.mmm.datagen.recipe.NBTShapelessRecipeBuilder;
import net.MrGise.mmm.datagen.recipe.NBTSingularShapelessRecipeBuilder;
import net.MrGise.mmm.item.ModItems;
import net.MrGise.mmm.item.custom.TabletItem;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
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
        pickaxeRecipe(pWriter, RecipeCategory.COMBAT, ModItems.ACTINOLITE.get(), null, ModItems.ACTIONLITE_PICKAXE.get(), "sky_ores");

        swordRecipe(pWriter, RecipeCategory.COMBAT, ModItems.SKIRON.get(), null, ModItems.SKIRON_SWORD.get(), "sky_ores");
        pickaxeRecipe(pWriter, RecipeCategory.COMBAT, ModItems.SKIRON.get(), null, ModItems.SKIRON_PICKAXE.get(), "sky_ores");
        axeRecipe(pWriter, RecipeCategory.COMBAT, ModItems.SKIRON.get(), null, ModItems.SKIRON_AXE.get(), "sky_ores");
        shovelRecipe(pWriter, RecipeCategory.COMBAT, ModItems.SKIRON.get(), null, ModItems.SKIRON_SHOVEL.get(), "sky_ores");
        hoeRecipe(pWriter, RecipeCategory.COMBAT, ModItems.SKIRON.get(), null, ModItems.SKIRON_HOE.get(), "sky_ores");
        paxelRecipe(pWriter, RecipeCategory.MISC, ModItems.SKIRON_PICKAXE.get(), ModItems.SKIRON_AXE.get(), ModItems.SKIRON_SHOVEL.get(), ModItems.SKIRON_HOE.get(), null, ModItems.SKIRON.get(), ModItems.SKIRON_PAXEL.get(), "sky_ores");
        hammerRecipe(pWriter, RecipeCategory.COMBAT, ModBlocks.SKIRON_BLOCK.get(), null, ModItems.SKIRON_HAMMER.get(), "sky_ores");

        armorRecipes(pWriter, RecipeCategory.MISC, ModItems.SKIRON.get(), ModItems.SKIRON_HELMET.get(), ModItems.SKIRON_CHESTPLATE.get(), ModItems.SKIRON_LEGGINGS.get(), ModItems.SKIRON_BOOTS.get(), "sky_ores");

        imbuedArmorRecipes(pWriter, RecipeCategory.MISC, ModItems.ACTINOLITE.get(),
                ModItems.SKIRON_HELMET.get(), ModItems.SKIRON_CHESTPLATE.get(), ModItems.SKIRON_LEGGINGS.get(), ModItems.SKIRON_BOOTS.get(),
                ModItems.SKIRON_ACTINOLITE_HELMET.get(), ModItems.SKIRON_ACTINOLITE_CHESTPLATE.get(), ModItems.SKIRON_ACTINOLITE_LEGGINGS.get(), ModItems.SKIRON_ACTINOLITE_BOOTS.get(), "sky_ores");

        glyphRecipe("fire", pWriter, RecipeCategory.MISC, ModItems.SKYSOLID_TABLET.get(), (TabletItem) ModItems.SKYSOLID_TABLET.get(), Items.BLAZE_POWDER, "fire_glyph_recipe");

        mimicDisguise("chest", pWriter, RecipeCategory.MISC, ModItems.MIMIC.get(), Blocks.CHEST.asItem(), "mimic_chest");

        // Trims
        trimSmithing(pWriter, ModItems.GLIDE_ARMOR_TRIM_SMITHING_TEMPLATE.get(), new ResourceLocation(MMM.MOD_ID, "glide_armor_trim"));


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

    protected static void glyphRecipe(String pGlyph, Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory, ItemLike pResult, TabletItem tabletItem, ItemLike ingredient, String pName) {
        ItemStack output = new ItemStack(pResult);

        // Add your desired tag to the output NBT
        CompoundTag nbt = new CompoundTag();
        ListTag glyphs = new ListTag();
        glyphs.add(StringTag.valueOf(pGlyph));
        nbt.put("glyphs", glyphs);
        output.setTag(nbt);

        NBTShapelessRecipeBuilder.shapeless(pCategory, output)
                .requires(Ingredient.of(tabletItem))
                .requires(Ingredient.of(ingredient))
                .save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID, pName));
    }

    protected static void mimicDisguise(String pForm, Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory, ItemLike pMimic, ItemLike pToForm, String pName) {
        ItemStack output = new ItemStack(pMimic);

        CompoundTag nbt = new CompoundTag();
        nbt.putString("form", pForm);
        output.setTag(nbt);

        NBTSingularShapelessRecipeBuilder.shapeless(pCategory, output)
                .requires(pMimic)
                .requires(pToForm)
                .save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID, pName));

    }

    protected static void nineItemIngotRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pUnpackedCategory, ItemLike pUnpacked, RecipeCategory pPackedCategory, ItemLike pPacked, String pPackedName, @javax.annotation.Nullable String pPackedGroup, String pUnpackedName, @javax.annotation.Nullable String pUnpackedGroup) {
        ShapelessRecipeBuilder.shapeless(pUnpackedCategory, pUnpacked, 9).requires(pPacked).group(pUnpackedGroup).unlockedBy(getHasName(pPacked), has(pPacked)).save(pFinishedRecipeConsumer, new ResourceLocation(pUnpackedName));
        ShapedRecipeBuilder.shaped(pPackedCategory, pPacked).define('#', pUnpacked)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .group(pPackedGroup).unlockedBy(getHasName(pUnpacked), has(pUnpacked)).save(pFinishedRecipeConsumer, new ResourceLocation(pPackedName));
    }

    protected static void imbuedArmorRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory, ItemLike pImbued, Item pHelmet, Item pChestplate, Item pLeggings, Item pBoots, Item pImbuedHelmet, Item pImbuedChestplate, Item pImbuedLeggings, Item pImbuedBoots, String pCollectiveGroup) {

        ShapelessRecipeBuilder.shapeless(pCategory, pImbuedHelmet).requires(pImbued).requires(pHelmet)
                .group(pCollectiveGroup).unlockedBy(getHasName(pImbued), has(pImbued)).unlockedBy(getHasName(pHelmet), has(pHelmet))
                .save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID + ":" + getItemName(pImbuedHelmet)));

        ShapelessRecipeBuilder.shapeless(pCategory, pImbuedChestplate).requires(pImbued).requires(pChestplate)
                .group(pCollectiveGroup).unlockedBy(getHasName(pImbued), has(pImbued)).unlockedBy(getHasName(pChestplate), has(pChestplate))
                .save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID + ":" + getItemName(pImbuedChestplate)));

        ShapelessRecipeBuilder.shapeless(pCategory, pImbuedLeggings).requires(pImbued).requires(pLeggings)
                .group(pCollectiveGroup).unlockedBy(getHasName(pImbued), has(pImbued)).unlockedBy(getHasName(pLeggings), has(pLeggings))
                .save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID + ":" + getItemName(pImbuedLeggings)));

        ShapelessRecipeBuilder.shapeless(pCategory, pImbuedBoots).requires(pImbued).requires(pBoots)
                .group(pCollectiveGroup).unlockedBy(getHasName(pImbued), has(pImbued)).unlockedBy(getHasName(pBoots), has(pBoots))
                .save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID + ":" + getItemName(pImbuedBoots)));

    }

    protected static void armorRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory, ItemLike pMaterial, Item pHelmet, Item pChestplate, Item pLeggings, Item pBoots, String pCollectiveGroup) {
        helmetRecipe(pFinishedRecipeConsumer, pCategory, pMaterial, pHelmet, pCollectiveGroup);
        chestplateRecipe(pFinishedRecipeConsumer, pCategory, pMaterial, pChestplate, pCollectiveGroup);
        leggingsRecipe(pFinishedRecipeConsumer, pCategory, pMaterial, pLeggings, pCollectiveGroup);
        bootsRecipe(pFinishedRecipeConsumer, pCategory, pMaterial, pBoots, pCollectiveGroup);

    }

        protected static void helmetRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory, ItemLike pMaterial, Item pHelmet, String pGroup) {
        ShapedRecipeBuilder.shaped(pCategory, pHelmet).define('#', pMaterial)
                .pattern("###")
                .pattern("# #")
                .group(pGroup).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID + ":" + getItemName(pHelmet)));
    }

    protected static void chestplateRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory, ItemLike pMaterial, Item pChestplate, String pGroup) {
        ShapedRecipeBuilder.shaped(pCategory, pChestplate).define('#', pMaterial)
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .group(pGroup).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID + ":" + getItemName(pChestplate)));
    }

    protected static void leggingsRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory, ItemLike pMaterial, Item pLeggings, String pGroup) {
        ShapedRecipeBuilder.shaped(pCategory, pLeggings).define('#', pMaterial)
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .group(pGroup).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID + ":" + getItemName(pLeggings)));
    }

    protected static void bootsRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory, ItemLike pMaterial, Item pBoots, String pGroup) {
        ShapedRecipeBuilder.shaped(pCategory, pBoots).define('#', pMaterial)
                .pattern("# #")
                .pattern("# #")
                .group(pGroup).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID + ":" + getItemName(pBoots)));
    }

    protected static void swordRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory, ItemLike pMaterial, ItemLike pStick, Item pResult, String pGroup) {
        if (pStick == null) {
            pStick = Items.STICK;
        }
        ShapedRecipeBuilder.shaped(pCategory, pResult).define('#', pMaterial).define('S', pStick)
                .pattern("#")
                .pattern("#")
                .pattern("S")
                .group(pGroup).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID + ":" + getItemName(pResult)));
    }

    protected static void pickaxeRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory, ItemLike pMaterial, ItemLike pStick, Item pResult, String pGroup) {
        if (pStick == null) {
            pStick = Items.STICK;
        }
        ShapedRecipeBuilder.shaped(pCategory, pResult).define('#', pMaterial).define('S', pStick)
                .pattern("###")
                .pattern(" S ")
                .pattern(" S ")
                .group(pGroup).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID + ":" + getItemName(pResult)));
    }

    protected static void axeRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory, ItemLike pMaterial, ItemLike pStick, Item pResult, String pGroup) {
        if (pStick == null) {
            pStick = Items.STICK;
        }
        ShapedRecipeBuilder.shaped(pCategory, pResult).define('#', pMaterial).define('S', pStick)
                .pattern("##")
                .pattern("S#")
                .pattern("S ")
                .group(pGroup).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID + ":" + getItemName(pResult) + "_right"));

        ShapedRecipeBuilder.shaped(pCategory, pResult).define('#', pMaterial).define('S', pStick)
                .pattern("##")
                .pattern("#S")
                .pattern(" S")
                .group(pGroup).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID + ":" + getItemName(pResult) + "_left"));
    }
    protected static void hoeRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory, ItemLike pMaterial, ItemLike pStick, Item pResult, String pGroup) {
        if (pStick == null) {
            pStick = Items.STICK;
        }
        ShapedRecipeBuilder.shaped(pCategory, pResult).define('#', pMaterial).define('S', pStick)
                .pattern("##")
                .pattern("S ")
                .pattern("S ")
                .group(pGroup).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID + ":" + getItemName(pResult) + "_right"));

        ShapedRecipeBuilder.shaped(pCategory, pResult).define('#', pMaterial).define('S', pStick)
                .pattern("##")
                .pattern(" S")
                .pattern(" S")
                .group(pGroup).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID + ":" + getItemName(pResult) + "_left"));
    }

    protected static void shovelRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory, ItemLike pMaterial, ItemLike pStick, Item pResult, String pGroup) {
        if (pStick == null) {
            pStick = Items.STICK;
        }
        ShapedRecipeBuilder.shaped(pCategory, pResult).define('#', pMaterial).define('S', pStick)
                .pattern("#")
                .pattern("S")
                .pattern("S")
                .group(pGroup).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID + ":" + getItemName(pResult)));
    }

    protected static void hammerRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory, ItemLike pBlockItem, ItemLike pStick, Item pResult, String pGroup) {
        if (pStick == null) {
            pStick = Items.STICK;
        }
        ShapedRecipeBuilder.shaped(pCategory, pResult).define('#', pBlockItem).define('S', pStick)
                .pattern("###")
                .pattern(" S ")
                .pattern(" S ")
                .group(pGroup).unlockedBy(getHasName(pBlockItem), has(pBlockItem)).save(pFinishedRecipeConsumer, new ResourceLocation(MMM.MOD_ID + ":" + getItemName(pResult)));
    }

    protected static void paxelRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory, ItemLike pPickaxe, ItemLike pAxe, ItemLike pShovel, ItemLike pHoe, ItemLike pStick, ItemLike pUnlockedBy, Item pResult, String pGroup) {
        if (pStick == null) {
            pStick = Items.STICK;
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
