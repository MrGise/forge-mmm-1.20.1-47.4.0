package net.MrGise.mmm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
//
public class CustomGrass extends BushBlock implements BonemealableBlock {

    public enum Length implements StringRepresentable {
        SHORT, BOTTOM, TOP;
        @Override public String getSerializedName() { return name().toLowerCase(); }
    }

    public static final EnumProperty<Length> LENGTH = EnumProperty.create("length", Length.class);

    private final RegistryObject<Block>[] plantableOn;

    @SafeVarargs
    public CustomGrass(Properties props, RegistryObject<Block>... plantableOn) {
        super(props);
        this.plantableOn = plantableOn;
        // default state: short
        this.registerDefaultState(this.stateDefinition.any().setValue(LENGTH, Length.SHORT));
    }

    /* ---------- state & placement ---------- */

    @Override
    public ItemStack getCloneItemStack(BlockGetter getter, BlockPos pos, BlockState state) {
        if (state.getValue(LENGTH) == Length.SHORT) {
            return super.getCloneItemStack(getter, pos, state);
        } else {
            ItemStack stack = new ItemStack(this);
            CompoundTag tag = new CompoundTag();

            tag.putBoolean("long", true);

            stack.setTag(tag);

            return stack;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> b) {
        b.add(LENGTH);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return isPlantable(state);
    }

    private boolean isPlantable(BlockState below) {
        if (plantableOn == null || plantableOn.length == 0) return super.mayPlaceOn(below, null, null);
        return Arrays.stream(plantableOn).anyMatch(ro -> below.is(ro.get()));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        // Always place short variant if it can survive
        BlockState base = this.defaultBlockState().setValue(LENGTH, Length.SHORT);
        return base.canSurvive(ctx.getLevel(), ctx.getClickedPos()) ? base : null;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Length len = state.getValue(LENGTH);
        if (len == Length.TOP) {
            BlockState below = level.getBlockState(pos.below());
            return below.is(this) && below.getValue(LENGTH) == Length.BOTTOM;
        }
        // SHORT or BOTTOM need valid soil
        return mayPlaceOn(level.getBlockState(pos.below()), level, pos.below());
    }

    /* ---------- neighbor syncing (like DoublePlant) ---------- */

    @Override
    @SuppressWarnings("deprecation")
    public BlockState updateShape(BlockState state, Direction dir, BlockState neighbor, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        Length len = state.getValue(LENGTH);

        if (len == Length.TOP && dir == Direction.DOWN) {
            // Top needs a bottom below
            if (!neighbor.is(this) || neighbor.getValue(LENGTH) != Length.BOTTOM) {
                return airOrShort(level, pos); // break top; bottom might already be gone
            }
        } else if (len == Length.BOTTOM && dir == Direction.UP) {
            // Bottom needs a top above (if tall)
            if (!neighbor.is(this) || neighbor.getValue(LENGTH) != Length.TOP) {
                // If bottom lost its top, revert to short instead of vanishing
                return this.defaultBlockState().setValue(LENGTH, Length.SHORT);
            }
        }

        return super.updateShape(state, dir, neighbor, level, pos, neighborPos);
    }

    private BlockState airOrShort(LevelAccessor level, BlockPos pos) {
        // Top with missing bottom should just vanish (like tall grass top)
        return Blocks.AIR.defaultBlockState();
    }

    /* ---------- bonemeal behavior ---------- */

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
        // Only bonemeal SHORT to grow tall; need air above
        return state.getValue(LENGTH) == Length.SHORT && level.isEmptyBlock(pos.above());
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true; // always succeed if valid target
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        if (state.getValue(LENGTH) == Length.SHORT && level.isEmptyBlock(pos.above())) {
            // place bottom at pos and top above
            level.setBlock(pos, state.setValue(LENGTH, Length.BOTTOM), Block.UPDATE_ALL);
            level.setBlock(pos.above(), this.defaultBlockState().setValue(LENGTH, Length.TOP), Block.UPDATE_ALL);
        }
    }

    /* ---------- shapes ---------- */

    private static final VoxelShape SHAPE_SHORT  = Shapes.box(2/16.0, 0.0, 2/16.0, 14/16.0, 14/16.0, 14/16.0);
    private static final VoxelShape SHAPE_TALL   = Shapes.box(0.0, 0.0, 0.0, 16/16.0, 16/16.0, 16/16.0);
    private static final VoxelShape SHAPE_TOP    = Shapes.box(0.0, 0.0, 0.0, 16/16.0, 16/16.0, 16/16.0);

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        return switch (state.getValue(LENGTH)) {
            case SHORT  -> SHAPE_SHORT;
            case BOTTOM -> SHAPE_TALL;
            case TOP    -> SHAPE_TOP;
        };
    }

    /* ---------- cleanup when broken ---------- */

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        Length len = state.getValue(LENGTH);

        if (len == Length.BOTTOM) {
            BlockPos topPos = pos.above();
            BlockState top = level.getBlockState(topPos);
            if (top.is(this) && top.getValue(LENGTH) == Length.TOP) {
                // Destroy top silently (no drops) when bottom is broken
                level.destroyBlock(topPos, false, player);
            }
        } else if (len == Length.TOP) {
            BlockPos bottomPos = pos.below();
            BlockState bottom = level.getBlockState(bottomPos);
            if (bottom.is(this) && bottom.getValue(LENGTH) == Length.BOTTOM) {
                // Break bottom if player breaks the top
                level.destroyBlock(bottomPos, !player.getMainHandItem().is(Items.SHEARS), player);
            }
        }

        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
        ItemStack tool = pParams.getParameter(LootContextParams.TOOL);

        // Only drop when shears or silk touch are used
        if (tool != null && (tool.is(Items.SHEARS) || tool.getEnchantmentLevel(Enchantments.SILK_TOUCH) > 0)) {
            return Collections.singletonList(new ItemStack(this));
        }

        return super.getDrops(pState, pParams);
    }

}
