package net.MrGise.mmm.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class FindHomeCommand {
    public FindHomeCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("home").then(Commands.literal("find")
                .executes(this::execute)));
    }

    private int execute(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        boolean hasHomepos = player.getPersistentData().getIntArray("mmm.homepos").length != 0;

        if(hasHomepos) {
            int[] playerPos = player.getPersistentData().getIntArray("mmm.homepos");
            String positionString = "(" + playerPos[0] + ", " + playerPos[1] + ", " + playerPos[2] + ")";

            player.sendSystemMessage(Component.literal("Your home is at " + positionString));

            context.getSource().sendSuccess(() -> Component.literal("Player " + player.getName().getString() + " has found his Home!"), false);
            return 1;
        } else {
            context.getSource().sendFailure(Component.literal("No Home Position has been set for player" + player.getName().getString()));
            return -1;
        }
    }

}
