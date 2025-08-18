package net.MrGise.mmm.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Optional;

public class CustomGrassBlock extends Block implements BonemealableBlock {

    private final RegistryObject<Block> grassless;

    private final RegistryObject<Block> grass;

    public CustomGrassBlock(Properties properties, RegistryObject<Block> grassless, RegistryObject<Block> grass) {
        super(properties);
        this.grassless = grassless;
        this.grass = grass;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        // Only spread if THIS block is the grassful variant

        spread(state, level, pos, random);

    }

    public void spread(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
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

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return pLevel.getBlockState(pPos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        spread(pState, pLevel, pPos, pRandom);
    }
}
