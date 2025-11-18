package net.MrGise.mmm.event;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.registry.ModBlocks;
import net.MrGise.mmm.registry.ModEnchantments;
import net.MrGise.mmm.registry.ModCreativeModeTabs;
import net.MrGise.mmm.registry.ModPaintings;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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

        if (event.getTab() == ModCreativeModeTabs.OVERWORLD.get()) {
            CompoundTag tag = new CompoundTag();

            ItemStack short_grass = ModBlocks.HEAVENLY_GRASS.get().asItem().getDefaultInstance();
            ItemStack long_grass = ModBlocks.HEAVENLY_GRASS.get().asItem().getDefaultInstance();

            long_grass.getOrCreateTag().putBoolean("long", true);

            event.getEntries().putAfter(short_grass, long_grass, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }

        if (event.getTab() == ModCreativeModeTabs.TOOLS_AND_MISC.get()) {
            CompoundTag tag = new CompoundTag();

            ItemStack magery_painting = Items.PAINTING.getDefaultInstance();

            Painting.storeVariant(magery_painting.getOrCreateTagElement("EntityTag"), ModPaintings.MAGERY.getHolder().get());

            event.getEntries().put(magery_painting, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }

    }

}
