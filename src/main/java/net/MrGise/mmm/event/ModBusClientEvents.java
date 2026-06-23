package net.MrGise.mmm.event;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.block.entity.renderer.ThingamajigBlockEntityRenderer;
import net.MrGise.mmm.particle.ConfettiParticle;
import net.MrGise.mmm.particle.ManaParticle;
import net.MrGise.mmm.registry.content.ModBlockEntities;
import net.MrGise.mmm.registry.decorative.ModParticles;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MMM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModBusClientEvents {

    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.PURPLE_MANA.get(), ManaParticle.BasicProvider::new);
        event.registerSpriteSet(ModParticles.RED_MANA.get(), ManaParticle.BasicProvider::new);
        event.registerSpriteSet(ModParticles.SMALL_PURPLE_MANA.get(), ManaParticle.GravProvider::new);

        event.registerSpriteSet(ModParticles.CONFETTI.get(), ConfettiParticle.GravProvider::new);
    }

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.THINGAMAJIG_BE.get(),
                ThingamajigBlockEntityRenderer::new);
    }
}
