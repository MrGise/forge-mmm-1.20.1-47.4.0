package net.MrGise.mmm.registry.middle;

import com.google.common.collect.ImmutableSet;
import net.MrGise.mmm.MMM;
import net.MrGise.mmm.registry.front.ModBlocks;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, MMM.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, MMM.MOD_ID);


    public static final RegistryObject<PoiType> BOWYERY_POI = registerPoiType("bowyery_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.BOWYERY_TABLE.get().getStateDefinition().getPossibleStates()),
                    1, 1));
    public static final RegistryObject<VillagerProfession> BOWYER = registerVillagerProfession("bowyer",
            () -> new VillagerProfession("bowyer",
                    x -> x.get() == BOWYERY_POI.get(), x -> x.get() == BOWYERY_POI.get(),
                    ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_FLETCHER));


    private static RegistryObject<PoiType> registerPoiType(String name, Supplier<? extends PoiType> supplier) {
        return POI_TYPES.register(name, supplier);
    }
    private static RegistryObject<VillagerProfession> registerVillagerProfession(String name, Supplier<? extends VillagerProfession> supplier) {
        return VILLAGER_PROFESSIONS.register(name, supplier);
    }

    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}
