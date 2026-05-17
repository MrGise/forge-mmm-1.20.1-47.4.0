package net.MrGise.mmm.registry.decorative;

import net.MrGise.mmm.MMM;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

//• Particles
public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MMM.MOD_ID);


    public static final RegistryObject<SimpleParticleType> PURPLE_MANA = createParticle("mana_purple");
    public static final RegistryObject<SimpleParticleType> RED_MANA = createParticle("mana_red");
    public static final RegistryObject<SimpleParticleType> SMALL_PURPLE_MANA = createParticle("mana_purple_small");

    public static final RegistryObject<SimpleParticleType> CONFETTI = createParticle("confetti");
    public static final RegistryObject<SimpleParticleType> SMALL_CONFETTI = createParticle("confetti_small");


    private static RegistryObject<SimpleParticleType> createParticle(String name) {
        return PARTICLE_TYPES.register(name, () -> new SimpleParticleType(true));
    }

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
