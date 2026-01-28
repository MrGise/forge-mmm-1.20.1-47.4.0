package net.MrGise.mmm.registry.content;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.registry.variables.ModFluidTypes;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, MMM.MOD_ID);


    public static final RegistryObject<FlowingFluid> SOURCE_COW_MILK = FLUIDS.register("cow_milk",
            () -> new ForgeFlowingFluid.Source(ModFluids.COW_MILK_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_COW_MILK = FLUIDS.register("flowing_cow_milk",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.COW_MILK_PROPERTIES));

    public static final ForgeFlowingFluid.Properties COW_MILK_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.COW_MILK, SOURCE_COW_MILK, FLOWING_COW_MILK)
           .slopeFindDistance(2).levelDecreasePerBlock(1).block(ModBlocks.COW_MILK_BLOCK);


    public static final RegistryObject<FlowingFluid> SOURCE_GOAT_MILK = FLUIDS.register("goat_milk",
            () -> new ForgeFlowingFluid.Source(ModFluids.GOAT_MILK_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_GOAT_MILK = FLUIDS.register("flowing_goat_milk",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.GOAT_MILK_PROPERTIES));

    public static final ForgeFlowingFluid.Properties GOAT_MILK_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.GOAT_MILK, SOURCE_GOAT_MILK, FLOWING_GOAT_MILK)
            .slopeFindDistance(2).levelDecreasePerBlock(1).block(ModBlocks.GOAT_MILK_BLOCK).bucket(ModItems.GOAT_MILK_BUCKET);



    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
