package net.MrGise.mmm.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class SetHomeCommand {
    public SetHomeCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("home").then(Commands.literal("set")
                .executes(this::execute)));
    }

    private int execute(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        BlockPos pos = player.blockPosition();
        String positionString = "(" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + ")";

        player.getPersistentData().putIntArray("mmm.homepos" ,
                new int[] {pos.getX(), pos.getY(), pos.getZ()});

        context.getSource().sendSuccess(() -> Component.literal("Set home of" + player.getName().getString() + positionString), true);

        return 1;
    }


}
