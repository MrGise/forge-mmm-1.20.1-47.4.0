package net.MrGise.mmm.registry;

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
            new ForgeTier(2, 500, 7f, 3f, 25,
                    ModTags.Blocks.NEEDS_SKIRON_TOOL, () -> Ingredient.of(ModItems.SKIRON.get())),
            new ResourceLocation(MMM.MOD_ID, "skiron"), List.of(Tiers.IRON, Tiers.STONE, Tiers.WOOD), List.of(Tiers.DIAMOND, Tiers.NETHERITE));

    public static final Tier ACTINOLITE = TierSortingRegistry.registerTier(
            new ForgeTier(3, 550, 6f, 3.5f, 35,
                    ModTags.Blocks.NEEDS_ACTINOLITE_TOOL, () -> Ingredient.of(ModItems.ACTINOLITE.get())),
            new ResourceLocation(MMM.MOD_ID, "actinolite"), List.of(ModToolTiers.SKIRON, Tiers.IRON, Tiers.STONE, Tiers.WOOD), List.of(Tiers.DIAMOND, Tiers.NETHERITE));

}