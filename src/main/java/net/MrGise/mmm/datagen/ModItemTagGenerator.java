package net.MrGise.mmm.datagen;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.registry.ModBlocks;
import net.MrGise.mmm.registry.ModItems;
import net.MrGise.mmm.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.tag.ForgeTags;

import java.util.concurrent.CompletableFuture;

// Generates item tags
public class ModItemTagGenerator extends ItemTagsProvider {

    public ModItemTagGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> future, CompletableFuture<TagLookup<Block>> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, future, completableFuture, MMM.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Tags here

        this.tag(ItemTags.MUSIC_DISCS)
                .add(ModItems.DROPPY_LIKES_RICOCHET_MUSIC_DISC.get())
                .add(ModItems.DROPPY_LIKES_EVERYTHING_MUSIC_DISC.get())
                .add(ModItems.TUNE_MUSIC_DISC.get());

        this.tag(ModTags.Items.CUCUMBERS)
                .add(ModItems.CUCUMBER.get());
        this.tag(ModTags.Items.CUCUMBER_SEEDS)
                .add(ModItems.CUCUMBER_SEEDS.get());

        this.tag(ModTags.Items.STRAWBERRIES)
                .add(ModItems.STRAWBERRY.get());
        this.tag(ModTags.Items.STRAWBERRY_SEEDS)
                .add(ModItems.STRAWBERRY_SEEDS.get());

        this.tag(ModTags.Items.POMEGRANATES)
                .add(ModItems.POMEGRANATE.get());
        this.tag(ModTags.Items.POMEGRANATE_SLICES)
                .add(ModItems.POMEGRANATE_SLICE.get());
        this.tag(ModTags.Items.POMEGRANATE_SEEDS)
                .add(ModItems.POMEGRANATE_SEEDS.get());

        this.tag(ModTags.Items.SKIRON_BLOCKS)
                .add(ModBlocks.SKIRON_BLOCK.get().asItem());

        this.tag(ModTags.Items.SKIRON_INGOTS)
                .add(ModItems.SKIRON.get());

        this.tag(ForgeTags.TOOLS_AXES)
                .add(ModItems.SKIRON_AXE.get());

        this.tag(ForgeTags.TOOLS_PICKAXES)
                .add(ModItems.SKIRON_PICKAXE.get())
                .add(ModItems.ACTIONLITE_PICKAXE.get());

        this.tag(ForgeTags.TOOLS_SHOVELS)
                .add(ModItems.SKIRON_SHOVEL.get());

        this.tag(vectorwing.farmersdelight.common.tag.ModTags.KNIVES)
                .add(ModItems.SKIRON_KNIFE.get());

        this.tag(ForgeTags.TOOLS_KNIVES)
                .add(ModItems.SKIRON_KNIFE.get());

        this.tag(Tags.Items.SEEDS)
                .add(ModItems.CUCUMBER_SEEDS.get())
                .add(ModItems.STRAWBERRY_SEEDS.get());

        this.tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.SKIRON_HELMET.get(),
                        ModItems.SKIRON_CHESTPLATE.get(),
                        ModItems.SKIRON_LEGGINGS.get(),
                        ModItems.SKIRON_BOOTS.get(), ModItems.SKIRON_ACTINOLITE_HELMET.get(),
                        ModItems.SKIRON_ACTINOLITE_CHESTPLATE.get(),
                        ModItems.SKIRON_ACTINOLITE_LEGGINGS.get(),
                        ModItems.SKIRON_ACTINOLITE_BOOTS.get());

        this.tag(ModTags.Items.TABLETS)
                .add(ModItems.SKYSOLID_TABLET.get());

        this.tag(ItemTags.TRIM_MATERIALS)
                .add(ModItems.SKIRON.get());

        this.tag(ItemTags.TRIM_TEMPLATES)
                .add(ModItems.GLIDE_ARMOR_TRIM_SMITHING_TEMPLATE.get());

        this.tag(ModTags.Items.SKIRON_NUGGETS).add(ModItems.SKIRON_NUGGET.get());

    }

    @Override
    public String getName() {
        return "Item Tags";
    }
}
