package net.MrGise.mmm.registry.variants;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.enchantment.AirWalkEnchantment;
import net.MrGise.mmm.enchantment.FallNegationEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

// Enchantment registry
public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MMM.MOD_ID);

    public static final RegistryObject<Enchantment> AIR_WALK =
            ENCHANTMENTS.register("air_walk",
                    () -> new AirWalkEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.ARMOR,
                            EquipmentSlot.FEET));

    public static final RegistryObject<Enchantment> FALL_NEGATION =
            ENCHANTMENTS.register("fall_negation",
                    () -> new FallNegationEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.ARMOR,
                            EquipmentSlot.FEET));


    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}
