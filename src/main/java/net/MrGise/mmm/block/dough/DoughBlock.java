package net.MrGise.mmm.block.dough;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DoughBlock extends Block {
    public DoughBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return Shapes.or(box(3.0d, 0.0d, 3.0d, 13.0d, 2.0d, 13.0d),
                box(4.0d, 2.0d, 4.0d, 12.0d, 3.0d, 12.0d));
    }
}
