package net.MrGise.mmm.item.block_item.description;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DescriptionBlockItem extends BlockItem {

    private String DescriptionTranslatable;
    private boolean ShiftToView = true;

    public DescriptionBlockItem(Properties pProperties, Block pBlock, String DescriptionTranslatable, boolean ShiftToView) {
        super(pBlock, pProperties);
        this.DescriptionTranslatable = DescriptionTranslatable;
        this.ShiftToView = ShiftToView;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        if(!Screen.hasShiftDown() && ShiftToView) {
            pTooltipComponents.add(Component.translatable("tooltip.mmm.description_item.tooltip.noshift"));
        } else {
            pTooltipComponents.add(Component.translatable("tooltip.mmm.description_block.tooltip." + DescriptionTranslatable).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        }


        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
