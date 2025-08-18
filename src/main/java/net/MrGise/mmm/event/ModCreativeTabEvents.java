package net.MrGise.mmm.event;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.block.ModBlocks;
import net.MrGise.mmm.enchantment.ModEnchantments;
import net.MrGise.mmm.item.ModCreativeModeTabs;
import net.MrGise.mmm.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = MMM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeTabEvents {

    @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent event) {

        if (event.getTab() == ModCreativeModeTabs.RUIN_ITEMS.get()) {
            Enchantment enchantment1 = ModEnchantments.LIGHTENING_STRIKER.get();

            for (int level = enchantment1.getMinLevel(); level <= enchantment1.getMaxLevel(); level++) {
                // Create an enchanted book with the given enchantment and level
                ItemStack enchantedBook = EnchantedBookItem.createForEnchantment(
                        new EnchantmentInstance(enchantment1, level));

                // Add it to the creative tab
                event.accept(enchantedBook);
            }

        }

        if (event.getTab() == ModCreativeModeTabs.THE_SKYLAND.get()) {
            CompoundTag tag = new CompoundTag();

            ItemStack short_grass = ModBlocks.HEAVENLY_GRASS.get().asItem().getDefaultInstance();
            ItemStack long_grass = ModBlocks.HEAVENLY_GRASS.get().asItem().getDefaultInstance();

            long_grass.getOrCreateTag().putBoolean("long", true);

            event.getEntries().putAfter(short_grass, long_grass, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

        }

    }

}
