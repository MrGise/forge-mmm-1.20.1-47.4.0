package net.MrGise.mmm.registry.variables;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.registry.content.ModItems;
import net.MrGise.mmm.registry.content.ModBlocks;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import static net.MrGise.floating.helper.Methods.*;

// Custom item properties
public class ModItemProperties {

    public static void addCustomItemProperties() {
        makeShield(ModItems.SKIRON_SHIELD.get());

        makeBow(ModItems.REINFORCED_STONE_BOW.get());
        makeBow(ModItems.REINFORCED_IRON_BOW.get());
        makeBow(ModItems.REINFORCED_GOLD_BOW.get());
        makeBow(ModItems.REINFORCED_DIAMOND_BOW.get());
        makeBow(ModItems.REINFORCED_NETHERITE_BOW.get());

        makeBow(ModItems.REINFORCED_SKIRON_BOW.get());
        makeBow(ModItems.REINFORCED_ACTINOLITE_BOW.get());

        ItemProperties.register(ModItems.MIMIC.get(), mmm("form"),
                (itemStack, clientLevel, livingEntity, i) -> {
                    if (itemStack.hasTag()) {
                        CompoundTag tag = itemStack.getTag();

                        if (tag.contains("form")) {
                            String form = tag.getString("form");

                            return switch (form) {
                                case "carrot" -> 0.2f;
                                default -> 0.0f;
                            };

                        }
                    }

                    return 0.0f;

                });

        ItemProperties.register(ModBlocks.MIMIC_BLOCK.get().asItem(), mmm("form"),
                (itemStack, clientLevel, livingEntity, i) -> {
                    if (itemStack.hasTag()) {
                        CompoundTag tag = itemStack.getTag();

                        if (tag.contains("form")) {
                            String form = tag.getString("form");

                            return switch (form) {
                                case "chest" -> 0.1f;
                                case "shulker_box" -> 0.3f;
                                default -> 0.0f;
                            };
                        }

                    }

                    return 0.0f;

                });

        ItemProperties.register(ModBlocks.HEAVENLY_GRASS.get().asItem(), mmm("long"),
                (pStack, pLevel, pEntity, pSeed) -> {
                    if (pStack.hasTag()) {
                        CompoundTag tag = pStack.getTag();

                        if (tag.contains("long")) {
                            if (tag.getBoolean("long")) {
                                return 1.0f;
                            }
                            return 0.0f;
                        }
                    }

                    return 0.0f;
                });
    }

    public static void makeBow(Item item) {
        ItemProperties.register(item, mcr("pull"), (stack, level, entity, i) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return entity.getUseItem() != stack ? 0.0F : (float)(stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F;
            }
        });

        ItemProperties.register(item, mcr("pulling"), (stack, level, entity, i) -> {
            return entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F;
        });
    }

    public static void makeShield(Item item) {
        ItemProperties.register(item, mcr("blocking"),
                (stack, level, entity, i) -> {
            return entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F;
        });
    }

}
