package net.MrGise.mmm.registry.front.item;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.item.*;
import net.MrGise.mmm.item.description.*;
import net.MrGise.mmm.registry.back.ModFoodProperties;
import net.MrGise.mmm.registry.front.ModBlocks;
import net.MrGise.mmm.registry.middle.ModSounds;
import net.MrGise.mmm.registry.middle.ModToolTiers;
import net.MrGise.mmm.registry.middle.ModArmorMaterials;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.item.KnifeItem;

import java.util.function.Supplier;

// Items
public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, MMM.MOD_ID);

    //. Items


    public static final RegistryObject<Item> TEST_ITEM = registerItem("test_item",
            () -> new Item(new Item.Properties()));

    //-- Ruins

    public static final RegistryObject<Item> GOLD_KEY = registerItem("gold_key",
            () -> new DescriptionItem(new Item.Properties().stacksTo(1), "gold_key", false));
    public static final RegistryObject<Item> MOSSY_GOLD_KEY = registerItem("mossy_gold_key",
            () -> new DescriptionItem(new Item.Properties().stacksTo(1), "mossy_gold_key", false));

    public static final RegistryObject<Item> GLIDE_ARMOR_TRIM_SMITHING_TEMPLATE = registerItem("glide_armor_trim_smithing_template",
            () -> new MultiLineDescriptionItem(new Item.Properties(), "smithing_template", false,
                    ChatFormatting.GRAY, ChatFormatting.GRAY, ChatFormatting.GRAY, ChatFormatting.BLUE, ChatFormatting.GRAY, ChatFormatting.BLUE));

    public static final RegistryObject<Item> SKYSOLID_TABLET = registerItem("skysolid_tablet",
            () -> new TabletItem(new Item.Properties(), 4));

    public static final RegistryObject<Item> DROPPY_LIKES_RICOCHET_MUSIC_DISC = registerItem("droppy_likes_ricochet_music_disc",
            () -> new RecordItem(7, ModSounds.DROPPY_LIKES_RICOCHET,
                    new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 1920));

    public static final RegistryObject<Item> DROPPY_LIKES_EVERYTHING_MUSIC_DISC = registerItem("droppy_likes_everything_music_disc",
            () -> new RecordItem(7, ModSounds.DROPPY_LIKES_RICOCHET_FULL,
                    new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 4255));

    public static final RegistryObject<Item> TUNE_MUSIC_DISC = registerItem("tune_music_disc",
            () -> new RecordItem(3, ModSounds.TUNE,
                    new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 800));


    public static final RegistryObject<Item> MIMIC = registerItem("mimic",
            () -> new MimicItem(new Item.Properties().stacksTo(1)));

    //-- Ore detector
    public static final RegistryObject<Item> ORE_DETECTOR = registerItem("ore_detector",
            () -> new OreDetectorItem(new Item.Properties().stacksTo(1).durability(512)));

    public static final RegistryObject<Item> ORE_REDETECTOR = registerItem("ore_redetector",
            () -> new OreRedetectorItem(new Item.Properties().stacksTo(1)));


    //-- Food

    public static final RegistryObject<Item> BREADSTICK = registerItem("breadstick",
            () -> new Item(new Item.Properties().food(ModFoodProperties.BREADSTICK)));

    public static final RegistryObject<Item> STRAWBERRY = registerItem("strawberry",
            () -> new Item(new Item.Properties().food(ModFoodProperties.STRAWBERRY)));
    public static final RegistryObject<Item> STRAWBERRY_SEEDS = registerItem("strawberry_seeds",
            () -> new ItemNameBlockItem(ModBlocks.STRAWBERRY.get(), new Item.Properties()));

    public static final RegistryObject<Item> CUCUMBER_SEEDS = registerItem("cucumber_seeds",
            () -> new ItemNameBlockItem(ModBlocks.CUCUMBER.get(), new Item.Properties()));
    public static final RegistryObject<Item> CUCUMBER = registerItem("cucumber",
            () -> new Item(new Item.Properties().food(ModFoodProperties.CUCUMBER)));
    public static final RegistryObject<Item> CUT_CUCUMBER = registerItem("cut_cucumber",
            () -> new Item(new Item.Properties().food(ModFoodProperties.CUCUMBER)));

    public static final RegistryObject<Item> POMEGRANATE = registerItem("pomegranate",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> POMEGRANATE_EMPTY_SLICE = registerItem("pomegranate_empty_slice",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> POMEGRANATE_SLICE = registerItem("pomegranate_slice",
            () -> new Item(new Item.Properties().craftRemainder(ModItems.POMEGRANATE_EMPTY_SLICE.get())));

    public static final RegistryObject<Item> POMEGRANATE_SEEDS = registerItem("pomegranate_seeds",
            () -> new Item(new Item.Properties().food(ModFoodProperties.POMEGRANATE_SEEDS)));

    public static final RegistryObject<Item> APPLE_SLICE = registerItem("apple_slice",
            () -> new Item(new Item.Properties().food(ModFoodProperties.APPLE_SLICE)));

    public static final RegistryObject<Item> HONEYED_APPLE_SLICE = registerItem("honeyed_apple_slice",
            () -> new Item(new Item.Properties().food(ModFoodProperties.HONEYED_APPLE_SLICE)));

    //-- Magic
    public static final RegistryObject<Item> SOLIDIFIED_MANA = registerItem("solidified_mana",
            () -> new Item(new Item.Properties()));


    //-- Actinolite
    public static final RegistryObject<Item> ACTINOLITE = registerItem("actinolite",
            () -> new HiddenShiftDescriptionItem(new Item.Properties(), "actinolite"));

    public static final RegistryObject<Item> ACTIONLITE_SWORD = registerItem("actinolite_sword",
            () -> new DescriptionPoisonSwordItem(ModToolTiers.ACTINOLITE, 3, 3, new Item.Properties().durability(ModToolTiers.ACTINOLITE.getUses()), "actinolite_sword", false));

    public static final RegistryObject<Item> ACTIONLITE_PICKAXE = registerItem("actinolite_pickaxe",
            () -> new PickaxeItem(ModToolTiers.ACTINOLITE, 2, -1, new Item.Properties().durability(ModToolTiers.ACTINOLITE.getUses() + 100)));



    //-- Skiron
    public static final RegistryObject<Item> SKIRON = registerItem("skiron",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAW_SKIRON = registerItem("raw_skiron",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SKIRON_NUGGET = registerItem("skiron_nugget",
            () -> new Item(new Item.Properties()));

    /*. Tools */
    //- Skiron
    public static final RegistryObject<Item> SKIRON_SWORD = registerItem("skiron_sword",
            () -> new SwordItem(ModToolTiers.SKIRON, 2, -3, new Item.Properties().durability(ModToolTiers.SKIRON.getUses())));

    public static final RegistryObject<Item> SKIRON_PICKAXE = registerItem("skiron_pickaxe",
            () -> new PickaxeItem(ModToolTiers.SKIRON, 1, -2, new Item.Properties().durability(ModToolTiers.SKIRON.getUses() + 100)));

    public static final RegistryObject<Item> SKIRON_AXE = registerItem("skiron_axe",
            () -> new AxeItem(ModToolTiers.SKIRON, 2, -1.5f, new Item.Properties().durability(ModToolTiers.SKIRON.getUses() + 50)));

    public static final RegistryObject<Item> SKIRON_SHOVEL = registerItem("skiron_shovel",
            () -> new ShovelItem(ModToolTiers.SKIRON, 1.75F, -2.75F, new Item.Properties().durability(ModToolTiers.SKIRON.getUses())));

    public static final RegistryObject<Item> SKIRON_HOE = registerItem("skiron_hoe",
            () -> new HoeItem(ModToolTiers.SKIRON, -1, -0.5f, new Item.Properties().durability(ModToolTiers.SKIRON.getUses() + 150)));

    public static final RegistryObject<Item> SKIRON_KNIFE = registerItem("skiron_knife",
            () -> new KnifeItem(ModToolTiers.SKIRON, 0.5F, -1.0F, new Item.Properties().durability(ModToolTiers.SKIRON.getUses() + 50)));

    public static final RegistryObject<Item> SKIRON_PAXEL = registerItem("skiron_paxel",
            () -> new PaxelItem(ModToolTiers.SKIRON, 5.5f, -1f, new Item.Properties().durability(ModToolTiers.SKIRON.getUses() + 150)));

    public static final RegistryObject<Item> SKIRON_HAMMER = registerItem("skiron_hammer",
            () -> new HammerItem(ModToolTiers.SKIRON, 1, 5, -3.5f, new Item.Properties().durability(ModToolTiers.SKIRON.getUses() - 50)));

    public static final RegistryObject<Item> SKIRON_SHIELD = registerItem("skiron_shield",
            () -> new ShieldItem(new Item.Properties().durability(ModToolTiers.SKIRON.getUses() - 50)));

    //- Bows
    public static final RegistryObject<Item> REINFORCED_STONE_BOW = registerItem("reinforced_stone_bow",
            () -> new ReinforcedBowItem(new Item.Properties().durability(452), 1.5f));

    public static final RegistryObject<Item> REINFORCED_IRON_BOW = registerItem("reinforced_iron_bow",
            () -> new ReinforcedBowItem(new Item.Properties().durability(506), 2.5f));

    public static final RegistryObject<Item> REINFORCED_GOLD_BOW = registerItem("reinforced_gold_bow",
            () -> new ReinforcedBowItem(new Item.Properties().durability(400), 3.5f));

    public static final RegistryObject<Item> REINFORCED_DIAMOND_BOW = registerItem("reinforced_diamond_bow",
            () -> new ReinforcedBowItem(new Item.Properties().durability(624), 4.0f));

    public static final RegistryObject<Item> REINFORCED_NETHERITE_BOW = registerItem("reinforced_netherite_bow",
    () -> new ReinforcedBowItem(new Item.Properties().durability(786), 5.5f));

    public static final RegistryObject<Item> REINFORCED_SKIRON_BOW = registerItem("reinforced_skiron_bow",
    () -> new ReinforcedBowItem(new Item.Properties().durability(546), 2.8f));

    public static final RegistryObject<Item> REINFORCED_ACTINOLITE_BOW = registerItem("reinforced_actinolite_bow",
    () -> new ReinforcedBowItem(new Item.Properties().durability(580), 3.2f));


    /*. Armor */
    public static final RegistryObject<Item> SKIRON_HELMET = registerItem("skiron_helmet",
            () -> new EffectArmorItem(ModArmorMaterials.SKIRON, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> SKIRON_CHESTPLATE = registerItem("skiron_chestplate",
            () -> new EffectArmorItem(ModArmorMaterials.SKIRON, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> SKIRON_LEGGINGS = registerItem("skiron_leggings",
            () -> new EffectArmorItem(ModArmorMaterials.SKIRON, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<Item> SKIRON_BOOTS = registerItem("skiron_boots",
            () -> new EffectArmorItem(ModArmorMaterials.SKIRON, ArmorItem.Type.BOOTS, new Item.Properties()));

    //- Skiron actinolite
    public static final RegistryObject<Item> SKIRON_ACTINOLITE_HELMET = registerItem("skiron_actinolite_helmet",
            () -> new EffectArmorItem(ModArmorMaterials.SKIRON_ACTINOLITE, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> SKIRON_ACTINOLITE_CHESTPLATE = registerItem("skiron_actinolite_chestplate",
            () -> new EffectArmorItem(ModArmorMaterials.SKIRON_ACTINOLITE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> SKIRON_ACTINOLITE_LEGGINGS = registerItem("skiron_actinolite_leggings",
            () -> new EffectArmorItem(ModArmorMaterials.SKIRON_ACTINOLITE, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<Item> SKIRON_ACTINOLITE_BOOTS = registerItem("skiron_actinolite_boots",
            () -> new EffectArmorItem(ModArmorMaterials.SKIRON_ACTINOLITE, ArmorItem.Type.BOOTS, new Item.Properties()));


    public static final RegistryObject<Item> SKOAL = registerItem("skoal",
            () -> new DescriptionFuelItem(new Item.Properties(), 1800, "skoal", true));

    //- Horse Armor
    public static final RegistryObject<Item> SKIRON_HORSE_ARMOR = registerItem("skiron_horse_armor",
            () -> new HorseArmorItem(6, new ResourceLocation(MMM.MOD_ID, "textures/entity/horse/armor/horse_armor_skiron.png"), new Item.Properties()));


    private static RegistryObject<Item> registerItem(String name, Supplier<? extends Item> item) {
        return ITEMS.register(name, item);
    } // I'm not sure why I did this
    
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
