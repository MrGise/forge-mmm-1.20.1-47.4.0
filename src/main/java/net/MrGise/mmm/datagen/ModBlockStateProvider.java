package net.MrGise.mmm.datagen;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.block.ModBlocks;
import net.MrGise.mmm.block.custom.AccessibleCropBlock;
import net.MrGise.mmm.block.custom.CucumberCropBlock;
import net.MrGise.mmm.block.custom.PortalBlock;
import net.MrGise.mmm.block.custom.StrawberryCropBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;
import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MMM.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        //. Test blocks

        blockWithItem(ModBlocks.TEST_BLOCK);
        blockWithItem(ModBlocks.ANIMATED_TEST_BLOCK);


        //-- Block with item

        blockWithItem(ModBlocks.SOUND_BLOCK);

        blockWithItem(ModBlocks.BROKEN_SKYSOLID);
        blockWithItem(ModBlocks.SKYSOLID);
        blockWithItem(ModBlocks.SKYSOIL);
        uniqueBottomCubeBottomTop(ModBlocks.HEAVENLY_GRASS_BLOCK.get(), "heavenly_grass_block", "skysoil");
        blockWithItem(ModBlocks.SKYWOOD_PLANKS);

        blockWithItem(ModBlocks.ACTINOLITE_ORE);
        blockWithItem(ModBlocks.SKIRON_ORE);
        blockWithItem(ModBlocks.SKOAL_ORE);
        blockWithItem(ModBlocks.SKIRON_BLOCK);
        blockWithItem(ModBlocks.RAW_SKIRON_BLOCK);
        blockWithItem(ModBlocks.SKOAL_BLOCK);



        //-- Other blocks

        stairsBlockWithItem(ModBlocks.SKYWOOD_STAIRS, (StairBlock) ModBlocks.SKYWOOD_STAIRS.get(), blockTexture(ModBlocks.SKYWOOD_PLANKS.get()));
        slabBlockWithItem(ModBlocks.SKYWOOD_SLAB, ((SlabBlock) ModBlocks.SKYWOOD_SLAB.get()), blockTexture(ModBlocks.SKYWOOD_PLANKS.get()), blockTexture(ModBlocks.SKYWOOD_PLANKS.get()));

        pressurePlateBlock((PressurePlateBlock) ModBlocks.SKYWOOD_PRESSURE_PLATE.get(), blockTexture(ModBlocks.SKYWOOD_PLANKS.get()));
        buttonBlock((ButtonBlock) ModBlocks.SKYWOOD_BUTTON.get(), blockTexture(ModBlocks.SKYWOOD_PLANKS.get()));
        fenceBlock((FenceBlock) ModBlocks.SKYWOOD_FENCE.get(), blockTexture(ModBlocks.SKYWOOD_PLANKS.get()));
        fenceGateBlock((FenceGateBlock) ModBlocks.SKYWOOD_FENCE_GATE.get(), blockTexture(ModBlocks.SKYWOOD_PLANKS.get()));

        wallBlock((WallBlock) ModBlocks.SKYSOLID_WALL.get(), blockTexture(ModBlocks.SKYSOLID.get()));

        doorBlockWithRenderType((DoorBlock) ModBlocks.SKYWOOD_DOOR.get(), modLoc("block/skywood_door_bottom"), modLoc("block/skywood_door_top"), "cutout");
        trapdoorBlockWithRenderType((TrapDoorBlock) ModBlocks.SKYWOOD_TRAPDOOR.get(), modLoc("block/skywood_trapdoor"), true, "cutout");

        makeCustomCrop((CucumberCropBlock)ModBlocks.CUCUMBER.get(), "cucumber_", "cucumber_", new ResourceLocation(MMM.MOD_ID, "cucumber_base"), new ResourceLocation(MMM.MOD_ID, "cucumber_base_tiny"), "0", 5, 6);
        makeCustomCrop((StrawberryCropBlock)ModBlocks.STRAWBERRY.get(), "strawberry", "strawberry_", new ResourceLocation(MMM.MOD_ID, "crop_cross"), new ResourceLocation(MMM.MOD_ID, "crop_cross"), "0", false, "block/strawberry");


        //-- Block Items

        blockItem(ModBlocks.SKYWOOD_PRESSURE_PLATE);
        blockItem(ModBlocks.SKYWOOD_FENCE_GATE);
        blockItem(ModBlocks.SKYWOOD_TRAPDOOR, "_bottom");

        portalBlock(ModBlocks.PORTAL_BLOCK, "portal_block");

    }

    private void simpleCubeBottomTop(Block block, String textureName) {
        cubeBottomTop(block, textureName + "_top", textureName + "_side", textureName + "_bottom", textureName);
    }

    private void uniqueBottomCubeBottomTop(Block block, String topNSide, String bottom) {
        cubeBottomTop(block, topNSide + "_top", topNSide + "_side", bottom, topNSide);
    }

    private void cubeBottomTop(Block block, String topName, String sideName, String bottomName, String modelName) {
        // Block model without tintindex — just uses baked-in texture colors
        ModelFile model = models().cubeBottomTop(
                modelName, // model name
                modLoc("block/" + sideName),   // side texture
                modLoc("block/" + bottomName), // bottom texture
                modLoc("block/" + topName)     // top texture
        );

        // Simple blockstate with one model
        simpleBlockWithItem(block, model);
    }

    //Region Crops

    public void makeSimpleCrop(CropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> states(state, block, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] states(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(((CucumberCropBlock) block).getAgeProperty()),
                new ResourceLocation(MMM.MOD_ID, "block/" + textureName + state.getValue(((CucumberCropBlock) block).getAgeProperty()))).renderType("cutout"));

        return models;
    }

    public void makeCustomCrop(AccessibleCropBlock block,
                               String modelName,
                               String textureName,
                               ResourceLocation defaultParent,
                               ResourceLocation customParent,
                               String textureLayer,
                               Integer... specialStages) {

        Set<Integer> stageSet = Set.of(specialStages);

        getVariantBuilder(block).forAllStates(state -> {
            int age = state.getValue(block.getAgeProperty());
            boolean isCustomStage = stageSet.contains(age);

            return new ConfiguredModel[]{
                    new ConfiguredModel(
                            models().getBuilder(modelName + age)
                                    .parent(models().getExistingFile(isCustomStage ? customParent : defaultParent))
                                    .texture(textureLayer, new ResourceLocation(MMM.MOD_ID, "block/" + textureName + age))
                                    .renderType("cutout")
                    )
            };
        });
    }

    public void makeCustomCrop(AccessibleCropBlock block,
                               String modelName,
                               String textureName,
                               ResourceLocation defaultParent,
                               ResourceLocation customParent,
                               String textureLayer,
                               boolean hasCustomParticleName,
                               String customParticleName,
                               Integer... specialStages) {

        Set<Integer> stageSet = Set.of(specialStages);

        getVariantBuilder(block).forAllStates(state -> {
            int age = state.getValue(block.getAgeProperty());
            boolean isCustomStage = stageSet.contains(age);

            if (hasCustomParticleName) {
                return new ConfiguredModel[]{
                        new ConfiguredModel(
                                models().getBuilder(modelName + age)
                                        .parent(models().getExistingFile(isCustomStage ? customParent : defaultParent))
                                        .texture(textureLayer, new ResourceLocation(MMM.MOD_ID, "block/" + textureName + age))
                                        .texture("particle", new ResourceLocation(MMM.MOD_ID, customParticleName))
                                        .renderType("cutout")
                        )
                };
            } else {
                return new ConfiguredModel[]{
                        new ConfiguredModel(
                                models().getBuilder(modelName + age)
                                        .parent(models().getExistingFile(isCustomStage ? customParent : defaultParent))
                                        .texture(textureLayer, new ResourceLocation(MMM.MOD_ID, "block/" + textureName + age))
                                        .texture("particle", new ResourceLocation(MMM.MOD_ID, "block/" + textureName + age))
                                        .renderType("cutout")
                        )
                };
            }
        });
    }


    //End

    private void portalBlock(RegistryObject<Block> pBlock, String name) {
        getVariantBuilder(pBlock.get()).forAllStates(state -> {
            if(state.getValue(PortalBlock.EYE)) {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll(name + "_eye",
                        new ResourceLocation(MMM.MOD_ID, "block/" + name + "_eye")))};
            } else {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll(name + "_noeye",
                        new ResourceLocation(MMM.MOD_ID, "block/" + name + "_noeye")))};
            }
        });
        simpleBlockItem(pBlock.get(), models().cubeAll(name + "_eye",
                new ResourceLocation(MMM.MOD_ID, "block/" + name + "_eye")));
    }

    private void blockItem(RegistryObject<Block> blockRegistryObject, String appendix) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile("mmm:block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath() + appendix));
    }

    private void blockItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile("mmm:block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
    }


    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void stairsBlockWithItem(RegistryObject<Block> block, StairBlock stairBlock, ResourceLocation texture) {
        stairsBlock(stairBlock, texture);
        blockItem(block);
    }

    public void slabBlockWithItem(RegistryObject<Block> pBlock, SlabBlock block, ResourceLocation doubleslab, ResourceLocation texture) {
        this.slabBlock(block, doubleslab, texture, texture, texture);
        blockItem(pBlock);
    }

}
