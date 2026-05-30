package net.MrGise.mmm.block.entity;

import net.MrGise.mmm.registry.content.ModBlockEntities;
import net.MrGise.mmm.registry.variables.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BowlBlockEntity extends BlockEntity {
    private SimpleContainer storedItems = new SimpleContainer(16);

    public enum ClickResult {
        INSERT, TAKE, POUR, TAKE_FLUID, MIX
    }

    public BowlBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BOWL_BE.get(), pos, state);
    }

    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

    private final FluidTank FLUID_TANK = createFluidTank();

    private FluidTank createFluidTank() {
        return new FluidTank(2000) {
            @Override
            protected void onContentsChanged() {
                setChanged();
                if (level != null && !level.isClientSide) {
                    level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
                }
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return true;
            }
        };
    }

    public FluidStack getFluid() {
        return FLUID_TANK.getFluid();
    }

    public void inputFluid(Player player, InteractionHand hand, int drain) {
        inputFluid(player.getItemInHand(hand), drain);
    }

    public void inputFluid(Player player, InteractionHand hand) {
        inputFluid(player.getItemInHand(hand), getDrain(player.getItemInHand(hand), 1000));
    }

    public void inputFluid(ItemStack stack, int drain) {
        stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(handler -> {
            int toDrain = Math.min(FLUID_TANK.getSpace(), drain);

            FluidStack simulated = handler.drain(toDrain, IFluidHandler.FluidAction.SIMULATE);

            int accepted = FLUID_TANK.fill(simulated, IFluidHandler.FluidAction.SIMULATE);

            if (accepted > 0) {
                FluidStack drained = handler.drain(accepted, IFluidHandler.FluidAction.EXECUTE);

                FLUID_TANK.fill(drained, IFluidHandler.FluidAction.EXECUTE);
            }
        });
    }

    public int getDrain(ItemStack stack, int maxDrain) {
        if (stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent()) {
            IFluidHandlerItem handler = stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);
            return Math.min(handler.drain(maxDrain, IFluidHandler.FluidAction.SIMULATE).getAmount(), Math.min(maxDrain, this.FLUID_TANK.getSpace()));
        } else {
            throw new IllegalArgumentException("BowlBlockEntity.getDrain was called with an item that isn't capable of handling fluid");
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.FLUID_HANDLER) {
            return lazyFluidHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    public SimpleContainer storedItems() {
        return this.storedItems;
    }

    public ClickResult getClickResult(ItemStack stack) {
        if (stack.isEmpty()) {
            return ClickResult.TAKE;
        }
        if (stack.is(ModTags.Items.MIXER_TOOL)) {
            return ClickResult.MIX;
        }
        if (stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent()) {

        }
        return stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).map(handler -> {
            int accepted = handler.fill(
                    new FluidStack(Fluids.WATER, 1000),
                    IFluidHandler.FluidAction.SIMULATE
            );

            return accepted > 0 ? ClickResult.TAKE_FLUID : ClickResult.POUR;
        }).orElse(ClickResult.INSERT);
    }

    public ItemStack addItem(ItemStack stack) {
        ItemStack remainder = this.storedItems.addItem(stack);

        setChanged();

        return remainder;
    }

    public void addItem(Player player, InteractionHand hand) {
        addItem(player.getItemInHand(hand));
    }

    public void takeItem(Player player, InteractionHand hand) {
        int containerSize = getLastContainerIndex();
        ItemStack extracted = storedItems().removeItem(containerSize, storedItems().getItem(containerSize).getCount());

        if (!player.addItem(extracted)) {
            player.drop(extracted, false);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);

        ContainerHelper.saveAllItems(tag, this.storedItems.items);

        tag = FLUID_TANK.writeToNBT(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);

        this.storedItems.clearContent();

        ContainerHelper.loadAllItems(tag, this.storedItems.items);

        FLUID_TANK.readFromNBT(tag);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.lazyFluidHandler = LazyOptional.of(() -> FLUID_TANK);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.lazyFluidHandler.invalidate();
    }

    public void drops() {
        Containers.dropContents(this.level, this.worldPosition, this.storedItems);
    }

    private int getLastContainerIndex() {
        return storedItems().getContainerSize() - 1;
    }
}