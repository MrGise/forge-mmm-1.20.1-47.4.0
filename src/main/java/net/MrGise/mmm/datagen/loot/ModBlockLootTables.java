package net.MrGise.mmm.datagen.loot;

import net.MrGise.mmm.block.ModBlocks;
import net.MrGise.mmm.block.custom.AccessibleCropBlock;
import net.MrGise.mmm.item.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {

    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }



    @Override
    protected void generate() {
        //--Loot Tables here
        this.dropSelf(ModBlocks.TEST_BLOCK.get());
        this.dropSelf(ModBlocks.ANIMATED_TEST_BLOCK.get());


        this.dropSelf(ModBlocks.SKOAL_BLOCK.get());
        this.dropSelf(ModBlocks.SKIRON_ORE.get());
        this.dropSelf(ModBlocks.SKIRON_BLOCK.get());
        this.dropSelf(ModBlocks.RAW_SKIRON_BLOCK.get());

        this.dropSelf(ModBlocks.BIRTHDAY_CAKE.get());

        this.dropSelf(ModBlocks.MIMIC_BLOCK.get());

        this.add(ModBlocks.SKYSOLID.get(),
                block -> createCustomSingularDrop(ModBlocks.SKYSOLID.get(), ModBlocks.BROKEN_SKYSOLID.get()));
        this.dropSelf(ModBlocks.SKYSOLID_WALL.get());
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

        this.createCustomCropDrops((AccessibleCropBlock) ModBlocks.CUCUMBER.get(), ModItems.CUCUMBER.get(), ModItems.CUCUMBER_SEEDS.get(), 1, 4, 0, 3);

        this.createCustomCropDrops((AccessibleCropBlock) ModBlocks.STRAWBERRY.get(), ModItems.STRAWBERRY.get(), ModItems.STRAWBERRY_SEEDS.get(), 2, 6, 0, 0);


        //- Ores

        this.add(ModBlocks.ACTINOLITE_ORE.get(),
                Block -> createOreDrop(ModBlocks.ACTINOLITE_ORE.get(),ModItems.ACTINOLITE.get()));

        this.add(ModBlocks.SKIRON_ORE.get(),
                block -> createMultipleOreDrop(ModBlocks.SKIRON_ORE.get(), ModItems.RAW_SKIRON.get(), 1, 3));

        this.add(ModBlocks.SKOAL_ORE.get(),
                block -> createMultipleOreDrop(ModBlocks.SKOAL_ORE.get(), ModItems.SKOAL.get(), 1, 4));

    }

    //- Reference:
    //-
    //- Vanilla:
    //- minCropDrop = 1
    //- maxCropDrop = 1
    //- minSeedDrop = 0
    //- maxSeedDrop = 3

    private void createCustomCropDrops(AccessibleCropBlock cropBlock, ItemLike dropItem, ItemLike seedItem,
            boolean vanillaBehavior, // Toggle between vanilla-like and custom
            float minCropDrop, float maxCropDrop,
            float minSeedDrop, float maxSeedDrop
    ) {
        createCustomCropDrops(cropBlock, dropItem, seedItem, vanillaBehavior, minCropDrop, maxCropDrop, minSeedDrop, maxSeedDrop, 1);
    }

    private void createCustomCropDrops(AccessibleCropBlock cropBlock, ItemLike dropItem, ItemLike seedItem,
            float minCropDrop, float maxCropDrop,
            float minSeedDrop, float maxSeedDrop
    ) {
        createCustomCropDrops(cropBlock, dropItem, seedItem, false, minCropDrop, maxCropDrop, minSeedDrop, maxSeedDrop, 1);
    }

    private void createCustomCropDrops(AccessibleCropBlock cropBlock, ItemLike dropItem, ItemLike seedItem,
            float minCropDrop, float maxCropDrop,
            float minSeedDrop, float maxSeedDrop,
           float seedDropChance
    ) {
        createCustomCropDrops(cropBlock, dropItem, seedItem, false, minCropDrop, maxCropDrop, minSeedDrop, maxSeedDrop, seedDropChance);
    }

    private void createCustomCropDrops(
            AccessibleCropBlock cropBlock, ItemLike dropItem, ItemLike seedItem,
            boolean vanillaBehavior, // Toggle between vanilla-like and custom
            float minCropDrop, float maxCropDrop,
            float minSeedDrop, float maxSeedDrop,
            float seedDropChance
            ) {

        LootItemCondition.Builder fullyGrownCondition = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(cropBlock)
                .setProperties(StatePropertiesPredicate.Builder.properties()
                        .hasProperty(cropBlock.getAgeProperty(), cropBlock.getMaxAge())
                );

        if (vanillaBehavior) {
            // Vanilla-like: 1 crop, 0–3 seeds (using createCropDrops helper)
            this.add(cropBlock, createCropDrops(cropBlock, dropItem.asItem(), seedItem.asItem(), fullyGrownCondition));
        } else {
            // Fully custom drop counts
            this.add(cropBlock, LootTable.lootTable()
                    // Crop drops
                    .withPool(LootPool.lootPool()
                            .when(fullyGrownCondition)
                            .add(LootItem.lootTableItem(dropItem)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(minCropDrop, maxCropDrop))))
                    )
                    // Seed drops
                    .withPool(LootPool.lootPool()
                            .when(fullyGrownCondition)
                            .when(LootItemRandomChanceCondition.randomChance(seedDropChance))
                            .add(LootItem.lootTableItem(seedItem)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(minSeedDrop, maxSeedDrop)))
                            )
                    )
            );
        }
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
