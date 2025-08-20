package net.MrGise.mmm.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.gameevent.GameEvent;

public class EdibleBlockItem extends BlockItem {
    private final int nutrition;
    private final float saturation;
    private final boolean alwaysEat; // if true, can eat even at full hunger when placement fails

    public EdibleBlockItem(Block block, Properties properties, FoodProperties foodProperties, boolean alwaysEat) {
        // IMPORTANT: do NOT set properties.food(...). We want it *not* inherently edible.
        super(block, properties);
        this.nutrition = foodProperties.getNutrition();
        this.saturation = foodProperties.getSaturationModifier();
        this.alwaysEat = alwaysEat;
    }

    // Right-click a block
    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Player player = ctx.getPlayer();

        if (player != null && player.canEat(alwaysEat) && player.isShiftKeyDown()) {
            player.startUsingItem(ctx.getHand());
            return InteractionResult.sidedSuccess(ctx.getLevel().isClientSide());
        }

        // Try normal block placement first
        InteractionResult place = super.useOn(ctx);
        if (place.consumesAction()) {
            return place; // placed successfully or otherwise consumed the action
        }

        // Placement failed -> try to eat
        if (player != null && player.canEat(alwaysEat)) {
            player.startUsingItem(ctx.getHand());
            return InteractionResult.sidedSuccess(ctx.getLevel().isClientSide());
        }

        return place; // PASS/FAIL as per normal
    }

    // Right-click air
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        // No placement possible in air, so try to eat
        if (player.canEat(alwaysEat)) {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(stack);
        }
        return InteractionResultHolder.pass(stack);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32; // standard eating time
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.EAT;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (entity instanceof Player player) {
            player.getFoodData().eat(nutrition, saturation);
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 0.5F,
                    level.random.nextFloat() * 0.1F + 0.9F);
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            if (!level.isClientSide) {
                level.gameEvent(entity, GameEvent.EAT, entity.blockPosition());
            }
        }
        return stack;
    }
}