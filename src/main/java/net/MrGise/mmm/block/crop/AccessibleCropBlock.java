package net.MrGise.mmm.block.crop;

import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public abstract class AccessibleCropBlock extends CropBlock {

    public AccessibleCropBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return super.getAgeProperty();
    }
}
