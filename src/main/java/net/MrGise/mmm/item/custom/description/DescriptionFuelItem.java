package net.MrGise.mmm.item.custom.description;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

public class DescriptionFuelItem extends DescriptionItem {

    private int BurnTime = 0;

    public DescriptionFuelItem(Properties pProperties, int BurnTime, String DescriptionTranslatable, boolean ShiftToView) {
        super(pProperties, DescriptionTranslatable, ShiftToView);
        this.BurnTime = BurnTime;
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return this.BurnTime;
    }
}
