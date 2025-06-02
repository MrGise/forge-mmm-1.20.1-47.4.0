package net.MrGise.mmm.item.custom;

import net.MrGise.mmm.block.ModBlocks;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class OreDetectorItem extends Item {

    public OreDetectorItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {

        if(!pContext.getLevel().isClientSide()) {

            BlockPos positionClicked = pContext.getClickedPos();
            Player player = pContext.getPlayer();
            boolean FoundBlock = false;

            for(int i = 0; i <= positionClicked.getY() + 64; i++) {
                BlockState blockState = pContext.getLevel().getBlockState(positionClicked.below(i));

                if(isOreBlock(blockState)) {

                    outputOrePosition(positionClicked.below(i), player, blockState.getBlock());
                    FoundBlock = true;

                    break;

                }

            }

            if(!FoundBlock) {
                outputFailure(player);
            }

        }

        pContext.getItemInHand().hurtAndBreak(1, pContext.getPlayer(),
                player -> player.broadcastBreakEvent(player.getUsedItemHand()));

        return InteractionResult.SUCCESS;
    }

    private void outputFailure(Player player) {

        player.sendSystemMessage(Component.literal("Failed to find an ore"));

    }

    private void outputOrePosition(BlockPos below, Player player, Block block) {
        player.sendSystemMessage(Component.literal("Found ore block: " + I18n.get(block.getDescriptionId()) + " at: (" + below.getX() + ", " + below.getY() + ", " + below.getZ() + ")."));
    }

    private boolean isOreBlock(BlockState blockState) {

        return blockState.is(Blocks.COAL_ORE) || blockState.is(Blocks.IRON_ORE) || blockState.is(Blocks.GOLD_ORE)|| blockState.is(Blocks.EMERALD_ORE) || blockState.is(Blocks.DIAMOND_ORE) || blockState.is(Blocks.REDSTONE_ORE) || blockState.is(Blocks.LAPIS_ORE)
        || blockState.is(Blocks.DEEPSLATE_COAL_ORE) || blockState.is(Blocks.DEEPSLATE_IRON_ORE) || blockState.is(Blocks.DEEPSLATE_GOLD_ORE)|| blockState.is(Blocks.DEEPSLATE_EMERALD_ORE) || blockState.is(Blocks.DEEPSLATE_DIAMOND_ORE) || blockState.is(Blocks.DEEPSLATE_REDSTONE_ORE) || blockState.is(Blocks.DEEPSLATE_LAPIS_ORE)
                || blockState.is(Blocks.NETHER_GOLD_ORE) || blockState.is(Blocks.NETHER_QUARTZ_ORE) || blockState.is(Blocks.ANCIENT_DEBRIS)
                || blockState.is(Blocks.COPPER_ORE) || blockState.is(Blocks.DEEPSLATE_COPPER_ORE) || blockState.is(ModBlocks.SKIRON_ORE.get()) || blockState.is(ModBlocks.SKOAL_BLOCK.get());

    }

}
