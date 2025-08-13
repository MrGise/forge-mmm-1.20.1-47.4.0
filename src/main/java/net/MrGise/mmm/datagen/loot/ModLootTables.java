package net.MrGise.mmm.datagen.loot;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.item.ModItems;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class ModLootTables implements LootTableSubProvider {

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> pOutput) {
        pOutput.accept(
                new ResourceLocation(MMM.MOD_ID, "blocks/strawberry_rclick"),
                createItemDropTable(ModItems.STRAWBERRY.get(), 4, 7)
        );

    }

    private LootTable.Builder createItemDropTable(Item item, int min, int max) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1, 1)) // 1 roll
                        .add(LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)))
                        )
                );
    }

}
