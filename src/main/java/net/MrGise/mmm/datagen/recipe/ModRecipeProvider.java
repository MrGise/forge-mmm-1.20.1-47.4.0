package net.MrGise.mmm.datagen.recipe;

import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import com.simibubi.create.api.data.recipe.ProcessingRecipeGen;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.MrGise.mmm.MMM;
import net.MrGise.mmm.datagen.recipe.builders.BowlRecipeBuilder;
import net.MrGise.mmm.datagen.recipe.builders.BowlRecipeBuilderBuilder;
import net.MrGise.mmm.datagen.recipe.builders.BowyeryRecipeBuilder;
import net.MrGise.floating.datagen.builders.NBTSingularShapelessRecipeBuilder;
import net.MrGise.mmm.datagen.recipe.create.MMMFillingRecipeGen;
import net.MrGise.mmm.registry.content.ModBlocks;
import net.MrGise.mmm.registry.content.ModItems;
import net.MrGise.mmm.registry.variables.ModTags;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.StrictNBTIngredient;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.ForgeRegistries;
import org.checkerframework.checker.nullness.qual.NonNull;
import vectorwing.farmersdelight.client.recipebook.CookingPotRecipeBookTab;
import vectorwing.farmersdelight.common.tag.ForgeTags;
import vectorwing.farmersdelight.data.builder.CookingPotRecipeBuilder;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static net.MrGise.floating.helper.Methods.*;

