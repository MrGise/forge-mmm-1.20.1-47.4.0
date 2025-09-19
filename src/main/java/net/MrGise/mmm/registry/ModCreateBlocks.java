package net.MrGise.mmm.registry;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.MrGise.mmm.MMM;
import net.MrGise.mmm.block.PortalBlock;
import net.MrGise.mmm.util.BuilderHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;

public class ModCreateBlocks {
    private static final CreateRegistrate registrate = MMM.registrate();

    public static final BlockEntry<Block> EXAMPLE_CONNECTION = registrate.block("example_connection", Block::new)
            .transform(BuilderHelper.emptyBlock(() -> ModSpriteShifts.EXAMPLE, BlockBehaviour.Properties.of()
                    .requiresCorrectToolForDrops().sound(ModSounds.NULL_BLOCK_SOUNDS).noLootTable()))
            .register();

    public static final BlockEntry<PortalBlock> CONNECTING_PORTAL_BLOCK = registrate.block("connecting_portal_block",
                    properties -> new PortalBlock(properties, ModItems.ACTINOLITE))
            .transform(BuilderHelper.portalBlock(() -> ModSpriteShifts.CONNECTING_PORTAL_OFF, () -> ModSpriteShifts.CONNECTING_PORTAL_ON,
                    BlockBehaviour.Properties.of().requiresCorrectToolForDrops().sound(ModSounds.NULL_BLOCK_SOUNDS).noLootTable()))
            .register();

    public static void register(IEventBus eventBus) {
        registrate.registerEventListeners(eventBus);
    }
}
