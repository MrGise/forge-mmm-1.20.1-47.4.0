package net.MrGise.mmm.block.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.RegistryObject;

public class SolidFluidBlock extends Block {
    private final RegistryObject<Item> bucket;

    public static final IntegerProperty LEVEL = IntegerProperty.create("level", 1, 6);

    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;

    public static final BooleanProperty FULL = BooleanProperty.create("full");

    public SolidFluidBlock(Properties properties, RegistryObject<Item> bucket) {
        super(properties);
        registerDefaultState(getStateDefinition().any().setValue(LEVEL, 6)
                .setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(FULL, false));
        this.bucket = bucket;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LEVEL, NORTH, EAST, SOUTH, WEST, FULL);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource source) {
        spread(state, level, pos);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState previous, boolean moving) {
        level.scheduleTick(pos, this, 5);
    }

    private void spread(BlockState state, ServerLevel level, BlockPos pos) {
        int height = state.getValue(LEVEL);

        boolean isMaxLevel = height == 6;

        BlockState north = isMaxLevel ? level.getBlockState(pos.north().above()) : level.getBlockState(pos.north());
        BlockState east = isMaxLevel ? level.getBlockState(pos.east().above()) : level.getBlockState(pos.east());
        BlockState south = isMaxLevel ? level.getBlockState(pos.south().above()) : level.getBlockState(pos.south());
        BlockState west = isMaxLevel ? level.getBlockState(pos.west().above()) : level.getBlockState(pos.west());

        if (height < 6 && !(north.is(state.getBlock()) && north.getValue(LEVEL).equals(height + 1))
                && !(south.is(state.getBlock()) && south.getValue(LEVEL).equals(height + 1))
                && !(west.is(state.getBlock()) && west.getValue(LEVEL).equals(height + 1))
                && !(east.is(state.getBlock()) && east.getValue(LEVEL).equals(height + 1))
                && !level.getBlockState(pos.above()).is(state.getBlock())) {
            level.destroyBlock(pos, false);
        } else {
            if (height > 1 && (!level.getBlockState(pos.below()).isAir())) {
                if (!level.getBlockState(pos.below()).is(state.getBlock())) {
                    for (Direction dir : Direction.Plane.HORIZONTAL) {
                        BlockPos targetPos = pos.relative(dir);
                        BlockState targetState = level.getBlockState(targetPos);

                        if ((targetState.isAir() || targetState.canBeReplaced())
                                && level.getEntities(null, new AABB(targetPos)).isEmpty()) {
                            level.setBlock(targetPos, state.setValue(LEVEL, height - 1)
                                    .trySetValue(FULL, false), 3);

                            level.updateNeighborsAt(targetPos, targetState.getBlock());
                        }
                    }
                }
            } else if (height > 1) {
                BlockState targetState = level.getBlockState(pos.below());

                if (targetState.isAir() || targetState.canBeReplaced()) {
                    if (level.getEntities(null, new AABB(pos.below())).isEmpty()) {
                        level.setBlock(pos.below(), state.setValue(LEVEL, height - 1).trySetValue(FULL, true)
                                .trySetValue(NORTH, false).trySetValue(EAST, false)
                                .trySetValue(SOUTH, false).trySetValue(WEST, false), 3);
                    }
                }
            }
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState other, LevelAccessor accessor, BlockPos pos, BlockPos otherPos) {
        int height = state.getValue(LEVEL);

        boolean isMaxLevel = state.getValue(LEVEL) == 6;

        BlockState north = isMaxLevel ? accessor.getBlockState(pos.north().above()) : accessor.getBlockState(pos.north());
        BlockState east = isMaxLevel ? accessor.getBlockState(pos.east().above()) : accessor.getBlockState(pos.east());
        BlockState south = isMaxLevel ? accessor.getBlockState(pos.south().above()) : accessor.getBlockState(pos.south());
        BlockState west = isMaxLevel ? accessor.getBlockState(pos.west().above()) : accessor.getBlockState(pos.west());

        boolean connectNorth = isMaxLevel ? north.is(state.getBlock()) && north.getValue(LEVEL) == 1 :
                                            north.is(state.getBlock()) && north.getValue(LEVEL) == height + 1;
        boolean connectEast = isMaxLevel ? east.is(state.getBlock()) && east.getValue(LEVEL) == 1 :
                                           east.is(state.getBlock()) && east.getValue(LEVEL) == height + 1;
        boolean connectSouth = isMaxLevel ? south.is(state.getBlock()) && south.getValue(LEVEL) == 1 :
                                            south.is(state.getBlock()) && south.getValue(LEVEL) == height + 1;
        boolean connectWest = isMaxLevel ? west.is(state.getBlock()) && west.getValue(LEVEL) == 1 :
                                           west.is(state.getBlock()) && west.getValue(LEVEL) == height + 1;
        if (state.getValue(NORTH) != connectNorth) state = state.setValue(NORTH, connectNorth);
        if (state.getValue(EAST) != connectEast) state = state.setValue(EAST, connectEast);
        if (state.getValue(SOUTH) != connectSouth) state = state.setValue(SOUTH, connectSouth);
        if (state.getValue(WEST) != connectWest) state = state.setValue(WEST, connectWest);

        if (height == 5 && state.getValue(NORTH) && state.getValue(EAST) && state.getValue(SOUTH) && state.getValue(WEST)) {
            state = state.setValue(FULL, true);
        }

        return state;
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos other, boolean p_60514_) {
        level.scheduleTick(pos, state.getBlock(), 5);

        super.neighborChanged(state, level, pos, block, other, p_60514_);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return state.getValue(FULL) ? Shapes.block()
                : switch (state.getValue(LEVEL)) {
            case 1 -> Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
            case 2 -> Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
            case 3 -> Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D);
            case 4 -> Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D);
            case 5 -> Block.box(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D);
            case 6 -> Shapes.block();
            default -> throw new IllegalStateException("Unexpected value: " + state.getValue(LEVEL));
        };
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.is(Items.BUCKET)) {
            stack.shrink(1);
            player.setItemInHand(hand, stack);
            player.addItem(bucket.get().getDefaultInstance());
            level.destroyBlock(pos, false);
            return InteractionResult.SUCCESS;
        }

        return super.use(state, level, pos, player, hand, result);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return getShape(state, getter, pos, context);
    }
}
