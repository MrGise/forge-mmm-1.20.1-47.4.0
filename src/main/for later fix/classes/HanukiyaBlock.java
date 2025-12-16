package net.MrGise.mmm.block;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.MrGise.mmm.registry.back.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class HanukiyaBlock extends HorizontalDirectionalBlock {
    public BiMap<Integer, String> numberToColor = HashBiMap.create();
    {
        numberToColor.putAll(Map.of(
                0, "",
                1, "white",
                2, "light_gray",
                3, "gray",
                4, "black",
                5, "brown",
                6, "red",
                7, "orange",
                8, "yellow",
                9, "lime"
        ));
        numberToColor.putAll(Map.of(
                10, "green",
                11, "cyan",
                12, "light_blue",
                13, "blue",
                14, "purple",
                15, "magenta",
                16, "pink",
                17, "none"
        ));
    }

    public Item getUnlitItem(String color) {
        return !color.isEmpty()
                ? ForgeRegistries.ITEMS.getValue(ResourceLocation.withDefaultNamespace(color + "_candle"))
                : Items.AIR;
    }

    public Item getItem(IntegerProperty colorNum, BlockState state) {
        return state.getValue(colorNum) == 0 ? Items.AIR : state.getValue(litToProperty.get(colorNum))
                ? ForgeRegistries.ITEMS.getValue(ResourceLocation.fromNamespaceAndPath("mmm", numberToColor.get(state.getValue(colorNum)) + "_candle_lit"))
                : getUnlitItem(numberToColor.get(state.getValue(colorNum)));
    }

    public BlockState convertToState(BlockState state, ItemLike item, IntegerProperty property) {
        for (String string : numberToColor.values()) {
            if (string.isEmpty()) continue;

            if (getUnlitItem(string) == item.asItem()) {
                return state
                        .setValue(property, numberToColor.inverse().get(string))
                        .setValue(litToProperty.get(property), false);
            }

            if (ForgeRegistries.ITEMS.getValue(
                    ResourceLocation.fromNamespaceAndPath("mmm", string + "_candle_lit")) == item.asItem()) {
                return state
                        .setValue(property, numberToColor.inverse().get(string))
                        .setValue(litToProperty.get(property), true);
            }
        }

        return state
                .setValue(property, 0)
                .setValue(litToProperty.get(property), false);
    }


    public static final IntegerProperty SHAMASH = IntegerProperty.create("shamash", 0, 17);
    public static final IntegerProperty CANDLE_2 = IntegerProperty.create("candle_2", 0, 17);
    public static final IntegerProperty CANDLE_3 = IntegerProperty.create("candle_3", 0, 17);
    public static final IntegerProperty CANDLE_4 = IntegerProperty.create("candle_4", 0, 17);
    public static final IntegerProperty CANDLE_5 = IntegerProperty.create("candle_5", 0, 17);
    public static final IntegerProperty CANDLE_6 = IntegerProperty.create("candle_6", 0, 17);
    public static final IntegerProperty CANDLE_7 = IntegerProperty.create("candle_7", 0, 17);
    public static final IntegerProperty CANDLE_8 = IntegerProperty.create("candle_8", 0, 17);
    public static final IntegerProperty CANDLE_9 = IntegerProperty.create("candle_9", 0, 17);

    public static final BooleanProperty SHAMASH_LIT = BooleanProperty.create("shamash_lit");
    public static final BooleanProperty CANDLE_2_LIT = BooleanProperty.create("candle_2_lit");
    public static final BooleanProperty CANDLE_3_LIT = BooleanProperty.create("candle_3_lit");
    public static final BooleanProperty CANDLE_4_LIT = BooleanProperty.create("candle_4_lit");
    public static final BooleanProperty CANDLE_5_LIT = BooleanProperty.create("candle_5_lit");
    public static final BooleanProperty CANDLE_6_LIT = BooleanProperty.create("candle_6_lit");
    public static final BooleanProperty CANDLE_7_LIT = BooleanProperty.create("candle_7_lit");
    public static final BooleanProperty CANDLE_8_LIT = BooleanProperty.create("candle_8_lit");
    public static final BooleanProperty CANDLE_9_LIT = BooleanProperty.create("candle_9_lit");

    private final BiMap<IntegerProperty, BooleanProperty> litToProperty = HashBiMap.create(Map.of(
            SHAMASH, SHAMASH_LIT,
            CANDLE_2, CANDLE_2_LIT,
            CANDLE_3, CANDLE_3_LIT,
            CANDLE_4, CANDLE_4_LIT,
            CANDLE_5, CANDLE_5_LIT,
            CANDLE_6, CANDLE_6_LIT,
            CANDLE_7, CANDLE_7_LIT,
            CANDLE_8, CANDLE_8_LIT,
            CANDLE_9, CANDLE_9_LIT
    ));

    private final IntegerProperty[] CANDLES_A = new IntegerProperty[]{
            SHAMASH, CANDLE_2, CANDLE_3, CANDLE_4, CANDLE_5, CANDLE_6, CANDLE_7, CANDLE_8, CANDLE_9
    };

    private final IntegerProperty[] A_CANDLES = new IntegerProperty[]{
            CANDLE_9, CANDLE_8, CANDLE_7, CANDLE_6, CANDLE_5, CANDLE_4, CANDLE_3, CANDLE_2, SHAMASH
    };

    private final IntegerProperty[] B_CANDLES = new IntegerProperty[]{
            CANDLE_9, CANDLE_8, CANDLE_7, CANDLE_6, CANDLE_5, CANDLE_4, CANDLE_3, CANDLE_2
    };

    public HanukiyaBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH)
                .setValue(SHAMASH, 0)
                .setValue(CANDLE_2, 0).setValue(CANDLE_3, 0)
                .setValue(CANDLE_4, 0).setValue(CANDLE_5, 0)
                .setValue(CANDLE_6, 0).setValue(CANDLE_7, 0)
                .setValue(CANDLE_8, 0).setValue(CANDLE_9, 0)
                .setValue(SHAMASH_LIT, false)
                .setValue(CANDLE_2_LIT, false).setValue(CANDLE_3_LIT, false)
                .setValue(CANDLE_4_LIT, false).setValue(CANDLE_5_LIT, false)
                .setValue(CANDLE_6_LIT, false).setValue(CANDLE_7_LIT, false)
                .setValue(CANDLE_8_LIT, false).setValue(CANDLE_9_LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACING);
        stateBuilder.add(SHAMASH);
        stateBuilder.add(SHAMASH_LIT);
        stateBuilder.add(CANDLE_2);
        stateBuilder.add(CANDLE_2_LIT);
        stateBuilder.add(CANDLE_3);
        stateBuilder.add(CANDLE_3_LIT);
        stateBuilder.add(CANDLE_4);
        stateBuilder.add(CANDLE_4_LIT);
        stateBuilder.add(CANDLE_5);
        stateBuilder.add(CANDLE_5_LIT);
        stateBuilder.add(CANDLE_6);
        stateBuilder.add(CANDLE_6_LIT);
        stateBuilder.add(CANDLE_7);
        stateBuilder.add(CANDLE_7_LIT);
        stateBuilder.add(CANDLE_8);
        stateBuilder.add(CANDLE_8_LIT);
        stateBuilder.add(CANDLE_9);
        stateBuilder.add(CANDLE_9_LIT);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getHorizontalDirection();

        return this.defaultBlockState().setValue(FACING, direction);
    }

    //-- Use method
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        ItemStack held = player.getItemInHand(hand);

        if (player.isShiftKeyDown()) {
            if (held.isEmpty()) {
                if (state.getValue(SHAMASH) != 0) {
                    if (state.getValue(SHAMASH_LIT)) {
                        player.getInventory().add(getItem(SHAMASH, state).getDefaultInstance());
                    }
                    state = state.setValue(SHAMASH, 0);
                }
            } else if (held.is(ModTags.Items.LIT_CANDLES) || held.is(ItemTags.CANDLES)) {
                takeCandle(state, SHAMASH, level, held);
            }
        } else if (held.is(ItemTags.SHOVELS)) {
            IntegerProperty toChange = getNextLit(state);

            if (toChange != null) {
                state = state.setValue(litToProperty.get(toChange), false);
                if (level.isClientSide()) {
                    held.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
                    if (!held.isDamageableItem()) {
                        held.shrink(1);
                    }
                }
            }
        } else if (held.is(Items.FLINT_AND_STEEL) || held.is(Items.FIRE_CHARGE)) {
            IntegerProperty toChange = getNextUnLit(state);

            if (toChange != null) {
                state = state.setValue(litToProperty.get(toChange), true);
                if (!level.isClientSide()) {
                    held.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
                    if (!held.isDamageableItem()) {
                        held.shrink(1);
                    }
                }
            }
        } else if (held.is(ItemTags.CANDLES) || held.is(ModTags.Items.LIT_CANDLES)) {
            IntegerProperty property = getNextEmpty(state);
            takeCandle(state, property, level, held);
        } else if (held.isEmpty()) {
            if (state.getBlock() instanceof HanukiyaBlock) {
                IntegerProperty prop = getNextFilled(state);
                    if (!(prop == null)) {
                        player.getInventory().add(getItem(prop, state).getDefaultInstance());
                    state = state.setValue(prop, 0);
                }
            }
        }

        player.setItemInHand(hand, held);

        level.setBlock(pos, state, 3);

        return InteractionResult.SUCCESS;
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide && !player.isCreative()) {
            for (IntegerProperty prop : CANDLES_A) {
                int i = state.getValue(prop);
                if (i != 0) {
                    ItemLike item = getUnlitItem(numberToColor.get(i));
                    popResource(level, pos, item.asItem().getDefaultInstance());
                }
            }
        }
        super.playerWillDestroy(level, pos, state, player);
    }

    private IntegerProperty getNextEmpty(BlockState state) {
        for (IntegerProperty property : CANDLES_A) {
            if (state.getValue(property) == 0) {
                return property;
            }
        }
        return null;
    }

    private IntegerProperty getNextFilled(BlockState state) {
        for (IntegerProperty property : B_CANDLES) {
            if (state.getValue(property) != 0) {
                return property;
            }
        }
        return null;
    }

    private IntegerProperty getNextLit(BlockState state) {
        for (IntegerProperty property : A_CANDLES) {
            if (state.getValue(litToProperty.get(property))) {
                return property;
            }
        }
        return null;
    }

    private IntegerProperty getNextUnLit(BlockState state) {
        for (IntegerProperty property : CANDLES_A) {
            if (!state.getValue(litToProperty.get(property))) {
                return property;
            }
        }
        return null;
    }

    public void takeCandle(BlockState state, IntegerProperty property, Level level, ItemStack toChange) {
        Item compareTo = toChange.getItem();
        if (!level.isClientSide()) {
            toChange.shrink(1);
        }
        state = convertToState(state, compareTo, property);
    }
}
