package net.MrGise.mmm.item;

import net.MrGise.mmm.block.CustomGrass;
import net.MrGise.mmm.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResult;
import net.MrGise.mmm.registry.ModBlocks;

public class CustomGrassItem extends BlockItem {

    public CustomGrassItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public InteractionResult place(BlockPlaceContext context) {
        ItemStack stack = context.getItemInHand();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();

        boolean isLong = stack.hasTag() && stack.getTag().getBoolean("long");

        if (isLong) {
            // Place long grass (bottom + top)
            BlockState bottom = ModBlocks.HEAVENLY_GRASS.get().defaultBlockState()
                    .setValue(CustomGrass.LENGTH, CustomGrass.Length.BOTTOM);

            BlockState top = ModBlocks.HEAVENLY_GRASS.get().defaultBlockState()
                    .setValue(CustomGrass.LENGTH, CustomGrass.Length.TOP);

            // Make sure the top space is free
            if (pos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(pos.above()).isAir() && level.getBlockState(pos.below()).is(ModTags.Blocks.HEAVENLY_GRASS_PLACEABLES)) {
                level.setBlock(pos, bottom, 3);
                level.setBlock(pos.above(), top, 3);
                stack.shrink(1);

                SoundType soundtype = bottom.getSoundType(level, pos, context.getPlayer());
                level.playSound(context.getPlayer(), pos, this.getPlaceSound(bottom, level, pos, context.getPlayer()), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);

                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.FAIL;
            }
        } else {
            // Place short grass
            return super.place(context);
        }
    }

    @Override
    public Component getName(ItemStack pStack) {
        if (pStack.hasTag()) {
            return Component.translatable(this.getDescriptionId(pStack) + "_tall");
        } else {
            return Component.translatable(this.getDescriptionId(pStack));
        }
    }
}
