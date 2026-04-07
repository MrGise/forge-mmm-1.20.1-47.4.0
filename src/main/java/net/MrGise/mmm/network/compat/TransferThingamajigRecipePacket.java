package net.MrGise.mmm.network.compat;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.screen.thingamajig.ThingamajigMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TransferThingamajigRecipePacket {
    private final int slot;
    private final boolean max;

    public TransferThingamajigRecipePacket(int slot, boolean max) {
        this.slot = slot;
        this.max = max;
    }

    public TransferThingamajigRecipePacket(FriendlyByteBuf buf) {
        this.slot = buf.readInt();
        this.max = buf.readBoolean();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(slot);
        buf.writeBoolean(max);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player == null) return;

            if (!(player.containerMenu instanceof ThingamajigMenu menu)) return;

            ItemStack stack = player.getInventory().getItem(slot);
            if (stack.isEmpty()) return;

            ItemStack existing = menu.getSlot(36).getItem();

            int existingSize = existing.getCount();

            int toTransfer = max ? stack.getCount() : 1;

            toTransfer = existingSize + toTransfer > existing.getMaxStackSize() ? 64 - existingSize : toTransfer;

            MMM.LOGGER.info("Crafting {} items", toTransfer);

            if (existingSize + toTransfer <= existing.getMaxStackSize()) {
                if (existing.isEmpty()) {
                    menu.getSlot(36).set(stack.split(toTransfer));
                } else if (ItemStack.isSameItemSameTags(existing, stack)) {
                    stack.shrink(toTransfer);
                    existing.grow(toTransfer);
                }
            }
            menu.blockEntity().setChanged();
        });
        ctx.setPacketHandled(true);
    }
}
