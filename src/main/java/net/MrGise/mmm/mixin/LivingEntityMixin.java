package net.MrGise.mmm.mixin;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.registry.variants.ModEnchantments;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "travel", at = @At("HEAD"))
    private void onTravel(Vec3 travelVec, CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        ItemStack boots = self.getItemBySlot(EquipmentSlot.FEET);
        Vec3 movement = self.getDeltaMovement();

        if (boots.isEmpty() || boots == null) return;

        int airWalkLevel = EnchantmentHelper.getTagEnchantmentLevel(ModEnchantments.AIR_WALK.get(), boots);
        boolean canApplyAirWalk = (movement.x != 0 || movement.z != 0) && !(self.isFallFlying() ||
                (self instanceof Player player && player.getAbilities().flying))
                && !self.onGround() && movement.y < 0;

        if (canApplyAirWalk && airWalkLevel > 0) {
            double fallAngle = airWalkLevel < 6 ? (5 - airWalkLevel) * -10 : 0;

            double horizontalSpeed = Math.sqrt(movement.x * movement.x + movement.z * movement.z);
            double targetYVelocity = airWalkLevel < 5 ? horizontalSpeed * Math.tan(Math.toRadians(fallAngle)) : 0;

            MMM.LOGGER.info("Applying air walk level {}: {}°, y velocity {} -> {}, method start", airWalkLevel, fallAngle, movement.y, targetYVelocity);

            self.setDeltaMovement(movement.x, Math.min(targetYVelocity, 0), movement.z);
        }
        if (EnchantmentHelper.getTagEnchantmentLevel(ModEnchantments.FALL_NEGATION.get(), boots) > 0) {
            self.resetFallDistance();
        }
    }
}