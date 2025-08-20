package net.MrGise.mmm.item.description;

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

public class DescriptionPortalBlockItem extends BlockItem {

    private String EyeName;
    private boolean ShiftToView = true;

    public DescriptionPortalBlockItem(Block pBlock, Properties pProperties, String EyeName, boolean ShiftToView) {
        super(pBlock, pProperties);
        this.EyeName = EyeName;
        this.ShiftToView = ShiftToView;
    }

    public DescriptionPortalBlockItem(Block pBlock, Properties pProperties, String EyeName) {
        super(pBlock, pProperties);
        this.EyeName = EyeName;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        if((!Screen.hasShiftDown() && ShiftToView) == true) {
            pTooltipComponents.add(Component.translatable("tooltip.mmm.description_item.tooltip.noshift"));
        } else {
            pTooltipComponents.add(Component.literal(" "));
            pTooltipComponents.add(Component.translatable("tooltip.mmm.portal_block.tooltip.common_line_1").withStyle(ChatFormatting.GRAY));
            pTooltipComponents.add(Component.translatable("tooltip.mmm.portal_block.tooltip.common_line_2").withStyle(ChatFormatting.GRAY));
        }
        pTooltipComponents.add(Component.literal(" "));
        pTooltipComponents.add(Component.translatable("tooltip.mmm.portal_block.tooltip.eye").withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(Component.translatable("tooltip.mmm.portal_block.tooltip." + EyeName).withStyle(ChatFormatting.GREEN));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

}
