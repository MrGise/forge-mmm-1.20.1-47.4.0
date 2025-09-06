package net.MrGise.mmm.event;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.command.FindHomeCommand;
import net.MrGise.mmm.command.ReturnHomeCommand;
import net.MrGise.mmm.command.SetHomeCommand;
import net.MrGise.mmm.item.HammerItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import java.util.*;

@Mod.EventBusSubscriber(modid = MMM.MOD_ID)
public class ModEvents {

    // Done with the help of https://github.com/CoFH/CoFHCore/blob/1.19.x/src/main/java/cofh/core/event/AreaEffectEvents.java

    public static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();
    @SubscribeEvent
    public static void onHammerUsage(BlockEvent.BreakEvent event) {

        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getMainHandItem();

        if (mainHandItem.getItem() instanceof HammerItem hammer && player instanceof ServerPlayer serverPlayer) {
            BlockPos initalBlockPos = event.getPos();
            if (HARVESTED_BLOCKS.contains(initalBlockPos)) {
                return;
            }

            for (BlockPos pos : HammerItem.getBlocksToBeDestroyed(HammerItem.range, initalBlockPos, serverPlayer)) {
                if (pos == initalBlockPos || !hammer.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                    continue;
                }

                HARVESTED_BLOCKS.add(pos);
                serverPlayer.gameMode.destroyBlock(pos);
                HARVESTED_BLOCKS.remove(pos);
            }
        }
    }

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        new SetHomeCommand(event.getDispatcher());
        new ReturnHomeCommand(event.getDispatcher());
        new FindHomeCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        event.getEntity().getPersistentData().putIntArray("mmm.homepos", event.getOriginal().getPersistentData().getIntArray("mmm.homepos"));
    }

    public static final Map<Block, Block> LogMap = new HashMap<>();
    public static final Map<Block, ItemLike> BarkMap = new HashMap<>();
    @SubscribeEvent
    public static void stripLogs(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        Player player = event.getEntity();
        InteractionHand hand = event.getHand();
        ItemStack heldStack = player.getItemInHand(hand);
        Block block = level.getBlockState(pos).getBlock();

        // Only strip if held item is an axe
        if (heldStack.getItem() instanceof AxeItem && LogMap.containsKey(block)) {
            Block stripped = LogMap.get(block);
            Direction.Axis axis = level.getBlockState(pos).getValue(RotatedPillarBlock.AXIS);

            // Play swing animation
            player.swing(hand);

            // Replace block with stripped version (keep axis)
            level.setBlock(pos, stripped.defaultBlockState().setValue(RotatedPillarBlock.AXIS, axis), 11);

            // Play strip sound
            level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0f, 1.0f);

            // Drop bark (if exists)
            if (BarkMap.containsKey(block)) {
                ItemLike dropped = BarkMap.get(block);
                ItemStack dropStack = new ItemStack(dropped, 1);

                if (!level.isClientSide) {
                    ItemEntity drop = new ItemEntity(
                            level,
                            pos.getX() + 0.5,
                            pos.getY() + 0.5,
                            pos.getZ() + 0.5,
                            dropStack
                    );
                    level.addFreshEntity(drop);
                }
            }

            // Damage axe (and break if necessary)
            heldStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));

            // Cancel normal interaction to prevent placing the log instead
            event.setCanceled(true);
        }
    }
}
