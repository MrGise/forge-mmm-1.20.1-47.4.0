package net.MrGise.mmm.resource;

import net.minecraft.util.StringRepresentable;

public enum TripleBlockPart implements StringRepresentable {
    UPPER,
    MIDDLE,
    LOWER;

    @Override
    public String getSerializedName() {
        return this.name().toLowerCase();
    }
}
