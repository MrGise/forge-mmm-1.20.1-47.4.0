package net.MrGise.mmm.registry.front;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.effect.SlimyEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

// Effects
public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MMM.MOD_ID);

    public static final RegistryObject<MobEffect> SLIMY_EFFECT = MOB_EFFECTS.register("slimy",
    () -> new SlimyEffect(MobEffectCategory.NEUTRAL, 0x36ebab).addAttributeModifier(Attributes.MOVEMENT_SPEED,
            "7107DE5E-7CE8-4030-940E-514C1F160890", -0.25f, AttributeModifier.Operation.MULTIPLY_TOTAL));


    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
