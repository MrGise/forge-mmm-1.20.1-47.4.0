package net.MrGise.mmm.network.compat;

import net.MrGise.mmm.screen.bowyery_table.BowyeryTableMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
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

    public void handle(Supplier<NetworkEvent.Context> contect) {
        NetworkEvent.Context ctx = contect.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player == null) return;
            if (!(player.containerMenu instanceof BowyeryTableMenu menu)) return;

            for (int i = 0; i < slots.size(); i++) {
                ItemStack stack = player.getInventory().getItem(slots.get(i));
                if (stack.isEmpty()) continue;

                ItemStack existing = menu.getSlot(i).getItem();
                if (existing.isEmpty()) {
                    menu.getSlot(i).set(stack.split(1));
                } else if (existing.getCount() < existing.getMaxStackSize()
                        && ItemStack.isSameItemSameTags(existing, stack)) {
                    existing.grow(1);
                    stack.shrink(1);
                }
            }
        });
        ctx.setPacketHandled(true);
    }
}
