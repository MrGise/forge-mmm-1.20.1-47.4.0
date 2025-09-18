package net.MrGise.mmm.registry;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.MrGise.mmm.MMM;
import net.MrGise.mmm.util.BuilderHelper;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;

public class ModCreateBlocks {
    private static final CreateRegistrate registrate = MMM.REGISTRATE;

    public static final BlockEntry<Block> EXAMPLE_CONNECTION = registrate.block("example_connection", Block::new)
            .transform(BuilderHelper.emptyBlock(() -> ModSpriteShifts.EXAMPLE, BlockBehaviour.Properties.of()
                    .requiresCorrectToolForDrops().sound(ModSounds.NULL_BLOCK_SOUNDS)))
            .register();

    public static void register(IEventBus eventBus) {}
}
