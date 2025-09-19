package net.MrGise.mmm.registry;

import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.SimpleCTBehaviour;
import net.MrGise.mmm.block.PortalBlock;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ModCTBehaviour {
    public static class PortalCTBehaviour extends SimpleCTBehaviour {
        private CTSpriteShiftEntry shiftOff;
        private CTSpriteShiftEntry shiftOn;

        public PortalCTBehaviour(CTSpriteShiftEntry shiftOff, CTSpriteShiftEntry shiftOn) {
            super(shiftOff);
            this.shiftOff = shiftOff;
            this.shiftOn = shiftOn;
        }

        @Override
        public CTSpriteShiftEntry getShift(BlockState state, Direction direction, @Nullable TextureAtlasSprite sprite) {
            return state.getValue(PortalBlock.EYE)
                    ? shiftOn
                    : shiftOff;
        }
    }
}
