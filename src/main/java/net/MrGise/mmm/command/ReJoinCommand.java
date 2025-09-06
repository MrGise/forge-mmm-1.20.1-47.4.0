package net.MrGise.mmm.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ReJoinCommand {
    public ReJoinCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("rejoin")
                .requires(commandSourceStack -> commandSourceStack.hasPermission(2)).executes(this :: execute));
    }

    private int execute(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        boolean notFirstTime = player.getPersistentData().getBoolean("mmm.first");

        if(notFirstTime) {
            player.getPersistentData().remove("mmm.first");
            context.getSource().sendSuccess(() -> Component.literal("mmm.first has been removed for player: " + player.getName().getString()), false);
            return 1;
        } else {
            context.getSource().sendFailure(Component.literal("mmm.first has already been removed for: " + player.getName().getString()));
            return -1;
        }
    }
}
