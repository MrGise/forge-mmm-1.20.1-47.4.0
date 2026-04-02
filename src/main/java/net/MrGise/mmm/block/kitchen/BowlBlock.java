package net.MrGise.mmm.block.kitchen;

import net.MrGise.mmm.block.entity.BowlBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
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
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BowlBlockEntity(pos, state);
    }
}
