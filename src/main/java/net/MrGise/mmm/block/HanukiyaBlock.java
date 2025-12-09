package net.MrGise.mmm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class HanukiyaBlock extends HorizontalDirectionalBlock {
    public enum Candle implements StringRepresentable {
        WHITE, LIGHT_GRAY,
        GRAY, BLACK,
        BROWN, RED,
        ORANGE, YELLOW,
        LIME, GREEN,
        CYAN, LIGHT_BLUE,
        BLUE, PURPLE,
        MAGENTA, PINK,
        EMPTY;

        @Override
        public String getSerializedName() {
            return name().toLowerCase();
        }
    }

    EnumProperty SHAMASH = EnumProperty.create("shamash", Candle.class);
    EnumProperty CANDLE_2 = EnumProperty.create("candle_2", Candle.class);
    EnumProperty CANDLE_3 = EnumProperty.create("candle_3", Candle.class);
    EnumProperty CANDLE_4 = EnumProperty.create("candle_4", Candle.class);
    EnumProperty CANDLE_5 = EnumProperty.create("candle_5", Candle.class);
    EnumProperty CANDLE_6 = EnumProperty.create("candle_6", Candle.class);
    EnumProperty CANDLE_7 = EnumProperty.create("candle_7", Candle.class);
    EnumProperty CANDLE_8 = EnumProperty.create("candle_8", Candle.class);
    EnumProperty CANDLE_9 = EnumProperty.create("candle_9", Candle.class);

    BooleanProperty LIT = BooleanProperty.create("lit");

    public HanukiyaBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH)
                .setValue(SHAMASH, Candle.EMPTY)
                .setValue(CANDLE_2, Candle.EMPTY).setValue(CANDLE_3, Candle.EMPTY)
                .setValue(CANDLE_4, Candle.EMPTY).setValue(CANDLE_5, Candle.EMPTY)
                .setValue(CANDLE_6, Candle.EMPTY).setValue(CANDLE_7, Candle.EMPTY)
                .setValue(CANDLE_8, Candle.EMPTY).setValue(CANDLE_9, Candle.EMPTY)
                .setValue(LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACING);
        stateBuilder.add(SHAMASH);
        stateBuilder.add(CANDLE_2);
        stateBuilder.add(CANDLE_3);
        stateBuilder.add(CANDLE_4);
        stateBuilder.add(CANDLE_5);
        stateBuilder.add(CANDLE_6);
        stateBuilder.add(CANDLE_7);
        stateBuilder.add(CANDLE_8);
        stateBuilder.add(CANDLE_9);
        stateBuilder.add(LIT);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getHorizontalDirection();

        return this.defaultBlockState().setValue(FACING, direction);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        ItemStack held = player.getItemInHand(hand);

        if (held.is(ItemTags.SHOVELS) || held.is(Items.FLINT_AND_STEEL) || held.is(Items.MAGMA_CREAM)) {
            if (held.is(Items.FLINT_AND_STEEL) || held.is(Items.MAGMA_CREAM)) {
                state.setValue(LIT, true);
            } else if (held.is(ItemTags.SHOVELS)) {
                state.setValue(LIT, false);
            }
        } else if (held.is(ItemTags.CANDLES)) {
            addCandle(state, held.getItem());
        } else if (held.isEmpty()) {
            removeCandle(state);
        }
        return InteractionResult.SUCCESS;
    }

    private void removeCandle(BlockState state) {
        if (state.getBlock() instanceof HanukiyaBlock) {
            if (state.getValue(CANDLE_9) != Candle.EMPTY) {
                state.setValue(CANDLE_9, Candle.EMPTY);
            } else if (state.getValue(CANDLE_8) != Candle.EMPTY) {
                state.setValue(CANDLE_8, Candle.EMPTY);
            } else if (state.getValue(CANDLE_7) != Candle.EMPTY) {
                state.setValue(CANDLE_7, Candle.EMPTY);
            } else if (state.getValue(CANDLE_6) != Candle.EMPTY) {
                state.setValue(CANDLE_6, Candle.EMPTY);
            } else if (state.getValue(CANDLE_5) != Candle.EMPTY) {
                state.setValue(CANDLE_5, Candle.EMPTY);
            } else if (state.getValue(CANDLE_4) != Candle.EMPTY) {
                state.setValue(CANDLE_4, Candle.EMPTY);
            } else if (state.getValue(CANDLE_3) != Candle.EMPTY) {
                state.setValue(CANDLE_3, Candle.EMPTY);
            } else if (state.getValue(CANDLE_2) != Candle.EMPTY) {
                state.setValue(CANDLE_2, Candle.EMPTY);
            } else if (state.getValue(SHAMASH) != Candle.EMPTY) {
                state.setValue(SHAMASH, Candle.EMPTY);
            } else {
                state.trySetValue(LIT, false);
            }
        }
    }

    public void addCandle(BlockState state, Item item) {
        if (state.getBlock() instanceof HanukiyaBlock) {
            if (state.getValue(SHAMASH) == Candle.EMPTY) {
                state.setValue(SHAMASH, getCandle(item));
            } else if (state.getValue(CANDLE_2) == Candle.EMPTY) {
                state.setValue(CANDLE_2, getCandle(item));
            } else if (state.getValue(CANDLE_3) == Candle.EMPTY) {
                state.setValue(CANDLE_3, getCandle(item));
            } else if (state.getValue(CANDLE_4) == Candle.EMPTY) {
                state.setValue(CANDLE_4, getCandle(item));
            } else if (state.getValue(CANDLE_5) == Candle.EMPTY) {
                state.setValue(CANDLE_5, getCandle(item));
            } else if (state.getValue(CANDLE_6) == Candle.EMPTY) {
                state.setValue(CANDLE_6, getCandle(item));
            } else if (state.getValue(CANDLE_7) == Candle.EMPTY) {
                state.setValue(CANDLE_7, getCandle(item));
            } else if (state.getValue(CANDLE_8) == Candle.EMPTY) {
                state.setValue(CANDLE_8, getCandle(item));
            } else if (state.getValue(CANDLE_9) == Candle.EMPTY) {
                state.setValue(CANDLE_9, getCandle(item));
            }
        }
    }

    public ItemLike getCandle(Candle type) {
        return switch (type) {
            case WHITE -> Blocks.WHITE_CANDLE;
            case LIGHT_GRAY -> Blocks.LIGHT_GRAY_CANDLE;
            case GRAY -> Blocks.GRAY_CANDLE;
            case BLACK -> Blocks.BLACK_CANDLE;
            case BROWN -> Blocks.BROWN_CANDLE;
            case RED -> Blocks.RED_CANDLE;
            case ORANGE -> Blocks.ORANGE_CANDLE;
            case YELLOW -> Blocks.YELLOW_CANDLE;
            case LIME -> Blocks.LIME_CANDLE;
            case GREEN -> Blocks.GREEN_CANDLE;
            case CYAN -> Blocks.CYAN_CANDLE;
            case LIGHT_BLUE -> Blocks.LIGHT_BLUE_CANDLE;
            case BLUE -> Blocks.BLUE_CANDLE;
            case PURPLE -> Blocks.PURPLE_CANDLE;
            case MAGENTA -> Blocks.MAGENTA_CANDLE;
            case PINK -> Blocks.PINK_CANDLE;
            case EMPTY -> null;
        };
    }

    public Candle getCandle(ItemLike held) {
        if (held == Blocks.WHITE_CANDLE) return Candle.WHITE;
        else if (held == Blocks.LIGHT_GRAY_CANDLE) return Candle.LIGHT_GRAY;
        else if (held == Blocks.GRAY_CANDLE) return Candle.GRAY;
        else if (held == Blocks.BLACK_CANDLE) return Candle.BLACK;
        else if (held == Blocks.BROWN_CANDLE) return Candle.BROWN;
        else if (held == Blocks.RED_CANDLE) return Candle.RED;
        else if (held == Blocks.ORANGE_CANDLE) return Candle.ORANGE;
        else if (held == Blocks.YELLOW_CANDLE) return Candle.YELLOW;
        else if (held == Blocks.LIME_CANDLE) return Candle.LIME;
        else if (held == Blocks.GREEN_CANDLE) return Candle.GREEN;
        else if (held == Blocks.CYAN_CANDLE) return Candle.CYAN;
        else if (held == Blocks.LIGHT_BLUE_CANDLE) return Candle.LIGHT_BLUE;
        else if (held == Blocks.BLUE_CANDLE) return Candle.BLUE;
        else if (held == Blocks.PURPLE_CANDLE) return Candle.PURPLE;
        else if (held == Blocks.MAGENTA_CANDLE) return Candle.MAGENTA;
        else if (held == Blocks.PINK_CANDLE) return Candle.PINK;
        else return Candle.EMPTY;
    }
}
