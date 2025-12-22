package net.MrGise.mmm;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.MrGise.mmm.datagen.advancement.ModTriggers;
import net.MrGise.mmm.effect.potion.BetterBrewingRecipe;
import net.MrGise.mmm.event.ModEvents;
import net.MrGise.mmm.registry.back.ModItemProperties;
import net.MrGise.mmm.registry.back.ModLootModifiers;
import net.MrGise.mmm.registry.create.ModCreateBlocks;
import net.MrGise.mmm.registry.front.*;
import net.MrGise.mmm.registry.front.item.ModItems;
import net.MrGise.mmm.registry.front.item.ModPotions;
import net.MrGise.mmm.registry.middle.ModEnchantments;
import net.MrGise.mmm.registry.middle.ModSounds;
import net.MrGise.mmm.registry.middle.ModVillagers;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
    // The main mod class
// The value here should match an entry in the META-INF/mods.toml file
@Mod(MMM.MOD_ID)
public class MMM {
    public static final String MOD_ID = "mmm";

    public static final NonNullSupplier<CreateRegistrate> REGISTRATE = () -> CreateRegistrate.create(MOD_ID);

    public static final CreateRegistrate registrate() {
        return REGISTRATE.get();
    }
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();


    public MMM(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        //\ Custom

        ModCreateBlocks.register(modEventBus);

        //- Basic
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModCreativeModeTabs.register(modEventBus);

        ModEnchantments.register(modEventBus);

        //- Minor
        ModEffects.register(modEventBus);
        ModPotions.register(modEventBus);

        ModPaintings.register(modEventBus);

        ModVillagers.register(modEventBus);

        //, Resources

        ModLootModifiers.register(modEventBus);

        ModSounds.register(modEventBus);

        //. Normal

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ComposterBlock.COMPOSTABLES.put(ModItems.CUCUMBER.get(), 0.3f);
            ComposterBlock.COMPOSTABLES.put(ModItems.CUCUMBER_SEEDS.get(), 0.2f);

            ComposterBlock.COMPOSTABLES.put(ModItems.STRAWBERRY.get(), 0.2f);
            ComposterBlock.COMPOSTABLES.put(ModItems.STRAWBERRY_SEEDS.get(), 0.1f);

            ComposterBlock.COMPOSTABLES.put(ModItems.APPLE_SLICE.get(), 0.08f);
            ComposterBlock.COMPOSTABLES.put(ModItems.HONEYED_APPLE_SLICE.get(), 0.1f);

            ComposterBlock.COMPOSTABLES.put(ModBlocks.HEAVENLY_GRASS.get(), 0.3f);

            ComposterBlock.COMPOSTABLES.put(ModBlocks.OXALIS.get(), 0.25f);


            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.OXALIS.getId(), ModBlocks.POTTED_OXALIS);


            ModEvents.LogMap.put(ModBlocks.SKYWOOD_LOG.get(), ModBlocks.STRIPPED_SKYWOOD_LOG.get());


            BrewingRecipeRegistry.addRecipe(new BetterBrewingRecipe(Potions.SLOWNESS, Items.SLIME_BALL, ModPotions.LIQUID_SLIME.get()));


            ModTriggers.register();
        });

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {

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
                ModItemProperties.addCustomItemProperties();
            });
        }
    }
}
