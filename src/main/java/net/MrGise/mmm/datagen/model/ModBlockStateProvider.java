package net.MrGise.mmm.datagen.model;

import net.MrGise.floating.block.CustomGrass;
import net.MrGise.mmm.MMM;
import net.MrGise.mmm.block.*;
import net.MrGise.floating.block.AccessibleCropBlock;
import net.MrGise.mmm.block.crop.CucumberCropBlock;
import net.MrGise.mmm.block.crop.StrawberryCropBlock;
import net.MrGise.mmm.block.dough.FlatteningDoughBlock;
import net.MrGise.mmm.block.dough.UncookedMatzaBlock;
import net.MrGise.mmm.block.fluid.SolidFluidBlock;
import net.MrGise.mmm.registry.content.ModBlocks;
import net.MrGise.mmm.registry.create.ModCreateBlocks;
import net.MrGise.mmm.resource.TripleBlockPart;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.client.model.generators.ModelBuilder.FaceRotation;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.MrGise.floating.helper.Methods.*;

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

        //-- Normal blocks

        uniqueCubeRotate(ModBlocks.BOWYERY_TABLE.get(), "bowyery_table",
                ResourceLocation.fromNamespaceAndPath("mmm", "block/bowyery_table/bowyery_table_bottom"),
                ResourceLocation.fromNamespaceAndPath("mmm", "block/bowyery_table/bowyery_table_top"),
                ResourceLocation.fromNamespaceAndPath("mmm", "block/bowyery_table/bowyery_table_side"),
                ResourceLocation.fromNamespaceAndPath("mmm", "block/bowyery_table/bowyery_table_side"),
                ResourceLocation.fromNamespaceAndPath("mmm", "block/bowyery_table/bowyery_table_bow"),
                ResourceLocation.fromNamespaceAndPath("mmm", "block/bowyery_table/bowyery_table_string"));


        //-- Production

        blockWithItemDirFix(ModBlocks.PLACED_BOWL, mmm("block/kitchen/bowl"));

        blockWithItem(ModBlocks.CHEESE_BLOCK);

        block(ModBlocks.PLACED_DOUGH, mmm("block/dough/dough"));

        getVariantBuilder(ModBlocks.FLATTENING_DOUGH.get())
                .partialState().with(FlatteningDoughBlock.SHAPE, FlatteningDoughBlock.Shape.FLATTENING)
                .modelForState().modelFile(models().getExistingFile(
                        ResourceLocation.fromNamespaceAndPath(MMM.MOD_ID, "block/dough/flattened_dough"))).addModel()
                .partialState().with(FlatteningDoughBlock.SHAPE, FlatteningDoughBlock.Shape.PIZZA)
                .modelForState().modelFile(models().getExistingFile(
                        ResourceLocation.fromNamespaceAndPath(MMM.MOD_ID, "block/dough/pizza_dough"))).addModel()
                .partialState().with(FlatteningDoughBlock.SHAPE, FlatteningDoughBlock.Shape.FLAT)
                .modelForState().modelFile(models().getExistingFile(
                        ResourceLocation.fromNamespaceAndPath(MMM.MOD_ID, "block/dough/flat_dough"))).addModel();

        VariantBlockStateBuilder builder = getVariantBuilder(ModBlocks.UNCOOKED_MATZA.get());
        for (int i = 1; i <= 13; i++) {
            ModelFile model = models().getExistingFile(ResourceLocation.
                    fromNamespaceAndPath(MMM.MOD_ID, "block/dough/uncooked_matza_" + i));

            for (Direction dir : new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST}) {
                int yRot = switch (dir) {
                    case EAST -> 90;
                    case SOUTH -> 180;
                    case WEST -> 270;
                    default -> 0;
                };

                builder.partialState().with(UncookedMatzaBlock.HOLES, i).with(UncookedMatzaBlock.FACING, dir)
                        .modelForState().modelFile(model).rotationY(yRot).addModel();
            }
        }
        builder.partialState().with(UncookedMatzaBlock.HOLES, 14)
                .modelForState().modelFile(models().getExistingFile(
                        ResourceLocation.fromNamespaceAndPath(MMM.MOD_ID, "block/dough/uncooked_matza_14"))).addModel();


        cubeBottomTopDirFix(ModBlocks.OAK_COUNTER.get(), mcLoc("block/smooth_stone"),
                modLoc("block/counter/oak_counter_side"), modLoc("block/counter/oak_counter_bottom"), "counter/oak_counter");


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
        logBlockWithItem((RotatedPillarBlock) ModBlocks.SKYWOOD.get(),
                "skywood_log", "skywood_log", "skywood");
        logBlockWithItem((RotatedPillarBlock) ModBlocks.STRIPPED_SKYWOOD.get(),
                "stripped_skywood_log", "stripped_skywood_log", "stripped_skywood");

        blockWithItem(ModBlocks.ACTINOLITE_ORE);
        blockWithItem(ModBlocks.SKIRON_ORE);
        blockWithItem(ModBlocks.SKOAL_ORE);
        blockWithItem(ModBlocks.SKIRON_BLOCK);
        blockWithItem(ModBlocks.RAW_SKIRON_BLOCK);
        blockWithItem(ModBlocks.SKOAL_BLOCK);

        //. Overworld & Co.
        blockWithItem(ModBlocks.MANA_ORE);

        thinLogBlockWithItem(((ThinLogBlock) ModBlocks.THIN_PINE_LOG.get()),
                "thin_pine_log", "thin_pine_log_top", "thin_pine_log");

        solidFluidBlock((SolidFluidBlock) ModBlocks.RAINSTONE.get());

        //\ Dimensions
        blockWithItem(ModBlocks.NULL_BLOCK);

        blockWithItem(ModBlocks.CANPHOR);


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
        trapdoorBlockWithRenderTypeAndItem(ModBlocks.SKYWOOD_TRAPDOOR, modLoc("block/skywood_trapdoor"), true, "cutout");

        //. World

        makeCustomCrop((CucumberCropBlock)ModBlocks.CUCUMBER.get(), "cucumber_", "cucumber_", mmm("cucumber_base"), mmm("cucumber_base_tiny"), "0", 5, 6);
        makeCustomCrop((StrawberryCropBlock)ModBlocks.STRAWBERRY.get(), "strawberry", "strawberry_", mmm("crop_cross"), mmm("crop_cross"), "0", false, "block/strawberry");

        flower(ModBlocks.OXALIS.get());
        pottedFlower(ModBlocks.POTTED_OXALIS.get(), "potted_oxalis", "oxalis");

        Map<RegistryObject<Block>, Boolean> tripleDoors = new LinkedHashMap<>();
        tripleDoors.put(ModBlocks.SKYWOOD_TRIPLE_DOOR, true);
        tripleDoors.put(ModBlocks.ACACIA_TRIPLE_DOOR, true);
        tripleDoors.put(ModBlocks.BIRCH_TRIPLE_DOOR, false);
        tripleDoors.put(ModBlocks.CRIMSON_TRIPLE_DOOR, false);
        tripleDoors.put(ModBlocks.DARK_OAK_TRIPLE_DOOR, false);
        tripleDoors.put(ModBlocks.IRON_TRIPLE_DOOR, true);
        tripleDoors.put(ModBlocks.JUNGLE_TRIPLE_DOOR, true);
        tripleDoors.put(ModBlocks.MANGROVE_TRIPLE_DOOR, false);
        tripleDoors.put(ModBlocks.OAK_TRIPLE_DOOR, true);
        tripleDoors.put(ModBlocks.SPRUCE_TRIPLE_DOOR, false);
        tripleDoors.put(ModBlocks.WARPED_TRIPLE_DOOR, false);
        tripleDoors.put(ModBlocks.BAMBOO_TRIPLE_DOOR, true);
        tripleDoors.put(ModBlocks.CHERRY_TRIPLE_DOOR, true);
        tripleDoors.forEach((block, cutout) -> {
            String name = ForgeRegistries.BLOCKS.getKey(block.get()).getPath();
            tripleDoorBlock(block, name, cutout);
        });

    }

    private void solidFluidBlock(SolidFluidBlock block) {
        ResourceLocation texture = mmm("block/" + name(block));
        ModelFile main_1 = models().withExistingParent(name(block) + "_1", mmm("block/solid_fluid_1")).texture("all", texture).texture("particle", texture);
        ModelFile main_2 = models().withExistingParent(name(block) + "_2", mmm("block/solid_fluid_2")).texture("all", texture).texture("particle", texture);
        ModelFile main_3 = models().withExistingParent(name(block) + "_3", mmm("block/solid_fluid_3")).texture("all", texture).texture("particle", texture);
        ModelFile main_4 = models().withExistingParent(name(block) + "_4", mmm("block/solid_fluid_4")).texture("all", texture).texture("particle", texture);
        ModelFile main_5 = models().withExistingParent(name(block) + "_5", mmm("block/solid_fluid_5")).texture("all", texture).texture("particle", texture);
        ModelFile main_6 = models().cubeAll("block/" + name(block) + "_6", texture).texture("particle", texture);

        MultiPartBlockStateBuilder builder = getMultipartBuilder(block)
                .part().modelFile(main_1).addModel()
                .condition(SolidFluidBlock.LEVEL, 1).condition(SolidFluidBlock.FULL, false)
                .end()

                .part().modelFile(main_2).addModel()
                .condition(SolidFluidBlock.LEVEL, 2).condition(SolidFluidBlock.FULL, false)
                .end()

                .part().modelFile(main_3).addModel()
                .condition(SolidFluidBlock.LEVEL, 3).condition(SolidFluidBlock.FULL, false)
                .end()

                .part().modelFile(main_4).addModel()
                .condition(SolidFluidBlock.LEVEL, 4).condition(SolidFluidBlock.FULL, false)
                .end()

                .part().modelFile(main_5).addModel()
                .condition(SolidFluidBlock.LEVEL, 5).condition(SolidFluidBlock.FULL, false)
                .end()

                .part().modelFile(main_6).addModel()
                .condition(SolidFluidBlock.LEVEL, 6).condition(SolidFluidBlock.FULL, false)
                .end()

                .part().modelFile(main_6).addModel()
                .condition(SolidFluidBlock.FULL, true)
                .end();

        for (int level = 1; level <= 6; level++) {
            ModelFile north_n = models().withExistingParent
                    (name(block) + "_connect_" + level, mmm("block/solid_fluid_north_" + level)).texture("all", texture);
            builder.part().modelFile(north_n).addModel()
                    .condition(SolidFluidBlock.LEVEL, level)
                    .condition(SolidFluidBlock.NORTH, true).condition(SolidFluidBlock.FULL, false)
                    .end()

                    .part().modelFile(north_n).rotationY(270).addModel()
                    .condition(SolidFluidBlock.LEVEL, level)
                    .condition(SolidFluidBlock.WEST, true).condition(SolidFluidBlock.FULL, false)
                    .end()

                    .part().modelFile(north_n).rotationY(180).addModel()
                    .condition(SolidFluidBlock.LEVEL, level)
                    .condition(SolidFluidBlock.SOUTH, true).condition(SolidFluidBlock.FULL, false)
                    .end()

                    .part().modelFile(north_n).rotationY(90).addModel()
                    .condition(SolidFluidBlock.LEVEL, level)
                    .condition(SolidFluidBlock.EAST, true).condition(SolidFluidBlock.FULL, false)
                    .end();
        }

        ModelFile north_6 = models().getExistingFile(mmm(name(block) + "_connect_6"));
        builder.part().modelFile(north_6).addModel()
                .condition(SolidFluidBlock.LEVEL, 6)
                .condition(SolidFluidBlock.NORTH, true).condition(SolidFluidBlock.FULL, true)
                .end()

                .part().modelFile(north_6).rotationY(270).addModel()
                .condition(SolidFluidBlock.LEVEL, 6)
                .condition(SolidFluidBlock.WEST, true).condition(SolidFluidBlock.FULL, true)
                .end()

                .part().modelFile(north_6).rotationY(180).addModel()
                .condition(SolidFluidBlock.LEVEL, 6)
                .condition(SolidFluidBlock.SOUTH, true).condition(SolidFluidBlock.FULL, true)
                .end()

                .part().modelFile(north_6).rotationY(90).addModel()
                .condition(SolidFluidBlock.LEVEL, 6)
                .condition(SolidFluidBlock.EAST, true).condition(SolidFluidBlock.FULL, true)
                .end();
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

    private void hotDogCube(Block block, String name, ResourceLocation bottom, ResourceLocation top, ResourceLocation front, ResourceLocation sides) {
        uniqueCube(block, name, bottom, top, front, sides, sides, sides);
    }

    private void hotDogCubeRotate(Block block, String name, ResourceLocation bottom, ResourceLocation top, ResourceLocation front, ResourceLocation sides) {
        getVariantBuilder(block)
                .forAllStates(state -> {
                    int yRot = switch (state.getValue(HorizontalDirectionalBlock.FACING)) {
                        case EAST  -> 90;
                        case SOUTH -> 180;
                        case WEST  -> 270;
                        default -> 0;
                    };

                    return ConfiguredModel.builder()
                            .modelFile(models().cube(name, bottom, top, front, sides, sides, sides))
                            .rotationY(yRot)
                            .build();
                });
    }

    private void hotDogCubeRotateWithItemDirFix(Block block, String name, ResourceLocation bottom, ResourceLocation top, ResourceLocation front, ResourceLocation sides) {
        ModelFile model = models().cube("block/" + name, bottom, top, front, sides, sides, sides).texture("particle", bottom);

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

    private void pottedFlower(Block block, String name, String plantName) {
        simpleBlock(block, models().singleTexture(name, mcr("flower_pot_cross"), "plant",
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

    private void cubeBottomTop(Block block,
                                ResourceLocation top, ResourceLocation side, ResourceLocation bottom,
                                String modelName) {

        // Block model that uses block/ textures
        ModelFile model = models().cubeBottomTop(modelName, side, bottom, top);

        simpleBlockWithItem(block, model);
    }

    private void cubeBottomTopDirFix(Block block,
                                ResourceLocation top, ResourceLocation side, ResourceLocation bottom,
                                String modelName) {

        // Block model that uses block/ textures
        ModelFile model = models().cubeBottomTop("block/" + modelName, side, bottom, top);

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

    private void blockItem(RegistryObject<Block> block, String appendix) {
        simpleBlockItem(block.get(), new ModelFile.UncheckedModelFile("mmm:block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath() + appendix));
    }

    private void blockItem(RegistryObject<Block> block) {
        simpleBlockItem(block.get(), new ModelFile.UncheckedModelFile("mmm:block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
    }


    private void blockWithItem(RegistryObject<Block> block) {
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }

    private void block(RegistryObject<Block> block) {
        simpleBlock(block.get(), cubeAll(block.get()));
    }

    private void blockWithItem(RegistryObject<Block> block, ResourceLocation customModel) {
        simpleBlockWithItem(block.get(), models().getExistingFile(customModel));
    }

    private void rotatingBlockWithItem(RegistryObject<Block> block, ResourceLocation customModel) {
        ModelFile model = models().getExistingFile(customModel);

        getVariantBuilder(block.get()).forAllStates(state -> {
                    int yRot = switch (state.getValue(HorizontalDirectionalBlock.FACING)) {
                        case EAST  -> 90;
                        case SOUTH -> 180;
                        case WEST  -> 270;
                        default -> 0;
                    };

                    return ConfiguredModel.builder().modelFile(model).rotationY(yRot).build();
                });

        simpleBlockItem(block.get(), model);
    }

    private void block(RegistryObject<Block> block, ResourceLocation customModel) {
        simpleBlock(block.get(), models().getExistingFile(customModel));
    }

    private void blockWithItemDirFix(RegistryObject<Block> blockRO) {
        Block block = blockRO.get();
        String path = blockRO.getId().getPath();

        // blockstate
        simpleBlock(block, models().cubeAll("block/" + path, modLoc("block/" + path)));

        // item model
        itemModels().withExistingParent("item/" + path, modLoc("block/" + path));
    }

    private void blockWithItemDirFix(RegistryObject<Block> blockRO, ResourceLocation customModel) {
        Block block = blockRO.get();
        String path = blockRO.getId().getPath();

        // blockstate
        simpleBlock(block, models().getExistingFile(customModel));

        // item model
        itemModels().withExistingParent("item/" + path, customModel);
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
                        mmm("block/" + name + "_eye")))};
            } else {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll(name + "_noeye",
                        mmm("block/" + name + "_noeye")))};
            }
        });
        simpleBlockItem(pBlock.get(), models().cubeAll(name + "_eye",
                mmm("block/" + name + "_eye")));
    }

    private void portalBlockAlt(Supplier<? extends Block> blockSupplier, String name) {
        Block pBlock = blockSupplier.get();
        getVariantBuilder(pBlock).forAllStates(state -> {
            if(state.getValue(PortalBlock.EYE)) {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll(name + "_on",
                        mmm("block/" + name + "_on")))};
            } else {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll(name + "_off",
                        mmm("block/" + name + "_off")))};
            }
        });
        simpleBlockItem(pBlock, models().cubeAll(name + "_on",
                mmm("block/" + name + "_on")));
    }

    private ConfiguredModel[] states(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(((CucumberCropBlock) block).getAgeProperty()),
                mmm("block/" + textureName + state.getValue(((CucumberCropBlock) block).getAgeProperty()))).renderType("cutout"));

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
                                    .texture(textureLayer, mmm("block/" + textureName + age))
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
                                        .texture(textureLayer, mmm("block/" + textureName + age))
                                        .texture("particle", mmm(customParticleName))
                                        .renderType("cutout")
                        )
                };
            } else {
                return new ConfiguredModel[]{
                        new ConfiguredModel(
                                models().getBuilder(modelName + age)
                                        .parent(models().getExistingFile(isCustomStage ? customParent : defaultParent))
                                        .texture(textureLayer, mmm("block/" + textureName + age))
                                        .texture("particle", mmm("block/" + textureName + age))
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

    private void tripleDoorBlock(RegistryObject<Block> block, String name, boolean cutout) {
        if (cutout) {
            tripleDoorBlockWithRenderType((TripleDoorBlock) block.get(), "door/" + name, "cutout");
        } else {
            tripleDoorBlock((TripleDoorBlock) block.get(), "door/" + name);
        }
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
            }else if (bottom && right && !open) {
                model = bottomRight;
            } else if (bottom && !right && !open) {
                model = bottomLeft;
            }else if (middle && right && open) {
                model = middleRightOpen;
            } else if (middle && !right && open) {
                model = middleLeftOpen;
            }else if (middle && right && !open) {
                model = middleRight;
            } else if (middle && !right && !open) {
                model = middleLeft;
            }else if (top && right && open) {
                model = topRightOpen;
            } else if (top && !right && open) {
                model = topLeftOpen;
            }else if (top && right && !open) {
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
