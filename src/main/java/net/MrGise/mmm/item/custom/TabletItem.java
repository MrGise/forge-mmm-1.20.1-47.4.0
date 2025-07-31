package net.MrGise.mmm.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TabletItem extends Item {

    int maxGlyphs;

    public TabletItem(Properties pProperties, int maxGlyphs) {
        super(pProperties);
        this.maxGlyphs = maxGlyphs;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);

        pTooltipComponents.add(Component.literal(" "));
        CompoundTag tag = pStack.getTag();
        if (tag != null && tag.contains("glyphs", Tag.TAG_LIST)) {
            ListTag glyphs = tag.getList("glyphs", Tag.TAG_STRING);

            if (!glyphs.isEmpty()) {
                pTooltipComponents.add(Component.translatable("tooltip.mmm.glyphs.title").withStyle(ChatFormatting.GRAY));
                for (int i = 0; i < glyphs.size(); i++) {
                    pTooltipComponents.add(Component.translatable("tooltip.mmm.glyphs.name." + glyphs.getString(i)).withStyle(ChatFormatting.AQUA));
                }
            } else {
                pTooltipComponents.add(Component.translatable("tooltip.mmm.glyphs.empty").withStyle(ChatFormatting.GRAY));
            }
        } else {
            pTooltipComponents.add(Component.translatable("tooltip.mmm.glyphs.empty").withStyle(ChatFormatting.GRAY));
        }

    }

    public int getMaxGlyphs() {
        return maxGlyphs;
    }

}
