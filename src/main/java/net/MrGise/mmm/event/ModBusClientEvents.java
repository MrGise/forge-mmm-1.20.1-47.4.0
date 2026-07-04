package net.MrGise.mmm.event;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.block.entity.renderer.BowlBlockEntityRenderer;
import net.MrGise.mmm.config.MMMConfigScreen;
import net.MrGise.mmm.particle.ConfettiParticle;
import net.MrGise.mmm.particle.ManaParticle;
import net.MrGise.mmm.particle.SmallConfettiParticle;
import net.MrGise.mmm.registry.content.ModBlockEntities;
import net.MrGise.mmm.registry.decorative.ModParticles;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = MMM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModBusClientEvents {
    @SubscribeEvent
    public static void onFMLClientSetup(FMLClientSetupEvent event) {
        ModLoadingContext.get().registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((mc, prevScreen) -> new MMMConfigScreen(prevScreen))
        );
    }

    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.PURPLE_MANA.get(), ManaParticle.BasicProvider::new);
        event.registerSpriteSet(ModParticles.RED_MANA.get(), ManaParticle.BasicProvider::new);
        event.registerSpriteSet(ModParticles.SMALL_PURPLE_MANA.get(), ManaParticle.GravProvider::new);

        event.registerSpriteSet(ModParticles.CONFETTI.get(), ConfettiParticle.GravProvider::new);
        event.registerSpriteSet(ModParticles.SMALL_CONFETTI.get(), SmallConfettiParticle.GravProvider::new);
    }

    @SubscribeEvent
    public static void registerBERenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.BOWL_BE.get(),
                BowlBlockEntityRenderer::new);
    }
}
