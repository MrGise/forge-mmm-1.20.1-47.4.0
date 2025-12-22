package net.MrGise.mmm.datagen.advancement;

import net.MrGise.mmm.event.BlockTouchTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class ModTriggers {
    public static final BlockTouchTrigger BLOCK_TOUCH = new BlockTouchTrigger();

    public static void register() {
        CriteriaTriggers.register(BLOCK_TOUCH);
    }
}
