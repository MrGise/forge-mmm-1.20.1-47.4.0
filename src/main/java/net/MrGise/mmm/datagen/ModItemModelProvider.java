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

        simpleItem(ModItems.ORE_DETECTOR);
        simpleItem(ModItems.SKOAL);
        simpleItem(ModItems.BREADSTICK);
        simpleItem(ModItems.SKIRON);
        simpleItem(ModItems.RAW_SKIRON);

        buttonItem(ModBlocks.SKYWOOD_BUTTON, ModBlocks.SKYWOOD_PLANKS);

    }

    public void buttonItem(RegistryObject<Block> block, RegistryObject<Block> blockTexture) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  new ResourceLocation(MMM.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(blockTexture.get()).getPath()));
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(MMM.MOD_ID, "item/" + item.getId().getPath()));
    }

}
