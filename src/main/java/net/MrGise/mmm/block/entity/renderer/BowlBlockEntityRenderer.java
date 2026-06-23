package net.MrGise.mmm.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.MrGise.mmm.MMM;
import net.MrGise.mmm.block.entity.BowlBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

import static net.MrGise.floating.helper.Methods.*;

public class BowlBlockEntityRenderer implements BlockEntityRenderer<BowlBlockEntity> {

    public BowlBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(BowlBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        List<ItemStack> items = blockEntity.storedItems();
        Level level = blockEntity.getLevel();

        if (level == null) {
            MMM.LOGGER.warn("Level is null, cannot render BowlBlockEntityRenderer");
            return;
        }

        int stackRenderCount = items.size();

        float stackIntervalAngle = 360.0f / stackRenderCount;

        float distFromCenter = 0.25f;

        MMM.LOGGER.info("Rendering BowlBlockEntityRenderer; number of items to render are: {} (internal value: {})", items.size(), stackRenderCount);

        for (int stackIndex = 0; stackIndex < stackRenderCount; ++stackIndex) {
            poseStack.pushPose();

            float stackAngleF = stackIntervalAngle * stackIndex;
            float stackAngleR = stackAngleF * Mth.DEG_TO_RAD;

            float xOffset = Mth.cos(stackAngleR) * distFromCenter;
            float zOffset = -Mth.sin(stackAngleR) * distFromCenter;

            poseStack.translate(0.5f + xOffset, 0.25f, 0.5f + zOffset);
            poseStack.scale(0.5f, 0.5f, 0.5f);

            poseStack.mulPose(Axis.YN.rotationDegrees(stackAngleF));
            poseStack.mulPose(Axis.XP.rotationDegrees(135));
            poseStack.mulPose(Axis.ZN.rotationDegrees(180));

            itemRenderer.renderStatic(items.get(stackIndex), ItemDisplayContext.FIXED, getLightLevel(level, blockEntity.getBlockPos()),
                    OverlayTexture.NO_OVERLAY, poseStack, bufferSource, level, 1);

            poseStack.popPose();
        }
    }
}
