package net.MrGise.mmm.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.MrGise.mmm.MMM;
import net.MrGise.mmm.registry.content.ModBlocks;
import net.MrGise.mmm.registry.content.ModItems;
import net.MrGise.mmm.registry.variants.ModVillagers;
import net.MrGise.mmm.util.ItemUtils;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Mod.EventBusSubscriber(modid = MMM.MOD_ID)
public class VillagerTradeEvents {
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
