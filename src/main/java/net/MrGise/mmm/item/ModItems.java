package net.MrGise.mmm.item;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.item.custom.HammerItem;
import net.MrGise.mmm.item.custom.PaxelItem;
import net.MrGise.mmm.item.custom.description.DescriptionFuelItem;
import net.MrGise.mmm.item.custom.OreDetectorItem;
import net.MrGise.mmm.item.custom.description.DescriptionItem;
import net.MrGise.mmm.item.custom.description.DescriptionPoisonSwordItem;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, MMM.MOD_ID);

    // Items
    public static final RegistryObject<Item> GOLD_KEY = ITEMS.register("gold_key",
            () -> new DescriptionItem(new Item.Properties().stacksTo(1), "gold_key", false));

    public static final RegistryObject<Item> MOSSY_GOLD_KEY = ITEMS.register("mossy_gold_key",
            () -> new DescriptionItem(new Item.Properties().stacksTo(1), "mossy_gold_key", false));


    public static final RegistryObject<Item> ORE_DETECTOR = ITEMS.register("ore_detector",
            () -> new OreDetectorItem(new Item.Properties().stacksTo(1).durability(512)));


    public static final RegistryObject<Item> BREADSTICK = ITEMS.register("breadstick",
            () -> new Item(new Item.Properties().food(ModFoodProperties.BREADSTICK)));


    // Actinolite
    public static final RegistryObject<Item> ACTINOLITE = ITEMS.register("actinolite",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ACTIONLITE_SWORD = ITEMS.register("actinolite_sword",
            () -> new DescriptionPoisonSwordItem(ModToolTiers.ACTINOLITE, 5, 3, new Item.Properties().durability(ModToolTiers.ACTINOLITE.getUses()), "actinolite_sword", false));


    public static final RegistryObject<Item> SKOAL = ITEMS.register("skoal",
            () -> new DescriptionFuelItem(new Item.Properties(), 1800, "skoal", true));


    // skiron
    public static final RegistryObject<Item> SKIRON = ITEMS.register("skiron",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAW_SKIRON = ITEMS.register("raw_skiron",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SKIRON_NUGGET = ITEMS.register("skiron_nugget",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SKIRON_SWORD = ITEMS.register("skiron_sword",
            () -> new SwordItem(ModToolTiers.SKIRON, 4, -2, new Item.Properties().durability(ModToolTiers.SKIRON.getUses())));

    public static final RegistryObject<Item> SKIRON_PICKAXE = ITEMS.register("skiron_pickaxe",
            () -> new PickaxeItem(ModToolTiers.SKIRON, 1, -2, new Item.Properties().durability(ModToolTiers.SKIRON.getUses() + 100)));

    public static final RegistryObject<Item> SKIRON_AXE = ITEMS.register("skiron_axe",
            () -> new AxeItem(ModToolTiers.SKIRON, 6.5f, -2.5f, new Item.Properties().durability(ModToolTiers.SKIRON.getUses() + 50)));

    public static final RegistryObject<Item> SKIRON_SHOVEL = ITEMS.register("skiron_shovel",
            () -> new ShovelItem(ModToolTiers.SKIRON, 1.75F, -2.75F, new Item.Properties().durability(ModToolTiers.SKIRON.getUses())));

    public static final RegistryObject<Item> SKIRON_HOE = ITEMS.register("skiron_hoe",
            () -> new HoeItem(ModToolTiers.SKIRON, -1, -0.5f, new Item.Properties().durability(ModToolTiers.SKIRON.getUses() + 150)));

    public static final RegistryObject<Item> SKIRON_PAXEL = ITEMS.register("skiron_paxel",
            () -> new PaxelItem(ModToolTiers.SKIRON, 5.5f, -1f, new Item.Properties().durability(ModToolTiers.SKIRON.getUses() + 150)));

    public static final RegistryObject<Item> SKIRON_HAMMER = ITEMS.register("skiron_hammer",
            () -> new HammerItem(ModToolTiers.SKIRON, 1, 6, -3, new Item.Properties().durability(ModToolTiers.SKIRON.getUses() - 50)));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
