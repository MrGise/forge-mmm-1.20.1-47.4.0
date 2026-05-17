package net.MrGise.mmm.registry.variables;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.registry.content.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

import static net.MrGise.floating.helper.Methods.*;

// Tool tiers
public class ModToolTiers {

    public static final Tier SKIRON = TierSortingRegistry.registerTier(
            new ForgeTier(2, 500, 7f, 3f, 25,
                    ModTags.Blocks.NEEDS_SKIRON_TOOL, () -> Ingredient.of(ModItems.SKIRON.get())),
            mmm("skiron"), List.of(Tiers.IRON, Tiers.STONE, Tiers.WOOD), List.of(Tiers.DIAMOND, Tiers.NETHERITE));

    public static final Tier ACTINOLITE = TierSortingRegistry.registerTier(
            new ForgeTier(3, 550, 6f, 3.5f, 35,
                    ModTags.Blocks.NEEDS_ACTINOLITE_TOOL, () -> Ingredient.of(ModItems.ACTINOLITE.get())),
            mmm("actinolite"), List.of(ModToolTiers.SKIRON, Tiers.IRON, Tiers.STONE, Tiers.WOOD), List.of(Tiers.DIAMOND, Tiers.NETHERITE));

}