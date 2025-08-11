package net.MrGise.mmm.datagen;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.item.ModItems;
import net.MrGise.mmm.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {

    public ModItemTagGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> future, CompletableFuture<TagLookup<Block>> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, future, completableFuture, MMM.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Tags here

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
