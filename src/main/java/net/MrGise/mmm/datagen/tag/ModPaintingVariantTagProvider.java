package net.MrGise.mmm.datagen.tag;

import net.MrGise.mmm.MMM;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PaintingVariantTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.PaintingVariantTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

    // Painting Variant Tag provider
public class ModPaintingVariantTagProvider extends PaintingVariantTagsProvider {
    public ModPaintingVariantTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, MMM.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider p_256017_) {
        this.tag(PaintingVariantTags.PLACEABLE)
                .addOptional(modLoc("magery"));
    }


    private ResourceLocation modLoc(String input) {
        return new ResourceLocation(MMM.MOD_ID, input);
    }
}
