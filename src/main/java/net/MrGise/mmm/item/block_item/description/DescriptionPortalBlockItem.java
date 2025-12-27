package net.MrGise.mmm.item.block_item.description;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DescriptionPortalBlockItem extends BlockItem {

    private String EyeName;
    private String EyeModId;
    private boolean ShiftToView = true;

    public DescriptionPortalBlockItem(Block pBlock, Properties pProperties, String EyeName, String EyeModId, boolean ShiftToView) {
        super(pBlock, pProperties);
        this.EyeName = EyeName;
        this.EyeModId = EyeModId;
        this.ShiftToView = ShiftToView;
    }

    public DescriptionPortalBlockItem(Block pBlock, Properties pProperties, String EyeName, String EyeModId) {
        super(pBlock, pProperties);
        this.EyeName = EyeName;
        this.EyeModId = EyeModId;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        if((!Screen.hasShiftDown() && ShiftToView)) {
            pTooltipComponents.add(Component.translatable("tooltip.mmm.description_item.tooltip.noshift"));
        } else {
            pTooltipComponents.add(Component.literal(" "));
            pTooltipComponents.add(Component.translatable("tooltip.mmm.portal_block.tooltip.common_line_1").withStyle(ChatFormatting.GRAY));
            pTooltipComponents.add(Component.translatable("tooltip.mmm.portal_block.tooltip.common_line_2").withStyle(ChatFormatting.GRAY));
        }
        pTooltipComponents.add(Component.literal(" "));
        pTooltipComponents.add(Component.translatable("tooltip.mmm.portal_block.tooltip.eye").withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(Component.literal(" ")
                .append(Component.translatable(ForgeRegistries.ITEMS
                        .getValue(ResourceLocation.fromNamespaceAndPath(EyeModId, EyeName)).getDescriptionId())
                        .append(" ")).append("(" + EyeModId + ":" + EyeName + ")").withStyle(ChatFormatting.GREEN));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

}
