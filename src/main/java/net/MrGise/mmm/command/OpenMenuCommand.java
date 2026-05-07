package net.MrGise.mmm.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.MrGise.mmm.network.ModNetwork;
import net.MrGise.mmm.network.OpenScreenPacket;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;

import java.awt.*;

public class OpenMenuCommand {
    public OpenMenuCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("open_menu")
                .then(Commands.literal("mmm:race_selection")
                        .then(Commands.argument("target", EntityArgument.player())
                                .requires(cs -> cs.hasPermission(Commands.LEVEL_GAMEMASTERS))
                                .executes(this::execute))));
    }

    private int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = EntityArgument.getPlayer(context, "target");

        ModNetwork.sendToPlayer(player, new OpenScreenPacket("race_selection"));

        return 1;
    }
}
