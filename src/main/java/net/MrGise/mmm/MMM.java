package net.MrGise.mmm;

import com.mojang.logging.LogUtils;
import net.MrGise.mmm.event.ModEvents;
import net.MrGise.mmm.registry.*;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.HashMap;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MMM.MOD_ID)
public class MMM
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "mmm";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public MMM(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        //\ Everything else

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModCreativeModeTabs.register(modEventBus);

        ModEnchantments.register(modEventBus);

        //, Resources

        ModSounds.register(modEventBus);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ComposterBlock.COMPOSTABLES.put(ModItems.CUCUMBER.get(), 0.3f);
            ComposterBlock.COMPOSTABLES.put(ModItems.CUCUMBER_SEEDS.get(), 0.2f);

            ComposterBlock.COMPOSTABLES.put(ModItems.STRAWBERRY.get(), 0.2f);
            ComposterBlock.COMPOSTABLES.put(ModItems.STRAWBERRY_SEEDS.get(), 0.1f);

            ComposterBlock.COMPOSTABLES.put(ModBlocks.OXALIS.get(), 0.25f);


            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.OXALIS.getId(), ModBlocks.POTTED_OXALIS);


            ModEvents.LogMap.put(ModBlocks.SKYWOOD_LOG.get(), ModBlocks.STRIPPED_SKYWOOD_LOG.get());
        });

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(ModBlocks.BIRTHDAY_CAKE);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                ModIItemProperties.addCustomItemProperties();
            });
        }
    }
}
