package net.MrGise.mmm.registry;

import net.MrGise.mmm.MMM;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

    // Defines sounds to be used in code
public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MMM.MOD_ID);

    public static final RegistryObject<SoundEvent> ORE_DETECTOR_SUCCESS = registerSoundEvent("ore_detector_success");

    public static final RegistryObject<SoundEvent> NULL_BLOCK_BREAK = registerSoundEvent("null_block_break");
    public static final RegistryObject<SoundEvent> NULL_BLOCK_HIT = registerSoundEvent("null_block_hit");
    public static final RegistryObject<SoundEvent> NULL_BLOCK_STEP = registerSoundEvent("null_block_step");
    public static final RegistryObject<SoundEvent> NULL_BLOCK_PLACE = registerSoundEvent("null_block_place");

    public static final RegistryObject<SoundEvent> DROPPY_LIKES_RICOCHET = registerSoundEvent("droppy_likes_ricochet");
    public static final RegistryObject<SoundEvent> DROPPY_LIKES_RICOCHET_FULL = registerSoundEvent("droppy_likes_ricochet_full");
    public static final RegistryObject<SoundEvent> TUNE = registerSoundEvent("tune");


    public static final ForgeSoundType NULL_BLOCK_SOUNDS = new ForgeSoundType(1.0f, 1.0f,
            ModSounds.NULL_BLOCK_BREAK, ModSounds.NULL_BLOCK_STEP, ModSounds.NULL_BLOCK_PLACE,
            ModSounds.NULL_BLOCK_HIT, () -> SoundEvents.STONE_FALL);


    //| Registration methods

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = new ResourceLocation(MMM.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }


    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
