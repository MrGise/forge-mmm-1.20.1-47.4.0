package net.MrGise.mmm.registry.front;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.registry.create.ModCreateBlocks;
import net.MrGise.mmm.registry.front.item.ModItems;
import net.MrGise.mmm.registry.middle.ModEnchantments;
import net.MrGise.mmm.util.ItemUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

    // Creative mode tabs
public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MMM.MOD_ID);

    public static final RegistryObject<CreativeModeTab> OVERWORLD = CREATIVE_MODE_TABS.register("the_skyland",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.SKIRON_ORE.get()))
                    .title(Component.translatable("creativetab.overworld")).displayItems((displayParameters, output) -> {
                        //,- The Skyland
                        //- Heavenly grass
                        output.accept(ModBlocks.HEAVENLY_GRASS.get());
                        ItemStack stack1 = ModBlocks.HEAVENLY_GRASS.get().asItem().getDefaultInstance();
                        stack1.getOrCreateTag().putBoolean("long", true);
                        output.accept(stack1);
                        output.accept(ModBlocks.HEAVENLY_GRASS_BLOCK.get());
                        output.accept(ModBlocks.SKYSOIL.get());
                        output.accept(ModBlocks.HEAVENLY_GRASS_BLOCK_SKYDIRT.get());
                        output.accept(ModBlocks.SKYDIRT.get());

                        //- Skysolid
                        output.accept(ModBlocks.SKYSOLID.get());
                        output.accept(ModBlocks.SKYSOLID_WALL.get());
                        output.accept(ModBlocks.BROKEN_SKYSOLID.get());
                        //* Skyground
                        output.accept(ModBlocks.SKYGROUND.get());
                        output.accept(ModBlocks.SKYGROUND_WALL.get());
                        output.accept(ModBlocks.BROKEN_SKYGROUND.get());

                        //- Actinolite
                        output.accept(ModBlocks.ACTINOLITE_ORE.get());
                        output.accept(ModItems.ACTINOLITE.get());

                        output.accept(ModItems.ACTINOLITE_SWORD.get());
                        output.accept(ModItems.ACTINOLITE_PICKAXE.get());
                        output.accept(ModItems.ACTINOLITE_AXE.get());
                        output.accept(ModItems.ACTINOLITE_SHOVEL.get());
                        output.accept(ModItems.ACTINOLITE_HOE.get());
                        output.accept(ModItems.ACTINOLITE_KNIFE.get());
                        output.accept(ModItems.ACTINOLITE_PAXEL.get());

                        //- Skiron
                        output.accept(ModBlocks.SKIRON_ORE.get());
                        output.accept(ModItems.RAW_SKIRON.get());
                        output.accept(ModItems.SKIRON.get());
                        output.accept(ModItems.SKIRON_NUGGET.get());
                        output.accept(ModBlocks.RAW_SKIRON_BLOCK.get());
                        output.accept(ModBlocks.SKIRON_BLOCK.get());

                        output.accept(ModItems.SKIRON_SWORD.get());
                        output.accept(ModItems.SKIRON_PICKAXE.get());
                        output.accept(ModItems.SKIRON_AXE.get());
                        output.accept(ModItems.SKIRON_SHOVEL.get());
                        output.accept(ModItems.SKIRON_HOE.get());
                        output.accept(ModItems.SKIRON_KNIFE.get());
                        output.accept(ModItems.SKIRON_PAXEL.get());
                        output.accept(ModItems.SKIRON_HAMMER.get());

                        output.accept(ModItems.SKIRON_SHIELD.get());

                        output.accept(ModItems.SKIRON_HELMET.get());
                        output.accept(ModItems.SKIRON_CHESTPLATE.get());
                        output.accept(ModItems.SKIRON_LEGGINGS.get());
                        output.accept(ModItems.SKIRON_BOOTS.get());
                        output.accept(ModItems.SKIRON_HORSE_ARMOR.get());

                        //- Skiron + Actinolite
                        output.accept(ModItems.SKIRON_ACTINOLITE_HELMET.get());
                        output.accept(ModItems.SKIRON_ACTINOLITE_CHESTPLATE.get());
                        output.accept(ModItems.SKIRON_ACTINOLITE_LEGGINGS.get());
                        output.accept(ModItems.SKIRON_ACTINOLITE_BOOTS.get());

                        //- Skoal
                        output.accept(ModBlocks.SKOAL_ORE.get());
                        output.accept(ModItems.SKOAL.get());
                        output.accept(ModBlocks.SKOAL_BLOCK.get());

                        //- Skywood
                        output.accept(ModBlocks.SKYWOOD_LOG.get());
                        output.accept(ModBlocks.STRIPPED_SKYWOOD_LOG.get());
                        output.accept(ModBlocks.SKYWOOD_PLANKS.get());
                        output.accept(ModBlocks.SKYWOOD_STAIRS.get());
                        output.accept(ModBlocks.SKYWOOD_SLAB.get());
                        output.accept(ModBlocks.SKYWOOD_FENCE.get());
                        output.accept(ModBlocks.SKYWOOD_FENCE_GATE.get());
                        output.accept(ModBlocks.SKYWOOD_DOOR.get());
                        output.accept(ModBlocks.SKYWOOD_TRIPLE_DOOR.get());
                        output.accept(ModBlocks.SKYWOOD_TRAPDOOR.get());
                        output.accept(ModBlocks.SKYWOOD_PRESSURE_PLATE.get());
                        output.accept(ModBlocks.SKYWOOD_BUTTON.get());

                        output.accept(ModItems.GLIDE_ARMOR_TRIM_SMITHING_TEMPLATE.get());


                        //| World
                        output.accept(ModBlocks.OXALIS.get());
                        output.accept(ModItems.STRAWBERRY_SEEDS.get());
                        output.accept(ModItems.CUCUMBER_SEEDS.get());
                        output.accept(ModBlocks.THIN_PINE_LOG.get());
                        output.accept(ModBlocks.MANA_ORE.get());
                        output.accept(ModItems.SOLIDIFIED_MANA.get());
                    }).build());

    //. Structures
    public static final RegistryObject<CreativeModeTab> STRUCTURES = CREATIVE_MODE_TABS.register("structures",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.MOSSY_GOLD_KEY.get()))
                    .title(Component.translatable("creativetab.structures")).displayItems((displayParameters, output) -> {
                        output.accept(ModItems.GOLD_KEY.get());
                        output.accept(ModItems.MOSSY_GOLD_KEY.get());
                        output.accept(ModItems.DROPPY_LIKES_RICOCHET_MUSIC_DISC.get());
                        output.accept(ModItems.DROPPY_LIKES_EVERYTHING_MUSIC_DISC.get());
                        output.accept(ModItems.TUNE_MUSIC_DISC.get());
                        output.accept(ModBlocks.BOWYERY_TABLE.get());
                        output.accept(ModItems.GLIDE_ARMOR_TRIM_SMITHING_TEMPLATE.get());
                        output.accept(ModItems.MIMIC.get());
                        output.accept(ModBlocks.MIMIC_BLOCK.get());
                        output.accept(ItemUtils.enchantedBook(
                                new EnchantmentInstance(Enchantments.INFINITY_ARROWS, 1),
                                new EnchantmentInstance(Enchantments.MENDING, 1)));
                        Enchantment enchantment1 = ModEnchantments.LIGHTENING_STRIKER.get();
                        for (int level = enchantment1.getMinLevel(); level <= enchantment1.getMaxLevel(); level++) {
                            ItemStack enchantedBook = ItemUtils.enchantedBook(new EnchantmentInstance(enchantment1, level));
                            output.accept(enchantedBook);
                        }
                    }).build());

    //. Food
    public static final RegistryObject<CreativeModeTab> MMM_CUISINE = CREATIVE_MODE_TABS.register("mmm_cuisine",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.CUCUMBER.get()))
                    .title(Component.translatable("creativetab.mmm_cuisine")).displayItems((displayParameters, output) -> {
                        output.accept(ModItems.BREADSTICK.get());

                        output.accept(ModItems.STRAWBERRY.get());
                        output.accept(ModItems.STRAWBERRY_SEEDS.get());

                        output.accept(ModItems.CUCUMBER.get());
                        output.accept(ModItems.CUT_CUCUMBER.get());
                        output.accept(ModItems.CUCUMBER_SEEDS.get());

                        output.accept(ModItems.POMEGRANATE.get());
                        output.accept(ModItems.POMEGRANATE_SLICE.get());
                        output.accept(ModItems.POMEGRANATE_EMPTY_SLICE.get());
                        output.accept(ModItems.POMEGRANATE_SEEDS.get());

                        output.accept(ModItems.APPLE_SLICE.get());
                        output.accept(ModItems.HONEYED_APPLE_SLICE.get());
                    }).build());

    //\ Dimensions
    public static final RegistryObject<CreativeModeTab> DIMENSIONS = CREATIVE_MODE_TABS.register("dimensions",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.NULL_BLOCK.get()))
                    .title(Component.translatable("creativetab.dimensions")).displayItems((displayParameters, output) -> {
                        output.accept(ModBlocks.NULL_BLOCK.get());
                        output.accept(ModBlocks.ACACIA_TRIPLE_DOOR.get());
                        output.accept(ModBlocks.BIRCH_TRIPLE_DOOR.get());
                        output.accept(ModBlocks.CRIMSON_TRIPLE_DOOR.get());
                    }).build());

    //. Tools and Misc
    public static final RegistryObject<CreativeModeTab> TOOLS_AND_MISC = CREATIVE_MODE_TABS.register("tools_and_misc",
            () -> CreativeModeTab.builder()
                    .withTabsBefore(DIMENSIONS.getId(), MMM_CUISINE.getId(),
                            OVERWORLD.getId(), STRUCTURES.getId())
                    .icon(() -> new ItemStack(ModItems.ORE_DETECTOR.get()))
                    .title(Component.translatable("creativetab.tools_and_misc")).displayItems((displayParameters, output) -> {
                        output.accept(ModItems.REINFORCED_STONE_BOW.get());
                        output.accept(ModItems.REINFORCED_IRON_BOW.get());
                        output.accept(ModItems.REINFORCED_GOLD_BOW.get());
                        output.accept(ModItems.REINFORCED_DIAMOND_BOW.get());
                        output.accept(ModItems.REINFORCED_NETHERITE_BOW.get());
                        output.accept(ModItems.REINFORCED_SKIRON_BOW.get());
                        output.accept(ModItems.REINFORCED_ACTINOLITE_BOW.get());

                        output.accept(ModItems.LIT_CANDLE.get());
                        output.accept(ModItems.LIT_CANDLE_BLACK.get());
                        output.accept(ModItems.LIT_CANDLE_BLUE.get());
                        output.accept(ModItems.LIT_CANDLE_BROWN.get());
                        output.accept(ModItems.LIT_CANDLE_CYAN.get());
                        output.accept(ModItems.LIT_CANDLE_GRAY.get());
                        output.accept(ModItems.LIT_CANDLE_GREEN.get());
                        output.accept(ModItems.LIT_CANDLE_LIGHT_BLUE.get());
                        output.accept(ModItems.LIT_CANDLE_LIGHT_GRAY.get());
                        output.accept(ModItems.LIT_CANDLE_LIME.get());
                        output.accept(ModItems.LIT_CANDLE_MAGENTA.get());
                        output.accept(ModItems.LIT_CANDLE_ORANGE.get());
                        output.accept(ModItems.LIT_CANDLE_PINK.get());
                        output.accept(ModItems.LIT_CANDLE_PURPLE.get());
                        output.accept(ModItems.LIT_CANDLE_RED.get());
                        output.accept(ModItems.LIT_CANDLE_WHITE.get());
                        output.accept(ModItems.LIT_CANDLE_YELLOW.get());

                        ItemStack painting1 = Items.PAINTING.getDefaultInstance();
                        Painting.storeVariant(painting1.getOrCreateTagElement("EntityTag"), ModPaintings.MAGERY.getHolder().get());
                        output.accept(painting1);

                        output.accept(ModCreateBlocks.EXAMPLE_CONNECTION.get());

                        output.accept(ModBlocks.TEST_BLOCK.get());
                        output.accept(ModBlocks.ANIMATED_TEST_BLOCK.get());
                        output.accept(ModItems.TEST_ITEM.get());
                        output.accept(ModItems.DIRECTORY_TEST.get());

                        output.accept(ModBlocks.BIRTHDAY_CAKE.get());

                        output.accept(ModItems.ORE_DETECTOR.get());
                        output.accept(ModItems.ORE_REDETECTOR.get());

                        output.accept(ModBlocks.SOUND_BLOCK.get());

                        output.accept(ModBlocks.PORTAL_BLOCK.get());
                        output.accept(ModCreateBlocks.CONNECTING_PORTAL_BLOCK.get());
                    }).build());



    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

}
