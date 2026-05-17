package net.MrGise.mmm.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import static net.MrGise.floating.helper.Methods.*;

public class ModNetwork {
    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            mmm("main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;

    public static void register() {
        CHANNEL.registerMessage(
                packetId++,
                SyncAllKnowingPacket.class,
                SyncAllKnowingPacket::encode,
                SyncAllKnowingPacket::new,
                SyncAllKnowingPacket::handle
        );
        CHANNEL.registerMessage(
                packetId++,
                OpenScreenPacket.class,
                (msg, buf) -> buf.writeUtf(msg.screenId),
                buf -> new OpenScreenPacket(buf.readUtf()),
                OpenScreenPacket::handle
        );
    }

    public static void sendToPlayer(ServerPlayer player, Object message) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
