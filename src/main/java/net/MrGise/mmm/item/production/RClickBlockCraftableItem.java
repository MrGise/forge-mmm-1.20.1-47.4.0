package net.MrGise.mmm.item.production;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.registries.ForgeRegistries;

public class RClickBlockCraftableItem extends Item {
    public RClickBlockCraftableItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);

        if (player == null || level.getBlockState(pos).isAir()) return InteractionResult.PASS;
        ItemStack stack = player.getItemInHand(context.getHand());

        ResourceLocation itemId = ForgeRegistries.ITEMS.getKey(stack.getItem());
        ResourceLocation lootTableId = itemId.withPrefix("crafting/item_click_on/");

        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            ServerLevel serverLevel = (ServerLevel) level;
            LootTable table = serverLevel.getServer().getLootData().getLootTable(lootTableId);

            LootParams params = new LootParams.Builder(serverLevel)
                    .withParameter(LootContextParams.THIS_ENTITY, serverPlayer)
                    .withParameter(LootContextParams.ORIGIN, serverPlayer.position())
                    .create(LootContextParamSets.GIFT);

            for (ItemStack result : table.getRandomItems(params)) {
                if (!serverPlayer.addItem(result)) {
                    serverPlayer.drop(result, false);
                }
            }

            stack.shrink(1);
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }
}