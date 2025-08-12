package net.MrGise.mmm.item.custom.description;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MultiLineDescriptionItem extends Item {

    // An Item with a description added to it

    private String DescriptionTranslatable;
    private boolean ShiftToView = true;
    ChatFormatting[] Colors;

    public MultiLineDescriptionItem(Properties pProperties, String DescriptionTranslatable, boolean ShiftToView, ChatFormatting... Colors) {
        super(pProperties);
        this.DescriptionTranslatable = DescriptionTranslatable;
        this.ShiftToView = ShiftToView;
        this.Colors = Colors;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (!Screen.hasShiftDown() && ShiftToView) {
            pTooltipComponents.add(Component.translatable("tooltip.mmm.description_item.tooltip.noshift"));
        } else {
            if (Colors != null) {
                for (int i = 0; i < Colors.length; i++) {
                    pTooltipComponents.add(
                            Component.translatable("tooltip.mmm.description_item.tooltip." + DescriptionTranslatable + "_" + i)
                                    .withStyle(Colors[i])
                    );
                }
            }
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

}
