package net.MrGise.mmm.item.custom;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class DescriptionFuelBlockItem extends DescriptionBlockItem {

    private int BurnTime;
    private String DescriptionTranslatable;
    private boolean ShiftToView = true;

    public DescriptionFuelBlockItem(Properties pProperties, Block pBlock, int BurnTime, String DescriptionTranslatable, boolean ShiftToView) {
        super(pProperties, pBlock, DescriptionTranslatable, ShiftToView);
        this.BurnTime = BurnTime;
        this.DescriptionTranslatable = DescriptionTranslatable;
        this.ShiftToView = ShiftToView;
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return this.BurnTime;
    }

}
