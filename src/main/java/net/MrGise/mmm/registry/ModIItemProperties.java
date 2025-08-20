package net.MrGise.mmm.registry;

import net.MrGise.mmm.MMM;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public class ModIItemProperties {

    public static void addCustomItemProperties() {

        ItemProperties.register(ModItems.ORE_REDETECTOR.get(), new ResourceLocation(MMM.MOD_ID, "on"),
                (itemStack, clientLevel, livingEntity, i) -> itemStack.hasTag() ? 1f : 0f);

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

}
