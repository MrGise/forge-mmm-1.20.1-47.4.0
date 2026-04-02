package net.MrGise.mmm.datagen.loot;

import net.MrGise.mmm.registry.content.ModItems;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.ForgeRegistries;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.BiConsumer;

import static net.MrGise.mmm.util.Methods.*;

// Miscellaneous loot tables
public class ModLootTables implements LootTableSubProvider {

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> output) {
        output.accept(
                mmm("blocks/strawberry_rclick"),
                createItemDropTable(ModItems.STRAWBERRY.get(), 4, 7)
        );

        createClickCraftItemTable(output, ModItems.BOILED_EGG.get(),
                item(ModItems.PEELED_BOILED_EGG.get(), 1, 1),
                item(ModItems.BROKEN_EGGSHELL.get(), 1, 1, 0.95f));
    }

    private LootTable.Builder createItemDropTable(Item item, int min, int max) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1, 1))
                        .add(LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)))
                        )
                );
    }

    private void createClickCraftItemTable(BiConsumer<ResourceLocation, LootTable.Builder> output,
                                           Item item, @NonNull RangedLootItem... lootItems) {
        ResourceLocation itemId = ForgeRegistries.ITEMS.getKey(item);
        LootTable.Builder builder = LootTable.lootTable();
        for (RangedLootItem lootItem : lootItems) {
            builder.withPool(LootPool.lootPool()
                    .setRolls(UniformGenerator.between(1, 1))
                    .add(LootItem.lootTableItem(lootItem.item)
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(lootItem.min, lootItem.max)))
                            .when(LootItemRandomChanceCondition.randomChance(lootItem.chance))));
        }
        output.accept(itemId.withPrefix("crafting/item_click_on/"), builder);
    }

    private record RangedLootItem(ItemLike item, int min, int max, float chance) {}

    private RangedLootItem item(ItemLike item, int min, int max, float chance) {
        return new RangedLootItem(item, min, max, chance);
    }

    private RangedLootItem item(ItemLike item, int min, int max) {
        return new RangedLootItem(item, min, max, 1.0f);
    }
}