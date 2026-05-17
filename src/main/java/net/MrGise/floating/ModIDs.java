package net.MrGise.floating;

import net.minecraft.util.StringRepresentable;

public enum ModIDs implements StringRepresentable {
    mmm,
    create,
    farmersdelight,
    jei,
    forge;

    @Override
    public String getSerializedName() {
        return this.name().toLowerCase();
    }

    public static String mmm() {
        return mmm.getSerializedName();
    }
    public static String create() {
        return create.getSerializedName();
    }
    public static String farmersdelight() {
        return farmersdelight.getSerializedName();
    }
    public static String jei() {
        return jei.getSerializedName();
    }
    public static String forge() {
        return forge.getSerializedName();
    }
}
