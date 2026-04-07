package net.MrGise.mmm.compat.jei;

import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

public class CustomGrassSubtypeInterpreter implements IIngredientSubtypeInterpreter<ItemStack> {
    @Override
    public String apply(ItemStack stack, UidContext context) {
        CompoundTag tag = stack.getTag();
        if (tag == null) return NONE;

        if (tag.contains("long", Tag.TAG_BYTE) && tag.getBoolean("long")) {
            return "long";
        }

        return NONE;
    }
}
