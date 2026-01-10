package net.MrGise.mmm.datagen.model;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.block.*;
import net.MrGise.mmm.block.crop.AccessibleCropBlock;
import net.MrGise.mmm.block.crop.CucumberCropBlock;
import net.MrGise.mmm.block.crop.StrawberryCropBlock;
import net.MrGise.mmm.registry.front.ModBlocks;
import net.MrGise.mmm.registry.create.ModCreateBlocks;
import net.MrGise.mmm.resource.TripleBlockPart;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.client.model.generators.ModelBuilder.FaceRotation;

import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

// Generates blockstates
public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MMM.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        //. Test blocks

        blockWithItemDirFix(ModBlocks.TEST_BLOCK);
        blockWithItemDirFix(ModBlocks.ANIMATED_TEST_BLOCK);


        //. Misc

        portalBlockAlt(ModCreateBlocks.CONNECTING_PORTAL_BLOCK, "connecting_portal_block");

        portalBlock(ModBlocks.PORTAL_BLOCK, "portal_block");

        blockWithItem(ModCreateBlocks.EXAMPLE_CONNECTION.get());

        blockWithItem(ModBlocks.SOUND_BLOCK);

        //-- Normal blocks

        uniqueCubeRotate(ModBlocks.BOWYERY_TABLE.get(), "bowyery_table",
                ResourceLocation.fromNamespaceAndPath("mmm", "block/bowyery_table_bottom"),
                ResourceLocation.fromNamespaceAndPath("mmm", "block/bowyery_table_top"),
                ResourceLocation.fromNamespaceAndPath("mmm", "block/bowyery_table_side"),
                ResourceLocation.fromNamespaceAndPath("mmm", "block/bowyery_table_side"),
                ResourceLocation.fromNamespaceAndPath("mmm", "block/bowyery_table_bow"),
                ResourceLocation.fromNamespaceAndPath("mmm", "block/bowyery_table_string"));


        //| Skyland
        blockWithItem(ModBlocks.BROKEN_SKYSOLID);
        blockWithItem(ModBlocks.SKYSOLID);
        blockWithItem(ModBlocks.SKYSOIL);
        uniqueBottomCubeBottomTop(ModBlocks.HEAVENLY_GRASS_BLOCK.get(), "heavenly_grass_block", "skysoil", true);
        customGrass((CustomGrass) ModBlocks.HEAVENLY_GRASS.get(), "heavenly_grass");

        blockWithItem(ModBlocks.SKYGROUND);
        blockWithItem(ModBlocks.BROKEN_SKYGROUND);
        blockWithItem(ModBlocks.SKYDIRT);
        uniqueBottomCubeBottomTop(ModBlocks.HEAVENLY_GRASS_BLOCK_SKYDIRT.get(), "heavenly_grass_block_skydirt", "skydirt", true);

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

        pressurePlateWithItem(ModBlocks.SKYWOOD_PRESSURE_PLATE, blockTexture(ModBlocks.SKYWOOD_PLANKS.get()));
        buttonBlock((ButtonBlock) ModBlocks.SKYWOOD_BUTTON.get(), blockTexture(ModBlocks.SKYWOOD_PLANKS.get()));
        fenceBlock((FenceBlock) ModBlocks.SKYWOOD_FENCE.get(), blockTexture(ModBlocks.SKYWOOD_PLANKS.get()));
        fenceGateBlockWithItem(ModBlocks.SKYWOOD_FENCE_GATE, blockTexture(ModBlocks.SKYWOOD_PLANKS.get()));

        wallBlock((WallBlock) ModBlocks.SKYSOLID_WALL.get(), blockTexture(ModBlocks.SKYSOLID.get()));

        wallBlock((WallBlock) ModBlocks.SKYGROUND_WALL.get(), blockTexture(ModBlocks.SKYGROUND.get()));

        doorBlockWithRenderType((DoorBlock) ModBlocks.SKYWOOD_DOOR.get(), modLoc("block/skywood_door_bottom"), modLoc("block/skywood_door_top"), "cutout");
        tripleDoorBlockWithRenderType((TripleDoorBlock) ModBlocks.SKYWOOD_TRIPLE_DOOR.get(), "door/skywood_triple_door", "cutout");
        trapdoorBlockWithRenderTypeAndItem(ModBlocks.SKYWOOD_TRAPDOOR, modLoc("block/skywood_trapdoor"), true, "cutout");

        //. World

        makeCustomCrop((CucumberCropBlock)ModBlocks.CUCUMBER.get(), "cucumber_", "cucumber_", new ResourceLocation(MMM.MOD_ID, "cucumber_base"), new ResourceLocation(MMM.MOD_ID, "cucumber_base_tiny"), "0", 5, 6);
        makeCustomCrop((StrawberryCropBlock)ModBlocks.STRAWBERRY.get(), "strawberry", "strawberry_", new ResourceLocation(MMM.MOD_ID, "crop_cross"), new ResourceLocation(MMM.MOD_ID, "crop_cross"), "0", false, "block/strawberry");

        flower(ModBlocks.OXALIS.get());
        pottedFlower(ModBlocks.POTTED_OXALIS.get(), "potted_oxalis", "oxalis");

        tripleDoorBlockWithRenderType((TripleDoorBlock) ModBlocks.ACACIA_TRIPLE_DOOR.get(), "door/acacia_triple_door", "cutout");
        tripleDoorBlock((TripleDoorBlock) ModBlocks.BIRCH_TRIPLE_DOOR.get(), "door/birch_triple_door");
        tripleDoorBlock((TripleDoorBlock) ModBlocks.CRIMSON_TRIPLE_DOOR.get(), "door/crimson_triple_door");
        tripleDoorBlock((TripleDoorBlock) ModBlocks.DARK_OAK_TRIPLE_DOOR.get(), "door/dark_oak_triple_door");
        tripleDoorBlockWithRenderType((TripleDoorBlock) ModBlocks.IRON_TRIPLE_DOOR.get(), "door/iron_triple_door", "cutout");
        tripleDoorBlockWithRenderType((TripleDoorBlock) ModBlocks.JUNGLE_TRIPLE_DOOR.get(), "door/jungle_triple_door", "cutout");
        tripleDoorBlock((TripleDoorBlock) ModBlocks.MANGROVE_TRIPLE_DOOR.get(), "door/mangrove_triple_door");
        tripleDoorBlockWithRenderType((TripleDoorBlock) ModBlocks.OAK_TRIPLE_DOOR.get(), "door/oak_triple_door", "cutout");
        tripleDoorBlock((TripleDoorBlock) ModBlocks.SPRUCE_TRIPLE_DOOR.get(), "door/spruce_triple_door");

    }

    private void uniqueCubeRotate(Block block, String name, ResourceLocation bottom, ResourceLocation top,
                                  ResourceLocation north, ResourceLocation south, ResourceLocation east, ResourceLocation west) {
        ModelFile model = models().cube(
                name,
                bottom, top,
                north, south, east, west
        ).texture("particle", bottom);

        getVariantBuilder(block)
                .forAllStates(state -> {
                    int yRot = switch (state.getValue(HorizontalDirectionalBlock.FACING)) {
                        case EAST  -> 90;
                        case SOUTH -> 180;
                        case WEST  -> 270;
                        default -> 0;
                    };

                    return ConfiguredModel.builder()
                            .modelFile(model)
                            .rotationY(yRot)
                            .build();
                });

        simpleBlockItem(block, model);
    }

    private void uniqueCube(Block block, String name, ResourceLocation bottom, ResourceLocation top,
                            ResourceLocation north, ResourceLocation south, ResourceLocation east, ResourceLocation west) {
        ModelFile model = models().cube(
                name,
                bottom, top,
                north, south, east, west
        );

        simpleBlockWithItem(block, model);
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

    public void makeSimpleCrop(CropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> states(state, block, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
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

    private void blockWithItemDirFix(RegistryObject<Block> blockRO) {
        Block block = blockRO.get();
        String path = blockRO.getId().getPath();

        // blockstate
        simpleBlock(block, models().cubeAll("block/" + path, modLoc("block/" + path)));

        // item model
        itemModels().withExistingParent("item/" + path, modLoc("block/" + path));
    }

    private void blockWithItem(Block block) {
        simpleBlockWithItem(block, cubeAll(block));
    }

    private void stairsBlockWithItem(RegistryObject<Block> block, StairBlock stairBlock, ResourceLocation texture) {
        stairsBlock(stairBlock, texture);
        blockItem(block);
    }

    public void slabBlockWithItem(RegistryObject<Block> pBlock, SlabBlock block, ResourceLocation doubleslab, ResourceLocation texture) {
        this.slabBlock(block, doubleslab, texture, texture, texture);
        blockItem(pBlock);
    }

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
    
    public void pressurePlateWithItem(RegistryObject<Block> block, ResourceLocation texture) {
        pressurePlateBlock((PressurePlateBlock) block.get(), texture);
        blockItem(block);
    }
    
    public void trapdoorBlockWithRenderTypeAndItem(RegistryObject<Block> block, ResourceLocation location, boolean orientable, String renderType) {
        trapdoorBlockWithRenderType((TrapDoorBlock) block.get(), location, orientable, renderType);
        blockItem(block, "_bottom");
    }
    
    public void fenceGateBlockWithItem(RegistryObject<Block> block, ResourceLocation location) {
        fenceGateBlock((FenceGateBlock) block.get(), location);
        blockItem(block);
    }

    public void tripleDoorBlockWithRenderType(TripleDoorBlock block, ResourceLocation bottom, ResourceLocation middle, ResourceLocation top, String renderType) {
        tripleDoorBlockInternalWithRenderType(block, key(block).toString(), bottom, middle, top, ResourceLocation.tryParse(renderType));
    }

    public void tripleDoorBlockWithRenderType(TripleDoorBlock block, String name, String renderType) {
        tripleDoorBlockInternalWithRenderType(block, key(block).toString(), modLoc("block/" + name + "_bottom"), modLoc("block/" + name + "_middle"), modLoc("block/" + name + "_top"), ResourceLocation.tryParse(renderType));
    }

    public void tripleDoorBlock(TripleDoorBlock block, ResourceLocation bottom, ResourceLocation middle, ResourceLocation top) {
        tripleDoorBlockInternal(block, key(block).toString(), bottom, middle, top);
    }

    public void tripleDoorBlock(TripleDoorBlock block, String name) {
        tripleDoorBlockInternal(block, key(block).toString(), modLoc("block/" + name + "_bottom"), modLoc("block/" + name + "_middle"), modLoc("block/" + name + "_top"));
    }

    private void tripleDoorBlockInternalWithRenderType(TripleDoorBlock block, String baseName, ResourceLocation bottom, ResourceLocation middle, ResourceLocation top, ResourceLocation renderType) {
        ModelFile bottomLeft = tripleDoorBottomLeft(baseName + "_bottom_left", bottom, middle, top).renderType(renderType);
        ModelFile bottomLeftOpen = tripleDoorBottomLeftOpen(baseName + "_bottom_left_open", bottom, middle, top).renderType(renderType);
        ModelFile bottomRight = tripleDoorBottomRight(baseName + "_bottom_right", bottom, middle, top).renderType(renderType);
        ModelFile bottomRightOpen = tripleDoorBottomRightOpen(baseName + "_bottom_right_open", bottom, middle, top).renderType(renderType);
        ModelFile middleLeft = tripleDoorMiddleLeft(baseName + "_middle_left", bottom, middle, top).renderType(renderType);
        ModelFile middleLeftOpen = tripleDoorMiddleLeftOpen(baseName + "_middle_left_open", bottom, middle, top).renderType(renderType);
        ModelFile middleRight = tripleDoorMiddleRight(baseName + "_middle_right", bottom, middle, top).renderType(renderType);
        ModelFile middleRightOpen = tripleDoorMiddleRightOpen(baseName + "_middle_right_open", bottom, middle, top).renderType(renderType);
        ModelFile topLeft = tripleDoorTopLeft(baseName + "_top_left", bottom, middle, top).renderType(renderType);
        ModelFile topLeftOpen = tripleDoorTopLeftOpen(baseName + "_top_left_open", bottom, middle, top).renderType(renderType);
        ModelFile topRight = tripleDoorTopRight(baseName + "_top_right", bottom, middle, top).renderType(renderType);
        ModelFile topRightOpen = tripleDoorTopRightOpen(baseName + "_top_right_open", bottom, middle, top).renderType(renderType);
        tripleDoorBlock(block,
                bottomLeft, bottomLeftOpen, bottomRight, bottomRightOpen,
                middleLeft, middleLeftOpen, middleRight, middleRightOpen,
                topLeft, topLeftOpen, topRight, topRightOpen);
    }

    private void tripleDoorBlockInternal(TripleDoorBlock block, String baseName, ResourceLocation bottom, ResourceLocation middle, ResourceLocation top) {
        ModelFile bottomLeft = tripleDoorBottomLeft(baseName + "_bottom_left", bottom, middle, top);
        ModelFile bottomLeftOpen = tripleDoorBottomLeftOpen(baseName + "_bottom_left_open", bottom, middle, top);
        ModelFile bottomRight = tripleDoorBottomRight(baseName + "_bottom_right", bottom, middle, top);
        ModelFile bottomRightOpen = tripleDoorBottomRightOpen(baseName + "_bottom_right_open", bottom, middle, top);
        ModelFile middleLeft = tripleDoorMiddleLeft(baseName + "_middle_left", bottom, middle, top);
        ModelFile middleLeftOpen = tripleDoorMiddleLeftOpen(baseName + "_middle_left_open", bottom, middle, top);
        ModelFile middleRight = tripleDoorMiddleRight(baseName + "_middle_right", bottom, middle, top);
        ModelFile middleRightOpen = tripleDoorMiddleRightOpen(baseName + "_middle_right_open", bottom, middle, top);
        ModelFile topLeft = tripleDoorTopLeft(baseName + "_top_left", bottom, middle, top);
        ModelFile topLeftOpen = tripleDoorTopLeftOpen(baseName + "_top_left_open", bottom, middle, top);
        ModelFile topRight = tripleDoorTopRight(baseName + "_top_right", bottom, middle, top);
        ModelFile topRightOpen = tripleDoorTopRightOpen(baseName + "_top_right_open", bottom, middle, top);
        tripleDoorBlock(block,
                bottomLeft, bottomLeftOpen, bottomRight, bottomRightOpen,
                middleLeft, middleLeftOpen, middleRight, middleRightOpen,
                topLeft, topLeftOpen, topRight, topRightOpen);
    }

    private void tripleDoorBlock(TripleDoorBlock block,
                                 ModelFile bottomLeft, ModelFile bottomLeftOpen,
                                 ModelFile bottomRight, ModelFile bottomRightOpen,
                                 ModelFile middleLeft, ModelFile middleLeftOpen,
                                 ModelFile middleRight, ModelFile middleRightOpen,
                                 ModelFile topLeft, ModelFile topLeftOpen,
                                 ModelFile topRight, ModelFile topRightOpen) {
        getVariantBuilder(block).forAllStatesExcept(state -> {
            int yRot = ((int) state.getValue(DoorBlock.FACING).toYRot()) + 90;
            boolean right = state.getValue(TripleDoorBlock.HINGE) == DoorHingeSide.RIGHT;
            boolean open = state.getValue(DoorBlock.OPEN);
            TripleBlockPart part = state.getValue(TripleDoorBlock.PART);
            boolean bottom = part == TripleBlockPart.LOWER;
            boolean middle = part == TripleBlockPart.MIDDLE;
            boolean top = part == TripleBlockPart.UPPER;
            if (open) {
                yRot += 90;
            }
            if (right && open) {
                yRot += 180;
            }
            yRot %= 360;

            ModelFile model = null;
            if (bottom && right && open) {
                model = bottomRightOpen;
            } else if (bottom && !right && open) {
                model = bottomLeftOpen;
            }
            if (bottom && right && !open) {
                model = bottomRight;
            } else if (bottom && !right && !open) {
                model = bottomLeft;
            }
            if (middle && right && open) {
                model = middleRightOpen;
            } else if (middle && !right && open) {
                model = middleLeftOpen;
            }
            if (middle && right && !open) {
                model = middleRight;
            } else if (middle && !right && !open) {
                model = middleLeft;
            }
            if (top && right && open) {
                model = topRightOpen;
            } else if (top && !right && open) {
                model = topLeftOpen;
            }
            if (top && right && !open) {
                model = topRight;
            } else if (top && !right && !open) {
                model = topLeft;
            }

            return ConfiguredModel.builder().modelFile(model)
                    .rotationY(yRot)
                    .build();
        }, DoorBlock.POWERED);
    }

    public BlockModelBuilder tripleDoorBottomLeft(String name, ResourceLocation bottom, ResourceLocation middle, ResourceLocation top) {
        return tripleDoor(name, "triple_door_bottom_left", bottom, middle, top);
    }

    public BlockModelBuilder tripleDoorBottomLeftOpen(String name, ResourceLocation bottom, ResourceLocation middle, ResourceLocation top) {
        return tripleDoor(name, "triple_door_bottom_left_open", bottom, middle, top);
    }

    public BlockModelBuilder tripleDoorBottomRight(String name, ResourceLocation bottom, ResourceLocation middle, ResourceLocation top) {
        return tripleDoor(name, "triple_door_bottom_right", bottom, middle, top);
    }

    public BlockModelBuilder tripleDoorBottomRightOpen(String name, ResourceLocation bottom, ResourceLocation middle, ResourceLocation top) {
        return tripleDoor(name, "triple_door_bottom_right_open", bottom, middle, top);
    }

    public BlockModelBuilder tripleDoorMiddleLeft(String name, ResourceLocation bottom, ResourceLocation middle, ResourceLocation top) {
        return tripleDoor(name, "triple_door_middle_left", bottom, middle, top);
    }

    public BlockModelBuilder tripleDoorMiddleLeftOpen(String name, ResourceLocation bottom, ResourceLocation middle, ResourceLocation top) {
        return tripleDoor(name, "triple_door_middle_left_open", bottom, middle, top);
    }

    public BlockModelBuilder tripleDoorMiddleRight(String name, ResourceLocation bottom, ResourceLocation middle, ResourceLocation top) {
        return tripleDoor(name, "triple_door_middle_right", bottom, middle, top);
    }

    public BlockModelBuilder tripleDoorMiddleRightOpen(String name, ResourceLocation bottom, ResourceLocation middle, ResourceLocation top) {
        return tripleDoor(name, "triple_door_middle_right_open", bottom, middle, top);
    }

    public BlockModelBuilder tripleDoorTopLeft(String name, ResourceLocation bottom, ResourceLocation middle, ResourceLocation top) {
        return tripleDoor(name, "triple_door_top_left", bottom, middle, top);
    }

    public BlockModelBuilder tripleDoorTopLeftOpen(String name, ResourceLocation bottom, ResourceLocation middle, ResourceLocation top) {
        return tripleDoor(name, "triple_door_top_left_open", bottom, middle, top);
    }

    public BlockModelBuilder tripleDoorTopRight(String name, ResourceLocation bottom, ResourceLocation middle, ResourceLocation top) {
        return tripleDoor(name, "triple_door_top_right", bottom, middle, top);
    }

    public BlockModelBuilder tripleDoorTopRightOpen(String name, ResourceLocation bottom, ResourceLocation middle, ResourceLocation top) {
        return tripleDoor(name, "triple_door_top_right_open", bottom, middle, top);
    }

    private BlockModelBuilder tripleDoor(String name, String model, ResourceLocation bottom, ResourceLocation middle, ResourceLocation top) {
        return models().withExistingParent(name, modLoc("block/" + model))
                .texture("bottom", bottom)
                .texture("middle", middle)
                .texture("top", top);
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }
}
