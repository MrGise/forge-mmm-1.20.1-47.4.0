package net.MrGise.mmm.block.entity;

import net.MrGise.mmm.registry.content.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BowlBlockEntity extends BlockEntity {
    private static final int SLOT_1 = 0;
    private static final int SLOT_2 = 1;
    private static final int SLOT_3 = 2;

    public BowlBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BOWL_BE.get(), pos, state);
    }
}
