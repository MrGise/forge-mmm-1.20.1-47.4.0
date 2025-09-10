package net.MrGise.mmm.registry;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.item.*;
import net.MrGise.mmm.item.description.*;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.item.KnifeItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, MMM.MOD_ID);

    //. Items


    public static final RegistryObject<Item> TEST_ITEM = ITEMS.register("test_item",
            () -> new Item(new Item.Properties()));

    //-- Ruins

    public static final RegistryObject<Item> GOLD_KEY = ITEMS.register("gold_key",
            () -> new DescriptionItem(new Item.Properties().stacksTo(1), "gold_key", false));
    public static final RegistryObject<Item> MOSSY_GOLD_KEY = ITEMS.register("mossy_gold_key",
            () -> new DescriptionItem(new Item.Properties().stacksTo(1), "mossy_gold_key", false));

    public static final RegistryObject<Item> GLIDE_ARMOR_TRIM_SMITHING_TEMPLATE = ITEMS.register("glide_armor_trim_smithing_template",
            () -> new MultiLineDescriptionItem(new Item.Properties(), "smithing_template", false,
                    ChatFormatting.GRAY, ChatFormatting.GRAY, ChatFormatting.GRAY, ChatFormatting.BLUE, ChatFormatting.GRAY, ChatFormatting.BLUE));

    public static final RegistryObject<Item> SKYSOLID_TABLET = ITEMS.register("skysolid_tablet",
            () -> new TabletItem(new Item.Properties(), 4));

    public static final RegistryObject<Item> DROPPY_LIKES_RICOCHET_MUSIC_DISC = ITEMS.register("droppy_likes_ricochet_music_disc",
            () -> new RecordItem(7, ModSounds.DROPPY_LIKES_RICOCHET,
                    new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 1920));

    public static final RegistryObject<Item> DROPPY_LIKES_EVERYTHING_MUSIC_DISC = ITEMS.register("droppy_likes_everything_music_disc",
            () -> new RecordItem(7, ModSounds.DROPPY_LIKES_RICOCHET_FULL,
                    new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 4255));


    public static final RegistryObject<Item> MIMIC = ITEMS.register("mimic",
            () -> new MimicItem(new Item.Properties().stacksTo(1)));

    //-- Ore detector
    public static final RegistryObject<Item> ORE_DETECTOR = ITEMS.register("ore_detector",
            () -> new OreDetectorItem(new Item.Properties().stacksTo(1).durability(512)));

    public static final RegistryObject<Item> ORE_REDETECTOR = ITEMS.register("ore_redetector",
            () -> new OreRedetectorItem(new Item.Properties().stacksTo(1)));


    //-- Food

    public static final RegistryObject<Item> BREADSTICK = ITEMS.register("breadstick",
            () -> new Item(new Item.Properties().food(ModFoodProperties.BREADSTICK)));

    public static final RegistryObject<Item> STRAWBERRY = ITEMS.register("strawberry",
            () -> new Item(new Item.Properties().food(ModFoodProperties.STRAWBERRY)));
    public static final RegistryObject<Item> STRAWBERRY_SEEDS = ITEMS.register("strawberry_seeds",
            () -> new ItemNameBlockItem(ModBlocks.STRAWBERRY.get(), new Item.Properties()));

    public static final RegistryObject<Item> CUCUMBER_SEEDS = ITEMS.register("cucumber_seeds",
            () -> new ItemNameBlockItem(ModBlocks.CUCUMBER.get(), new Item.Properties()));
    public static final RegistryObject<Item> CUCUMBER = ITEMS.register("cucumber",
            () -> new Item(new Item.Properties().food(ModFoodProperties.CUCUMBER)));
    public static final RegistryObject<Item> CUT_CUCUMBER = ITEMS.register("cut_cucumber",
            () -> new Item(new Item.Properties().food(ModFoodProperties.CUCUMBER)));

    //-- Magic
    public static final RegistryObject<Item> SOLIDIFIED_MANA = ITEMS.register("solidified_mana",
            () -> new Item(new Item.Properties()));


    //-- Actinolite
    public static final RegistryObject<Item> ACTINOLITE = ITEMS.register("actinolite",
            () -> new HiddenShiftDescriptionItem(new Item.Properties(), "actinolite"));

    public static final RegistryObject<Item> ACTIONLITE_SWORD = ITEMS.register("actinolite_sword",
            () -> new DescriptionPoisonSwordItem(ModToolTiers.ACTINOLITE, 3, 3, new Item.Properties().durability(ModToolTiers.ACTINOLITE.getUses()), "actinolite_sword", false));

    public static final RegistryObject<Item> ACTIONLITE_PICKAXE = ITEMS.register("actinolite_pickaxe",
            () -> new PickaxeItem(ModToolTiers.ACTINOLITE, 2, -1, new Item.Properties().durability(ModToolTiers.ACTINOLITE.getUses() + 100)));



    //-- Skiron
    public static final RegistryObject<Item> SKIRON = ITEMS.register("skiron",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAW_SKIRON = ITEMS.register("raw_skiron",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SKIRON_NUGGET = ITEMS.register("skiron_nugget",
            () -> new Item(new Item.Properties()));

    /*. Tools */
    //- Skiron
    public static final RegistryObject<Item> SKIRON_SWORD = ITEMS.register("skiron_sword",
            () -> new SwordItem(ModToolTiers.SKIRON, 2, -3, new Item.Properties().durability(ModToolTiers.SKIRON.getUses())));

    public static final RegistryObject<Item> SKIRON_PICKAXE = ITEMS.register("skiron_pickaxe",
            () -> new PickaxeItem(ModToolTiers.SKIRON, 1, -2, new Item.Properties().durability(ModToolTiers.SKIRON.getUses() + 100)));

    public static final RegistryObject<Item> SKIRON_AXE = ITEMS.register("skiron_axe",
            () -> new AxeItem(ModToolTiers.SKIRON, 2, -1.5f, new Item.Properties().durability(ModToolTiers.SKIRON.getUses() + 50)));

    public static final RegistryObject<Item> SKIRON_SHOVEL = ITEMS.register("skiron_shovel",
            () -> new ShovelItem(ModToolTiers.SKIRON, 1.75F, -2.75F, new Item.Properties().durability(ModToolTiers.SKIRON.getUses())));

    public static final RegistryObject<Item> SKIRON_HOE = ITEMS.register("skiron_hoe",
            () -> new HoeItem(ModToolTiers.SKIRON, -1, -0.5f, new Item.Properties().durability(ModToolTiers.SKIRON.getUses() + 150)));

    public static final RegistryObject<Item> SKIRON_KNIFE = ITEMS.register("skiron_knife",
            () -> new KnifeItem(ModToolTiers.SKIRON, 0.5F, -1.0F, new Item.Properties().durability(ModToolTiers.SKIRON.getUses() + 50)));

    public static final RegistryObject<Item> SKIRON_PAXEL = ITEMS.register("skiron_paxel",
            () -> new PaxelItem(ModToolTiers.SKIRON, 5.5f, -1f, new Item.Properties().durability(ModToolTiers.SKIRON.getUses() + 150)));

    public static final RegistryObject<Item> SKIRON_HAMMER = ITEMS.register("skiron_hammer",
            () -> new HammerItem(ModToolTiers.SKIRON, 1, 5, -3.5f, new Item.Properties().durability(ModToolTiers.SKIRON.getUses() - 50)));

    //- Bows
    public static final RegistryObject<Item> REINFORCED_STONE_BOW = ITEMS.register("reinforced_stone_bow",
            () -> new BowItem(new Item.Properties().durability(452)));

    public static final RegistryObject<Item> REINFORCED_GOLD_BOW = ITEMS.register("reinforced_gold_bow",
            () -> new BowItem(new Item.Properties().durability(244)));

    public static final RegistryObject<Item> REINFORCED_IRON_BOW = ITEMS.register("reinforced_iron_bow",
            () -> new BowItem(new Item.Properties().durability(506)));

    /*. Armor */
    public static final RegistryObject<Item> SKIRON_HELMET = ITEMS.register("skiron_helmet",
            () -> new EffectArmorItem(ModArmorMaterials.SKIRON, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> SKIRON_CHESTPLATE = ITEMS.register("skiron_chestplate",
            () -> new EffectArmorItem(ModArmorMaterials.SKIRON, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> SKIRON_LEGGINGS = ITEMS.register("skiron_leggings",
            () -> new EffectArmorItem(ModArmorMaterials.SKIRON, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<Item> SKIRON_BOOTS = ITEMS.register("skiron_boots",
            () -> new EffectArmorItem(ModArmorMaterials.SKIRON, ArmorItem.Type.BOOTS, new Item.Properties()));

    //- Skiron actinolite
    public static final RegistryObject<Item> SKIRON_ACTINOLITE_HELMET = ITEMS.register("skiron_actinolite_helmet",
            () -> new EffectArmorItem(ModArmorMaterials.SKIRON_ACTINOLITE, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> SKIRON_ACTINOLITE_CHESTPLATE = ITEMS.register("skiron_actinolite_chestplate",
            () -> new EffectArmorItem(ModArmorMaterials.SKIRON_ACTINOLITE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> SKIRON_ACTINOLITE_LEGGINGS = ITEMS.register("skiron_actinolite_leggings",
            () -> new EffectArmorItem(ModArmorMaterials.SKIRON_ACTINOLITE, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<Item> SKIRON_ACTINOLITE_BOOTS = ITEMS.register("skiron_actinolite_boots",
            () -> new EffectArmorItem(ModArmorMaterials.SKIRON_ACTINOLITE, ArmorItem.Type.BOOTS, new Item.Properties()));


    public static final RegistryObject<Item> SKOAL = ITEMS.register("skoal",
            () -> new DescriptionFuelItem(new Item.Properties(), 1800, "skoal", true));

    //- Horse Armor
    public static final RegistryObject<Item> SKIRON_HORSE_ARMOR = ITEMS.register("skiron_horse_armor",
            () -> new HorseArmorItem(6, new ResourceLocation(MMM.MOD_ID, "textures/entity/horse/armor/horse_armor_skiron.png"), new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
