package net.MrGise.mmm.item;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DescriptionItem extends Item {

    private String DescriptionTranslatable;
    private boolean ShiftToView = true;

    public DescriptionItem(Properties pProperties, String DescriptionTranslatable, boolean ShiftToView) {
        super(pProperties);
        this.DescriptionTranslatable = DescriptionTranslatable;
        this.ShiftToView = ShiftToView;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        if((!Screen.hasShiftDown() && ShiftToView) == true) {
            pTooltipComponents.add(Component.translatable("tooltip.mmm.description_item.tooltip.noshift"));

        } else {
            pTooltipComponents.add(Component.translatable("tooltip.mmm.description_item.tooltip." + DescriptionTranslatable));
        }


        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
