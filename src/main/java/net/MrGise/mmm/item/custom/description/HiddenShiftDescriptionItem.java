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

public class HiddenShiftDescriptionItem extends Item {

    // An Item with a description added to it, description can only be accessed with shift key and is otherwise hidden

    private String DescriptionTranslatable;

    public HiddenShiftDescriptionItem(Properties pProperties, String DescriptionTranslatable) {
        super(pProperties);
        this.DescriptionTranslatable = DescriptionTranslatable;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        if(Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.mmm.description_item.tooltip." + DescriptionTranslatable).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
