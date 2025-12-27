package net.MrGise.mmm.registry.front;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.block.*;
import net.MrGise.mmm.block.crop.CucumberCropBlock;
import net.MrGise.mmm.block.crop.StrawberryCropBlock;
import net.MrGise.mmm.item.block_item.*;
import net.MrGise.mmm.item.block_item.description.DescriptionBlockItem;
import net.MrGise.mmm.item.block_item.description.DescriptionFuelBlockItem;
import net.MrGise.mmm.item.block_item.description.DescriptionPortalBlockItem;
import net.MrGise.mmm.registry.back.ModFoodProperties;
import net.MrGise.mmm.registry.front.item.ModItems;
import net.MrGise.mmm.registry.middle.ModSounds;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;
import java.util.function.Supplier;
    // Blocks
public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, MMM.MOD_ID);

    //. Blocks

    public static final RegistryObject<Block> MIMIC_BLOCK = registerBlock("mimic_block",
            () -> new MimicBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(2.5F)),
            b -> () -> new MimicBlockItem(b.get(), new Item.Properties()));

    //- Skyland
    public static final RegistryObject<Block> SKYSOLID = registerBlock("skysolid",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.ICE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));

    public static final RegistryObject<Block> SKYSOLID_WALL = registerBlock("skysolid_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(ModBlocks.SKYSOLID.get())));

    public static final RegistryObject<Block> BROKEN_SKYSOLID = registerBlock("broken_skysolid",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.ICE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));

    public static final RegistryObject<Block> SKYSOIL = registerBlock("skysoil",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIRT).mapColor(DyeColor.CYAN).randomTicks()));

    public static final RegistryObject<Block> HEAVENLY_GRASS_BLOCK = registerBlock("heavenly_grass_block",
            () -> new CustomGrassBlock(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK).mapColor(DyeColor.CYAN),
                    ModBlocks.SKYSOIL, ModBlocks.HEAVENLY_GRASS));

    public static final RegistryObject<Block> HEAVENLY_GRASS = registerCustomGrass("heavenly_grass",
            () -> new CustomGrass(BlockBehaviour.Properties.copy(Blocks.GRASS),
                    ModBlocks.HEAVENLY_GRASS_BLOCK, ModBlocks.SKYSOIL,
                    ModBlocks.HEAVENLY_GRASS_BLOCK_SKYDIRT, ModBlocks.SKYDIRT));

    public static final RegistryObject<Block> SKYGROUND = registerBlock("skyground",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.GOLD).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));

    public static final RegistryObject<Block> SKYGROUND_WALL = registerBlock("skyground_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(ModBlocks.SKYGROUND.get())));

    public static final RegistryObject<Block> BROKEN_SKYGROUND = registerBlock("broken_skyground",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.GOLD).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));

    public static final RegistryObject<Block> SKYDIRT = registerBlock("skydirt",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIRT).mapColor(DyeColor.YELLOW).randomTicks()));

    public static final RegistryObject<Block> HEAVENLY_GRASS_BLOCK_SKYDIRT = registerBlock("heavenly_grass_block_skydirt",
            () -> new CustomGrassBlock(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK).mapColor(DyeColor.CYAN),
                    ModBlocks.SKYDIRT, ModBlocks.HEAVENLY_GRASS));

    //- Ores
    //* Skyland ores

    public static final RegistryObject<Block> SKIRON_ORE = registerBlock("skiron_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.ICE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F, 3.0F), UniformInt.of(2, 8)));

    public static final RegistryObject<Block> ACTINOLITE_ORE = registerBlock("actinolite_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.ICE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.5F, 3.5F), UniformInt.of(8, 12)));

    public static final RegistryObject<Block> SKOAL_ORE = registerBlock("skoal_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.ICE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F, 3.0F), UniformInt.of(4, 10)));

    public static final RegistryObject<Block> SKIRON_BLOCK = registerBlock("skiron_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    public static final RegistryObject<Block> SKOAL_BLOCK = registerBurnableBlockWithDescription("skoal_block",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.ICE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F)),
            18000, "skoal_block", true);

    public static final RegistryObject<Block> RAW_SKIRON_BLOCK = registerBlock("raw_skiron_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK)));


    //* World ores

    public static final RegistryObject<Block> MANA_ORE = registerBlock("mana_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE), UniformInt.of(7, 20)));


    //- Misc
    public static final RegistryObject<Block> BIRTHDAY_CAKE = registerBlock("birthday_cake",
            () -> new BirthdayCakeBlock(BlockBehaviour.Properties.copy(Blocks.CAKE)));

    public static final RegistryObject<Block> SOUND_BLOCK = registerBlock("sound_block",
            () -> new SoundBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GLOW_LICHEN).instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).noLootTable().strength(-1, 0)));

    public static final RegistryObject<Block> PORTAL_BLOCK = registerPortalBlockWithDescription("portal_block",
            () -> new PortalBlock(BlockBehaviour.Properties.of().mapColor(MapColor.ICE).strength(8, 10).sound(SoundType.GLASS)
                    .lightLevel(state -> state.getValue(PortalBlock.EYE) ? 6 : 0).isRedstoneConductor(((blockState, blockGetter, blockPos) -> false)).noLootTable(), ModItems.ACTINOLITE), "actinolite");


    //- Skyland pt. 2
    public static final RegistryObject<Block> SKYWOOD_LOG = registerBlock("skywood_log",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).mapColor(MapColor.GLOW_LICHEN)));

    public static final RegistryObject<Block> STRIPPED_SKYWOOD_LOG = registerBlock("stripped_skywood_log",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.GLOW_LICHEN)));

    public static final RegistryObject<Block> SKYWOOD_PLANKS = registerBlock("skywood_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.GLOW_LICHEN)));

    public static final RegistryObject<Block> SKYWOOD_STAIRS = registerBlock("skywood_stairs",
            () -> new StairBlock(() -> ModBlocks.SKYWOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS).mapColor(MapColor.GLOW_LICHEN)));

    public static final RegistryObject<Block> SKYWOOD_SLAB = registerBlock("skywood_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB).mapColor(MapColor.GLOW_LICHEN)));

    public static final RegistryObject<Block> SKYWOOD_PRESSURE_PLATE = registerBlock("skywood_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.GLOW_LICHEN), BlockSetType.OAK));

    public static final RegistryObject<Block> SKYWOOD_BUTTON = registerBlock("skywood_button",
            () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON).mapColor(MapColor.GLOW_LICHEN), BlockSetType.OAK, 30, true));

    public static final RegistryObject<Block> SKYWOOD_FENCE = registerBlock("skywood_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE).mapColor(MapColor.GLOW_LICHEN)));

    public static final RegistryObject<Block> SKYWOOD_FENCE_GATE = registerBlock("skywood_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE).mapColor(MapColor.GLOW_LICHEN), SoundEvents.FENCE_GATE_OPEN, SoundEvents.FENCE_GATE_CLOSE));

    public static final RegistryObject<Block> SKYWOOD_DOOR = registerBlock("skywood_door",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR).mapColor(MapColor.GLOW_LICHEN), BlockSetType.OAK));

    public static final RegistryObject<Block> SKYWOOD_TRAPDOOR = registerBlock("skywood_trapdoor",
            () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.GLOW_LICHEN), BlockSetType.OAK));

    public static final RegistryObject<Block> SKYWOOD_TRIPLE_DOOR = registerBlock("skywood_triple_door",
            () -> new TripleDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.GLOW_LICHEN), BlockSetType.OAK));


    //- Crops
    public static final RegistryObject<Block> STRAWBERRY = BLOCKS.register("strawberry",
            () -> new StrawberryCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noCollission().noOcclusion()));

    public static final RegistryObject<Block> CUCUMBER = BLOCKS.register("cucumber",
            () -> new CucumberCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noCollission().noOcclusion()));


    //- nature

    public static final RegistryObject<Block> THIN_PINE_LOG = registerBlock("thin_pine_log",
            () -> new ThinLogBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_LOG)));

    public static final RegistryObject<Block> OXALIS = registerEdibleBlock("oxalis",
            () -> new FlowerBlock(() -> MobEffects.LUCK, 4, BlockBehaviour.Properties.copy(Blocks.OXEYE_DAISY).noCollission().noOcclusion()),
            ModFoodProperties.OXALIS, false);

    public static final RegistryObject<Block> POTTED_OXALIS = BLOCKS.register("potted_oxalis",
            () -> new FlowerPotBlock((() -> (FlowerPotBlock) Blocks.FLOWER_POT), OXALIS, BlockBehaviour.Properties.copy(Blocks.POTTED_OXEYE_DAISY).noOcclusion()));


    //. Structures

    public static final RegistryObject<Block> BOWYERY_TABLE = registerBlockWithDescription("bowyery_table",
            () -> new BowyeryTableBlock(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE)),
            "bowyery_table_disclaimer", false);


    //\ Dimensions
    public static final RegistryObject<Block> NULL_BLOCK = registerSecretBlock("null_block",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE).noOcclusion().lightLevel(state -> 15)
                    .strength(3.0f, 10.0f).requiresCorrectToolForDrops().sound(ModSounds.NULL_BLOCK_SOUNDS)));

    //-- Test blocks

    public static final RegistryObject<Block> TEST_BLOCK = registerBlock("test/block",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(3.5f).sound(SoundType.METAL)));

    public static final RegistryObject<Block> ANIMATED_TEST_BLOCK = registerBlock("test/animated_block",
            () -> new Block(BlockBehaviour.Properties.copy(ModBlocks.TEST_BLOCK.get())));


    //. Block registration methods
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        return registerBlock(name, block,
                b -> () -> new BlockItem(b.get(), new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<T> registerSecretBlock(String name, Supplier<T> block) {
        return registerBlock(name, block,
                b -> () -> new SecretBlockItem(b.get(), new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<T> registerEdibleBlock(String name, Supplier<T> block, FoodProperties foodProperties, boolean alwaysEat) {
        return registerBlock(name, block,
                b -> () -> new EdibleBlockItem(b.get(), new Item.Properties(), foodProperties, alwaysEat));
    }

    private static <T extends Block> RegistryObject<T> registerCustomGrass(String name, Supplier<T> block) {
        return registerBlock(name, block,
                b -> () -> new CustomGrassItem(b.get(), new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<T> registerBlockWithDescription(String name, Supplier<T> block, String DescriptionTranslatable, boolean ShiftToView) {
        return registerBlock(name, block,
                b -> () -> new DescriptionBlockItem(new Item.Properties(), b.get(), DescriptionTranslatable, ShiftToView));
    }

    private static <T extends Block> RegistryObject<T> registerPortalBlockWithDescription(String name, Supplier<T> block, String eyeName) {
        return registerBlock(name, block,
                b -> () -> new DescriptionPortalBlockItem(b.get(), new Item.Properties(), eyeName, MMM.MOD_ID));
    }

    private static <T extends Block> RegistryObject<T> registerBurnableBlock(String name, Supplier<T> block, int burnTime) {
        return registerBlock(name, block,
                b -> () -> new FuelBlockItem(b.get(), new Item.Properties(), burnTime));
    }

    private static <T extends Block> RegistryObject<T> registerBurnableBlockWithDescription(String name, Supplier<T> block, int burnTime, String DescriptionTranslatable, boolean ShiftToView) {
        return registerBlock(name, block,
                b -> () -> new DescriptionFuelBlockItem(new Item.Properties(), b.get(), burnTime, DescriptionTranslatable, ShiftToView));
    }

    //\ The ultimate registration method
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block,
                                                                     Function<RegistryObject<T>, Supplier<? extends Item>> itemFactory) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        ModItems.ITEMS.register(name, itemFactory.apply(toReturn));
        return toReturn;
    }


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
