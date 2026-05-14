package net.MrGise.mmm;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.MrGise.mmm.datagen.advancement.ModTriggers;
import net.MrGise.mmm.effect.potion.BetterBrewingRecipe;
import net.MrGise.mmm.registry.variables.ModRecipes;
import net.MrGise.mmm.registry.decorative.ModParticles;
import net.MrGise.mmm.registry.variables.ModFluidTypes;
import net.MrGise.mmm.registry.variables.ModItemProperties;
import net.MrGise.mmm.registry.variables.ModLootModifiers;
import net.MrGise.mmm.registry.create.ModCreateBlocks;
import net.MrGise.mmm.registry.content.*;
import net.MrGise.mmm.registry.content.ModItems;
import net.MrGise.mmm.registry.variables.ModMenuTypes;
import net.MrGise.mmm.registry.variants.ModPaintings;
import net.MrGise.mmm.registry.variants.ModPotions;
import net.MrGise.mmm.registry.variants.ModEnchantments;
import net.MrGise.mmm.registry.decorative.ModSounds;
import net.MrGise.mmm.registry.variants.ModVillagers;
import net.MrGise.mmm.network.ModNetwork;
import net.MrGise.mmm.screen.bowyery_table.BowyeryTableScreen;
import net.MrGise.mmm.screen.thingamajig.ThingamajigScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.phys.Vec3;
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

import java.util.HashMap;
import java.util.Map;

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

        //. Normal

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        //\ Custom

        ModCreateBlocks.register(modEventBus);

        //: Content
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModFluidTypes.register(modEventBus);
        ModFluids.register(modEventBus);

        ModCreativeModeTabs.register(modEventBus);

        ModEnchantments.register(modEventBus);

        //- Minor
        ModEffects.register(modEventBus);
        ModPotions.register(modEventBus);

        ModPaintings.register(modEventBus);

        ModVillagers.register(modEventBus);

        //, Resources

        ModRecipes.register(modEventBus);

        ModParticles.register(modEventBus);

        ModNetwork.register();

        ModLootModifiers.register(modEventBus);

        ModSounds.register(modEventBus);

        //~ Block entities & Menus

        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
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

            Map<Block, Block> newMap = new HashMap<>(AxeItem.STRIPPABLES);

            newMap.put(ModBlocks.SKYWOOD_LOG.get(), ModBlocks.STRIPPED_SKYWOOD_LOG.get());
            newMap.put(ModBlocks.SKYWOOD.get(), ModBlocks.STRIPPED_SKYWOOD.get());

            AxeItem.STRIPPABLES = newMap;


            BrewingRecipeRegistry.addRecipe(new BetterBrewingRecipe(Potions.SLOWNESS, Items.SLIME_BALL, ModPotions.LIQUID_SLIME.get()));


            ModTriggers.register();


            DispenserBlock.registerBehavior(ModItems.CONFETTI_CANNON.get(), new DefaultDispenseItemBehavior(){
                @Override
                protected ItemStack execute(BlockSource source, ItemStack stack) {
                    double velScatDiff = 0.8;

                    double velocity = 1.0 - velScatDiff;
                    double scatter = 1.0 + velScatDiff;

                    ServerLevel level = source.getLevel();

                    Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
                    Position position = DispenserBlock.getDispensePosition(source);

                    Vec3 forward = new Vec3(direction.getStepX(), direction.getStepY(), direction.getStepZ()).normalize();
                    Vec3 up = direction.getAxis() == Direction.Axis.Y
                            ? new Vec3(1, 0, 0) : new Vec3(0, 1, 0);
                    Vec3 right = forward.cross(up).normalize();

                    up = right.cross(forward).normalize();

                    RandomSource random = level.getRandom();

                    for (int i = 0; i < 60; i++) {
                        double sideOffset = (random.nextDouble() - 0.5) * scatter;
                        double upOffset = (random.nextDouble() - 0.5) * scatter;

                        Vec3 velocityVec = forward.add(right.scale(sideOffset)).add(up.scale(upOffset)).scale(velocity).normalize();

                        level.sendParticles(ModParticles.CONFETTI.get(),
                                position.x() + forward.x(), position.y() + forward.y(), position.z() + forward.z(),
                                0, velocityVec.x(), velocityVec.y(), velocityVec.z(), 1);
                    }
                    level.playSound(null, source.getPos(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0f, 0.8f);

                    return stack;
                }
            });
        });

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(ModBlocks.THINGAMAJIG);
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

                MenuScreens.register(ModMenuTypes.THINGAMAJIG_MENU.get(), ThingamajigScreen::new);
                MenuScreens.register(ModMenuTypes.BOWYERY_TABLE_MENU.get(), BowyeryTableScreen::new);
            });
        }
    }
}
