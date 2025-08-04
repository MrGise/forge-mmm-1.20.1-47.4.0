package net.MrGise.mmm.item.custom;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MimicItem extends Item {

    public MimicItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        if (!pLevel.isClientSide && !pPlayer.isCreative() && !pPlayer.isSpectator()) {

            float from = pPlayer.getHealth();

            if (from >= 3) {
                pPlayer.setHealth(from - 3);

            } else {
                pPlayer.setHealth(0);
            }

        }
        pPlayer.playSound(SoundEvents.PLAYER_HURT);
        pPlayer.animateHurt(0.0f);

        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public Component getName(ItemStack pStack) {

        CompoundTag tag = pStack.getTag();
        if (tag != null && tag.contains("form", Tag.TAG_STRING)) {
            String form = tag.getString("form");
            String key = this.getDescriptionId(pStack) + "." + form;

            if (I18n.exists(key)) {
                return Component.translatable(key);
            } else {
                return Component.translatable("item.mmm.mimic.fail");
            }
        } else {
            return Component.translatable(this.getDescriptionId(pStack));
        }

    }
}
