package net.MrGise.mmm.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.MrGise.mmm.resource.ModNetwork;
import net.MrGise.mmm.resource.SyncAllKnowingPacket;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;

public class ToggleKnowCommand {
    public ToggleKnowCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("know")
                        .then(Commands.literal("toggle").executes(this::toggle))
                        .then(Commands.literal("on").executes(this::on))
                        .then(Commands.literal("off").executes(this::off))
                        .then(Commands.literal("get").executes(this::get)));
    }

    private int toggle(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player.getPersistentData().contains("all_knowing")) {
            boolean current = player.getPersistentData().getBoolean("all_knowing");
            player.getPersistentData().putBoolean("all_knowing", !current);

            ModNetwork.CHANNEL.send(
                    PacketDistributor.PLAYER.with(() -> player),
                    new SyncAllKnowingPacket(true)
            );

            context.getSource().sendSuccess(() -> Component.literal(player.getName().getString()).append(Component.translatable("command.mmm.know." + (!current ? "get" : "lose"))), false);
            return 1;
        } else {
            player.getPersistentData().putBoolean("all_knowing", true);

            ModNetwork.CHANNEL.send(
                    PacketDistributor.PLAYER.with(() -> player),
                    new SyncAllKnowingPacket(true)
            );

            context.getSource().sendSuccess(() -> Component.literal(player.getName().getString()).append(Component.translatable("command.mmm.know.get_first")), false);
            return 1;
        }
    }

    private int on(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        player.getPersistentData().putBoolean("all_knowing", true);

        ModNetwork.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> player),
                new SyncAllKnowingPacket(true)
        );

        context.getSource().sendSuccess(() -> Component.literal(player.getName().getString()).append(Component.translatable("command.mmm.know.get")), false);
        return 1;
    }

    private int off(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        player.getPersistentData().putBoolean("all_knowing", false);

        ModNetwork.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> player),
                new SyncAllKnowingPacket(true)
        );

        context.getSource().sendSuccess(() -> Component.literal(player.getName().getString()).append(Component.translatable("command.mmm.know.lose")), false);
        return 1;
    }

    private int get(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        player.sendSystemMessage(player.getPersistentData().getBoolean("all_knowing") ? Component.translatable("command.mmm.know.has") : Component.translatable("command.mmm.know.hasnt"));

        return 1;
    }
}
