package net.MrGise.mmm.datagen.model;

import net.MrGise.mmm.MMM;
import net.MrGise.floating.datagen.builders.SeparateTransformModelBuilder;
import net.MrGise.floating.datagen.builders.SeparateTransformModelBuilder.*;
import net.MrGise.mmm.registry.content.ModBlocks;
import net.MrGise.mmm.registry.content.ModItems;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static net.MrGise.floating.helper.Methods.*;

// Generates item models
public class ModItemModelProvider extends ItemModelProvider {

    private final PackOutput output;

    private final Map<ResourceLocation, SeparateTransformModelBuilder> customModels = new HashMap<>();

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MMM.MOD_ID, existingFileHelper);
        this.output = output;
    }

    @Override
    protected void registerModels() {
        // Item models

        simpleItemDirFix(ModItems.TEST_ITEM);
        simpleItemDirFix(ModItems.DIRECTORY_TEST);


        //- Treasures

        simpleItemDirFix(ModItems.GLIDE_ARMOR_TRIM_SMITHING_TEMPLATE);

        simpleItem(ModItems.DROPPY_LIKES_RICOCHET_MUSIC_DISC);
        simpleItem(ModItems.DROPPY_LIKES_EVERYTHING_MUSIC_DISC);
        simpleItem(ModItems.TUNE_MUSIC_DISC);

        //- Food

        simpleItemDirFix(ModItems.BOILED_EGG);
        simpleItemDirFix(ModItems.PEELED_BOILED_EGG);
        simpleItemDirFix(ModItems.BROKEN_EGGSHELL);

        simpleItemDirFix(ModItems.BREADSTICK);

        simpleItemDirFix(ModItems.STRAWBERRY);
        simpleItemDirFix(ModItems.STRAWBERRY_SEEDS);

        simpleItemDirFix(ModItems.CUCUMBER_SEEDS);
        simpleItemDirFix(ModItems.CUCUMBER);
        simpleItemDirFix(ModItems.CUT_CUCUMBER);

        simpleItemDirFix(ModItems.POMEGRANATE);
        simpleItemDirFix(ModItems.POMEGRANATE_SLICE);
        simpleItemDirFix(ModItems.POMEGRANATE_EMPTY_SLICE);
        simpleItemDirFix(ModItems.POMEGRANATE_SEEDS);

        simpleItemDirFix(ModItems.APPLE_SLICE);
        simpleItemDirFix(ModItems.HONEYED_APPLE_SLICE);

        simpleItemDirFix(ModItems.MATZA);
        simpleItemDirFix(ModItems.UNCOOKED_MATZA);

        simpleItemDirFix(ModItems.GOAT_MILK_BUCKET);

        //- Materials
        //* Skyland

        simpleItem(ModItems.SKOAL);

        simpleItem(ModItems.SKIRON);
        simpleItem(ModItems.SKIRON_NUGGET);
        simpleItem(ModItems.RAW_SKIRON);

        simpleItem(ModItems.SKIRON_HORSE_ARMOR);

        simpleItem(ModItems.ACTINOLITE);

        //* Magic

        simpleItem(ModItems.SOLIDIFIED_MANA);

        //- Stones n' stuff
        simpleItem(ModItems.RAINSTONE_BUCKET);
        simpleItem(ModItems.RAINSTONE_SHARD);

        //- Candles
        candleModel(ModItems.LIT_CANDLE, modLoc("custom/candle_lit"), "item/candle/models/candle/candle_lit");
        candleModel(ModItems.LIT_CANDLE_BLACK, modLoc("custom/black_candle_lit"), "item/candle/models/candle/black_candle_lit");
        candleModel(ModItems.LIT_CANDLE_BLUE, modLoc("custom/blue_candle_lit"), "item/candle/models/candle/blue_candle_lit");
        candleModel(ModItems.LIT_CANDLE_BROWN, modLoc("custom/brown_candle_lit"), "item/candle/models/candle/brown_candle_lit");
        candleModel(ModItems.LIT_CANDLE_CYAN, modLoc("custom/cyan_candle_lit"), "item/candle/models/candle/cyan_candle_lit");
        candleModel(ModItems.LIT_CANDLE_GRAY, modLoc("custom/gray_candle_lit"), "item/candle/models/candle/gray_candle_lit");
        candleModel(ModItems.LIT_CANDLE_GREEN, modLoc("custom/green_candle_lit"), "item/candle/models/candle/green_candle_lit");
        candleModel(ModItems.LIT_CANDLE_LIGHT_BLUE, modLoc("custom/light_blue_candle_lit"), "item/candle/models/candle/light_blue_candle_lit");
        candleModel(ModItems.LIT_CANDLE_LIGHT_GRAY, modLoc("custom/light_gray_candle_lit"), "item/candle/models/candle/light_gray_candle_lit");
        candleModel(ModItems.LIT_CANDLE_LIME, modLoc("custom/lime_candle_lit"), "item/candle/models/candle/lime_candle_lit");
        candleModel(ModItems.LIT_CANDLE_MAGENTA, modLoc("custom/magenta_candle_lit"), "item/candle/models/candle/magenta_candle_lit");
        candleModel(ModItems.LIT_CANDLE_ORANGE, modLoc("custom/orange_candle_lit"), "item/candle/models/candle/orange_candle_lit");
        candleModel(ModItems.LIT_CANDLE_PINK, modLoc("custom/pink_candle_lit"), "item/candle/models/candle/pink_candle_lit");
        candleModel(ModItems.LIT_CANDLE_PURPLE, modLoc("custom/purple_candle_lit"), "item/candle/models/candle/purple_candle_lit");
        candleModel(ModItems.LIT_CANDLE_RED, modLoc("custom/red_candle_lit"), "item/candle/models/candle/red_candle_lit");
        candleModel(ModItems.LIT_CANDLE_WHITE, modLoc("custom/white_candle_lit"), "item/candle/models/candle/white_candle_lit");
        candleModel(ModItems.LIT_CANDLE_YELLOW, modLoc("custom/yellow_candle_lit"), "item/candle/models/candle/yellow_candle_lit");

        //- Other items
        handheldItem(ModItems.CONFETTI_CANNON);

        /*- Tools -*/
        handheldItem(ModItems.ACTINOLITE_SWORD);
        handheldItem(ModItems.ACTINOLITE_PICKAXE);
        handheldItem(ModItems.ACTINOLITE_AXE);
        handheldItem(ModItems.ACTINOLITE_SHOVEL);
        handheldItem(ModItems.ACTINOLITE_HOE);
        handheldItem(ModItems.ACTINOLITE_KNIFE);

        handheldItem(ModItems.SKIRON_SWORD);
        handheldItem(ModItems.SKIRON_PICKAXE);
        handheldItem(ModItems.SKIRON_AXE);
        handheldItem(ModItems.SKIRON_SHOVEL);
        handheldItem(ModItems.SKIRON_HOE);
        handheldItem(ModItems.SKIRON_KNIFE);

        handModelItem(ModItems.ROLLING_PIN, modLoc("custom/rolling_pin"), new Texture("all", "item/tool/rolling_pin"));

        handheldItem(ModItems.IRON_FORK, modLoc("item/tool/iron_fork"));


        //- Item models for blocks
        buttonItem(ModBlocks.SKYWOOD_BUTTON, ModBlocks.SKYWOOD_PLANKS);
        fenceItem(ModBlocks.SKYWOOD_FENCE, ModBlocks.SKYWOOD_PLANKS);
        wallItem(ModBlocks.SKYSOLID_WALL, ModBlocks.SKYSOLID);

        wallItem(ModBlocks.SKYGROUND_WALL, ModBlocks.SKYGROUND);

        notSoSimpleBlockItem(ModBlocks.HEAVENLY_GRASS, false, "_top", true);
        notSoSimpleBlockItem(ModBlocks.HEAVENLY_GRASS, false, "_short", false);
        itemWithPredicate(ModBlocks.HEAVENLY_GRASS.get().asItem(), "long",
                modLoc("item/heavenly_grass_short"), modLoc("item/heavenly_grass_top"));

        simpleBlockItem(ModBlocks.SKYWOOD_DOOR);
        tallBlockItemWithDirPF(ModBlocks.SKYWOOD_TRIPLE_DOOR, "door");
        simpleBlockItem(ModBlocks.OXALIS, "block/oxalis");

        tallBlockItemWithDirPF(ModBlocks.ACACIA_TRIPLE_DOOR, "door");
        tallBlockItemWithDirPF(ModBlocks.BIRCH_TRIPLE_DOOR, "door");
        tallBlockItemWithDirPF(ModBlocks.CRIMSON_TRIPLE_DOOR, "door");
        tallBlockItemWithDirPF(ModBlocks.DARK_OAK_TRIPLE_DOOR, "door");
        tallBlockItemWithDirPF(ModBlocks.IRON_TRIPLE_DOOR, "door");
        tallBlockItemWithDirPF(ModBlocks.JUNGLE_TRIPLE_DOOR, "door");
        tallBlockItemWithDirPF(ModBlocks.MANGROVE_TRIPLE_DOOR, "door");
        tallBlockItemWithDirPF(ModBlocks.OAK_TRIPLE_DOOR , "door");
        tallBlockItemWithDirPF(ModBlocks.SPRUCE_TRIPLE_DOOR , "door");
        tallBlockItemWithDirPF(ModBlocks.WARPED_TRIPLE_DOOR , "door");
        tallBlockItemWithDirPF(ModBlocks.BAMBOO_TRIPLE_DOOR , "door");
        tallBlockItemWithDirPF(ModBlocks.CHERRY_TRIPLE_DOOR , "door");

    }

    @Override
    protected CompletableFuture<?> generateAll(CachedOutput cache) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        futures.add(super.generateAll(cache));

        for (Map.Entry<ResourceLocation, SeparateTransformModelBuilder> entry : customModels.entrySet()) {
            ResourceLocation loc = entry.getKey();

            if (existingFileHelper.exists(loc, PackType.CLIENT_RESOURCES, ".json", "models")) {
                throw new IllegalStateException("Duplicate model: " + loc + " already exists in assets. Remove it from datagen or from your manual assets.");
            }

            Path path = this.output.getOutputFolder(PackOutput.Target.RESOURCE_PACK)
                    .resolve(loc.getNamespace() + "/models/" + loc.getPath() + ".json");

            futures.add(DataProvider.saveStable(cache, entry.getValue().toJson(), path));
        }

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    // Items
    /*
    Armor trims
    Templates located at models/references/trims
    */


    private ItemModelBuilder notSoSimpleBlockItem(RegistryObject<Block> item, boolean before, String name, boolean separateTexture) {
        String modelPath = before ? name + item.getId().getPath() : item.getId().getPath() + name;
        String texturePath = separateTexture
                ? modelPath
                : item.getId().getPath();

        return withExistingParent(modelPath,
                mcr("item/generated")).texture("layer0",
                mmm("block/" + texturePath));
    }

    private ItemModelBuilder notSoSimpleItem(RegistryObject<Item> item, String name) {
        return withExistingParent(item.getId().getPath(),
                mcr("item/generated")).texture("layer0",
                mmm("item/" + item.getId().getPath() + name));
    }

    private void itemWithPredicate(Item item, String predicateName,
                                    ResourceLocation baseModel,
                                    ResourceLocation overrideModel) {
        ItemModelBuilder builder = getBuilder(item.toString())
                .parent(getExistingFile(baseModel));

        builder.override()
                .predicate(mmm(predicateName), 1.0f)
                .model(getExistingFile(overrideModel));
    }

    /* Normal Items */
    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                mcr("item/handheld")).texture("layer0",
                mmm("item/" + item.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item, ResourceLocation texture) {
        return withExistingParent(item.getId().getPath(),
                mcr("item/handheld")).texture("layer0",
                texture);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                mcr("item/generated")).texture("layer0",
                mmm("item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleItemDirFix(RegistryObject<Item> item) {
        return withExistingParent("item/" + item.getId().getPath(),
                mcr("item/generated")).texture("layer0",
                mmm("item/" + item.getId().getPath()));
    }

    private SeparateTransformModelBuilder handModelItem(
            RegistryObject<Item> item,
            ResourceLocation model,
            ResourceLocation flatTexture,
            Texture... modelTextures) {

        // Construct the proper output path: modid:item/item_name
        ResourceLocation outputLoc = nAp(item.getId().getNamespace(), "item/" + item.getId().getPath());

        SeparateTransformModelBuilder builder =
                new SeparateTransformModelBuilder(outputLoc, existingFileHelper)
                        .base(model, flatTexture)
                        .addTexturesWithPrefix(MMM.MOD_ID + ":", modelTextures)
                        .modelInHandBasic();

        customModels.put(outputLoc, builder);

        return builder;
    }

    private SeparateTransformModelBuilder handModelItem (
            RegistryObject<Item> item,
            ResourceLocation model,
            Texture... modelTextures) {
        return handModelItem(item, model, ResourceLocation.fromNamespaceAndPath(MMM.MOD_ID, "item/" + item.getId().getPath()), modelTextures);
    }

    private SeparateTransformModelBuilder candleModel(
            RegistryObject<Item> item, ResourceLocation model,
            String baseTexture) {
        return handModelItem(item, model, ResourceLocation.fromNamespaceAndPath(MMM.MOD_ID, "item/candle/" + item.getId().getPath()),
                new Texture("all", baseTexture),
                new Texture("particle", baseTexture),
                new Texture("flame", "item/models/flame"));
    }

    //- Blocks

    private ItemModelBuilder simpleBlockItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                mcr("item/generated")).texture("layer0",
                mmm("item/" + item.getId().getPath()));
    }

    private ItemModelBuilder tallBlockItemWithDirPF(RegistryObject<Block> item, String dirPF) {
        return tallBlockItem(item, dirPF + "/", "");
    }

    private ItemModelBuilder tallBlockItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                mmm("item/tall_generated")).texture("layer0",
                mmm("item/" + item.getId().getPath()));
    }

    private ItemModelBuilder tallBlockItem(RegistryObject<Block> item, String prefix, String suffix) {
        return withExistingParent(item.getId().getPath(),
                mmm("item/tall_generated")).texture("layer0",
                mmm("item/" + prefix + item.getId().getPath() + suffix));
    }

    private ItemModelBuilder simpleBlockItem(RegistryObject<Block> item, String textureName) {
        return withExistingParent(item.getId().getPath(),
                mcr("item/generated")).texture("layer0", mmm(textureName));
    }

    public void fenceItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
                .texture("texture", mmm("block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void wallItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall", mmm("block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void buttonItem(RegistryObject<Block> block, RegistryObject<Block> blockTexture) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  mmm("block/" + ForgeRegistries.BLOCKS.getKey(blockTexture.get()).getPath()));
    }
}
