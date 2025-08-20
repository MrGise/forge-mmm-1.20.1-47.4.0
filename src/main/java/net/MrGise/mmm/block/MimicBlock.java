package net.MrGise.mmm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class MimicBlock extends HorizontalDirectionalBlock {

    private enum MimicForms implements StringRepresentable {
        NO_FORM,
        DEFORMED,
        CHEST,
        SHULKER_BOX;

        @Override
        public String getSerializedName() {
            return name().toLowerCase();
        }
    }
    public static final EnumProperty<MimicForms> FORM = EnumProperty.create("form", MimicForms.class);

    public MimicBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FORM, MimicForms.NO_FORM));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        ItemStack stack = pContext.getItemInHand();
        CompoundTag tag = stack.getTag();

        if (tag != null && tag.contains("form", Tag.TAG_STRING)) {
            String form = tag.getString("form");

            return switch (form) {
                case "chest" -> defaultBlockState().setValue(FORM, MimicForms.CHEST);
                case "shulker_box" -> defaultBlockState().setValue(FORM, MimicForms.SHULKER_BOX);
                default -> defaultBlockState().setValue(FORM, MimicForms.DEFORMED);
            };

        }

        return defaultBlockState();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FORM);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

        if (!pLevel.isClientSide && !pPlayer.isCreative() && !pPlayer.isSpectator()) {

            float from = pPlayer.getHealth();

            if (from >= 3) {
                pPlayer.setHealth(from - 3);

            } else {
                pPlayer.setHealth(0);
            }

        }
        pPlayer.playSound(SoundEvents.PLAYER_HURT);
        pPlayer.animateHurt(0.0f);

        return InteractionResult.SUCCESS;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (pState.getValue(FORM)){
            case NO_FORM -> Block.box(1, 0, 1, 15, 12, 15);
            case CHEST -> Block.box(1, 0, 1, 15, 14, 15);
            default -> Block.box(0,0,0,16,16,16);
        };
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter world, BlockPos pos) {
        return switch (state.getValue(FORM)) {
            case NO_FORM, CHEST -> 1.0f;
            default -> 0.2f;
        };
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter world, BlockPos pos) {
        return switch (state.getValue(FORM)) {
            case NO_FORM, CHEST -> true;
            default -> false;
        };
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter world, BlockPos pos) {
        return getShape(state, world, pos, CollisionContext.empty());
    }

}
