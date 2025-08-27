package net.MrGise.mmm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ThinLogBlock extends RotatedPillarBlock {
    public ThinLogBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(AXIS)) {
            case X -> Shapes.box(0/16.0, 3/16.0, 3/16.0, 16/16.0, 13/16.0, 13/16.0);
            case Y -> Shapes.box(3/16.0, 0/16.0, 3/16.0, 13/16.0, 16/16.0, 13/16.0);
            case Z -> Shapes.box(3/16.0, 3/16.0, 0/16.0, 13/16.0, 13/16.0, 16/16.0);
        };
    }
}
