package net.MrGise.mmm.item.block_item;

import net.MrGise.mmm.resource.ClientKnowledgeData;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class SecretBlockItem extends BlockItem {
    public SecretBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public Component getName(ItemStack stack) {
        return playerKnows() ? super.getName(stack)
                : super.getName(stack).copy().withStyle(ChatFormatting.OBFUSCATED);
    }

    private static boolean playerKnows() {
        return ClientKnowledgeData.ALL_KNOWING;
    }
}
