package net.MrGise.mmm.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.MrGise.mmm.MMM;
import net.MrGise.mmm.command.*;
import net.MrGise.mmm.datagen.advancement.ModTriggers;
import net.MrGise.mmm.item.HammerItem;
import net.MrGise.mmm.registry.content.ModBlocks;
import net.MrGise.mmm.registry.content.ModFluids;
import net.MrGise.mmm.registry.content.ModItems;
import net.MrGise.mmm.registry.variables.ModTags;
import net.MrGise.mmm.network.ModNetwork;
import net.MrGise.mmm.network.SyncAllKnowingPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.server.command.ConfigCommand;
import vectorwing.farmersdelight.common.tag.ForgeTags;

import java.util.*;

@Mod.EventBusSubscriber(modid = MMM.MOD_ID)
public class ModGeneralEvents {

    @SubscribeEvent
    public static void onPlayerFirstJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity().level().isClientSide) return;
        Player player = event.getEntity();

        CompoundTag data = player.getPersistentData();
        CompoundTag persistent = data.getCompound(Player.PERSISTED_NBT_TAG);

        if (!persistent.contains("mmm.first")) {
            persistent.putBoolean("mmm.first", true);
            //. First time Logic

            MMM.LOGGER.info("Player {} joined for the first time!", player.getName().getString());
        } else {
            if (data.getBoolean("mmm.first")) {
                persistent.putBoolean("mmm.first", false);
                //. Second time logic

                MMM.LOGGER.info("Player {} joined for the second time!", player.getName().getString());
            } else {
                //. Every other time logic
                MMM.LOGGER.info("Player {} joined!", player.getName().getString());
            }
        }

        // Write back the persistent tag (important!)
        data.put(Player.PERSISTED_NBT_TAG, persistent);
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();
        boolean knows = player.getPersistentData().getBoolean("all_knowing");

        ModNetwork.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> player),
                new SyncAllKnowingPacket(knows)
        );
    }

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
    public static void onPlayerRClickOnBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        ItemStack item = event.getItemStack();
        BlockState clickedOn = level.getBlockState(event.getPos());
        BlockPos targetedPos = event.getHitVec().getBlockPos();
        BlockPos directionalPos = event.getHitVec().getBlockPos().relative(event.getHitVec().getDirection());
        boolean succeeded = false;

        if (item.is(Items.MILK_BUCKET)) { // Milk bucket placement
            if (player.isShiftKeyDown()) return;

            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);

            if (!level.isClientSide()) {
                if (level.isEmptyBlock(directionalPos) || level.getBlockState(directionalPos).canBeReplaced(ModFluids.SOURCE_COW_MILK.get())) {
                    level.setBlock(directionalPos, ModBlocks.COW_MILK_BLOCK.get().defaultBlockState(), 3);
                    succeeded = true;
                }
            }
            if (succeeded) {
                level.playSound(null, player.blockPosition(), SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS);
                if (!player.getAbilities().instabuild) {
                    item.shrink(1);
                    player.setItemInHand(event.getHand(), new ItemStack(Items.BUCKET));
                }
            }
        } else if (item.is(ForgeTags.DOUGH) && clickedOn.is(ModTags.Blocks.COUNTERS)
                && event.getHitVec().getDirection() == Direction.UP
                && level.getBlockState(directionalPos).canBeReplaced()) {
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);

            if (!level.isClientSide()) {
                level.setBlock(directionalPos, ModBlocks.PLACED_DOUGH.get().defaultBlockState(), 3);
            }

            level.playSound(null, player.blockPosition(), SoundEvents.WOOL_PLACE, SoundSource.PLAYERS);
            if (!player.getAbilities().instabuild) {
                item.shrink(1);
            }
        }
    }

    @SubscribeEvent
    public static void onBucketFill(FillBucketEvent event) {
        Player player = event.getEntity();
        Level level = event.getLevel();

        if (player == null || level.isClientSide) return;
        if (!event.getEmptyBucket().is(Items.BUCKET)) return;

        HitResult result = event.getTarget();
        if (!(result instanceof BlockHitResult)) return;

        BlockPos pos = ((BlockHitResult) result).getBlockPos();

        if (level.getBlockState(pos).is(ModBlocks.GOAT_MILK_BLOCK.get())) {
            level.playSound(
                    null,
                    pos,
                    SoundEvents.BUCKET_FILL,
                    SoundSource.PLAYERS
            );
        }

        if (!level.getBlockState(pos).is(ModBlocks.COW_MILK_BLOCK.get())) return;

        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);

        event.setFilledBucket(new ItemStack(Items.MILK_BUCKET));
        event.setResult(Event.Result.ALLOW);

        level.playSound(
                null,
                pos,
                SoundEvents.BUCKET_FILL,
                SoundSource.PLAYERS
        );
    }

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        new SetHomeCommand(event.getDispatcher());
        new ReturnHomeCommand(event.getDispatcher());
        new FindHomeCommand(event.getDispatcher());

        new ReJoinCommand(event.getDispatcher());

        new ToggleKnowCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        CompoundTag newData = event.getEntity().getPersistentData();
        CompoundTag originalData = event.getOriginal().getPersistentData();
        newData.putIntArray("mmm.homepos", originalData.getIntArray("mmm.homepos"));
        newData.putBoolean("all_knowing", originalData.getBoolean("all_knowing"));
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

    @SubscribeEvent
    public static void checkAdvancements(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.player instanceof ServerPlayer player)) return;

        BlockPos pos = player.blockPosition();
        ModTriggers.BLOCK_TOUCH.trigger(player, pos);
    }

    @SubscribeEvent
    public static void addCustomVillagerTrades(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.FARMER) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(1).add((trader, random) -> new MerchantOffer(
                    new ItemStack(ModItems.CUCUMBER.get(), 20),
                    new ItemStack(Items.EMERALD, 1),
                    16, 2, 0.05f
            ));
        }
    }

    @SubscribeEvent
    public static void addNeededAnvilRecipes(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();

        if (left.isEmpty() || right.isEmpty()) return;
        if (!(right.getItem() instanceof EnchantedBookItem)) return;
        if (!(left.getItem() instanceof BowItem)) return;

        Map<Enchantment, Integer> bookEnchants =
                EnchantmentHelper.getEnchantments(right);

        boolean hasInfinity = bookEnchants.containsKey(Enchantments.INFINITY_ARROWS);
        boolean hasMending = bookEnchants.containsKey(Enchantments.MENDING);

        if (!hasInfinity || !hasMending) return;

        ItemStack output = left.copy();

        Map<Enchantment, Integer> result = new HashMap<>(EnchantmentHelper.getEnchantments(output));
        result.putAll(bookEnchants);

        EnchantmentHelper.setEnchantments(result, output);

        event.setOutput(output);
        event.setCost(30);
        event.setMaterialCost(1);
    }
}
