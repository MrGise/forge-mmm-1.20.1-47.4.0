package net.MrGise.mmm.datagen;
// The main datagen class

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.datagen.loot.ModGlobalLootModifierProvider;
import net.MrGise.mmm.datagen.loot.ModLootTableProvider;
import net.MrGise.mmm.datagen.model.ModBlockStateProvider;
import net.MrGise.mmm.datagen.model.ModItemModelProvider;
import net.MrGise.mmm.datagen.advancement.ModAdvancementProvider;
import net.MrGise.mmm.datagen.recipe.ModRecipeProvider;
import net.MrGise.mmm.datagen.tag.ModBlockTagGenerator;
import net.MrGise.mmm.datagen.tag.ModItemTagGenerator;
import net.MrGise.mmm.datagen.tag.ModPaintingVariantTagProvider;
import net.MrGise.mmm.datagen.tag.ModPoiTypeTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = MMM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        //-- Result
        generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput));
        generator.addProvider(event.includeClient(), new ForgeAdvancementProvider(packOutput, lookupProvider, existingFileHelper, List.of(new ModAdvancementProvider())));

        //-- Loot
        generator.addProvider(event.includeServer(), ModLootTableProvider.create(packOutput));
        generator.addProvider(event.includeClient(), new ModGlobalLootModifierProvider(packOutput));

        //-- Tags
        BlockTagsProvider blockTagsProvider = new ModBlockTagGenerator(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new ModItemTagGenerator(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeClient(), new ModPoiTypeTagsProvider(packOutput, lookupProvider, existingFileHelper));

        //-- Models
        generator.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModBlockStateProvider(packOutput, existingFileHelper));

        generator.addProvider(event.includeClient(), new ModPaintingVariantTagProvider(packOutput, lookupProvider, existingFileHelper));

    }

}
