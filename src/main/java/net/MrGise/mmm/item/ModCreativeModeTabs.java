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

    public static final RegistryObject<CreativeModeTab> SKYLAND_BLOCKS = CREATIVE_MODE_TABS.register("skyland_blocks",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.SKIRON_ORE.get()))
                    .title(Component.translatable("creativetab.skyland_blocks")).displayItems((displayParameters, output) -> {
                        output.accept(ModBlocks.SKYSOLID.get());
                        output.accept(ModBlocks.BROKEN_SKYSOLID.get());
                        output.accept(ModBlocks.DECORATIVE_SKYSOLID.get());
                        output.accept(ModBlocks.SKIRON_ORE.get());
                    }).build());

    public static final RegistryObject<CreativeModeTab> RUIN_ITEMS = CREATIVE_MODE_TABS.register("ruin_items",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.MOSSY_GOLD_KEY.get()))
                    .title(Component.translatable("creativetab.ruin_items")).displayItems((displayParameters, output) -> {
                        output.accept(ModItems.GOLD_KEY.get());
                        output.accept(ModItems.MOSSY_GOLD_KEY.get());
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

}
