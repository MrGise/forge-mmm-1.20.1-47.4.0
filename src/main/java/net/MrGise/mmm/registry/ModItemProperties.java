package net.MrGise.mmm.registry;

import net.MrGise.mmm.MMM;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class ModItemProperties {

    public static void addCustomItemProperties() {

        ItemProperties.register(ModItems.ORE_REDETECTOR.get(), new ResourceLocation(MMM.MOD_ID, "on"),
                (itemStack, clientLevel, livingEntity, i) -> itemStack.hasTag() ? 1f : 0f);

        makeBow(ModItems.REINFORCED_STONE_BOW.get());
        makeBow(ModItems.REINFORCED_IRON_BOW.get());
        makeBow(ModItems.REINFORCED_GOLD_BOW.get());
        makeBow(ModItems.REINFORCED_DIAMOND_BOW.get());

        ItemProperties.register(ModItems.MIMIC.get(), new ResourceLocation(MMM.MOD_ID, "form"),
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

        ItemProperties.register(ModBlocks.MIMIC_BLOCK.get().asItem(), new ResourceLocation(MMM.MOD_ID, "form"),
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

        ItemProperties.register(ModBlocks.HEAVENLY_GRASS.get().asItem(), new ResourceLocation(MMM.MOD_ID, "long"),
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
        ItemProperties.register(item, new ResourceLocation("pull"), (stack, level, entity, i) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return entity.getUseItem() != stack ? 0.0F : (float)(stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F;
            }
        });

        ItemProperties.register(item, new ResourceLocation("pulling"), (stack, level, entity, i) -> {
            return entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F;
        });
    }

}
