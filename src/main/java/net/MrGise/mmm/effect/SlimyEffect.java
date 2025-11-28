package net.MrGise.mmm.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class SlimyEffect extends MobEffect {
    public SlimyEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int tick) {
        if (entity.horizontalCollision) {
            Vec3 initialVec = entity.getDeltaMovement();
            Vec3 climbVec = new Vec3(initialVec.x, 0.20, initialVec.z);

            entity.setDeltaMovement(climbVec.x * 0.91D, climbVec.y * 0.98D, climbVec.z * 0.91D);
        }

        super.applyEffectTick(entity, tick);
    }

    @Override
    public boolean isDurationEffectTick(int i, int a) {
        return true;
    }
}
