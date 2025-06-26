package net.MrGise.mmm.item;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.util.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ModToolTiers {

    public static final Tier SKIRON = TierSortingRegistry.registerTier(
            new ForgeTier(2, 500, 7f, 2.5f, 25,
                    ModTags.Blocks.NEEDS_SKIRON_TOOL, () -> Ingredient.of(ModItems.SKIRON.get())),
            new ResourceLocation(MMM.MOD_ID, "skiron"), List.of(Tiers.IRON, Tiers.STONE, Tiers.WOOD), List.of(Tiers.DIAMOND, Tiers.NETHERITE));

}