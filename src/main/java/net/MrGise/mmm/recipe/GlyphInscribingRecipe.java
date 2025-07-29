package net.MrGise.mmm.recipe;

import net.MrGise.mmm.item.custom.TabletItem;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class GlyphInscribingRecipe extends CustomRecipe {

    public GlyphInscribingRecipe(ResourceLocation pId, CraftingBookCategory pCategory) {
        super(pId, pCategory);
    }

    @Override
    public boolean matches(CraftingContainer craftingContainer, Level level) {
        TabletItem tablet = null;
        int totalGlyphs = 0;

        for (int i = 0; i < craftingContainer.getContainerSize(); i++) {
            ItemStack stack = craftingContainer.getItem(i);

            if (!stack.isEmpty()) {
                if (!(stack.getItem() instanceof TabletItem)) {
                    return false;
                }

                if (tablet == null) {
                    tablet = (TabletItem) stack.getItem();
                } else if (stack.getItem() != tablet) {
                    return false;
                }

                CompoundTag tag = stack.getTag();
                if (tag != null && tag.contains("glyphs", Tag.TAG_LIST)) {
                    ListTag glyphs = tag.getList("glyphs", Tag.TAG_STRING);
                    totalGlyphs = glyphs.size();
                }

            }

        }

        return tablet != null && totalGlyphs == tablet.getMaxGlyphs();
    }

    @Override
    public ItemStack assemble(CraftingContainer craftingContainer, RegistryAccess registryAccess) {
        ItemStack result = ItemStack.EMPTY;
        TabletItem tablet = null;
        ListTag combinedGlyphs = new ListTag();

        for (int i = 0; i < craftingContainer.getContainerSize(); i++) {
            ItemStack stack = craftingContainer.getItem(i);

            if (!stack.isEmpty() && stack.getItem() instanceof TabletItem) {

                if (tablet == null) {
                    tablet = (TabletItem) stack.getItem();
                    result = new ItemStack(tablet);
                }

                CompoundTag tag = stack.getTag();

                if (tag != null && tag.contains("glyphs", Tag.TAG_LIST)) {
                    ListTag glyphs = tag.getList("glyphs", Tag.TAG_STRING);
                    for (int j = 0; j < glyphs.size(); j++) {
                        if (combinedGlyphs.size() < tablet.getMaxGlyphs()) {
                            combinedGlyphs.add(glyphs.get(j));
                        }

                    }

                }

            }

        }

        if (!combinedGlyphs.isEmpty()) {
            CompoundTag newTag = new CompoundTag();
            newTag.put("glyphs", combinedGlyphs);
            result.setTag(newTag);
        }

        return result;
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return i * i1 >= 2;
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.GLYPH_INSCRIBING.get();
    }

}
