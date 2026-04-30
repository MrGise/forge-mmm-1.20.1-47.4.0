package net.MrGise.mmm.item.block_item;

import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;

public class AlternateBucketItem extends BlockItem {
    @Nullable
    private String descriptionId;

    public AlternateBucketItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResult place(BlockPlaceContext context) {
        if (!this.getBlock().isEnabled(context.getLevel().enabledFeatures())) {
            return InteractionResult.FAIL;
        } else if (!context.canPlace()) {
            return InteractionResult.FAIL;
        } else {
            BlockPlaceContext placeContext = this.updatePlacementContext(context);
            if (placeContext == null) {
                return InteractionResult.FAIL;
            } else {
                BlockState placeState = this.getPlacementState(placeContext);
                if (placeState == null) {
                    return InteractionResult.FAIL;
                } else if (!this.placeBlock(placeContext, placeState)) {
                    return InteractionResult.FAIL;
                } else {
                    BlockPos targetPos = placeContext.getClickedPos();
                    Level level = placeContext.getLevel();
                    Player player = placeContext.getPlayer();
                    ItemStack itemstack = placeContext.getItemInHand();
                    BlockState targetState = level.getBlockState(targetPos);
                    if (targetState.is(placeState.getBlock())) {
                        targetState = this.updateBlockStateFromTag(targetPos, level, itemstack, targetState);
                        this.updateCustomBlockEntityTag(targetPos, level, player, itemstack, targetState);
                        targetState.getBlock().setPlacedBy(level, targetPos, targetState, player, itemstack);
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, targetPos, itemstack);
                        }
                    }

                    SoundType soundtype = targetState.getSoundType(level, targetPos, context.getPlayer());
                    level.playSound(player, targetPos, this.getPlaceSound(targetState, level, targetPos, context.getPlayer()), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    level.gameEvent(GameEvent.BLOCK_PLACE, targetPos, GameEvent.Context.of(player, targetState));
                    if (player == null || !player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }
                    if (player != null && !player.getAbilities().instabuild) {
                        player.addItem(Items.BUCKET.getDefaultInstance());
                    }

                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
            }
        }
    }

    @Override
    public String getDescriptionId() {
        if (this.descriptionId == null) {
            this.descriptionId = Util.makeDescriptionId("item", BuiltInRegistries.ITEM.getKey(this));
        }

        return this.descriptionId;
    }
}
