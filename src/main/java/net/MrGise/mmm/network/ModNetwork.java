package net.MrGise.mmm.network;

import net.MrGise.mmm.network.compat.TransferBowyeryRecipePacket;
import net.MrGise.mmm.network.compat.TransferThingamajigRecipePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModNetwork {
    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("mmm", "main"),
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
                TransferThingamajigRecipePacket.class,
                TransferThingamajigRecipePacket::encode,
                TransferThingamajigRecipePacket::new,
                TransferThingamajigRecipePacket::handle
        );
        CHANNEL.registerMessage(
                packetId++,
                TransferBowyeryRecipePacket.class,
                TransferBowyeryRecipePacket::encode,
                TransferBowyeryRecipePacket::new,
                TransferBowyeryRecipePacket::handle
        );
    }
}
