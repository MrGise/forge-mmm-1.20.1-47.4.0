package net.MrGise.mmm.item;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class MimicBlockItem extends BlockItem {

    public MimicBlockItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public Component getName(ItemStack pStack) {

        CompoundTag tag = pStack.getTag();
        if (tag != null && tag.contains("form", Tag.TAG_STRING)) {
            String form = tag.getString("form");
            String key = this.getDescriptionId(pStack) + "." + form;

            if (I18n.exists(key)) {
                return Component.translatable(key);
            } else {
                return Component.translatable("item.mmm.mimic.fail");
            }
        } else {
            return Component.translatable(this.getDescriptionId(pStack));
        }

    }

}
