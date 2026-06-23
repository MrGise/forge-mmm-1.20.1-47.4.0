package net.MrGise.mmm.block.kitchen;

import net.MrGise.mmm.block.entity.BowlBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BowlBlock extends BaseEntityBlock {
    public BowlBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return Shapes.join(Shapes.box(1 / 16d, 0 / 16d, 1 / 16d, 15 / 16d, 3 / 16d, 15 / 16d),
                Shapes.box(2 / 16d, 1 / 16d, 2 / 16d, 14 / 16d, 3 / 16d, 14 / 16d), BooleanOp.NOT_SAME);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (level.getBlockEntity(pos) instanceof BowlBlockEntity be) {
            switch (be.getClickResult(player.getItemInHand(hand))) {
                case INSERT -> {
                    be.addItem(player, hand);
                    return InteractionResult.SUCCESS;
                }
                case TAKE -> {
                    be.takeItem(player, hand);
                    return InteractionResult.SUCCESS;
                }
                case POUR -> {
                    be.inputFluid(player, hand);
                    return InteractionResult.SUCCESS;
                }
                case TAKE_FLUID -> {
                    be.takeFluid(player, hand);
                    return InteractionResult.SUCCESS;
                }
                case MIX -> {
                    if (be.isCrafting()) {
                        be.progressCrafting(level);
                    } else {
                        be.startCrafting(level);
                    }
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.FAIL;
        }
        return InteractionResult.PASS;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BowlBlockEntity(pos, state);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            if (level.getBlockEntity(pos) instanceof BowlBlockEntity be) {
                be.drops();
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}
