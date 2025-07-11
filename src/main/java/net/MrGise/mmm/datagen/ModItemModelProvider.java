package net.MrGise.mmm.datagen;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.block.ModBlocks;
import net.MrGise.mmm.item.ModItems;
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
        simpleItem(ModItems.ORE_DETECTOR);

        simpleItem(ModItems.BREADSTICK);

        simpleItem(ModItems.STRAWBERRY);

        simpleItem(ModItems.SKOAL);

        simpleItem(ModItems.SKIRON);
        simpleItem(ModItems.SKIRON_NUGGET);
        simpleItem(ModItems.RAW_SKIRON);

        simpleItem(ModItems.ACTINOLITE);

        /* Tools */
        handheldItem(ModItems.ACTIONLITE_SWORD);
        handheldItem(ModItems.ACTIONLITE_PICKAXE);

        handheldItem(ModItems.SKIRON_SWORD);
        handheldItem(ModItems.SKIRON_PICKAXE);
        handheldItem(ModItems.SKIRON_AXE);
        handheldItem(ModItems.SKIRON_SHOVEL);
        handheldItem(ModItems.SKIRON_HOE);
        handheldItem(ModItems.SKIRON_PAXEL);


        // Item models for blocks
        buttonItem(ModBlocks.SKYWOOD_BUTTON, ModBlocks.SKYWOOD_PLANKS);
        fenceItem(ModBlocks.SKYWOOD_FENCE, ModBlocks.SKYWOOD_PLANKS);
        wallItem(ModBlocks.SKYSOLID_WALL, ModBlocks.SKYSOLID);

        simpleBlockItem(ModBlocks.SKYWOOD_DOOR);

    }

    // Items
    /*
     Armor trims
     Templates located at models/references/trims
    */

    private ItemModelBuilder trimmedArmorItemBoots(RegistryObject<Item> item, String trim) {
        return withExistingParent(item.getId().getPath() + "_" + trim + "_trim",
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(MMM.MOD_ID, "item/" + item.getId().getPath())).texture("layer1",
                "minecraft:trims/items/boots_trim");
    }

    /* Normal Items */
    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(MMM.MOD_ID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(MMM.MOD_ID, "item/" + item.getId().getPath()));
    }

    // Blocks

    private ItemModelBuilder simpleBlockItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(MMM.MOD_ID, "item/" + item.getId().getPath()));
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