// Generates recipes
public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    static final List<ProcessingRecipeGen> GENERATORS = new ArrayList<>();
    static final int BUCKET = FluidType.BUCKET_VOLUME;
    static final int BOTTLE = 250;

    public static final List<ItemLike> SKIRON_SMELTABLES = List.of(ModBlocks.SKIRON_ORE.get(), ModItems.RAW_SKIRON.get());
    public static final List<ItemLike> SKOAL_SMELTABLES = List.of(ModBlocks.SKOAL_ORE.get());

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> writer) {
        //Recipes here

        //- Forks
        forkRecipe(writer, RecipeCategory.TOOLS, ModItems.IRON_FORK.get(),
                Ingredient.of(Tags.Items.NUGGETS_IRON), Ingredient.of(Tags.Items.INGOTS_IRON), Items.IRON_INGOT);

        spoonRecipe(writer, RecipeCategory.TOOLS, ModItems.WOODEN_SPOON.get(),
                Ingredient.of(Tags.Items.RODS_WOODEN), Ingredient.of(ItemTags.PLANKS), ModBlocks.PLACED_BOWL.get());

        shapedRecipe(writer, RecipeCategory.MISC, ModItems.GOLD_KEY.get(), Map.of('G', Ingredient.of(Tags.Items.INGOTS_GOLD)), Items.GOLD_INGOT, "  G", "GGG", "GG ");

        cuttingRecipe(writer, Ingredient.of(ModTags.Items.CUCUMBERS), ModItems.CUT_CUCUMBER.get(), Ingredient.of(ForgeTags.TOOLS_KNIVES),
                1, 1.0f, "cucumber_cutting",
                new resultWithChance(ModItems.CUCUMBER_SEEDS.get(), 1, 0.1f));

        cuttingRecipe(writer, Ingredient.of(ModTags.Items.POMEGRANATES), ModItems.POMEGRANATE_SLICE.get(), Ingredient.of(ForgeTags.TOOLS_KNIVES),
                2, 1.0f, "pomegranate_cutting");

        cuttingRecipe(writer, Ingredient.of(Items.APPLE), ModItems.APPLE_SLICE.get(), Ingredient.of(ForgeTags.TOOLS_KNIVES),
                8, 1.0f, "apple_slicing");
        cuttingRecipe(writer, Ingredient.of(AllItems.HONEYED_APPLE), ModItems.HONEYED_APPLE_SLICE.get(), Ingredient.of(ForgeTags.TOOLS_KNIVES),
                8, 1.0f, "honeyed_apple_slicing");

        exchangeRecipe(writer, RecipeCategory.FOOD, Ingredient.of(ModTags.Items.POMEGRANATE_SLICES), ModItems.POMEGRANATE.get(), "food/", ModItems.POMEGRANATE_SEEDS.get(), "food/", 16);

        exchangeRecipe(writer, RecipeCategory.FOOD, Ingredient.of(ModTags.Items.CUCUMBERS), ModItems.CUCUMBER.get(), "food/", ModItems.CUCUMBER_SEEDS.get(), "food/");
        exchangeRecipe(writer, RecipeCategory.FOOD, Ingredient.of(ModTags.Items.STRAWBERRIES), ModItems.STRAWBERRY.get(), "food/", ModItems.STRAWBERRY_SEEDS.get(), "food/");

        //: Magic
        shapelessRecipe(writer, RecipeCategory.MISC, ModItems.RAINSTONE_BUCKET.get(), 1, ModItems.RAINSTONE_SHARD.get(),
                Map.of(Ingredient.of(ModItems.RAINSTONE_SHARD.get()), 4, Ingredient.of(ModItems.SOLIDIFIED_MANA.get()), 1, Ingredient.of(Items.BUCKET), 1));


        //- Actinolite tools
        swordRecipe(writer, RecipeCategory.COMBAT, Ingredient.of(ModTags.Items.ACTINOLITE), null, ModItems.ACTINOLITE_SWORD.get(), ModItems.ACTINOLITE.get(), "sky_ores");
        pickaxeRecipe(writer, RecipeCategory.COMBAT, Ingredient.of(ModTags.Items.ACTINOLITE), null, ModItems.ACTINOLITE_PICKAXE.get(), ModItems.ACTINOLITE.get(), "sky_ores");
        axeRecipe(writer, RecipeCategory.COMBAT, Ingredient.of(ModTags.Items.ACTINOLITE), null, ModItems.ACTINOLITE_AXE.get(), ModItems.ACTINOLITE.get(), "sky_ores");
        hoeRecipe(writer, RecipeCategory.COMBAT, Ingredient.of(ModTags.Items.ACTINOLITE), null, ModItems.ACTINOLITE_HOE.get(), ModItems.ACTINOLITE.get(), "sky_ores");
        shovelRecipe(writer, RecipeCategory.COMBAT, Ingredient.of(ModTags.Items.ACTINOLITE), null, ModItems.ACTINOLITE_SHOVEL.get(), ModItems.ACTINOLITE.get(), "sky_ores");


        //- Skiron tools
        swordRecipe(writer, RecipeCategory.COMBAT, Ingredient.of(ModTags.Items.SKIRON_INGOTS), null, ModItems.SKIRON_SWORD.get(), ModItems.SKIRON.get(), "sky_ores");
        pickaxeRecipe(writer, RecipeCategory.TOOLS, Ingredient.of(ModTags.Items.SKIRON_INGOTS), null, ModItems.SKIRON_PICKAXE.get(), ModItems.SKIRON.get(), "sky_ores");
        axeRecipe(writer, RecipeCategory.COMBAT, Ingredient.of(ModTags.Items.SKIRON_INGOTS), null, ModItems.SKIRON_AXE.get(), ModItems.SKIRON.get(), "sky_ores");
        shovelRecipe(writer, RecipeCategory.TOOLS, Ingredient.of(ModTags.Items.SKIRON_INGOTS), null, ModItems.SKIRON_SHOVEL.get(), ModItems.SKIRON.get(), "sky_ores");
        hoeRecipe(writer, RecipeCategory.TOOLS, Ingredient.of(ModTags.Items.SKIRON_INGOTS), null, ModItems.SKIRON_HOE.get(), ModItems.SKIRON.get(), "sky_ores");

        armorRecipes(writer, RecipeCategory.COMBAT, Ingredient.of(ModTags.Items.SKIRON_INGOTS), ModItems.SKIRON.get(), ModItems.SKIRON_HELMET.get(), ModItems.SKIRON_CHESTPLATE.get(), ModItems.SKIRON_LEGGINGS.get(), ModItems.SKIRON_BOOTS.get(), "sky_ores");

        imbuedArmorRecipes(writer, RecipeCategory.COMBAT, Ingredient.of(ModTags.Items.ACTINOLITE), ModItems.ACTINOLITE.get(),
                ModItems.SKIRON_HELMET.get(), ModItems.SKIRON_CHESTPLATE.get(), ModItems.SKIRON_LEGGINGS.get(), ModItems.SKIRON_BOOTS.get(),
                ModItems.SKIRON_ACTINOLITE_HELMET.get(), ModItems.SKIRON_ACTINOLITE_CHESTPLATE.get(), ModItems.SKIRON_ACTINOLITE_LEGGINGS.get(), ModItems.SKIRON_ACTINOLITE_BOOTS.get(), "sky_ores");

        //region Mimics
        mimicDisguise("carrot", writer, RecipeCategory.MISC, ModItems.MIMIC.get(), Items.CARROT, ModItems.MIMIC.get(), "mimic_carrot");

        mimicDisguise("chest", writer, RecipeCategory.MISC, ModItems.MIMIC.get(), Blocks.CHEST.asItem(), ModBlocks.MIMIC_BLOCK.get(), "mimic_chest");
        mimicDisguise("shulker_box", writer, RecipeCategory.MISC, ModItems.MIMIC.get(), Blocks.SHULKER_BOX.asItem(), ModBlocks.MIMIC_BLOCK.get(), "mimic_shulker_box");
        //End

        //-- Trims
        trimSmithing(writer, ModItems.GLIDE_ARMOR_TRIM_SMITHING_TEMPLATE.get(), mmm("glide_armor_trim"));

        copySmithingTemplate(writer, ModItems.GLIDE_ARMOR_TRIM_SMITHING_TEMPLATE.get(), ModBlocks.BROKEN_SKYSOLID.get());

        // Nine-to-one ratio
        nineBlockStorageRecipes(writer, RecipeCategory.MISC, ModItems.SKOAL.get(), RecipeCategory.MISC, ModBlocks.SKOAL_BLOCK.get(),
                "skoal_block", "sky_ores", "skoal", null);

        nineBlockStorageRecipes(writer, RecipeCategory.MISC, ModItems.SKIRON.get(), RecipeCategory.MISC, ModBlocks.SKIRON_BLOCK.get(),
                "skiron_block", "sky_ores", "skiron", null);

        nineBlockStorageRecipes(writer, RecipeCategory.MISC, ModItems.RAW_SKIRON.get(), RecipeCategory.MISC, ModBlocks.RAW_SKIRON_BLOCK.get(),
                "raw_skiron_block", "sky_ores", "raw_skiron", null);

        nineItemIngotRecipes(writer, RecipeCategory.MISC, ModItems.SKIRON_NUGGET.get(), Ingredient.of(ModTags.Items.SKIRON_NUGGETS),
                RecipeCategory.MISC, ModItems.SKIRON.get(), Ingredient.of(ModTags.Items.SKIRON_INGOTS),
                "skiron_nugget_from_skiron", "sky_ores"
                ,"skiron_from_nuggets", "sky_ores");

        //| Bowyery
        bowyery(Ingredient.of(Items.STICK), Ingredient.of(Tags.Items.STRING), Ingredient.of(Tags.Items.STRING),
                Items.BOW, 1, writer);

        bowyery(Ingredient.of(Items.BOW), Ingredient.of(Tags.Items.STONE), Ingredient.of(Tags.Items.STONE),
                ModItems.REINFORCED_STONE_BOW.get(), 1, writer);

        bowyery(Ingredient.of(Items.BOW), Ingredient.of(Tags.Items.NUGGETS_IRON), Ingredient.of(Tags.Items.NUGGETS_IRON),
                ModItems.REINFORCED_IRON_BOW.get(), 1, writer);

        bowyery(Ingredient.of(Items.BOW), Ingredient.of(Tags.Items.NUGGETS_GOLD), Ingredient.of(Tags.Items.NUGGETS_GOLD),
                ModItems.REINFORCED_GOLD_BOW.get(), 1, writer);

        bowyery(Ingredient.of(Items.BOW), Ingredient.of(Tags.Items.GEMS_DIAMOND), Ingredient.of(Tags.Items.GEMS_DIAMOND),
                ModItems.REINFORCED_DIAMOND_BOW.get(), 1, writer);

        bowyery(Ingredient.of(ModItems.REINFORCED_DIAMOND_BOW.get()), Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(Tags.Items.INGOTS_NETHERITE),
                ModItems.REINFORCED_NETHERITE_BOW.get(), 1, writer);

        bowyery(Ingredient.of(Items.BOW), Ingredient.of(ModTags.Items.SKIRON_NUGGETS), Ingredient.of(ModTags.Items.SKIRON_NUGGETS),
                ModItems.REINFORCED_SKIRON_BOW.get(), 1, writer);

        bowyery(Ingredient.of(Items.BOW), Ingredient.of(ModTags.Items.ACTINOLITE), Ingredient.of(ModTags.Items.ACTINOLITE),
                ModItems.REINFORCED_ACTINOLITE_BOW.get(), 1, writer);

        //| Bowl recipes
        bowl(BowlRecipeBuilderBuilder.bowl(Items.COOKIE).craftLength(10).ingredient(Ingredient.of(Items.COCOA_BEANS))
                .ingredient(Ingredient.of(AllTags.AllItemTags.WHEAT_FLOUR.tag)).build()
                .unlockedBy(getHasName(Items.COOKIE), has(Items.COOKIE)), Items.WHEAT, "bowl_dry", writer, mmm("simple_cookie"));

        //-- Smelting and stuff
        oreSmeltingAndBlasting(writer, SKIRON_SMELTABLES, RecipeCategory.MISC, ModItems.SKIRON.get(), 0.15f, 0.25f,
                200, 100, "sky_ores");

        oreSmeltingAndBlasting(writer, SKOAL_SMELTABLES, RecipeCategory.MISC, ModItems.SKOAL.get(), 0.25f, 0.5f,
                200, 100, "sky_ores");

        smelting(writer, Ingredient.of(ModBlocks.BROKEN_SKYSOLID.get()), RecipeCategory.MISC, ModBlocks.SKYSOLID.get(), ModBlocks.BROKEN_SKYSOLID.get(), 100, "skyland_misc");

        //- Food
        cookingDirFix(writer, Ingredient.of(ModItems.UNCOOKED_MATZA.get()), "food/", RecipeCategory.FOOD,
                ModItems.MATZA.get(), "food/", AllItems.DOUGH, 100, "matza");

        potRecipe(writer, ModItems.BOILED_EGG.get(), 1, 1200, 5.0f, "boiled_egg_single", CookingPotRecipeBookTab.MEALS,
                Ingredient.of(Items.EGG),
                StrictNBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER)),
                StrictNBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER)));
        potRecipe(writer, ModItems.BOILED_EGG.get(), 2, 1200, 5.0f, "boiled_egg_double", CookingPotRecipeBookTab.MEALS,
                Ingredient.of(Items.EGG), Ingredient.of(Items.EGG),
                StrictNBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER)),
                StrictNBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER)));
        potRecipe(writer, ModItems.BOILED_EGG.get(), 3, 1200, 5.0f, "boiled_egg_triple", CookingPotRecipeBookTab.MEALS,
                Ingredient.of(Items.EGG), Ingredient.of(Items.EGG), Ingredient.of(Items.EGG),
                StrictNBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER)),
                StrictNBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER)));
        potRecipe(writer, ModItems.BOILED_EGG.get(), 4, 1200, 5.0f, "boiled_egg_quadrouple", CookingPotRecipeBookTab.MEALS,
                Ingredient.of(Items.EGG), Ingredient.of(Items.EGG), Ingredient.of(Items.EGG), Ingredient.of(Items.EGG),
                StrictNBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER)),
                StrictNBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER)));

        //-- Wood
        exchangeRecipe(writer, RecipeCategory.MISC, Ingredient.of(ModBlocks.SKYWOOD_LOG.get()), ModBlocks.SKYWOOD_LOG.get(), ModBlocks.SKYWOOD_PLANKS.get(), 4);
        exchangeRecipe(writer, RecipeCategory.MISC, Ingredient.of(ModBlocks.STRIPPED_SKYWOOD_LOG.get()), ModBlocks.STRIPPED_SKYWOOD_LOG.get(), ModBlocks.SKYWOOD_PLANKS.get(), 4);

        slab(writer, RecipeCategory.MISC, ModBlocks.SKYWOOD_SLAB.get(), ModBlocks.SKYWOOD_PLANKS.get());
        stairs(writer, RecipeCategory.MISC, ModBlocks.SKYWOOD_STAIRS.get(), Ingredient.of(ModBlocks.SKYWOOD_PLANKS.get()), ModBlocks.SKYWOOD_PLANKS.get());
    }

    public static void registerProcessing(DataGenerator gen, PackOutput output) {
        GENERATORS.add(new MMMFillingRecipeGen(output));

        gen.addProvider(true, new DataProvider() {
            @Override
            public String getName() {
                return "MMM's processing recipes";
            }

            @Override
            public CompletableFuture<?> run(CachedOutput cachedOutput) {
                return CompletableFuture.allOf(GENERATORS.stream()
                        .map(gen -> gen.run(cachedOutput))
                        .toArray(CompletableFuture[]::new));
            }
        });
    }

    protected static void mimicDisguise(String pForm, Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory, ItemLike pMimic, ItemLike pToForm, ItemLike pResultMimic, String pName) {
        ItemStack output = new ItemStack(pResultMimic);

        CompoundTag nbt = new CompoundTag();
        nbt.putString("form", pForm);
        output.setTag(nbt);

        NBTSingularShapelessRecipeBuilder.shapeless(pCategory, output)
                .requires(pMimic)
                .requires(pToForm)
                .save(pFinishedRecipeConsumer, mmm(pName));
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

        builder.build(finishedRecipeConsumer, mmm(name));
    }

    protected static void potRecipe(Consumer<FinishedRecipe> finishedRecipeConsumer,
                                    ItemLike result, int count,
                                    int cookingTime, float xp, String name,
                                    CookingPotRecipeBookTab tab,
                                    @NonNull Ingredient... ingredients) {
        CookingPotRecipeBuilder builder = CookingPotRecipeBuilder.cookingPotRecipe(result, count, cookingTime, xp);

        for (Ingredient ingredient : ingredients) {
            builder.addIngredient(ingredient);
        }
        builder.setRecipeBookTab(tab);

        ItemLike[] unlockedBy = Arrays.stream(ingredients[0].getItems())
                .map(ItemStack::getItem)
                .toArray(ItemLike[]::new);

        builder.unlockedByItems("has_for_" + name, unlockedBy);

        builder.build(finishedRecipeConsumer, mmm(name));
    }

    protected static void potRecipe(Consumer<FinishedRecipe> finishedRecipeConsumer,
                                    ItemLike result, int count, int cookingTime,
                                    float xp, ItemLike container, String name,
                                    @NonNull Ingredient... ingredients) {
        CookingPotRecipeBuilder builder = CookingPotRecipeBuilder.cookingPotRecipe(result, count, cookingTime, xp, container);

        for (Ingredient ingredient : ingredients) {
            builder.addIngredient(ingredient);
        }

        builder.build(finishedRecipeConsumer, mmm(name));
    }

    protected static void bowyery(ItemLike bow, ItemLike slot1, ItemLike slot2, ItemLike output, int count, Consumer<FinishedRecipe> writer) {
        new BowyeryRecipeBuilder(bow, slot1, slot2, output, count)
                .unlockedBy(getHasName(output), has(output)).save(writer);
    }

    protected static void bowyery(Ingredient bow, Ingredient slot1, Ingredient slot2, ItemLike output, int count, Consumer<FinishedRecipe> writer) {
        new BowyeryRecipeBuilder(bow, slot1, slot2, output, count)
                .unlockedBy(getHasName(output), has(output)).save(writer);
    }

    protected static void bowl(Consumer<FinishedRecipe> writer, ItemLike result, int count,
                               FluidIngredient fluidIngredient, int craftLength, ItemLike unlockedBy,
                               String group, @NonNull Ingredient... ingredients) {
        new BowlRecipeBuilder(result, count, fluidIngredient, craftLength, ingredients)
                .unlockedBy(getHasName(unlockedBy), has(unlockedBy)).group(group).save(writer);
    }
    protected static void bowl(BowlRecipeBuilder builder, ItemLike unlockedBy, String group, Consumer<FinishedRecipe> writer) {
        builder.unlockedBy(getHasName(unlockedBy), has(unlockedBy)).group(group).save(writer);
    }
    protected static void bowl(BowlRecipeBuilder builder, ItemLike unlockedBy, String group, Consumer<FinishedRecipe> writer, ResourceLocation id) {
        builder.unlockedBy(getHasName(unlockedBy), has(unlockedBy)).group(group).save(writer, id);
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

    protected static void shapelessRecipe(Consumer<FinishedRecipe> finishedRecipeConsumer,
                                          RecipeCategory category,
                                          ItemLike result, int count, ItemLike unlockedBy,
                                          Map<Ingredient, Integer> ingredients) {
        ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(category, result, count);

        for (Ingredient ing : ingredients.keySet()) {
            builder.requires(ing, ingredients.get(ing));
        }

        builder.unlockedBy(getHasName(unlockedBy), has(unlockedBy));

        builder.save(finishedRecipeConsumer);
    }

    protected static void forkRecipe(Consumer<FinishedRecipe> finishedRecipeConsumer,
                                     RecipeCategory category, Item result, Ingredient nugget, Ingredient ingot, ItemLike unlockedBy) {
        shapedRecipe(finishedRecipeConsumer, category, result, Map.of('N', nugget, 'I', ingot), unlockedBy, "  I", " N ", "N  ");
    }

    protected static void spoonRecipe(Consumer<FinishedRecipe> writer, RecipeCategory category,
                                      Item result, Ingredient stick, Ingredient material, ItemLike unlockedBy) {
        shapedRecipe(writer, category, result, Map.of('S', stick, 'M', material), unlockedBy, "S  ", " S ", "  M");
    }

    protected static void exchangeRecipe(Consumer<FinishedRecipe> finishedRecipeConsumer, RecipeCategory category, Ingredient ingredient, ItemLike unlockedBy, ItemLike result) {
        exchangeRecipe(finishedRecipeConsumer, category, ingredient, unlockedBy, result, 1);
    }

    protected static void exchangeRecipe(Consumer<FinishedRecipe> finishedRecipeConsumer, RecipeCategory category,
                                            Ingredient ingredient, ItemLike unlockedBy, ItemLike result, int count) {
        ShapelessRecipeBuilder.shapeless(category, result, count).requires(ingredient)
                .unlockedBy(getHasName(unlockedBy), has(unlockedBy)).save(finishedRecipeConsumer,
                        mmm(result.asItem().toString().toLowerCase() + "_from_" + unlockedBy.asItem().toString().toLowerCase()));
    }

    protected static void exchangeRecipe(Consumer<FinishedRecipe> finishedRecipeConsumer, RecipeCategory category,
                                            Ingredient ingredient, ItemLike unlockedBy, String ingredientPrefix,
                                         ItemLike result, String resultPrefix, int count) {
        ShapelessRecipeBuilder.shapeless(category, result, count).requires(ingredient)
                .unlockedBy(getHasName(unlockedBy), has(unlockedBy)).save(finishedRecipeConsumer,
                        mmm(result.asItem().toString().toLowerCase().replaceFirst(resultPrefix, "") + "_from_" + unlockedBy.asItem().toString().toLowerCase().replaceFirst(ingredientPrefix, "")));
    }

    protected static void exchangeRecipe(Consumer<FinishedRecipe> finishedRecipeConsumer, RecipeCategory category,
                                            Ingredient ingredient, ItemLike unlockedBy, String ingredientPrefix,
                                         ItemLike result, String resultPrefix) {
        exchangeRecipe(finishedRecipeConsumer, category, ingredient, unlockedBy, ingredientPrefix, result, resultPrefix, 1);
    }

    protected static void nineItemIngotRecipes(Consumer<FinishedRecipe> writer, RecipeCategory unpackedCategory,
                                               ItemLike unpacked, Ingredient toPack, RecipeCategory packedCategory,
                                               ItemLike packed, Ingredient toUnpack, String packedName,
                                               @Nullable String packedGroup, String unpackedName,
                                               @Nullable String unpackedGroup) {
        ShapelessRecipeBuilder.shapeless(unpackedCategory, unpacked, 9)
                .requires(toUnpack, 1).group(unpackedGroup).unlockedBy(getHasName(packed), has(packed))
                .save(writer, mmm(unpackedName));
        ShapelessRecipeBuilder.shapeless(packedCategory, packed, 1)
                .requires(toPack, 9).group(packedGroup).unlockedBy(getHasName(unpacked), has(unpacked))
                .save(writer, mmm(packedName));
    }

    protected static void imbuedArmorRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory,
                                                Ingredient pImbued, ItemLike unlockedBy, Item pHelmet, Item pChestplate, Item pLeggings, Item pBoots, Item pImbuedHelmet, Item pImbuedChestplate, Item pImbuedLeggings, Item pImbuedBoots, String pCollectiveGroup) {

        ShapelessRecipeBuilder.shapeless(pCategory, pImbuedHelmet).requires(pImbued).requires(pHelmet)
                .group(pCollectiveGroup).unlockedBy(getHasName(unlockedBy), has(unlockedBy)).unlockedBy(getHasName(pHelmet), has(pHelmet))
                .save(pFinishedRecipeConsumer, mmm(getItemName(pImbuedHelmet)));

        ShapelessRecipeBuilder.shapeless(pCategory, pImbuedChestplate).requires(pImbued).requires(pChestplate)
                .group(pCollectiveGroup).unlockedBy(getHasName(unlockedBy), has(unlockedBy)).unlockedBy(getHasName(pChestplate), has(pChestplate))
                .save(pFinishedRecipeConsumer, mmm(getItemName(pImbuedChestplate)));

        ShapelessRecipeBuilder.shapeless(pCategory, pImbuedLeggings).requires(pImbued).requires(pLeggings)
                .group(pCollectiveGroup).unlockedBy(getHasName(unlockedBy), has(unlockedBy)).unlockedBy(getHasName(pLeggings), has(pLeggings))
                .save(pFinishedRecipeConsumer, mmm(getItemName(pImbuedLeggings)));

        ShapelessRecipeBuilder.shapeless(pCategory, pImbuedBoots).requires(pImbued).requires(pBoots)
                .group(pCollectiveGroup).unlockedBy(getHasName(unlockedBy), has(unlockedBy)).unlockedBy(getHasName(pBoots), has(pBoots))
                .save(pFinishedRecipeConsumer, mmm(getItemName(pImbuedBoots)));

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
                .group(pGroup).unlockedBy(getHasName(unlockedBy), has(unlockedBy)).save(pFinishedRecipeConsumer, mmm(getItemName(pHelmet)));
    }

    protected static void chestplateRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory,
                                            Ingredient pMaterial, ItemLike unlockedBy, Item pChestplate, String pGroup) {
        ShapedRecipeBuilder.shaped(pCategory, pChestplate).define('#', pMaterial)
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .group(pGroup).unlockedBy(getHasName(unlockedBy), has(unlockedBy)).save(pFinishedRecipeConsumer, mmm(getItemName(pChestplate)));
    }

    protected static void leggingsRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory,
                                            Ingredient pMaterial, ItemLike unlockedBy, Item pLeggings, String pGroup) {
        ShapedRecipeBuilder.shaped(pCategory, pLeggings).define('#', pMaterial)
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .group(pGroup).unlockedBy(getHasName(unlockedBy), has(unlockedBy)).save(pFinishedRecipeConsumer, mmm(getItemName(pLeggings)));
    }

    protected static void bootsRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory, Ingredient pMaterial, ItemLike unlockedBy, Item pBoots, String pGroup) {
        ShapedRecipeBuilder.shaped(pCategory, pBoots).define('#', pMaterial)
                .pattern("# #")
                .pattern("# #")
                .group(pGroup).unlockedBy(getHasName(unlockedBy), has(unlockedBy)).save(pFinishedRecipeConsumer, mmm(getItemName(pBoots)));
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
                .group(pGroup).unlockedBy(getHasName(unlockedBy), has(unlockedBy)).save(pFinishedRecipeConsumer, mmm(getItemName(pResult)));
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
                .group(pGroup).unlockedBy(getHasName(unlockedBy), has(unlockedBy)).save(pFinishedRecipeConsumer, mmm(getItemName(pResult)));
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
                .group(pGroup).unlockedBy(getHasName(unlockedBy), has(unlockedBy)).save(pFinishedRecipeConsumer, mmm(getItemName(pResult) + "_right"));

        ShapedRecipeBuilder.shaped(pCategory, pResult).define('#', pMaterial).define('S', pStick)
                .pattern("##")
                .pattern("#S")
                .pattern(" S")
                .group(pGroup).unlockedBy(getHasName(unlockedBy), has(unlockedBy)).save(pFinishedRecipeConsumer, mmm(getItemName(pResult) + "_left"));
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
                .group(pGroup).unlockedBy(getHasName(unlockedBy), has(unlockedBy)).save(pFinishedRecipeConsumer, mmm(getItemName(pResult) + "_right"));

        ShapedRecipeBuilder.shaped(pCategory, pResult).define('#', pMaterial).define('S', pStick)
                .pattern("##")
                .pattern(" S")
                .pattern(" S")
                .group(pGroup).unlockedBy(getHasName(unlockedBy), has(unlockedBy)).save(pFinishedRecipeConsumer, mmm(getItemName(pResult) + "_left"));
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
                .save(pFinishedRecipeConsumer, mmm(getItemName(pResult)));
    }

    protected static void knifeRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory,
                                        Ingredient pMaterial, ItemLike unlockedBy, Ingredient pStick, Item pResult, String pGroup) {
        if (pStick == null) {
            pStick = Ingredient.of(Items.STICK);
        }
        ShapedRecipeBuilder.shaped(pCategory, pResult).define('#', pMaterial).define('S', pStick)
                .pattern("#")
                .pattern("S")
                .group(pGroup).unlockedBy(getHasName(unlockedBy), has(unlockedBy)).save(pFinishedRecipeConsumer, mmm(getItemName(pResult)));
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
                .group(pGroup).unlockedBy(getHasName(pUnlockedBy), has(pUnlockedBy)).save(pFinishedRecipeConsumer, mmm(getItemName(pResult)));
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

    protected static void cooking(Consumer<FinishedRecipe> finishedRecipeConsumer,
                                  Ingredient ingredient, RecipeCategory category, ItemLike result, ItemLike unlockedBy,
                                  int basicCookingTime, String group) {
        campfireCooking(finishedRecipeConsumer, ingredient, category, result, unlockedBy, basicCookingTime * 3, group);
        smelting(finishedRecipeConsumer, ingredient, category, result, unlockedBy, basicCookingTime, group);
        smoking(finishedRecipeConsumer, ingredient, category, result, unlockedBy, Math.round(basicCookingTime / 2f), group);
    }

    protected static void cookingDirFix(Consumer<FinishedRecipe> finishedRecipeConsumer,
                                        Ingredient ingredient, String prefix, RecipeCategory category,
                                        ItemLike result, String prefix1, ItemLike unlockedBy,
                                        int basicCookingTime, String group) {
        campfireCookingDirFix(finishedRecipeConsumer, ingredient, prefix, category, result, prefix1, unlockedBy, basicCookingTime * 3, group);
        smeltingDirFix(finishedRecipeConsumer, ingredient, prefix, category, result, prefix1, unlockedBy, basicCookingTime, group);
        smokingDirFix(finishedRecipeConsumer, ingredient, prefix, category, result, prefix1, unlockedBy, Math.round(basicCookingTime / 2f), group);
    }

    protected static void campfireCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, Ingredient pIngredient, RecipeCategory pCategory, ItemLike pResult, ItemLike unlockedBy,
                                    int pCookingTIme, String pGroup) {
        cooking(pFinishedRecipeConsumer, RecipeSerializer.CAMPFIRE_COOKING_RECIPE, pIngredient, pCategory, pResult, unlockedBy, pCookingTIme, pGroup, "_from_campfire_cooking");
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

    protected static void campfireCookingDirFix(Consumer<FinishedRecipe> pFinishedRecipeConsumer,
                                                Ingredient pIngredient, String prefix, RecipeCategory pCategory,
                                                ItemLike pResult, String prefix1, ItemLike unlockedBy,
                                                int pCookingTIme, String pGroup) {
        cookingDirFix(pFinishedRecipeConsumer, RecipeSerializer.CAMPFIRE_COOKING_RECIPE,
                pIngredient, prefix, pCategory, pResult, prefix1, unlockedBy, pCookingTIme, pGroup, "_from_campfire_cooking");
    }

    protected static void smeltingDirFix(Consumer<FinishedRecipe> pFinishedRecipeConsumer,
                                         Ingredient pIngredient, String prefix, RecipeCategory pCategory,
                                         ItemLike pResult, String prefix1, ItemLike unlockedBy,
                                         int pCookingTIme, String pGroup) {
        cookingDirFix(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE,
                pIngredient, prefix, pCategory, pResult, prefix1, unlockedBy, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void blastingDirFix(Consumer<FinishedRecipe> pFinishedRecipeConsumer,
                                         Ingredient pIngredient, String prefix, RecipeCategory pCategory,
                                         ItemLike pResult, String prefix1, ItemLike unlockedBy,
                                         int pCookingTIme, String pGroup) {
        cookingDirFix(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE,
                pIngredient, prefix, pCategory, pResult, prefix1, unlockedBy, pCookingTIme, pGroup, "_from_blasting");
    }

    protected static void smokingDirFix(Consumer<FinishedRecipe> pFinishedRecipeConsumer,
                                        Ingredient pIngredient, String prefix, RecipeCategory pCategory,
                                        ItemLike pResult, String prefix1, ItemLike unlockedBy,
                                        int pCookingTIme, String pGroup) {
        cookingDirFix(pFinishedRecipeConsumer, RecipeSerializer.SMOKING_RECIPE,
                pIngredient, prefix, pCategory, pResult, prefix1, unlockedBy, pCookingTIme, pGroup, "_from_smoking");
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

    protected static void cookingDirFix(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer,
                                    Ingredient pIngredient, String prefix, RecipeCategory pCategory, ItemLike pResult, String prefix1, ItemLike unlockedByAndName,
                                    int pCookingTime, String pGroup, String pRecipeName) {

            SimpleCookingRecipeBuilder.generic(pIngredient, pCategory, pResult, 0, pCookingTime, pCookingSerializer).group(pGroup).unlockedBy(getHasName(unlockedByAndName), has(unlockedByAndName))
                    .save(pFinishedRecipeConsumer, MMM.MOD_ID + ":" + getItemNameDirFix(pResult, prefix1) + pRecipeName + "_" + getItemNameDirFix(unlockedByAndName, prefix));

    }

    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> writer, RecipeCategory unpackedCategory, ItemLike unpacked,
                                                  RecipeCategory packedCategory, ItemLike packed,
                                                  String unpackedName, @Nullable String unpackedGroup, String packedName, @Nullable String packedGroup) {
        ShapelessRecipeBuilder.shapeless(unpackedCategory, unpacked, 9).requires(packed).group(packedGroup)
                .unlockedBy(getHasName(packed), has(packed)).save(writer, mmm(packedName));
        ShapedRecipeBuilder.shaped(packedCategory, packed).define('#', unpacked)
                .pattern("###").pattern("###").pattern("###").group(unpackedGroup)
                .unlockedBy(getHasName(unpacked), has(unpacked)).save(writer, mmm(unpackedName));
    }

    protected static String getItemNameDirFix(ItemLike item, String prefix) {
        return ForgeRegistries.ITEMS.getKey(item.asItem()).getPath().replaceFirst(prefix, "");
    }

    private record resultWithChance(ItemLike result, int count, float chance) {}

}
