package net.MrGise.mmm.network.compat;

import net.MrGise.mmm.screen.bowyery_table.BowyeryTableMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class TransferBowyeryRecipePacket {
    private final List<Integer> slots;

    public TransferBowyeryRecipePacket(List<Integer> slots) {
        this.slots = slots;
    }

    public TransferBowyeryRecipePacket(FriendlyByteBuf buf) {
        this.slots = buf.readList(FriendlyByteBuf::readInt);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeCollection(slots, FriendlyByteBuf::writeInt);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player == null) return;
            if (!(player.containerMenu instanceof BowyeryTableMenu menu)) return;

            // slots list is now [menuSlot, inventorySlot, count, menuSlot, inventorySlot, count, ...]
            for (int i = 0; i < slots.size() - 2; i += 3) {
                int menuSlot = slots.get(i);
                int inventorySlot = slots.get(i + 1);
                int count = slots.get(i + 2);

                ItemStack source = player.getInventory().getItem(inventorySlot);
                if (source.isEmpty()) continue;

                ItemStack menuStack = menu.getSlot(menuSlot).getItem();
                int toTake = Math.min(count, source.getCount());

                if (menuStack.isEmpty()) {
                    ItemStack toPlace = source.copy();
                    toPlace.setCount(toTake);
                    menu.getSlot(menuSlot).set(toPlace);
                    source.shrink(toTake);
                } else if (ItemStack.isSameItemSameTags(menuStack, source)) {
                    int space = menuStack.getMaxStackSize() - menuStack.getCount();
                    int adding = Math.min(space, toTake);
                    menuStack.grow(adding);
                    source.shrink(adding);
                }
            }

            menu.slotsChanged(menu.getSlot(0).container);
        });
        ctx.setPacketHandled(true);
    }
}
