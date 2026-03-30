package net.MrGise.mmm.mixin;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.registry.variants.ModEnchantments;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "travel", at = @At("HEAD"))
    private void onTravel(Vec3 travelVec, CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;

        ItemStack boots = self.getItemBySlot(EquipmentSlot.FEET);
        if (boots.isEmpty() || boots == null) return;
        int level = EnchantmentHelper.getTagEnchantmentLevel(ModEnchantments.AIR_WALK.get(), boots);
        if (level <= 0) return;

        Vec3 movement = self.getDeltaMovement();
        boolean canApplyEffect = (movement.x != 0 || movement.z != 0) && !(self.isFallFlying() ||
                (self instanceof Player player && player.getAbilities().flying))
                && !self.onGround() && movement.y < 0;

        if (canApplyEffect) {
            double fallAngle = level < 6 ? (5 - level) * -10 : 0;

            double horizontalSpeed = Math.sqrt(movement.x * movement.x + movement.z * movement.z);
            double targetYVelocity = level < 5 ? horizontalSpeed * Math.tan(Math.toRadians(fallAngle)) : 0;

            MMM.LOGGER.info("Applying air walk level {}: {}°, y velocity {} -> {}", level, fallAngle, movement.y, targetYVelocity);

            self.setDeltaMovement(movement.x, Math.min(targetYVelocity, 0), movement.z);
        }
    }
}