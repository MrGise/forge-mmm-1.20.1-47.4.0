package net.MrGise.mmm.datagen.loot;

import net.MrGise.mmm.block.ModBlocks;
import net.MrGise.mmm.item.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {

    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }



    @Override
    protected void generate() {
        //Loot Tables here
        this.dropSelf(ModBlocks.SKOAL_BLOCK.get());
        this.dropSelf(ModBlocks.SKIRON_ORE.get());
        this.dropSelf(ModBlocks.SKIRON_BLOCK.get());
        this.dropSelf(ModBlocks.RAW_SKIRON_BLOCK.get());

        this.dropSelf(ModBlocks.BIRTHDAY_CAKE.get());

        this.dropSelf(ModBlocks.MIMIC_BLOCK.get());

        this.add(ModBlocks.SKYSOLID.get(),
                block -> createCustomSingularDrop(ModBlocks.SKYSOLID.get(), ModBlocks.BROKEN_SKYSOLID.get()));
        this.dropSelf(ModBlocks.SKYSOLID_WALL.get());
        this.dropSelf(ModBlocks.DECORATIVE_SKYSOLID.get());
        this.dropSelf(ModBlocks.BROKEN_SKYSOLID.get());

        this.dropSelf(ModBlocks.SKYWOOD_PLANKS.get());
        this.dropSelf(ModBlocks.SKYWOOD_STAIRS.get());
        this.add(ModBlocks.SKYWOOD_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.SKYWOOD_SLAB.get()));
        this.dropSelf(ModBlocks.SKYWOOD_PRESSURE_PLATE.get());
        this.dropSelf(ModBlocks.SKYWOOD_BUTTON.get());
        this.dropSelf(ModBlocks.SKYWOOD_FENCE.get());
        this.dropSelf(ModBlocks.SKYWOOD_FENCE_GATE.get());
        this.dropSelf(ModBlocks.SKYWOOD_TRAPDOOR.get());
        this.add(ModBlocks.SKYWOOD_DOOR.get(),
                Block -> createDoorTable(ModBlocks.SKYWOOD_DOOR.get()));

        // Ores

        this.add(ModBlocks.ACTINOLITE_ORE.get(),
                Block -> createOreDrop(ModBlocks.ACTINOLITE_ORE.get(),ModItems.ACTINOLITE.get()));

        this.add(ModBlocks.SKIRON_ORE.get(),
                block -> createMultipleOreDrop(ModBlocks.SKIRON_ORE.get(), ModItems.RAW_SKIRON.get(), 1, 3));

        this.add(ModBlocks.SKOAL_ORE.get(),
                block -> createMultipleOreDrop(ModBlocks.SKOAL_ORE.get(), ModItems.SKOAL.get(), 1, 4));

    }

    protected LootTable.Builder createCustomSingularDrop(Block pBlock, ItemLike pItem) {
        return createSilkTouchDispatchTable(pBlock, this.applyExplosionDecay(pBlock, LootItem.lootTableItem(pItem)
                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    protected LootTable.Builder createCustomMultipleDrop(Block pBlock, ItemLike pItem, float MinDrop, float MaxDrop) {
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock, LootItem.lootTableItem(pItem)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(MinDrop, MaxDrop)))
                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    protected LootTable.Builder createMultipleOreDrop(Block pBlock, Item pItem, float MinDrop, float MaxDrop) {
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock, LootItem.lootTableItem(pItem)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(MinDrop, MaxDrop)))
                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
