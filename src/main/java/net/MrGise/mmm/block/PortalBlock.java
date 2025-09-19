package net.MrGise.mmm.block;

import net.MrGise.mmm.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.RegistryObject;

public class PortalBlock extends Block {
    boolean debug = false;
    private RegistryObject<Item> Eye = ModItems.SKIRON;
    public static final BooleanProperty EYE = BooleanProperty.create("eye");

    public PortalBlock(Properties pProperties, RegistryObject<Item> Eye) {
        super(pProperties);
        this.Eye = Eye;
        this.registerDefaultState(this.defaultBlockState().setValue(EYE, false));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pHand == InteractionHand.MAIN_HAND) {
            ItemStack heldItem = pPlayer.getItemInHand(pHand);
            boolean hasEye = pState.getValue(EYE);

            if (pLevel.isClientSide) {
                if (debug) {
                    pPlayer.sendSystemMessage(Component.literal("Held: " + heldItem.getItem().toString()));
                    pPlayer.sendSystemMessage(Component.literal("Has eye: " + hasEye));
                }
            } else {
                // Insert eye if not present and player holds correct item
                if (!hasEye && heldItem.getItem() == Eye.get()) {
                    pLevel.setBlock(pPos, pState.setValue(EYE, true), 3);
                    pLevel.updateNeighborsAt(pPos, this);
                    if (!pPlayer.isCreative() && !pPlayer.isSpectator()) {
                        heldItem.shrink(1); // remove item from hand
                    }
                }
                // Remove eye if present and player hand is empty
                else if (hasEye && heldItem.isEmpty()) {
                    pLevel.setBlock(pPos, pState.setValue(EYE, false), 3);
                    pLevel.updateNeighborsAt(pPos, this);
                    ItemStack stack = new ItemStack(Eye.get());

                    if (!pPlayer.isCreative() && !pPlayer.isSpectator()) {
                        boolean added = pPlayer.getInventory().add(stack);
                        if (!added) {
                            pPlayer.drop(stack, false);
                        }
                    }
                }
            }

            return InteractionResult.SUCCESS;
        }

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return state.getValue(EYE) ? 8 : 0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(EYE);
    }

}