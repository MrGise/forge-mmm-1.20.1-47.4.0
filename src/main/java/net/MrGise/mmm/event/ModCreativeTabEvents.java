package net.MrGise.mmm.event;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.enchantment.ModEnchantments;
import net.MrGise.mmm.item.ModCreativeModeTabs;
import net.MrGise.mmm.item.ModItems;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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

        if (event.getTab() == ModCreativeModeTabs.MISCELLANEOUS_MMM.get()) {
            Enchantment enchantment1 = ModEnchantments.LIGHTENING_STRIKER.get();

            ItemStack enchantedBook = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment1, 3));
            event.accept(enchantedBook);

            ItemStack sword = new ItemStack(ModItems.ACTIONLITE_SWORD.get());
            sword.enchant(enchantment1, 3);
            event.accept(sword);

            ItemStack sword1 = new ItemStack(ModItems.ACTIONLITE_SWORD.get());
            sword1.enchant(enchantment1, 3);
            event.accept(sword1);

        }

    }

}
