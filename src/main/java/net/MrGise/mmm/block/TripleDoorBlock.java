package net.MrGise.mmm.block;

import net.MrGise.mmm.resource.TripleBlockPart;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;

public class TripleDoorBlock extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final EnumProperty<DoorHingeSide> HINGE = BlockStateProperties.DOOR_HINGE;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final EnumProperty<TripleBlockPart> PART = EnumProperty.create("part", TripleBlockPart.class);
    protected static final float AABB_DOOR_THICKNESS = 3.0F;
    protected static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
    protected static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape WEST_AABB = Block.box(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape EAST_AABB = Block.box(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);
    private final BlockSetType type;

    public TripleDoorBlock(Properties properties, BlockSetType type) {
        super(properties);
        this.type = type;
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(OPEN, Boolean.valueOf(false))
                .setValue(HINGE, DoorHingeSide.LEFT)
                .setValue(POWERED, Boolean.valueOf(false))
                .setValue(PART, TripleBlockPart.LOWER));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PART, FACING, OPEN, HINGE, POWERED);
    }

    public BlockSetType type() {
        return this.type;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        boolean flag = !state.getValue(OPEN);
        boolean flag1 = state.getValue(HINGE) == DoorHingeSide.RIGHT;
        return switch (direction) {
            case NORTH -> flag ? NORTH_AABB : (flag1 ? WEST_AABB : EAST_AABB);
            case SOUTH -> flag ? SOUTH_AABB : (flag1 ? EAST_AABB : WEST_AABB);
            case WEST -> flag ? WEST_AABB : (flag1 ? SOUTH_AABB : NORTH_AABB);
            default -> flag ? EAST_AABB : (flag1 ? NORTH_AABB : SOUTH_AABB);
        };
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState other, LevelAccessor accessor, BlockPos pos, BlockPos otherPos) {
        if (direction.getAxis() != Direction.Axis.Y) {
            return super.updateShape(state, direction, other, accessor, pos, otherPos);
        }

        TripleBlockPart part = state.getValue(PART);

        // What part do we expect in this direction?
        TripleBlockPart expectedPart = switch (part) {
            case LOWER -> direction == Direction.UP ? TripleBlockPart.MIDDLE : null;
            case MIDDLE -> direction == Direction.UP ? TripleBlockPart.UPPER
                    : direction == Direction.DOWN ? TripleBlockPart.LOWER
                    : null;
            case UPPER -> direction == Direction.DOWN ? TripleBlockPart.MIDDLE : null;
        };

        // If we expect a part but it doesn't match → break
        if (expectedPart != null) {
            if (!other.is(this) || other.getValue(PART) != expectedPart) {
                return Blocks.AIR.defaultBlockState();
            }

            // Sync properties from the neighbor
            return state
                    .setValue(FACING, other.getValue(FACING))
                    .setValue(OPEN, other.getValue(OPEN))
                    .setValue(HINGE, other.getValue(HINGE))
                    .setValue(POWERED, other.getValue(POWERED));
        }

        // LOWER must survive on ground
        if (part == TripleBlockPart.LOWER && direction == Direction.DOWN && !state.canSurvive(accessor, pos)) {
            return Blocks.AIR.defaultBlockState();
        }

        return super.updateShape(state, direction, other, accessor, pos, otherPos);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide && player.isCreative()) {
            dropOnlyFromTopCreative(level, pos, state, player);
        }

        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter getter, BlockPos pos, PathComputationType type) {
        return switch (type) {
            case LAND, AIR -> state.getValue(OPEN);
            default -> false;
        };
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        if (pos.getY() < level.getMaxBuildHeight() - 2
                && level.getBlockState(pos.above()).canBeReplaced(context)
                && level.getBlockState(pos.above().above()).canBeReplaced(context)) {
            boolean flag = level.hasNeighborSignal(pos) || level.hasNeighborSignal(pos.above()) || level.hasNeighborSignal(pos.above().above());
            return this.defaultBlockState()
                    .setValue(FACING, context.getHorizontalDirection())
                    .setValue(HINGE, this.getHinge(context))
                    .setValue(POWERED, Boolean.valueOf(flag))
                    .setValue(OPEN, Boolean.valueOf(flag))
                    .setValue(PART, TripleBlockPart.LOWER);
        } else {
            return null;
        }
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack) {
        level.setBlock(pos.above(), state.setValue(PART, TripleBlockPart.MIDDLE), 3);
        level.setBlock(pos.above().above(), state.setValue(PART, TripleBlockPart.UPPER), 3);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (!this.type.canOpenByHand()) {
            return InteractionResult.PASS;
        } else {
            state = state.cycle(OPEN);
            level.setBlock(pos, state, 10);
            this.playSound(player, level, pos, state.getValue(OPEN));
            level.gameEvent(player, this.isOpen(state) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos other, boolean isOpen) {
        TripleBlockPart part = state.getValue(PART);
        boolean powered = switch (part) {
            case LOWER ->
                    level.hasNeighborSignal(pos) ||
                            level.hasNeighborSignal(pos.above());

            case MIDDLE ->
                    level.hasNeighborSignal(pos.above()) ||
                            level.hasNeighborSignal(pos.below());

            case UPPER ->
                    level.hasNeighborSignal(pos) ||
                            level.hasNeighborSignal(pos.below());
        };
        if (!this.defaultBlockState().is(block) && powered != state.getValue(POWERED)) {
            if (powered != state.getValue(OPEN)) {
                this.playSound((Entity)null, level, pos, powered);
                level.gameEvent((Entity)null, powered ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
            }

            level.setBlock(pos, state.setValue(POWERED, powered).setValue(OPEN, powered), 2);
        }

    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
        BlockPos below = pos.below();
        BlockState stateBelow = reader.getBlockState(below);
        return state.getValue(PART) == TripleBlockPart.LOWER ? stateBelow.isFaceSturdy(reader, below, Direction.UP) : stateBelow.is(this);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return mirror == Mirror.NONE ? state : state.rotate(mirror.getRotation(state.getValue(FACING))).cycle(HINGE);
    }

    @Override
    public long getSeed(BlockState state, BlockPos pos) {
        return Mth.getSeed(pos.getX(), pos.below(switch(state.getValue(PART)) {
            case UPPER -> 2;
            case MIDDLE -> 1;
            case LOWER -> 0;
        }).getY(), pos.getZ());
    }

    public static boolean isWoodenDoor(Level level, BlockPos pos) {
        return isWoodenDoor(level.getBlockState(pos));
    }


    public static boolean isWoodenDoor(BlockState state) {
        Block block = state.getBlock();
        if (block instanceof TripleDoorBlock door) {
            if (door.type().canOpenByHand()) {
                return true;
            }
        }

        return false;
    }

    private void playSound(@Nullable Entity entity, Level level, BlockPos pos, boolean open) {
        level.playSound(entity, pos, open ? this.type.doorOpen() : this.type.doorClose(), SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F);
    }

    public boolean isOpen(BlockState state) {
        return state.getValue(OPEN);
    }

    public void setOpen(@Nullable Entity entity, Level level, BlockState state, BlockPos pos, boolean open) {
        if (state.is(this) && state.getValue(OPEN) != open) {
            level.setBlock(pos, state.setValue(OPEN, Boolean.valueOf(open)), 10);
            this.playSound(entity, level, pos, open);
            level.gameEvent(entity, open ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
        }
    }

    private DoorHingeSide getHinge(BlockPlaceContext context) {
        BlockGetter getter = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction horizDir = context.getHorizontalDirection();
        BlockPos above = pos.above();
        BlockPos top = above.above();
        Direction CCW = horizDir.getCounterClockWise();
        BlockPos CCWpos = pos.relative(CCW);
        BlockState CCWstate = getter.getBlockState(CCWpos);
        BlockPos aboveCCWpos = above.relative(CCW);
        BlockState aboveCCWstate = getter.getBlockState(aboveCCWpos);
        BlockPos topCCWpos = top.relative(CCW);
        BlockState topCCWstate = getter.getBlockState(topCCWpos);
        Direction CW = horizDir.getClockWise();
        BlockPos CWpos = pos.relative(CW);
        BlockState CWstate = getter.getBlockState(CWpos);
        BlockPos aboveCWpos = above.relative(CW);
        BlockState aboveCWstate = getter.getBlockState(aboveCWpos);
        BlockPos topCWpos = top.relative(CW);
        BlockState topCWstate = getter.getBlockState(topCWpos);
        int sideDecider = (CCWstate.isCollisionShapeFullBlock(getter, CCWpos) ? -1 : 0)
                + (aboveCCWstate.isCollisionShapeFullBlock(getter, aboveCCWpos) ? -1 : 0)
                + (topCCWstate.isCollisionShapeFullBlock(getter, topCCWpos) ? -1 : 0)
                + (CWstate.isCollisionShapeFullBlock(getter, CWpos) ? 1 : 0)
                + (aboveCWstate.isCollisionShapeFullBlock(getter, aboveCWpos) ? 1 : 0)
                + (topCWstate.isCollisionShapeFullBlock(getter, topCWpos) ? 1 : 0);
        boolean forHingeCCW = CCWstate.is(this) && CCWstate.getValue(PART) == TripleBlockPart.LOWER;
        boolean forHingeCW = CWstate.is(this) && CWstate.getValue(PART) == TripleBlockPart.LOWER;
        if ((!forHingeCCW || forHingeCW) && sideDecider <= 0) {
            if ((!forHingeCW || forHingeCCW) && sideDecider >= 0) {
                int stepX = horizDir.getStepX();
                int stepZ = horizDir.getStepZ();
                Vec3 vec3 = context.getClickLocation();
                double d0 = vec3.x - (double)pos.getX();
                double d1 = vec3.z - (double)pos.getZ();
                return (stepX >= 0 || !(d1 < 0.5D))
                        && (stepX <= 0 || !(d1 > 0.5D))
                        && (stepZ >= 0 || !(d0 > 0.5D))
                        && (stepZ <= 0 || !(d0 < 0.5D))
                        ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT;
            } else {
                return DoorHingeSide.LEFT;
            }
        } else {
            return DoorHingeSide.RIGHT;
        }
    }

    protected static void dropOnlyFromTopCreative(Level level, BlockPos pos, BlockState state, Player player) {
        TripleBlockPart part = state.getValue(PART);
        if (part == TripleBlockPart.LOWER) {
            BlockPos above = pos.above();
            BlockState aboveState = level.getBlockState(above);
            if (aboveState.is(state.getBlock()) && aboveState.getValue(PART) == TripleBlockPart.MIDDLE) {
                BlockState blockstate1 = aboveState.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
                level.setBlock(above, blockstate1, 35);
                level.levelEvent(player, 2001, above, Block.getId(aboveState));
            }
        }
        if (part == TripleBlockPart.UPPER) {
            BlockPos below = pos.below();
            BlockState belowState = level.getBlockState(below);
            if (belowState.is(state.getBlock()) && belowState.getValue(PART) == TripleBlockPart.MIDDLE) {
                BlockState blockstate1 = belowState.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
                level.setBlock(below, blockstate1, 35);
                level.levelEvent(player, 2001, below, Block.getId(belowState));
            }
        }
    }
}
