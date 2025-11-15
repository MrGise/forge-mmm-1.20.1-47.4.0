package net.MrGise.mmm.registry.create;

import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.SimpleCTBehaviour;
import net.MrGise.mmm.block.PortalBlock;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

    // Custom CTBehaviour for the mod
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

    public static class OpenCTBehaviour extends SimpleCTBehaviour {
        public OpenCTBehaviour(CTSpriteShiftEntry shift) {
            super(shift);
        }

        public boolean testConnection(BlockAndTintGetter reader, BlockPos currentPos, BlockState connectiveCurrentState,
                                        Direction textureSide, final Direction horizontal, final Direction vertical, int sh, int sv) {
            BlockState trueCurrentState = reader.getBlockState(currentPos);
            BlockPos targetPos = currentPos.relative(horizontal, sh)
                    .relative(vertical, sv);
            BlockState connectiveTargetState =
                    getCTBlockState(reader, trueCurrentState, textureSide, currentPos, targetPos);
            return connectsTo(connectiveCurrentState, connectiveTargetState, reader, currentPos, targetPos, textureSide,
                    sh == 0 ? null : sh == -1 ? horizontal.getOpposite() : horizontal,
                    sv == 0 ? null : sv == -1 ? vertical.getOpposite() : vertical);
        }
    }

    public static class NoodleCTBehaviour extends SimpleCTBehaviour {
        public NoodleCTBehaviour(CTSpriteShiftEntry shift) {
            super(shift);
        }

        @Override
        public boolean connectsTo(BlockState state, BlockState other, BlockAndTintGetter reader, BlockPos pos, BlockPos otherPos, Direction face) {
            return state.getBlock() == other.getBlock(); // connect if same block registered in the entry
        }

        @Override
        public boolean connectsTo(BlockState state, BlockState other, BlockAndTintGetter reader, BlockPos pos, BlockPos otherPos, Direction face, Direction primaryOffset, Direction secondaryOffset) {
            return connectsTo(state, other, reader, pos, otherPos, face);
        }
    }

    public static class NoodlePortalCTBehaviour extends SimpleCTBehaviour {
        private CTSpriteShiftEntry shiftOff;
        private CTSpriteShiftEntry shiftOn;

        public NoodlePortalCTBehaviour(CTSpriteShiftEntry shiftOff, CTSpriteShiftEntry shiftOn) {
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

        @Override
        public boolean connectsTo(BlockState state, BlockState other, BlockAndTintGetter reader, BlockPos pos, BlockPos otherPos, Direction face) {
            return state.getBlock() == other.getBlock(); // connect if same block registered in the entry
        }

        @Override
        public boolean connectsTo(BlockState state, BlockState other, BlockAndTintGetter reader, BlockPos pos, BlockPos otherPos, Direction face, Direction primaryOffset, Direction secondaryOffset) {
            return connectsTo(state, other, reader, pos, otherPos, face);
        }
    }
}
