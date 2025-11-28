package net.MrGise.mmm.datagen;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.loot.AddItemModifierC;
import net.MrGise.mmm.registry.front.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

// Generates loot table modifiers
public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifierProvider(PackOutput output) {
        super(output, MMM.MOD_ID);
    }

    @Override
    protected void start() {
        //. Loot modifiers here

        add("cucumber_from_plains_village_chest", new AddItemModifierC(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/village/village_plains_house")).build(),
                LootItemRandomChanceCondition.randomChance(0.3f).build()
        }, ModItems.CUCUMBER.get(), 3));
        add("cucumber_seeds_from_plains_village_chest", new AddItemModifierC(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/village/village_plains_house")).build(),
                LootItemRandomChanceCondition.randomChance(0.35f).build()
        }, ModItems.CUCUMBER_SEEDS.get(), 5));

        add("cucumber_from_taiga_village_chest", new AddItemModifierC(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/village/village_taiga_house")).build(),
                LootItemRandomChanceCondition.randomChance(0.3f).build()
        }, ModItems.CUCUMBER.get(), 5));
        add("cucumber_seeds_from_taiga_village_chest", new AddItemModifierC(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/village/village_taiga_house")).build(),
                LootItemRandomChanceCondition.randomChance(0.35f).build()
        }, ModItems.CUCUMBER_SEEDS.get(), 7));
    }
}
