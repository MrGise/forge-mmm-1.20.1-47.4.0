package net.MrGise.mmm.datagen;

import net.MrGise.mmm.datagen.loot.ModBlockLootTables;
import net.MrGise.mmm.datagen.loot.ModLootTables;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class ModLootTableProvider {

    public static LootTableProvider create(PackOutput packOutput) {
        return new LootTableProvider(packOutput,
                Set.of(),
                List.of(
                        new LootTableProvider.SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK),
                        new LootTableProvider.SubProviderEntry(ModLootTables::new, LootContextParamSets.EMPTY) // custom loot tables
                ));
    }

}
