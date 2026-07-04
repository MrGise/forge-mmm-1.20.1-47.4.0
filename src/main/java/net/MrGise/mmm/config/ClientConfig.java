package net.MrGise.mmm.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    //Values
    public static final ForgeConfigSpec.IntValue BOWL_YROT_OFFSET;
    public static final ForgeConfigSpec.IntValue BOWL_XROT_OFFSET;
    public static final ForgeConfigSpec.IntValue BOWL_ZROT_OFFSET;

    static {
        BUILDER.push("MMM Client Configuration");

        BOWL_YROT_OFFSET = BUILDER.defineInRange("Bowl renderer Y rotation offset", 0, 0, 360);
        BOWL_XROT_OFFSET = BUILDER.defineInRange("Bowl renderer X rotation offset", 0, 0, 360);
        BOWL_ZROT_OFFSET = BUILDER.defineInRange("Bowl renderer Z rotation offset", 0, 0, 360);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
