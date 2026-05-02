package net.MrGise.floating.resource.texture;

import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import net.minecraft.resources.ResourceLocation;

import static com.simibubi.create.foundation.block.connected.CTSpriteShifter.getCT;
import static net.MrGise.floating.helper.Methods.*;

public class CTTypeGetter {
    private final String modid;
    public CTTypeGetter(String modid) {
        this.modid = modid;
    }

    public CTSpriteShiftEntry all(String name) {
        return getCT(FloatingCTTypes.SUPERDIRECTIONAL, loc(name), loc(name + "_connected"));
    }

    public CTSpriteShiftEntry omni(String name) {
        return getCT(AllCTTypes.OMNIDIRECTIONAL, loc(name), loc(name + "_connected"));
    }

    public CTSpriteShiftEntry horiz(String name) {
        return getCT(AllCTTypes.HORIZONTAL, loc(name), loc(name + "_connected"));
    }

    public CTSpriteShiftEntry verti(String name) {
        return getCT(AllCTTypes.VERTICAL, loc(name), loc(name + "_connected"));
    }

    private ResourceLocation loc(String name) {
        return nAp(modid, name);
    }
}
