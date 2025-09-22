package net.MrGise.mmm.registry;

import net.MrGise.mmm.MMM;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static class Items {

        public static final TagKey<Item> SKIRON_BLOCKS = forgeTag("storage_blocks/skiron");

        public static final TagKey<Item> SKIRON_INGOTS = forgeTag("ingots/skiron");

        public static final TagKey<Item> ACTINOLITE = forgeTag("gems/actinolite");

        public static final TagKey<Item> SKIRON_NUGGETS = forgeTag("nuggets/skiron");

        public static final TagKey<Item> TABLETS = tag("tablets");

        public static final TagKey<Item> SUGGESTED = tag("suggested");

        public static final TagKey<Item> CUCUMBERS = forgeTag("crops/cucumber");

        public static final TagKey<Item> CUCUMBER_SEEDS = forgeTag("seeds/cucumber");

        public static final TagKey<Item> STRAWBERRIES = forgeTag("crops/strawberry");

        public static final TagKey<Item> STRAWBERRY_SEEDS = forgeTag("seeds/strawberry");

        public static final TagKey<Item> POMEGRANATES = forgeTag("crops/pomegranate");
        public static final TagKey<Item> POMEGRANATE_SLICES = tag("items/pomegranate_slice");
        public static final TagKey<Item> POMEGRANATE_SEEDS = forgeTag("seeds/pomegranate");


        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(MMM.MOD_ID, name));
        }

        private static TagKey<Item> forgeTag(String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }

    }

    public static class Blocks {

        public static final TagKey<Block> NEEDS_SKIRON_TOOL = tag("needs_skiron_tool");

        public static final TagKey<Block> NEEDS_ACTINOLITE_TOOL = tag("needs_actinolite_tool");

        public static final TagKey<Block> MINEABLE_WITH_PAXEL = tag("mineable/paxel");

        public static final TagKey<Block> HEAVENLY_GRASS_PLACEABLES = tag("heavenly_grass_placeables");

        public static final TagKey<Block> MINEABLE_WITH_HAMMER = tag("mineable/hammer");

        public static final TagKey<Block> SKYLAND_ORES = forgeTag("ores/skyland");

        public static final TagKey<Block> DETECTABLE_ORE = tag("detectable_ore");


        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(MMM.MOD_ID, name));
        }
        private static TagKey<Block> forgeTag(String name) {
            return BlockTags.create(new ResourceLocation("forge", name));
        }

    }

}
