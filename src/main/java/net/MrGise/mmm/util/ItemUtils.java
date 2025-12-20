package net.MrGise.mmm.util;

import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import org.jetbrains.annotations.NotNull;

public class ItemUtils {
    public static ItemStack enchantedBook(@NotNull EnchantmentInstance... enchantments) {
        ItemStack book = Items.ENCHANTED_BOOK.getDefaultInstance();

        for (EnchantmentInstance enchantment : enchantments) {
            EnchantedBookItem.addEnchantment(book, enchantment);
        }

        return book;
    }
}
