package net.MrGise.mmm.registry.variables;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.fluid.BaseFluidType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Vector3f;

import static net.MrGise.mmm.util.Methods.*;

public class ModFluidTypes {
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, MMM.MOD_ID);


    public static final RegistryObject<FluidType> COW_MILK = registerFluidType("cow_milk",
            new BaseFluidType(new ResourceLocation(MMM.MOD_ID, "fluid/milk_still"),
                    new ResourceLocation(MMM.MOD_ID, "fluid/milk_flow"), mcr("block/water_overlay"), 0xA1FFFFFF,
                    new Vector3f(1f, 1f, 1f),
                    FluidType.Properties.create().viscosity(8).density(15).supportsBoating(true)));

    public static final RegistryObject<FluidType> GOAT_MILK = registerFluidType("goat_milk",
            new BaseFluidType(new ResourceLocation(MMM.MOD_ID, "fluid/milk_still"),
                    new ResourceLocation(MMM.MOD_ID, "fluid/milk_flow"), mcr("block/water_overlay"), 0xA1FFFFFF,
                    new Vector3f(1f, 1f, 1f),
                    FluidType.Properties.create().viscosity(8).density(15).supportsBoating(true)));


    public static RegistryObject<FluidType> registerFluidType(String name, FluidType type) {
        return FLUID_TYPES.register(name, () -> type);
    }

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}
