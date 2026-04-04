package net.MrGise.mmm.network.compat;

import net.MrGise.mmm.screen.thingamajig.ThingamajigMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TransferThingamajigRecipePacket {
    private final int slot;

    public TransferThingamajigRecipePacket(int slot) {
        this.slot = slot;
    }

    public TransferThingamajigRecipePacket(FriendlyByteBuf buf) {
        this.slot = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(slot);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player == null) return;

            if (!(player.containerMenu instanceof ThingamajigMenu menu)) return;

            ItemStack stack = player.getInventory().getItem(slot);
            if (stack.isEmpty()) return;

            ItemStack existing = menu.getSlot(0).getItem();
            if (existing.isEmpty()) {
                menu.getSlot(0).set(stack.split(1));
            } else if (existing.getCount() < existing.getMaxStackSize()
                    && ItemStack.isSameItemSameTags(existing, stack)) {
                existing.grow(1);
                stack.shrink(1);
            }
            menu.blockEntity().setChanged();
        });
        ctx.setPacketHandled(true);
    }
}
