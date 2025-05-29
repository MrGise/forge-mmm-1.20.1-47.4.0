package net.MrGise.mmm.item;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.item.custom.DescriptionFuelItem;
import net.MrGise.mmm.item.custom.FuelItem;
import net.MrGise.mmm.item.custom.OreDetectorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, MMM.MOD_ID);

    public static final RegistryObject<Item> GOLD_KEY = ITEMS.register("gold_key",
            () -> new DescriptionItem(new Item.Properties().stacksTo(1), "gold_key", false));

    public static final RegistryObject<Item> MOSSY_GOLD_KEY = ITEMS.register("mossy_gold_key",
            () -> new DescriptionItem(new Item.Properties().stacksTo(1), "mossy_gold_key", false));

    public static final RegistryObject<Item> ORE_DETECTOR = ITEMS.register("ore_detector",
            () -> new OreDetectorItem(new Item.Properties().stacksTo(1).durability(512)));

    public static final RegistryObject<Item> BREADSTICK = ITEMS.register("breadstick",
            () -> new Item(new Item.Properties().food(ModFoodProperties.BREADSTICK)));

    public static final RegistryObject<Item> SKOAL = ITEMS.register("skoal",
            () -> new DescriptionFuelItem(new Item.Properties(), 1800, "skoal", true));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
