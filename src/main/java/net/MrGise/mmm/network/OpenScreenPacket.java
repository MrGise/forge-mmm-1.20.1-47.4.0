package net.MrGise.mmm.network;

import net.MrGise.mmm.screen.race_selection.RaceSelectionScreen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenScreenPacket {
    public final String screenId;

    public OpenScreenPacket(String screenId) {
        this.screenId = screenId;
    }

    public static void handle(OpenScreenPacket message, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();

            if (message.screenId.equals("race_selection")) {
                mc.setScreen(new RaceSelectionScreen());
            }
        });

        context.get().setPacketHandled(true);
    }
}
