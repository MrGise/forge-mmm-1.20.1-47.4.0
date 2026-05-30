package net.MrGise.mmm.block.kitchen;

import net.MrGise.mmm.block.entity.BowlBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
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
        return Shapes.or(Shapes.box(1.0d, 0.0d, 1.0d, 15.0d, 3.0d, 15.0d),
                Shapes.box(2.0d, 1.0d, 2.0d, 14.0d, 3.0d, 14.0d));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (level.getBlockEntity(pos) instanceof BowlBlockEntity be) {
            switch(be.getClickResult(player.getItemInHand(hand))) {
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
}
