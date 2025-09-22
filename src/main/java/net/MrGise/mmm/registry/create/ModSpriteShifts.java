package net.MrGise.mmm.registry.create;

import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import net.MrGise.mmm.MMM;
import net.minecraft.resources.ResourceLocation;

import static com.simibubi.create.foundation.block.connected.CTSpriteShifter.getCT;

public class ModSpriteShifts {

    public static CTSpriteShiftEntry EXAMPLE = superdir("block/example_connection");

    public static CTSpriteShiftEntry CONNECTING_PORTAL_OFF = superdir("block/connecting_portal_block_off");
    public static CTSpriteShiftEntry CONNECTING_PORTAL_ON = superdir("block/connecting_portal_block_on");

    public static CTSpriteShiftEntry superdir(String name) {
        return getCT(ModCTTypes.SUPERDIRECTIONAL, modLoc(name), modLoc(name + "_connected"));
    }

    public static CTSpriteShiftEntry omni(String name) {
        return getCT(AllCTTypes.OMNIDIRECTIONAL, modLoc(name), modLoc(name + "_connected"));
    }

    public static CTSpriteShiftEntry horizontal(String name) {
        return getCT(AllCTTypes.HORIZONTAL, modLoc(name), modLoc(name + "_connected"));
    }

    public static CTSpriteShiftEntry vertical(String name) {
        return getCT(AllCTTypes.VERTICAL, modLoc(name), modLoc(name + "_connected"));
    }

    private static ResourceLocation modLoc(String name) {
        return new ResourceLocation(MMM.MOD_ID, name);
    }
}
