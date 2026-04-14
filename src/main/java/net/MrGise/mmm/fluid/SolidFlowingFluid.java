package net.MrGise.mmm.fluid;

import net.MrGise.mmm.MMM;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public abstract class SolidFlowingFluid extends ForgeFlowingFluid {

    public SolidFlowingFluid(Properties properties) {
        super(properties);
    }

    @Override
    public void spreadTo(LevelAccessor accessor, BlockPos pos, BlockState state, Direction direction, FluidState fluidState) {
        AABB box = new AABB(pos);
        if (!accessor.getEntities(null, box).isEmpty()) {
            MMM.LOGGER.info("There were entities in {}: {}",pos , accessor.getEntities(null, box));
            return;
        }

        super.spreadTo(accessor, pos, state, direction, fluidState);
    }

    public static class Flowing extends SolidFlowingFluid {
        public Flowing(Properties properties)
        {
            super(properties);
            registerDefaultState(getStateDefinition().any().setValue(LEVEL, 7));
        }

        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }

        public boolean isSource(FluidState state) {
            return false;
        }
    }

    public static class Source extends SolidFlowingFluid
    {
        public Source(Properties properties)
        {
            super(properties);
        }

        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        public int getAmount(FluidState state) {
            return 8;
        }

        public boolean isSource(FluidState state) {
            return true;
        }
    }
}
