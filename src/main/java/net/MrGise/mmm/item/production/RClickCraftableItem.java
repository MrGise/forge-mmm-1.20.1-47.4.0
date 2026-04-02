package net.MrGise.mmm.item.production;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.registries.ForgeRegistries;

public class RClickCraftableItem extends Item {
    public RClickCraftableItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        ResourceLocation itemId = ForgeRegistries.ITEMS.getKey(stack.getItem());
        ResourceLocation lootTableId = itemId.withPrefix("crafting/item_click/");

        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            ServerLevel serverLevel = (ServerLevel) level;
            LootTable table = serverLevel.getServer().getLootData().getLootTable(lootTableId);

            LootParams params = new LootParams.Builder(serverLevel)
                    .withParameter(LootContextParams.THIS_ENTITY, serverPlayer)
                    .withParameter(LootContextParams.ORIGIN, serverPlayer.position())
                    .create(LootContextParamSets.GIFT);

            for (ItemStack given : table.getRandomItems(params)) {
                if (!serverPlayer.addItem(given)) {
                    serverPlayer.drop(given, false);
                }
            }

            stack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}
