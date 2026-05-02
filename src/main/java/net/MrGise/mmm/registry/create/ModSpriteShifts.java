package net.MrGise.mmm.registry.create;

import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import net.MrGise.floating.resource.texture.CTTypeGetter;
import net.MrGise.mmm.MMM;

// CTSpriteShifts for the mod
public class ModSpriteShifts {
    private static final CTTypeGetter getter = new CTTypeGetter(MMM.MOD_ID);

    public static CTSpriteShiftEntry EXAMPLE = getter.all("block/example_connection");

    public static CTSpriteShiftEntry CONNECTING_PORTAL_OFF = getter.all("block/connecting_portal_block_off");
    public static CTSpriteShiftEntry CONNECTING_PORTAL_ON = getter.all("block/connecting_portal_block_on");
}
