package net.MrGise.mmm.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.MrGise.mmm.MMM;
import net.MrGise.mmm.command.*;
import net.MrGise.mmm.datagen.advancement.ModTriggers;
import net.MrGise.mmm.item.HammerItem;
import net.MrGise.mmm.registry.front.ModBlocks;
import net.MrGise.mmm.registry.front.item.ModItems;
import net.MrGise.mmm.registry.middle.ModVillagers;
import net.MrGise.mmm.util.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@Mod.EventBusSubscriber(modid = MMM.MOD_ID)
public class ModEvents {

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
    public static void addModVillagerTrades(VillagerTradesEvent event) {
        if (event.getType() == ModVillagers.BOWYER.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            villagerSell(trades, 1, 8, new ItemStack(Items.BOW), 12, 3, 0.1f);
            villagerSell(trades, 1, 10, new ItemStack(ModItems.REINFORCED_STONE_BOW.get()), 12, 3, 0.15f);
            villagerSell(trades, 1, 4, new ItemStack(Items.ARROW, 32), 6, 1, 0.05f);
            villagerSell(trades, 1, 1, new ItemStack(Items.STRING, 6), 32, 2, 0.05f);

            villagerSell(trades, 2, 12, new ItemStack(ModItems.REINFORCED_GOLD_BOW.get()), 12, 4, 0.2f);
            villagerSell(trades, 2, 16, new ItemStack(ModItems.REINFORCED_IRON_BOW.get()), 12, 4, 0.25f);
            villagerBuy(trades, 2, new ItemStack(Items.BOW), null, 6, 9, 4, 0.25f);
            villagerBuy(trades, 2, new ItemStack(ModItems.REINFORCED_STONE_BOW.get()), null, 8, 9, 4, 0.25f);
            villagerUpgrade(trades, 2,
                    new ItemStack(Items.BOW), 4,
                    new ItemStack(ModItems.REINFORCED_STONE_BOW.get()), 16, 2, 0.05f);

            villagerSell(trades, 3, 20, new ItemStack(ModItems.REINFORCED_SKIRON_BOW.get()), 10, 5, 0.26f);
            villagerSell(trades, 3, 22, new ItemStack(ModItems.REINFORCED_ACTINOLITE_BOW.get()), 10, 5, 0.3f);
            villagerBuy(trades, 3, new ItemStack(ModItems.REINFORCED_IRON_BOW.get()), null, 14, 9, 4, 0.25f);
            villagerBuy(trades, 3, new ItemStack(ModItems.REINFORCED_GOLD_BOW.get()), null, 10, 9, 4, 0.25f);
            villagerUpgrade(trades, 3,
                    new ItemStack(Items.BOW), 8,
                    new ItemStack(ModItems.REINFORCED_IRON_BOW.get()), 16, 3, 0.05f);
            villagerUpgrade(trades, 3,
                    new ItemStack(Items.BOW), 6,
                    new ItemStack(ModItems.REINFORCED_GOLD_BOW.get()), 16, 3, 0.05f);

            villagerSell(trades, 4, 25, new ItemStack(ModItems.REINFORCED_DIAMOND_BOW.get()), 12, 6, 0.35f);
            villagerSell(trades, 4, 30, new ItemStack(ModItems.REINFORCED_NETHERITE_BOW.get()), 10, 6, 0.4f);
            villagerBuy(trades, 4, new ItemStack(ModItems.REINFORCED_SKIRON_BOW.get()), null, 18, 9, 4, 0.25f);
            villagerBuy(trades, 4, new ItemStack(ModItems.REINFORCED_ACTINOLITE_BOW.get()), null, 20, 9, 4, 0.25f);
            villagerUpgrade(trades, 4,
                    new ItemStack(Items.BOW), 10,
                    new ItemStack(ModItems.REINFORCED_SKIRON_BOW.get()), 16, 4, 0.05f);
            villagerUpgrade(trades, 4,
                    new ItemStack(Items.BOW), 12,
                    new ItemStack(ModItems.REINFORCED_ACTINOLITE_BOW.get()), 16, 4, 0.05f);

            villagerSell(trades, 5, 32, ItemUtils.enchantedBook(
                    new EnchantmentInstance(Enchantments.INFINITY_ARROWS, 1),
                    new EnchantmentInstance(Enchantments.MENDING, 1)), 6, 8, 0.2f);
            villagerBuy(trades, 5, new ItemStack(ModItems.REINFORCED_DIAMOND_BOW.get()), null, 22, 9, 5, 0.25f);
            villagerBuy(trades, 5, new ItemStack(ModItems.REINFORCED_NETHERITE_BOW.get()), null, 25, 9, 6, 0.25f);
            villagerUpgrade(trades, 4,
                    new ItemStack(Items.BOW), 14,
                    new ItemStack(ModItems.REINFORCED_DIAMOND_BOW.get()), 16, 5, 0.05f);
            villagerUpgrade(trades, 4,
                    new ItemStack(Items.BOW), 16,
                    new ItemStack(ModItems.REINFORCED_NETHERITE_BOW.get()), 16, 5, 0.05f);

        }
    }
    @SubscribeEvent
    public static void addWanderingTraderTrades(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> generic = event.getGenericTrades();
        List<VillagerTrades.ItemListing> rare = event.getRareTrades();

        generic.add((trader, random) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 2),
                new ItemStack(ModBlocks.OXALIS.get(), 1),
                16, 2, 0.05f
        ));
        generic.add((trader, random) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 1),
                new ItemStack(ModItems.CUCUMBER_SEEDS.get(), 2),
                16, 1, 0.05f
        ));
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

    private static void villagerTrade(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades, int level, MerchantOffer offer) {
        trades.get(level).add((tr, rn) -> offer);
    }
    private static void villagerSell(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades, int level, int price, ItemStack product, int uses, int xp, float multiplier) {
        if (price > 0) {
            if (price > 64 && price <= 128) {
                villagerTrade(trades, level, new MerchantOffer(
                        new ItemStack(Items.EMERALD, 64),
                        new ItemStack(Items.EMERALD, price -64),
                        product, uses, xp, multiplier
                ));
            } else {
                villagerTrade(trades, level, new MerchantOffer(
                        new ItemStack(Items.EMERALD, price),
                        product, uses, xp, multiplier
                ));
            }
        }
    }
    private static void villagerBuy(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades, int level, ItemStack req1, @Nullable ItemStack req2, int For, int uses, int xp, float multiplier) {
        if (For <= 64 && For > 0) {
            if (req2 == null) {
                villagerTrade(trades, level, new MerchantOffer(
                        req1,
                        new ItemStack(Items.EMERALD, For),
                        uses, xp, multiplier
                ));
            } else {
                villagerTrade(trades, level, new MerchantOffer(
                        req1, req2,
                        new ItemStack(Items.EMERALD, For),
                        uses, xp, multiplier
                ));
            }
        }
    }
    private static void villagerUpgrade(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades, int level, ItemStack foR, int price, ItemStack to, int uses, int xp, float multiplier) {
        villagerTrade(trades, level, new MerchantOffer(
                foR, new ItemStack(Items.EMERALD, price),
                to, uses, xp, multiplier
        ));
    }
}
