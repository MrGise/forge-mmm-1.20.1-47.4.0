package net.MrGise.mmm.item;

import net.MrGise.mmm.registry.ModTags;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Vanishable;

public class PaxelItem extends DiggerItem implements Vanishable {

    public PaxelItem(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pAttackDamageModifier, pAttackSpeedModifier, pTier, ModTags.Blocks.MINEABLE_WITH_PAXEL, pProperties);
    }

}
