package net.MrGise.mmm.block.entity;

import net.MrGise.mmm.recipe.BowlRecipe;
import net.MrGise.mmm.registry.content.ModBlockEntities;
import net.MrGise.mmm.registry.variables.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
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

import java.util.List;
import java.util.Optional;

public class BowlBlockEntity extends BlockEntity {
    public static final int MAX_WEIGHT = 128;
    private final NonNullList<ItemStack> storedItems = NonNullList.create();

    private BowlRecipe currentRecipe;

    public enum ClickResult {
        INSERT, TAKE, POUR, TAKE_FLUID, MIX
    }

    public BowlBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BOWL_BE.get(), pos, state);
    }


    public static int getSingleWeight(ItemStack stack) {
        if (stack.is(Items.BUNDLE)) {
            return BundleItem.BUNDLE_IN_BUNDLE_WEIGHT;
        }

        return 64 / stack.getMaxStackSize();
    }

    public static int getWeight(ItemStack stack) {
        return getSingleWeight(stack) * stack.getCount();
    }

    public int getContentWeight() {
        return storedItems.stream().mapToInt(BowlBlockEntity::getWeight).sum();
    }

    public int getFreeWeight() {
        return MAX_WEIGHT - this.getContentWeight();
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


    public BowlRecipe getRecipe() {
        return this.currentRecipe;
    }


    private int craftProgress = 0;
    public void startCrafting(Level level) {
        Optional<BowlRecipe> recipe = level.getRecipeManager().getAllRecipesFor(BowlRecipe.Type.INSTANCE)
                .stream().filter(r -> r.matches(this.storedItems(), this.getFluid()))
                .findFirst();

        if (recipe.isEmpty()) return;

        this.currentRecipe = recipe.get();
        this.craftProgress = 1;

        setChanged();
    }

    public void progressCrafting(Level level) {
        if (getRecipe() == null || !getRecipe().matches(this.storedItems, this.getFluid())) {
            cancelCrafting();
            return;
        }

        this.craftProgress ++;

        if (this.craftProgress >= getRecipe().getCraftLength()) {
            completeCrafting(level);
        }

        setChanged();
    }

    private void cancelCrafting() {
        this.craftProgress = 0;
        this.currentRecipe = null;
        setChanged();
    }

    private void completeCrafting(Level level) {
        if (getRecipe() == null) return;

        for (Ingredient ingredient : getRecipe().getIngredients()) {
            for (int i = 0; i < this.storedItems.size(); i++) {
                if (ingredient.test(this.storedItems.get(i))) {
                    this.storedItems.get(i).shrink(1);
                    if (this.storedItems.get(i).isEmpty()) {
                        this.storedItems.remove(i);
                    }
                    break;
                }
            }
        }

        if (getRecipe().requiresFluid()) {
            this.FLUID_TANK.drain(getRecipe().getRequiredFluid().getRequiredAmount(), IFluidHandler.FluidAction.EXECUTE);
        }

        addItem(getRecipe().getResultItem(level.registryAccess()));

        cancelCrafting();
    }


    public boolean isCrafting() {
        return craftProgress > 0 && currentRecipe != null;
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

    public void inputFluid(ItemStack stack, int maxDrain) {
        stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(handler -> {
            int toDrain = Math.min(FLUID_TANK.getSpace(), maxDrain);

            FluidStack simulated = handler.drain(toDrain, IFluidHandler.FluidAction.SIMULATE);

            int accepted = FLUID_TANK.fill(simulated, IFluidHandler.FluidAction.SIMULATE);

            if (accepted > 0) {
                FluidStack drained = handler.drain(accepted, IFluidHandler.FluidAction.EXECUTE);

                FLUID_TANK.fill(drained, IFluidHandler.FluidAction.EXECUTE);
            }
        });
    }

    public void takeFluid(Player player, InteractionHand hand) {
        takeFluid(player.getItemInHand(hand), 1000);
    }

    public void takeFluid(ItemStack stack, int maxInput) {
        stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(handler -> {
            int accepted = handler.fill(this.FLUID_TANK.getFluid(), IFluidHandler.FluidAction.SIMULATE);

            if (accepted > 0) {
                int toDrain = Math.min(accepted, Math.min(FLUID_TANK.getSpace(), maxInput));
                handler.fill(this.FLUID_TANK.drain(accepted, IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
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

    public List<ItemStack> storedItems() {
        return this.storedItems;
    }

    public ClickResult getClickResult(ItemStack stack) {
        if (stack.isEmpty()) {
            return ClickResult.TAKE;
        }
        if (stack.is(ModTags.Items.MIXER_TOOL)) {
            return ClickResult.MIX;
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
        int freeWeight = getFreeWeight();

        int typeWeight = getSingleWeight(stack);
        int toInsert = Math.min(stack.getCount(), freeWeight / typeWeight);

        if (toInsert <= 0) {
            return stack;
        }

        ItemStack inserted = stack.copyWithCount(toInsert);

        for (ItemStack existing : this.storedItems) {
            if (ItemStack.isSameItemSameTags(existing, inserted)) {
                existing.grow(toInsert);
                stack.shrink(toInsert);
                setChanged();

                return stack;
            }
        }
        storedItems.add(inserted);
        stack.shrink(toInsert);
        setChanged();

        return stack;
    }

    public void addItem(Player player, InteractionHand hand) {
        addItem(player.getItemInHand(hand));
    }

    public void takeItem(Player player, InteractionHand hand) {
        ItemStack extracted = takeType();

        if (!player.addItem(extracted)) {
            player.drop(extracted, false);
        }

        setChanged();
    }

    public ItemStack takeType() {
        if (storedItems().isEmpty()) return ItemStack.EMPTY;

        ItemStack stack = storedItems.remove(0);

        setChanged();

        return stack;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);

        ListTag itemsTag = new ListTag();
        for (ItemStack stack : this.storedItems) {
            if (!stack.isEmpty()) {
                CompoundTag stackTag = new CompoundTag();
                stack.save(stackTag);
                itemsTag.add(stackTag);
            }
        }

        tag.put("Items", itemsTag);

        FLUID_TANK.writeToNBT(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);

        this.storedItems.clear();

        ListTag itemsTag = tag.getList("Items", Tag.TAG_COMPOUND);

        for (int i = 0; i < itemsTag.size(); i++) {
            this.storedItems.add(ItemStack.of(itemsTag.getCompound(i)));
        }

        FLUID_TANK.readFromNBT(tag);

        setChanged();
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

    @Override
    public void setChanged() {
        super.setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }
}