package net.MrGise.mmm.datagen;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.registry.ModBlocks;
import net.MrGise.mmm.registry.ModTags;
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

        this.tag(ModTags.Blocks.HEAVENLY_GRASS_PLACEABLES)
                .add(ModBlocks.HEAVENLY_GRASS_BLOCK.get())
                .add(ModBlocks.SKYSOIL.get());

        this.tag(ModTags.Blocks.DETECTABLE_ORE)
                .addTag(Tags.Blocks.ORES);

        this.tag(ModTags.Blocks.SKYLAND_ORES)
                .add(ModBlocks.SKOAL_ORE.get())
                .add(ModBlocks.SKIRON_ORE.get());

        this.tag(Tags.Blocks.ORES)
                .add(ModBlocks.ACTINOLITE_ORE.get())
                .add(ModBlocks.SKIRON_ORE.get())
                .add(ModBlocks.SKOAL_ORE.get())

                .add(ModBlocks.MANA_ORE.get());

        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.SKYWOOD_PLANKS.get())
                .add(ModBlocks.SKYWOOD_STAIRS.get())
                .add(ModBlocks.SKYWOOD_SLAB.get())
                .add(ModBlocks.SKYWOOD_BUTTON.get())
                .add(ModBlocks.SKYWOOD_PRESSURE_PLATE.get())
                .add(ModBlocks.SKYWOOD_FENCE_GATE.get())
                .add(ModBlocks.SKYWOOD_FENCE.get())
                .add(ModBlocks.SKYWOOD_TRAPDOOR.get())
                .add(ModBlocks.SKYWOOD_DOOR.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.SKYSOLID.get())
                .add(ModBlocks.BROKEN_SKYSOLID.get())
                .add(ModBlocks.SKOAL_ORE.get())
                .add(ModBlocks.SKIRON_ORE.get())
                .add(ModBlocks.ACTINOLITE_ORE.get())
                .add(ModBlocks.SKOAL_BLOCK.get())
                .add(ModBlocks.SKIRON_BLOCK.get())
                .add(ModBlocks.RAW_SKIRON_BLOCK.get())

                .add(ModBlocks.MANA_ORE.get())

                .add(ModBlocks.NULL_BLOCK.get());

        this.tag(ModTags.Blocks.MINEABLE_WITH_HAMMER)
                .addTag(BlockTags.MINEABLE_WITH_PICKAXE);

        this.tag(ModTags.Blocks.MINEABLE_WITH_PAXEL)
                .addTag(BlockTags.MINEABLE_WITH_PICKAXE)
                .addTag(BlockTags.MINEABLE_WITH_AXE)
                .addTag(BlockTags.MINEABLE_WITH_SHOVEL)
                .addTag(BlockTags.MINEABLE_WITH_HOE);


        this.tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.SKOAL_ORE.get())
                .add(ModBlocks.SKIRON_BLOCK.get())
                .add(ModBlocks.RAW_SKIRON_BLOCK.get())
                .add(ModBlocks.SKOAL_BLOCK.get());

        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.SKIRON_ORE.get());

        this.tag(ModTags.Blocks.NEEDS_SKIRON_TOOL)
                .add(ModBlocks.ACTINOLITE_ORE.get());

        this.tag(ModTags.Blocks.NEEDS_ACTINOLITE_TOOL)
                .add(ModBlocks.MANA_ORE.get());

        this.tag(BlockTags.FENCES)
                .add(ModBlocks.SKYWOOD_FENCE.get());
        this.tag(BlockTags.WALLS)
                .add(ModBlocks.SKYSOLID_WALL.get());
        this.tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.SKYWOOD_FENCE_GATE.get());

    }

    @Override
    public String getName() {
        return "Block Tags";
    }

}
