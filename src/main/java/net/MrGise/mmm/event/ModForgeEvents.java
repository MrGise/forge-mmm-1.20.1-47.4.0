package net.MrGise.mmm.event;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.item.ReinforcedBowItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MMM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModForgeEvents {
    @SubscribeEvent
    public static void onComputeFovModifierEvent(ComputeFovModifierEvent event) {
        if (event.getPlayer().isUsingItem() && event.getPlayer().getUseItem().getItem() instanceof ReinforcedBowItem) {
            float fovModifier = 1f;
            int ticksUsingItem = event.getPlayer().getTicksUsingItem();
            float deltaTicks = (float) ticksUsingItem / 20.0F;
            if (deltaTicks > 1.0F) {
                deltaTicks = 1.0F;
            } else {
                deltaTicks *= deltaTicks;
            }
            fovModifier *= 1.0F - deltaTicks * 0.15F;
            event.setNewFovModifier(fovModifier);
        }
    }

}
