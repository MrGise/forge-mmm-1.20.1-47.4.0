package net.MrGise.mmm.block.dough;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.registry.variables.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class UncookedMatzaBlock extends HorizontalDirectionalBlock {
    private static int maxHoles = 14;

    public static final IntegerProperty HOLES = IntegerProperty.create("holes", 1, maxHoles);

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public UncookedMatzaBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(HOLES, 1).setValue(FACING, Direction.NORTH));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        ItemStack stack = player.getItemInHand(hand);

        if (!(state.getBlock() instanceof UncookedMatzaBlock)) return super.use(state, level, pos, player, hand, result);

        if (stack.is(ModTags.Items.HOLEABLE) && (state.getValue(HOLES) < maxHoles) && !player.isShiftKeyDown()) {
            level.setBlock(pos, state.setValue(HOLES, state.getValue(HOLES) + 1), 3);
            level.playSound(player, pos, SoundEvents.WOOL_HIT, SoundSource.BLOCKS, 1.0f, 1.0f);
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HOLES, FACING);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return Block.box(0.0d, 0.0d, 0.0d, 16.0d, 1.0d, 16.0d);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP)) {
            level.destroyBlock(pos, true);
        }
    }
}
