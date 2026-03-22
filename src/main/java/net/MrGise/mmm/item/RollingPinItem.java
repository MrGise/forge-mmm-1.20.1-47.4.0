package net.MrGise.mmm.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.MrGise.mmm.block.dough.FlatteningDoughBlock;
import net.MrGise.mmm.registry.content.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vectorwing.farmersdelight.common.registry.ModSounds;

public class RollingPinItem extends Item {
    private final Multimap<Attribute, AttributeModifier> defAttributeModifiers;

    public RollingPinItem(Properties properties, float attackDamage) {
        super(properties);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE,
                new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED,
                new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", -2.2f, AttributeModifier.Operation.ADDITION));

        this.defAttributeModifiers = builder.build();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        return switch (slot) {
            case MAINHAND -> defAttributeModifiers;
            default -> ImmutableMultimap.of();
        };
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);

        if (state.is(ModBlocks.PLACED_DOUGH.get())) {
            level.setBlock(pos, ModBlocks.FLATTENING_DOUGH.get().defaultBlockState(), 3);

            level.playSound(context.getPlayer(), context.getClickedPos(), SoundEvents.WOOL_HIT, SoundSource.BLOCKS, 1.0F, 1.0F);

            return InteractionResult.SUCCESS;
        } else if (state.is(ModBlocks.FLATTENING_DOUGH.get())) {
            FlatteningDoughBlock.Shape stateShape = state.getValue(FlatteningDoughBlock.SHAPE);
            if (stateShape == FlatteningDoughBlock.Shape.FLAT) {
                return InteractionResult.FAIL;
            } else if (stateShape == FlatteningDoughBlock.Shape.PIZZA) {
                level.setBlock(pos, ModBlocks.FLATTENING_DOUGH.get().defaultBlockState()
                        .setValue(FlatteningDoughBlock.SHAPE, FlatteningDoughBlock.Shape.FLAT), 3);

                level.playSound(context.getPlayer(), context.getClickedPos(), SoundEvents.WOOL_HIT, SoundSource.BLOCKS, 1.0F, 1.0F);

                return InteractionResult.SUCCESS;
            } else if (stateShape == FlatteningDoughBlock.Shape.FLATTENING) {
                level.setBlock(pos, ModBlocks.FLATTENING_DOUGH.get().defaultBlockState()
                        .setValue(FlatteningDoughBlock.SHAPE, FlatteningDoughBlock.Shape.PIZZA), 3);

                level.playSound(context.getPlayer(), context.getClickedPos(), SoundEvents.WOOL_HIT, SoundSource.BLOCKS, 1.0F, 1.0F);

                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.FAIL;
            }
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity enemy) {
        entity.level().playSound(null, enemy.blockPosition(), ModSounds.ITEM_SKILLET_ATTACK_WEAK.get(), SoundSource.NEUTRAL);
        enemy.level().playSound(null, enemy.blockPosition(), ModSounds.ITEM_SKILLET_ATTACK_WEAK.get(), SoundSource.NEUTRAL);
        return super.hurtEnemy(stack, entity, enemy);
    }
}
