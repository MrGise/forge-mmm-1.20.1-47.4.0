package net.MrGise.mmm.registry.content;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.block.entity.BowlBlockEntity;
import net.MrGise.mmm.block.entity.ThingamajigBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

// Block entities
public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MMM.MOD_ID);


    public static final RegistryObject<BlockEntityType<ThingamajigBlockEntity>> THINGAMAJIG_BE =
            registerBlockEntity("thingamajig_block_entity",
                    () -> BlockEntityType.Builder.of(ThingamajigBlockEntity::new,
                            ModBlocks.THINGAMAJIG.get()).build(null));

    public static final RegistryObject<BlockEntityType<BowlBlockEntity>> BOWL_BE =
            registerBlockEntity("bowl_block_entity",
                    () -> BlockEntityType.Builder.of(BowlBlockEntity::new,
                            ModBlocks.PLACED_BOWL.get()).build(null));


    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> registerBlockEntity(
            String name, Supplier<BlockEntityType<T>> type) {
        return BLOCK_ENTITIES.register(name, type);
    }

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
