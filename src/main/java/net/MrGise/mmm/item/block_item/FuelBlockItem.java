package net.MrGise.mmm.item.block_item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class FuelBlockItem extends BlockItem {

    private int BurnTime;

    public FuelBlockItem(Block pBlock, Properties pProperties, int BurnTime) {
        super(pBlock, pProperties);
        this.BurnTime = BurnTime;

    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return this.BurnTime;
    }

}
