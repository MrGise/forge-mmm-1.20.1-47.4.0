package net.MrGise.mmm.resource;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncAllKnowingPacket {
    private final boolean value;

    public SyncAllKnowingPacket(boolean value) {
        this.value = value;
    }

    public SyncAllKnowingPacket(FriendlyByteBuf buf) {
        this.value = buf.readBoolean();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(value);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ClientKnowledgeData.ALL_KNOWING = value;
        });
        ctx.get().setPacketHandled(true);
    }
}