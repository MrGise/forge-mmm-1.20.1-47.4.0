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
//Fluid types
public class ModFluidTypes {
    static ResourceLocation WATER_OVERLAY = mcr("block/water_overlay");
    static ResourceLocation MILK_STILL = crt("fluid/milk_still");
    static ResourceLocation MILK_FLOWING = crt("fluid/milk_flow");


    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, MMM.MOD_ID);


    public static final RegistryObject<FluidType> COW_MILK = registerFluidType("cow_milk",
            new BaseFluidType(MILK_STILL, MILK_FLOWING, WATER_OVERLAY,
                    0xA1FFFFFF, new Vector3f(1f, 1f, 1f),
                    FluidType.Properties.create().viscosity(8).density(15).supportsBoating(true)));

    public static final RegistryObject<FluidType> GOAT_MILK = registerFluidType("goat_milk",
            new BaseFluidType(MILK_STILL, MILK_FLOWING, WATER_OVERLAY,
                    0xA1FFFFFF, new Vector3f(1f, 1f, 1f),
                    FluidType.Properties.create().viscosity(8).density(15).supportsBoating(true)));

    public static final RegistryObject<FluidType> RAINSTONE = registerFluidType("rainstone",
            new BaseFluidType(MILK_STILL, MILK_FLOWING, WATER_OVERLAY,
                    0xA15b5b5b, new Vector3f(0.5f, 0.5f, 0.5f),
                    FluidType.Properties.create().viscosity(14).density(6)));


    public static RegistryObject<FluidType> registerFluidType(String name, FluidType type) {
        return FLUID_TYPES.register(name, s(type));
    }

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}
