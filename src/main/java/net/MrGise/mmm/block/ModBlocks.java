package net.MrGise.mmm.block;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.block.custom.BirthdayCakeBlock;
import net.MrGise.mmm.block.custom.SoundBlock;
import net.MrGise.mmm.item.ModItems;
import net.MrGise.mmm.item.custom.description.DescriptionFuelBlockItem;
import net.MrGise.mmm.item.custom.FuelBlockItem;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
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

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, MMM.MOD_ID);

    // Blocks

    public static final RegistryObject<Block> SKYSOLID = registerBlock("skysolid",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.ICE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));

    public static final RegistryObject<Block> SKOAL_BLOCK = registerBurnableBlockWithDescription("skoal_block",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.ICE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F)),
            18000, "skoal_block", true);

    public static final RegistryObject<Block> DECORATIVE_SKYSOLID = registerBlock("decorative_skysolid",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.ICE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));

    public static final RegistryObject<Block> BROKEN_SKYSOLID = registerBlock("broken_skysolid",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.ICE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));

    public static final RegistryObject<Block> SKIRON_ORE = registerBlock("skiron_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.ICE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F, 3.0F), UniformInt.of(4, 10)));

    public static final RegistryObject<Block> SKOAL_ORE = registerBlock("skoal_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.ICE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F, 3.0F), UniformInt.of(4, 10)));


    public static final RegistryObject<Block> BIRTHDAY_CAKE = registerBlock("birthday_cake",
            () -> new BirthdayCakeBlock(BlockBehaviour.Properties.copy(Blocks.CAKE)));

    public static final RegistryObject<Block> SOUND_BLOCK = registerBlock("sound_block",
            () -> new SoundBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GLOW_LICHEN).instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).noLootTable().strength(-1, 0)));


    public static final RegistryObject<Block> SKYWOOD_PLANKS = registerBlock("skywood_planks",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.GLOW_LICHEN).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));

    public static final RegistryObject<Block> SKYWOOD_STAIRS = registerBlock("skywood_stairs",
            () -> new StairBlock(() -> ModBlocks.SKYWOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS).mapColor(MapColor.GLOW_LICHEN)));

    public static final RegistryObject<Block> SKYWOOD_SLAB = registerBlock("skywood_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB).mapColor(MapColor.GLOW_LICHEN)));

    public static final RegistryObject<Block> SKYWOOD_PRESSURE_PLATE = registerBlock("skywood_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS).mapColor(MapColor.GLOW_LICHEN), BlockSetType.OAK));

    public static final RegistryObject<Block> SKYWOOD_BUTTON = registerBlock("skywood_button",
            () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB).mapColor(MapColor.GLOW_LICHEN), BlockSetType.OAK, 30, true));

    // Block registration methods
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<T> registerBurnableBlock(String name, Supplier<T> block, int BurnTime) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerFuelBlockItem(name, toReturn, BurnTime);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<T> registerBurnableBlockWithDescription(String name, Supplier<T> block, int BurnTime, String DescriptionTranslatable, boolean ShiftToView) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerDescriptionFuelBlockItem(name, toReturn, BurnTime, DescriptionTranslatable, ShiftToView);
        return toReturn;
    }

    // Block item registration methods
    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<Item> registerFireResistantBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().fireResistant()));
    }

    private static <T extends Block> RegistryObject<Item> registerFuelBlockItem(String name, RegistryObject<T> block, int BurnTime) {
        return ModItems.ITEMS.register(name, () -> new FuelBlockItem(block.get(), new Item.Properties(), BurnTime));
    }

    private static <T extends Block> RegistryObject<Item> registerDescriptionFuelBlockItem(String name, RegistryObject<T> block, int BurnTime, String DescriptionTranslatable, boolean ShiftToView) {
        return ModItems.ITEMS.register(name, () -> new DescriptionFuelBlockItem(new Item.Properties(), block.get(), BurnTime, DescriptionTranslatable, ShiftToView));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
