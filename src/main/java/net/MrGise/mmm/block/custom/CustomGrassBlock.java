package net.MrGise.mmm.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;

public class CustomGrassBlock extends Block {

    private final RegistryObject<Block> grassless;

    public CustomGrassBlock(Properties properties, RegistryObject<Block> grassless) {
        super(properties);
        this.grassless = grassless;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        // Only spread if THIS block is the grassful variant
        if (state.is(this)) {
            if (!canStayGrass(level, pos)) {
                // If it can't survive, turn into grassless variant
                level.setBlockAndUpdate(pos, grassless.get().defaultBlockState());
            } else {
                // Try to spread
                BlockPos targetPos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                BlockState targetState = level.getBlockState(targetPos);

                if (targetState.is(grassless.get()) && canStayGrass(level, targetPos)) {
                    level.setBlockAndUpdate(targetPos, this.defaultBlockState());
                }
            }
        }
    }

    private boolean canStayGrass(LevelReader level, BlockPos pos) {
        BlockPos abovePos = pos.above();
        BlockState aboveState = level.getBlockState(abovePos);

        // Must have enough light and not be covered by a fully solid block
        int light = level.getMaxLocalRawBrightness(abovePos);
        return light >= 4 && !aboveState.isSolidRender(level, abovePos);
    }
}
