package net.MrGise.mmm.registry.front.item;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.registry.front.ModEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(ForgeRegistries.POTIONS, MMM.MOD_ID);

    public static final RegistryObject<Potion> LIQUID_SLIME = potion("liquid_slime", ModEffects.SLIMY_EFFECT, 200);


    private static RegistryObject<Potion> potion(String name, RegistryObject<MobEffect> effect, int duration) {
        return potion(name, effect, duration, 0);
    }
    private static RegistryObject<Potion> potion(String name, RegistryObject<MobEffect> effect, int duration, int amplifier) {
        return POTIONS.register(name,
                () -> new Potion(new MobEffectInstance(effect.get(), duration, amplifier)));
    }

    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}
