package net.MrGise.mmm.datagen.tag;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.registry.content.ModFluids;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModFluidTagsProvider extends FluidTagsProvider {
    public ModFluidTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MMM.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(Tags.Fluids.MILK)
                .add(ModFluids.SOURCE_COW_MILK.get())
                .add(ModFluids.FLOWING_COW_MILK.get())
                .add(ModFluids.SOURCE_GOAT_MILK.get())
                .add(ModFluids.FLOWING_GOAT_MILK.get());
    }
}
