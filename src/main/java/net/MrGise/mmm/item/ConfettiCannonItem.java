package net.MrGise.mmm.item;

import net.MrGise.mmm.registry.decorative.ModParticles;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ConfettiCannonItem extends Item {
    public ConfettiCannonItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        } else {
            double velScatDiff = 0.8;

            int spawnDist = 2;

            double velocity = 1.0 - velScatDiff;
            double scatter = 1.0 + velScatDiff;

            RandomSource random = level.random;

            Vec3 lookAngle = player.getLookAngle();
            Vec3 up = player.getUpVector(1.0f).normalize();
            Vec3 right = lookAngle.cross(up).normalize();

            for (int i = 0; i < 10; i++) {
                double sideOffset = (random.nextDouble() - 0.5) * scatter;
                double upOffset = (random.nextDouble() - 0.5) * scatter;

                Vec3 velocityVec = lookAngle.add(right.scale(sideOffset)).add(up.scale(upOffset)).scale(velocity).normalize();

                level.addParticle(ModParticles.CONFETTI.get(),
                        player.getX() + lookAngle.x() / spawnDist, player.getEyeY() + lookAngle.y() / spawnDist, player.getZ() + lookAngle.z() / spawnDist,
                        velocityVec.x(), velocityVec.y(), velocityVec.z());
            }
            for (int i = 0; i < 80; i++) {
                double sideOffset = (random.nextDouble() - 0.5) * scatter;
                double upOffset = (random.nextDouble() - 0.5) * scatter;

                Vec3 velocityVec = lookAngle.add(right.scale(sideOffset)).add(up.scale(upOffset)).scale(velocity).normalize();

                level.addParticle(ModParticles.SMALL_CONFETTI.get(),
                        player.getX() + lookAngle.x() / spawnDist, player.getEyeY() + lookAngle.y() / spawnDist, player.getZ() + lookAngle.z() / spawnDist,
                        velocityVec.x(), velocityVec.y(), velocityVec.z());
            }
            level.playSound(player, player.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 1.0f, 0.8f);

            player.getCooldowns().addCooldown(this, 10);

            return InteractionResultHolder.success(player.getItemInHand(hand));
        }
    }
}
