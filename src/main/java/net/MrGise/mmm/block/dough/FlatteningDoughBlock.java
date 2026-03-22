package net.MrGise.mmm.block.dough;

import net.MrGise.mmm.registry.content.ModBlocks;
import net.MrGise.mmm.registry.variables.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FlatteningDoughBlock extends Block {
    public enum Shape implements StringRepresentable {
        FLATTENING,
        PIZZA,
        FLAT;

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase();
        }
    }

    public static final EnumProperty<Shape> SHAPE = EnumProperty.create("shape", Shape.class);

    public FlatteningDoughBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(SHAPE, Shape.FLATTENING));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SHAPE);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.is(ModTags.Items.HOLEABLE) && (state.getValue(SHAPE) == Shape.FLAT) && !player.isShiftKeyDown()) {
            level.setBlock(pos, ModBlocks.UNCOOKED_MATZA.get().defaultBlockState().setValue(UncookedMatzaBlock.FACING, player.getDirection()), 11);
            level.playSound(player, pos, SoundEvents.WOOL_HIT, SoundSource.BLOCKS, 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(SHAPE)) {
            case FLATTENING -> Block.box(1.0d, 0.0d, 1.0d, 15.0d, 2.0d, 15.0d);
            case PIZZA -> Block.box(0.0d, 0.0d, 0.0d, 16.0d, 2.0d, 16.0d);
            case FLAT -> Block.box(0.0d, 0.0d, 0.0d, 16.0d, 1.0d, 16.0d);
        };
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP)) {
            level.destroyBlock(pos, true);
        }
    }
}
