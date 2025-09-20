package net.MrGise.mmm.datagen;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.block.*;
import net.MrGise.mmm.registry.ModBlocks;
import net.MrGise.mmm.registry.create.ModCreateBlocks;
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
import net.minecraftforge.client.model.generators.ModelBuilder.FaceRotation;

import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

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

        portalBlockAlt(ModCreateBlocks.CONNECTING_PORTAL_BLOCK, "connecting_portal_block");

        blockWithItem(ModCreateBlocks.EXAMPLE_CONNECTION.get());

        blockWithItem(ModBlocks.SOUND_BLOCK);

        //| Skyland
        blockWithItem(ModBlocks.BROKEN_SKYSOLID);
        blockWithItem(ModBlocks.SKYSOLID);
        blockWithItem(ModBlocks.SKYSOIL);
        uniqueBottomCubeBottomTop(ModBlocks.HEAVENLY_GRASS_BLOCK.get(), "heavenly_grass_block", "skysoil", true);
        customGrass((CustomGrass) ModBlocks.HEAVENLY_GRASS.get(), "heavenly_grass");

        blockWithItem(ModBlocks.SKYWOOD_PLANKS);
        logBlockWithItem((RotatedPillarBlock) ModBlocks.SKYWOOD_LOG.get(),
                "skywood_log", "skywood_log_top", "skywood_log");
        logBlockWithItem((RotatedPillarBlock) ModBlocks.STRIPPED_SKYWOOD_LOG.get(),
                "stripped_skywood_log", "stripped_skywood_log_top", "stripped_skywood_log");

        blockWithItem(ModBlocks.ACTINOLITE_ORE);
        blockWithItem(ModBlocks.SKIRON_ORE);
        blockWithItem(ModBlocks.SKOAL_ORE);
        blockWithItem(ModBlocks.SKIRON_BLOCK);
        blockWithItem(ModBlocks.RAW_SKIRON_BLOCK);
        blockWithItem(ModBlocks.SKOAL_BLOCK);

        //. World
        blockWithItem(ModBlocks.MANA_ORE);

        thinLogBlockWithItem(((ThinLogBlock) ModBlocks.THIN_PINE_LOG.get()),
                "thin_pine_log", "thin_pine_log_top", "thin_pine_log");

        //\ Dimensions
        blockWithItem(ModBlocks.NULL_BLOCK);


        //-- Other blocks
        //| Skyland

        stairsBlockWithItem(ModBlocks.SKYWOOD_STAIRS, (StairBlock) ModBlocks.SKYWOOD_STAIRS.get(), blockTexture(ModBlocks.SKYWOOD_PLANKS.get()));
        slabBlockWithItem(ModBlocks.SKYWOOD_SLAB, ((SlabBlock) ModBlocks.SKYWOOD_SLAB.get()), blockTexture(ModBlocks.SKYWOOD_PLANKS.get()), blockTexture(ModBlocks.SKYWOOD_PLANKS.get()));

        pressurePlateBlock((PressurePlateBlock) ModBlocks.SKYWOOD_PRESSURE_PLATE.get(), blockTexture(ModBlocks.SKYWOOD_PLANKS.get()));
        buttonBlock((ButtonBlock) ModBlocks.SKYWOOD_BUTTON.get(), blockTexture(ModBlocks.SKYWOOD_PLANKS.get()));
        fenceBlock((FenceBlock) ModBlocks.SKYWOOD_FENCE.get(), blockTexture(ModBlocks.SKYWOOD_PLANKS.get()));
        fenceGateBlock((FenceGateBlock) ModBlocks.SKYWOOD_FENCE_GATE.get(), blockTexture(ModBlocks.SKYWOOD_PLANKS.get()));

        wallBlock((WallBlock) ModBlocks.SKYSOLID_WALL.get(), blockTexture(ModBlocks.SKYSOLID.get()));

        doorBlockWithRenderType((DoorBlock) ModBlocks.SKYWOOD_DOOR.get(), modLoc("block/skywood_door_bottom"), modLoc("block/skywood_door_top"), "cutout");
        trapdoorBlockWithRenderType((TrapDoorBlock) ModBlocks.SKYWOOD_TRAPDOOR.get(), modLoc("block/skywood_trapdoor"), true, "cutout");

        //. World

        makeCustomCrop((CucumberCropBlock)ModBlocks.CUCUMBER.get(), "cucumber_", "cucumber_", new ResourceLocation(MMM.MOD_ID, "cucumber_base"), new ResourceLocation(MMM.MOD_ID, "cucumber_base_tiny"), "0", 5, 6);
        makeCustomCrop((StrawberryCropBlock)ModBlocks.STRAWBERRY.get(), "strawberry", "strawberry_", new ResourceLocation(MMM.MOD_ID, "crop_cross"), new ResourceLocation(MMM.MOD_ID, "crop_cross"), "0", false, "block/strawberry");

        flower(ModBlocks.OXALIS.get());
        pottedFlower(ModBlocks.POTTED_OXALIS.get(), "potted_oxalis", "oxalis");

        //-- Block Items

        blockItem(ModBlocks.SKYWOOD_PRESSURE_PLATE);
        blockItem(ModBlocks.SKYWOOD_FENCE_GATE);
        blockItem(ModBlocks.SKYWOOD_TRAPDOOR, "_bottom");

        portalBlock(ModBlocks.PORTAL_BLOCK, "portal_block");

    }

    private void pottedFlower(Block block, String name, String plantName) {
        simpleBlock(block, models().singleTexture(name, new ResourceLocation("flower_pot_cross"), "plant",
                modLoc("block/" + plantName)).renderType("cutout"));
    }

    private void flower(Block block) {
        simpleBlock(block,
                models().cross(blockTexture(block).getPath(), blockTexture(block)).renderType("cutout"));

        //- Has no item model
    }

    private void customGrass(CustomGrass block, String textureName) {
        // Short variant (cross model)
        ModelFile shortModel = models()
                .withExistingParent("block/" + textureName, "block/cross")
                .texture("cross", "block/" + textureName).renderType("cutout");

        // Tall grass bottom
        ModelFile bottomModel = models()
                .withExistingParent("block/" + textureName + "_bottom", "block/cross")
                .texture("cross", "block/" + textureName + "_bottom").renderType("cutout");

        // Tall grass top
        ModelFile topModel = models()
                .withExistingParent("block/" + textureName + "_top", "block/cross")
                .texture("cross", "block/" + textureName + "_top").renderType("cutout");

        // Blockstates
        getVariantBuilder(block).forAllStates(state -> {
            return switch (state.getValue(CustomGrass.LENGTH)) {
                case SHORT -> ConfiguredModel.allYRotations(shortModel, 0, false);
                case BOTTOM -> new ConfiguredModel[] {
                        new ConfiguredModel(bottomModel)
                };
                case TOP -> new ConfiguredModel[] {
                        new ConfiguredModel(topModel)
                };
            };
        });

        // Item model (always the short variant)
        itemModels().withExistingParent(
                ForgeRegistries.BLOCKS.getKey(block).getPath(), "item/generated"
        ).texture("layer0", "block/" + textureName);
    }

    private void simpleCubeBottomTop(Block block, String textureName, boolean rotateTop) {
        cubeBottomTop(block, textureName + "_top", textureName + "_side", textureName + "_bottom", textureName, rotateTop);
    }

    private void uniqueBottomCubeBottomTop(Block block, String topNSide, String bottom, boolean rotateTop) {
        cubeBottomTop(block, topNSide + "_top", topNSide + "_side", bottom, topNSide, rotateTop);
    }

    private void cubeBottomTop(Block block,
                                String topName, String sideName, String bottomName,
                                String modelName,
                                boolean rotateTop) {
        if (rotateTop) {
            cubeBottomTopRandomRotation(block, bottomName, topName, sideName);
        } else {
            cubeBottomTop(block, topName, sideName, bottomName, modelName);
        }
    }

    private void cubeBottomTop(Block block,
                                String topName, String sideName, String bottomName,
                                String modelName) {

        // Block model that uses block/ textures
        ModelFile model = models().cubeBottomTop(
                modelName,
                modLoc("block/" + sideName),
                modLoc("block/" + bottomName),
                modLoc("block/" + topName)
        );

            simpleBlockWithItem(block, model);
    }

    protected void cubeBottomTopRandomRotation(Block block, String bottomName, String topName, String sideName) {
        ResourceLocation side = modLoc("block/" + sideName);
        ResourceLocation bottom = modLoc("block/" + bottomName);
        ResourceLocation top = modLoc("block/" + topName);

        FaceRotation[] rotations = {FaceRotation.ZERO, FaceRotation.CLOCKWISE_90, FaceRotation.UPSIDE_DOWN, FaceRotation.COUNTERCLOCKWISE_90};

        // Create four models with top rotated by 0, 90, 180, 270 degrees
        for (int i = 0; i < rotations.length; i++) {
            String modelName = blockName(block) + "_rot" + (i * 90);
            models().withExistingParent(modelName, mcLoc("block/cube_bottom_top"))
                    .texture("bottom", bottom)
                    .texture("top", top)
                    .texture("side", side)
                    .element()
                    .from(0, 0, 0).to(16, 16, 16)
                    .face(Direction.UP).texture("#top").rotation(rotations[i]).end()
                    .face(Direction.DOWN).texture("#bottom").end()
                    .face(Direction.NORTH).texture("#side").end()
                    .face(Direction.SOUTH).texture("#side").end()
                    .face(Direction.WEST).texture("#side").end()
                    .face(Direction.EAST).texture("#side").end()
                    .end();
        }

        // Blockstate definition: all four models, no rotation on block itself
        getVariantBuilder(block).partialState().addModels(
                new ConfiguredModel(models().getExistingFile(modLoc("block/" + blockName(block) + "_rot0"))),
                new ConfiguredModel(models().getExistingFile(modLoc("block/" + blockName(block) + "_rot90"))),
                new ConfiguredModel(models().getExistingFile(modLoc("block/" + blockName(block) + "_rot180"))),
                new ConfiguredModel(models().getExistingFile(modLoc("block/" + blockName(block) + "_rot270")))
        );

        ModelFile model = models().getExistingFile(modLoc("block/" + blockName(block) + "_rot0"));

        simpleBlockItem(block, model);

    }

    private String blockName(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block).getPath();
    }

    private void logBlockWithItem(RotatedPillarBlock block, String sideName, String topName, String baseModelName) {
        ResourceLocation side = modLoc("block/" + sideName);
        ResourceLocation top = modLoc("block/" + topName);

        // Vertical log model
        ModelFile vertical = models().cubeColumn(baseModelName, side, top);

        // Horizontal log model
        ModelFile horizontal = models().cubeColumnHorizontal(baseModelName + "_horizontal", side, top);

        // Blockstate builder: chooses correct model based on AXIS
        axisBlock(block, vertical, horizontal);

        // Item model uses vertical log model
        simpleBlockItem(block, vertical);
    }

    private void thinLogBlockWithItem(RotatedPillarBlock block, String sideName, String topName, String baseModelName) {
        ResourceLocation side = modLoc("block/" + sideName);
        ResourceLocation top = modLoc("block/" + topName);

        // === Axis-specific models just extend parent ===
        ModelFile vertical = models().withExistingParent(baseModelName, modLoc("block/thin_log"))
                .texture("side", side).texture("end", top);

        ModelFile logX = models().withExistingParent(baseModelName + "_x", modLoc("block/thin_log_x"))
                .texture("side", side).texture("end", top);

        ModelFile logZ = models().withExistingParent(baseModelName + "_z", modLoc("block/thin_log_z"))
                .texture("side", side).texture("end", top);

        // === Blockstate builder ===
        getVariantBuilder(block)
                .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.Y)
                .modelForState().modelFile(vertical).addModel()
                .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.X)
                .modelForState().modelFile(logX).addModel()
                .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.Z)
                .modelForState().modelFile(logZ).addModel();

        // === Item model ===
        simpleBlockItem(block, vertical);
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
                                String modelName, String textureName,
                                ResourceLocation defaultParent, ResourceLocation customParent,
                                String textureLayer, Integer... specialStages) {

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
                                String modelName, String textureName,
                                ResourceLocation defaultParent, ResourceLocation customParent,
                                String textureLayer, boolean hasCustomParticleName,
                                String customParticleName, Integer... specialStages) {

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

    private void portalBlockAlt(Supplier<? extends Block> blockSupplier, String name) {
        Block pBlock = blockSupplier.get();
        getVariantBuilder(pBlock).forAllStates(state -> {
            if(state.getValue(PortalBlock.EYE)) {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll(name + "_on",
                        new ResourceLocation(MMM.MOD_ID, "block/" + name + "_on")))};
            } else {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll(name + "_off",
                        new ResourceLocation(MMM.MOD_ID, "block/" + name + "_off")))};
            }
        });
        simpleBlockItem(pBlock, models().cubeAll(name + "_on",
                new ResourceLocation(MMM.MOD_ID, "block/" + name + "_on")));
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

    private void blockWithItem(Block blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject, cubeAll(blockRegistryObject));
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
