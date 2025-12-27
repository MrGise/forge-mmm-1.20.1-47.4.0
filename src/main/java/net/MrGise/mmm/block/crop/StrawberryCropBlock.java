package net.MrGise.mmm.block.crop;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.registry.front.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StrawberryCropBlock extends AccessibleCropBlock {
    public static final int MAX_AGE = 6;
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 6);
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D)};
    private static final Logger log = LoggerFactory.getLogger(StrawberryCropBlock.class);

    public StrawberryCropBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.STRAWBERRY_SEEDS.get();
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE_BY_AGE[this.getAge(pState)];
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack heldItem = player.getItemInHand(hand);

        // If holding bonemeal, let vanilla CropBlock handle it
        if (heldItem.is(Items.BONE_MEAL)) {
            return super.use(state, level, pos, player, hand, hit);
        }

        // Handle harvesting with empty hand
        if (!level.isClientSide && hand == InteractionHand.MAIN_HAND && heldItem.isEmpty()) {
            int currentAge = state.getValue(AGE);

            if (currentAge >= this.getMaxAge()) {
                // Drop from custom loot table
                ResourceLocation lootTable = new ResourceLocation(MMM.MOD_ID, "blocks/strawberry_rclick");
                LootTable table = level.getServer().getLootData().getLootTable(lootTable);

                LootParams.Builder params = new LootParams.Builder((ServerLevel) level)
                        .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
                        .withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
                        .withOptionalParameter(LootContextParams.BLOCK_ENTITY, level.getBlockEntity(pos))
                        .withParameter(LootContextParams.BLOCK_STATE, state)
                        .withParameter(LootContextParams.THIS_ENTITY, player);

                table.getRandomItems(params.create(LootContextParamSets.BLOCK))
                        .forEach(stack -> Block.popResource(level, pos, stack));

                // Reduce the age by 3 (minimum 0)
                int newAge = Math.max(0, currentAge - 3);
                level.setBlock(pos, state.setValue(AGE, newAge), 2);

                ((ServerLevel) level).levelEvent(2001, pos, Block.getId(state));
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

}
