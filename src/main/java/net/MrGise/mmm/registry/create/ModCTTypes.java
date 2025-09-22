package net.MrGise.mmm.registry.create;

import com.simibubi.create.foundation.block.connected.CTType;
import com.simibubi.create.foundation.block.connected.ConnectedTextureBehaviour;
import net.MrGise.mmm.MMM;
import net.createmod.catnip.lang.Lang;
import net.minecraft.resources.ResourceLocation;

public enum ModCTTypes implements CTType {

    SUPERDIRECTIONAL(8, ConnectedTextureBehaviour.ContextRequirement.builder().all().build()) {
        @Override
        public int getTextureIndex(ConnectedTextureBehaviour.CTContext c) {
            int tileX = 0, tileY = 0;
            int borders = (!c.up ? 1 : 0) + (!c.down ? 1 : 0) + (!c.left ? 1 : 0) + (!c.right ? 1 : 0);

            if (c.up)
                tileX++;
            if (c.down)
                tileX += 2;
            if (c.left)
                tileY++;
            if (c.right)
                tileY += 2;

            if (borders == 0) {
                if (c.topRight)
                    tileX++;
                if (c.topLeft)
                    tileX += 2;
                if (c.bottomRight)
                    tileY += 2;
                if (c.bottomLeft)
                    tileY++;
            }

            if (borders == 1) {
                if (!c.right) {
                    if (c.topLeft || c.bottomLeft) {
                        tileY = 4;
                        tileX = -1 + (c.bottomLeft ? 1 : 0) + (c.topLeft ? 1 : 0) * 2;
                    }
                }
                if (!c.left) {
                    if (c.topRight || c.bottomRight) {
                        tileY = 5;
                        tileX = -1 + (c.bottomRight ? 1 : 0) + (c.topRight ? 1 : 0) * 2;
                    }
                }
                if (!c.down) {
                    if (c.topLeft || c.topRight) {
                        tileY = 6;
                        tileX = -1 + (c.topLeft ? 1 : 0) + (c.topRight ? 1 : 0) * 2;
                    }
                }
                if (!c.up) {
                    if (c.bottomLeft || c.bottomRight) {
                        tileY = 7;
                        tileX = -1 + (c.bottomLeft ? 1 : 0) + (c.bottomRight ? 1 : 0) * 2;
                    }
                }
            }

            if (borders == 2) {
                if ((c.up && c.left && c.topLeft) || (c.down && c.left && c.bottomLeft)
                        || (c.up && c.right && c.topRight) || (c.down && c.right && c.bottomRight))
                    tileX += 3;
            }

            return tileX + 8 * tileY;
        }
    };

    private final ResourceLocation id;
    private final int sheetSize;
    private final ConnectedTextureBehaviour.ContextRequirement contextRequirement;

    ModCTTypes(int sheetSize, ConnectedTextureBehaviour.ContextRequirement contextRequirement) {
        this.id = new ResourceLocation(MMM.MOD_ID, Lang.asId(name()));
        this.sheetSize = sheetSize;
        this.contextRequirement = contextRequirement;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public int getSheetSize() {
        return sheetSize;
    }

    @Override
    public ConnectedTextureBehaviour.ContextRequirement getContextRequirement() {
        return contextRequirement;
    }
}
