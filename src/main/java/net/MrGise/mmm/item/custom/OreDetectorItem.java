package net.MrGise.mmm.item.custom;

import net.MrGise.mmm.block.ModBlocks;
import net.MrGise.mmm.util.ModTags;
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

        player.sendSystemMessage(Component.literal(" "));
        player.sendSystemMessage(Component.literal("Failed to find an ore"));
        player.sendSystemMessage(Component.literal(" "));

    }

    private void outputOrePosition(BlockPos below, Player player, Block block) {
        player.sendSystemMessage(Component.literal(" "));
        player.sendSystemMessage(Component.literal("Found ore block: " + I18n.get(block.getDescriptionId()) + " at: (" + below.getX() + ", " + below.getY() + ", " + below.getZ() + ")."));
        player.sendSystemMessage(Component.literal(" "));
    }

    private boolean isOreBlock(BlockState blockState) {

        return blockState.is(ModTags.Blocks.DETECTABLE_ORE);

    }

}
