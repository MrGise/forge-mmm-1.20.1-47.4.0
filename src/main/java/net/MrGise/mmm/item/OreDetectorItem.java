package net.MrGise.mmm.item;

import net.MrGise.mmm.registry.content.ModItems;
import net.MrGise.mmm.registry.decorative.ModParticles;
import net.MrGise.mmm.registry.decorative.ModSounds;
import net.MrGise.mmm.util.InventoryUtil;
import net.MrGise.mmm.registry.variables.ModTags;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
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

                    if (InventoryUtil.hasPlayerStackInInventory(player, ModItems.ORE_REDETECTOR.get())) {
                        addDataToRedetector(player, positionClicked.below(i), blockState.getBlock());
                    }

                    pContext.getLevel().playSeededSound(null, player.getX(), player.getY(), player.getZ(),
                            ModSounds.ORE_DETECTOR_SUCCESS.get(), SoundSource.BLOCKS, 1f, 1f, 0);

                    spawnFoundParticles(pContext, positionClicked, blockState);

                    break;

                }

            }

            if(!FoundBlock) {
                outputFailure(player);
            }

        }

        pContext.getItemInHand().hurtAndBreak(1, pContext.getPlayer(),
                player -> player.broadcastBreakEvent(player.getUsedItemHand()));

        pContext.getPlayer().getCooldowns().addCooldown(this, 10);

        return InteractionResult.SUCCESS;
    }

    private void spawnFoundParticles(UseOnContext pContext, BlockPos positionClicked, BlockState blockState) {
        for(int i = 0; i < 10; i++) {
            ServerLevel level = (ServerLevel) pContext.getLevel();

            level.sendParticles(ModParticles.PURPLE_MANA.get(),
                    positionClicked.getX() + 0.5d, positionClicked.getY() + 1, positionClicked.getZ() + 0.5d, 1,
                    Math.cos(i * 18) * 0.15d, 0.15d, Math.sin(i * 18) * 0.15d, 0.1);
            level.sendParticles(ModParticles.SMALL_PURPLE_MANA.get(),
                    positionClicked.getX() + 0.5d, positionClicked.getY() + 1, positionClicked.getZ() + 0.5d, 1,
                    Math.cos(i * 18) * 0.15d, 0.15d, Math.sin(i * 18) * 0.15d, 0.1);
        }
    }

    private void addDataToRedetector(Player player, BlockPos pos, Block block) {
        ItemStack redetector = player.getInventory().getItem(InventoryUtil.getFirstInventoryIndex(player, ModItems.ORE_REDETECTOR.get()));

        CompoundTag data = new CompoundTag();
        data.putString("mmm.current_ore", "Last found ore block: " + I18n.get(block.getDescriptionId()) + " at: (" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + ").");

        redetector.setTag(data);

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
