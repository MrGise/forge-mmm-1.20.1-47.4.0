package net.MrGise.mmm.event;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.registry.variants.ModEnchantments;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = MMM.MOD_ID)
public class EnchantEvents {
    //Feel like I'm gonna need it (26/3/2026, 11:42)

    private static final Map<UUID, Vec2> previousPositions = new HashMap<>();
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Player player = event.player;
        if (player.level().isClientSide()) return;

        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        int airWalkLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.AIR_WALK.get(), boots);

        Vec2 prev = previousPositions.getOrDefault(player.getUUID(), new Vec2((float) player.getX(), (float) player.getZ()));
        boolean isMoving = player.getX() != prev.x || player.getZ() != prev.y;
        previousPositions.put(player.getUUID(), new Vec2((float) player.getX(), (float) player.getZ()));

        boolean isFalling = player.getDeltaMovement().y < 0 && !player.onGround();

        if (isFalling && isMoving && player.tickCount % 3 == 0 && airWalkLevel > 0) {
            ServerLevel serverLevel = (ServerLevel) player.level();
            serverLevel.sendParticles(
                    ParticleTypes.CLOUD,
                    player.getX(), player.getY(), player.getZ(),
                    3,
                    0.2, 0.1, 0.2,
                    0.01
            );
        }
    }
}
