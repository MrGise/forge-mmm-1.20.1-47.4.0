package net.MrGise.mmm.event;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.registry.content.ModBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.util.MutableHashedLinkedMap;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = MMM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBusEvents {
    @SubscribeEvent
    public static void atTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> entries = event.getEntries();
            entries.putAfter(itemStack(Blocks.OAK_DOOR), itemStack(ModBlocks.OAK_TRIPLE_DOOR), all());
            entries.putAfter(itemStack(Blocks.ACACIA_DOOR), itemStack(ModBlocks.ACACIA_TRIPLE_DOOR), all());
            entries.putAfter(itemStack(Blocks.DARK_OAK_DOOR), itemStack(ModBlocks.DARK_OAK_TRIPLE_DOOR), all());
            entries.putAfter(itemStack(Blocks.SPRUCE_DOOR), itemStack(ModBlocks.SPRUCE_TRIPLE_DOOR), all());
            entries.putAfter(itemStack(Blocks.CRIMSON_DOOR), itemStack(ModBlocks.CRIMSON_TRIPLE_DOOR), all());
            entries.putAfter(itemStack(Blocks.IRON_DOOR), itemStack(ModBlocks.IRON_TRIPLE_DOOR), all());
            entries.putAfter(itemStack(Blocks.JUNGLE_DOOR), itemStack(ModBlocks.JUNGLE_TRIPLE_DOOR), all());
            entries.putAfter(itemStack(Blocks.BIRCH_DOOR), itemStack(ModBlocks.BIRCH_TRIPLE_DOOR), all());
            entries.putAfter(itemStack(Blocks.MANGROVE_DOOR), itemStack(ModBlocks.MANGROVE_TRIPLE_DOOR), all());
            entries.putAfter(itemStack(Blocks.WARPED_DOOR), itemStack(ModBlocks.WARPED_TRIPLE_DOOR), all());
            entries.putAfter(itemStack(Blocks.BAMBOO_DOOR), itemStack(ModBlocks.BAMBOO_TRIPLE_DOOR), all());
            entries.putAfter(itemStack(Blocks.CHERRY_DOOR), itemStack(ModBlocks.CHERRY_TRIPLE_DOOR), all());


        }
    }

    private static ItemStack itemStack(RegistryObject<Block> toConvert) {
        return toConvert.get().asItem().getDefaultInstance();
    }
    private static ItemStack itemStack(Block toConvert) {
        return toConvert.asItem().getDefaultInstance();
    }
    private static ItemStack itemStack(Item toConvert) {
        return toConvert.getDefaultInstance();
    }
    private static CreativeModeTab.TabVisibility all() {
        return CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS;
    }
    private static CreativeModeTab.TabVisibility search() {
        return CreativeModeTab.TabVisibility.SEARCH_TAB_ONLY;
    }
    private static CreativeModeTab.TabVisibility parent() {
        return CreativeModeTab.TabVisibility.PARENT_TAB_ONLY;
    }
}
