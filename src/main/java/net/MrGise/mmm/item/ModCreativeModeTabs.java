package net.MrGise.mmm.item;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MMM.MOD_ID);

    public static final RegistryObject<CreativeModeTab> THE_SKYLAND = CREATIVE_MODE_TABS.register("the_skyland",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.SKIRON_ORE.get()))
                    .title(Component.translatable("creativetab.the_skyland")).displayItems((displayParameters, output) -> {
                        output.accept(ModBlocks.SKYSOLID.get());
                        output.accept(ModBlocks.SKYSOLID_WALL.get());
                        output.accept(ModBlocks.BROKEN_SKYSOLID.get());
                        output.accept(ModBlocks.DECORATIVE_SKYSOLID.get());

                        output.accept(ModBlocks.ACTINOLITE_ORE.get());
                        output.accept(ModItems.ACTINOLITE.get());
                        output.accept(ModItems.ACTIONLITE_SWORD.get());

                        output.accept(ModBlocks.SKIRON_ORE.get());
                        output.accept(ModItems.RAW_SKIRON.get());
                        output.accept(ModItems.SKIRON.get());
                        output.accept(ModItems.SKIRON_NUGGET.get());
                        output.accept(ModBlocks.RAW_SKIRON_BLOCK.get());
                        output.accept(ModBlocks.SKIRON_BLOCK.get());
                        output.accept(ModItems.SKIRON_SWORD.get());
                        output.accept(ModItems.SKIRON_PICKAXE.get());
                        output.accept(ModItems.SKIRON_AXE.get());
                        output.accept(ModItems.SKIRON_SHOVEL.get());
                        output.accept(ModItems.SKIRON_HOE.get());
                        output.accept(ModItems.SKIRON_PAXEL.get());
                        output.accept(ModItems.SKIRON_HAMMER.get());

                        output.accept(ModBlocks.SKOAL_ORE.get());
                        output.accept(ModItems.SKOAL.get());
                        output.accept(ModBlocks.SKOAL_BLOCK.get());

                        output.accept(ModBlocks.SKYWOOD_PLANKS.get());
                        output.accept(ModBlocks.SKYWOOD_STAIRS.get());
                        output.accept(ModBlocks.SKYWOOD_SLAB.get());
                        output.accept(ModBlocks.SKYWOOD_FENCE.get());
                        output.accept(ModBlocks.SKYWOOD_FENCE_GATE.get());
                        output.accept(ModBlocks.SKYWOOD_DOOR.get());
                        output.accept(ModBlocks.SKYWOOD_TRAPDOOR.get());
                        output.accept(ModBlocks.SKYWOOD_PRESSURE_PLATE.get());
                        output.accept(ModBlocks.SKYWOOD_BUTTON.get());
                    }).build());

    public static final RegistryObject<CreativeModeTab> RUIN_ITEMS = CREATIVE_MODE_TABS.register("ruin_items",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.MOSSY_GOLD_KEY.get()))
                    .title(Component.translatable("creativetab.ruin_items")).displayItems((displayParameters, output) -> {
                        output.accept(ModItems.GOLD_KEY.get());
                        output.accept(ModItems.MOSSY_GOLD_KEY.get());
                    }).build());

    public static final RegistryObject<CreativeModeTab> MISCELLANEOUS_MMM = CREATIVE_MODE_TABS.register("miscellaneous_mmm",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.ORE_DETECTOR.get()))
                    .title(Component.translatable("creativetab.miscellaneous_mmm")).displayItems((displayParameters, output) -> {
                        output.accept(ModBlocks.BIRTHDAY_CAKE.get());

                        output.accept(ModItems.ORE_DETECTOR.get());

                        output.accept(ModBlocks.SOUND_BLOCK.get());
                    }).build());

    public static final RegistryObject<CreativeModeTab> MMM_CUISINE = CREATIVE_MODE_TABS.register("mmm_cuisine",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.BREADSTICK.get()))
                    .title(Component.translatable("creativetab.mmm_cuisine")).displayItems((displayParameters, output) -> {
                        output.accept(ModItems.BREADSTICK.get());
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

}
