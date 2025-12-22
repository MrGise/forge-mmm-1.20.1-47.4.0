package net.MrGise.mmm.datagen.advancement;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.event.BlockTouchTrigger;
import net.MrGise.mmm.registry.front.ModBlocks;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.ChangeDimensionTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.function.Consumer;

public class ModAdvancementProvider implements ForgeAdvancementProvider.AdvancementGenerator {
    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
        Advancement dimensionAdvancement = Advancement.Builder.advancement()
                .display(ModBlocks.NULL_BLOCK.get(),
                        Component.translatable("advancement.mmm.dimensions"), Component.translatable("advancement.mmm.dimensions.desc"),
                        ResourceLocation.fromNamespaceAndPath(MMM.MOD_ID, "textures/block/skysolid.png"), FrameType.GOAL,
                        true, true, false)
                .addCriterion("exit_overworld",
                        ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(ResourceKey.create(Registries.DIMENSION, Level.END.location())))
                .save(saver, ResourceLocation.fromNamespaceAndPath(MMM.MOD_ID, "dimensions"), existingFileHelper);
        Advancement nullAdvancement = Advancement.Builder.advancement()
                .display(ModBlocks.NULL_BLOCK.get(),
                        Component.translatable("advancement.mmm.null"), Component.translatable("advancement.mmm.null.desc"),
                        null, FrameType.TASK,
                        true, true, false)
                .parent(dimensionAdvancement)
                .addCriterion("on_null", BlockTouchTrigger.TriggerInstance.onBlock(ModBlocks.NULL_BLOCK.get()))
                .save(saver, ResourceLocation.fromNamespaceAndPath(MMM.MOD_ID, "null"), existingFileHelper);
        Advancement nullGetAdvancement = Advancement.Builder.advancement()
                .display(ModBlocks.NULL_BLOCK.get(),
                        Component.translatable("advancement.mmm.null_get"), Component.translatable("advancement.mmm.null_get.desc"),
                        null, FrameType.TASK,
                        true, true, true)
                .parent(nullAdvancement)
                .addCriterion("has_null", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.NULL_BLOCK.get()))
                .save(saver, ResourceLocation.fromNamespaceAndPath(MMM.MOD_ID, "null_get"), existingFileHelper);


    }
}
