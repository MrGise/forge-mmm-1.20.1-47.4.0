package net.MrGise.mmm.datagen;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.registry.ModBlocks;
import net.MrGise.mmm.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MMM.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Item models

        simpleItem(ModItems.TEST_ITEM);


        //- Treasures

        simpleItem(ModItems.GLIDE_ARMOR_TRIM_SMITHING_TEMPLATE);

        simpleItem(ModItems.DROPPY_LIKES_RICOCHET_MUSIC_DISC);
        simpleItem(ModItems.DROPPY_LIKES_EVERYTHING_MUSIC_DISC);

        simpleItem(ModItems.SKYSOLID_TABLET);

        simpleItem(ModItems.ORE_DETECTOR);

        //- Food

        simpleItem(ModItems.BREADSTICK);

        simpleItem(ModItems.STRAWBERRY);
        simpleItem(ModItems.STRAWBERRY_SEEDS);

        simpleItem(ModItems.CUCUMBER_SEEDS);
        simpleItem(ModItems.CUCUMBER);
        simpleItem(ModItems.CUT_CUCUMBER);

        simpleItem(ModItems.POMEGRANATE);
        simpleItem(ModItems.POMEGRANATE_SLICE);
        simpleItem(ModItems.POMEGRANATE_EMPTY_SLICE);
        simpleItem(ModItems.POMEGRANATE_SEEDS);

        simpleItem(ModItems.APPLE_SLICE);
        simpleItem(ModItems.HONEYED_APPLE_SLICE);

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

        /*- Tools -*/
        handheldItem(ModItems.ACTIONLITE_SWORD);
        handheldItem(ModItems.ACTIONLITE_PICKAXE);

        handheldItem(ModItems.SKIRON_SWORD);
        handheldItem(ModItems.SKIRON_PICKAXE);
        handheldItem(ModItems.SKIRON_AXE);
        handheldItem(ModItems.SKIRON_SHOVEL);
        handheldItem(ModItems.SKIRON_HOE);
        handheldItem(ModItems.SKIRON_KNIFE);
        handheldItem(ModItems.SKIRON_PAXEL);


        //- Item models for blocks
        buttonItem(ModBlocks.SKYWOOD_BUTTON, ModBlocks.SKYWOOD_PLANKS);
        fenceItem(ModBlocks.SKYWOOD_FENCE, ModBlocks.SKYWOOD_PLANKS);
        wallItem(ModBlocks.SKYSOLID_WALL, ModBlocks.SKYSOLID);

        notSoSimpleBlockItem(ModBlocks.HEAVENLY_GRASS, false, "_top");
        notSoSimpleBlockItemUnseperate(ModBlocks.HEAVENLY_GRASS, false, "_short");
        itemWithPredicate(ModBlocks.HEAVENLY_GRASS.get().asItem(), "long",
                modLoc("item/heavenly_grass_short"), modLoc("item/heavenly_grass_top"));

        simpleBlockItem(ModBlocks.SKYWOOD_DOOR);
        simpleBlockItem(ModBlocks.OXALIS, "block/oxalis");

    }

    // Items
    /*
    Armor trims
    Templates located at models/references/trims
    */


    private ItemModelBuilder notSoSimpleBlockItem(RegistryObject<Block> item, boolean before, String name) {
        if (before) {
            return withExistingParent(name + item.getId().getPath(),
                    new ResourceLocation("item/generated")).texture("layer0",
                    new ResourceLocation(MMM.MOD_ID, "block/" + name + item.getId().getPath()));
        } else {
            return withExistingParent(item.getId().getPath() + name,
                    new ResourceLocation("item/generated")).texture("layer0",
                    new ResourceLocation(MMM.MOD_ID, "block/" + item.getId().getPath() + name));
        }
    }

    private ItemModelBuilder notSoSimpleBlockItemUnseperate(RegistryObject<Block> item, boolean before, String name) {
        if (before) {
            return withExistingParent(name + item.getId().getPath(),
                    new ResourceLocation("item/generated")).texture("layer0",
                    new ResourceLocation(MMM.MOD_ID, "block/" + item.getId().getPath()));
        } else {
            return withExistingParent(item.getId().getPath() + name,
                    new ResourceLocation("item/generated")).texture("layer0",
                    new ResourceLocation(MMM.MOD_ID, "block/" + item.getId().getPath()));
        }
    }

    private ItemModelBuilder notSoSimpleItem(RegistryObject<Item> item, String name) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(MMM.MOD_ID, "item/" + item.getId().getPath() + name));
    }

    private void itemWithPredicate(Item item, String predicateName,
                                    ResourceLocation baseModel,
                                    ResourceLocation overrideModel) {
        ItemModelBuilder builder = getBuilder(item.toString())
                .parent(getExistingFile(baseModel));

        builder.override()
                .predicate(new ResourceLocation(MMM.MOD_ID, predicateName), 1.0f)
                .model(getExistingFile(overrideModel));
    }

    /* Normal Items */
    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(MMM.MOD_ID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(MMM.MOD_ID, "item/" + item.getId().getPath()));
    }

    //- Blocks

    private ItemModelBuilder simpleBlockItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(MMM.MOD_ID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleBlockItem(RegistryObject<Block> item, String textureName) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(MMM.MOD_ID, textureName));
    }

    public void fenceItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  new ResourceLocation(MMM.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void wallItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  new ResourceLocation(MMM.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void buttonItem(RegistryObject<Block> block, RegistryObject<Block> blockTexture) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  new ResourceLocation(MMM.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(blockTexture.get()).getPath()));
    }
}
