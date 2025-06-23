package net.MrGise.mmm.datagen;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.block.ModBlocks;
import net.MrGise.mmm.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {

    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MMM.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Tags here
        this.tag(ModTags.Blocks.DETECTABLE_ORE)
                .addTag(Tags.Blocks.ORES);

        this.tag(ModTags.Blocks.SKYLAND_ORES)
                .add(ModBlocks.SKOAL_ORE.get())
                .add(ModBlocks.SKIRON_ORE.get());

        this.tag(Tags.Blocks.ORES)
                .add(ModBlocks.SKIRON_ORE.get())
                .add(ModBlocks.SKOAL_ORE.get());

        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.SKYWOOD_PLANKS.get())
                .add(ModBlocks.SKYWOOD_STAIRS.get())
                .add(ModBlocks.SKYWOOD_SLAB.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.SKOAL_ORE.get())
                .add(ModBlocks.SKIRON_ORE.get())
                .add(ModBlocks.SKOAL_BLOCK.get())
                .add(ModBlocks.SKYSOLID.get())
                .add(ModBlocks.DECORATIVE_SKYSOLID.get())
                .add(ModBlocks.BROKEN_SKYSOLID.get())
                .add(ModBlocks.SKIRON_BLOCK.get())
                .add(ModBlocks.RAW_SKIRON_BLOCK.get());

        this.tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.SKOAL_ORE.get())
                .add(ModBlocks.SKIRON_BLOCK.get())
                .add(ModBlocks.RAW_SKIRON_BLOCK.get())
                .add(ModBlocks.SKOAL_BLOCK.get());

        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.SKIRON_ORE.get());

    }

    @Override
    public String getName() {
        return "Block Tags";
    }

}
