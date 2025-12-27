package net.MrGise.mmm.item.block_item.description;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class DescriptionFuelBlockItem extends DescriptionBlockItem {
    private int BurnTime;

    public DescriptionFuelBlockItem(Properties pProperties, Block pBlock, int BurnTime, String DescriptionTranslatable, boolean ShiftToView) {
        super(pProperties, pBlock, DescriptionTranslatable, ShiftToView);
        this.BurnTime = BurnTime;
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return this.BurnTime;
    }

}
