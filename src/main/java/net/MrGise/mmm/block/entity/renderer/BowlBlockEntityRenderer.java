package net.MrGise.mmm.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.MrGise.mmm.MMM;
import net.MrGise.mmm.block.entity.BowlBlockEntity;
import net.MrGise.mmm.config.ClientConfig;
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
    private final boolean debug = false;


    public BowlBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(BowlBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        List<ItemStack> items = blockEntity.storedItems();
        Level level = blockEntity.getLevel();

        if (debug) {
            if (level == null) {
                MMM.LOGGER.warn("Level is null, cannot render BowlBlockEntityRenderer");
                return;
            }
        }

        int stackRenderCount = items.size();

        float stackIntervalAngle = 360.0f / stackRenderCount;

        float distFromCenter = 0.25f;

        if (debug) {
            MMM.LOGGER.info("Rendering BowlBlockEntityRenderer;" +
                    " number of items to render are: {} (internal value: {}) [Block Entity's storedItems: {}]",
                    items.size(), stackRenderCount, blockEntity.storedItems().toString());
        }

        int yRotOffset = ClientConfig.BOWL_YROT_OFFSET.get();
        int lean = ClientConfig.BOWL_XROT_OFFSET.get();

        for (int stackIndex = 0; stackIndex < stackRenderCount; ++stackIndex) {
            poseStack.pushPose();

            float stackAngleF = stackIntervalAngle * stackIndex + blockEntity.getYRotOffset();
            float stackAngleR = stackAngleF * Mth.DEG_TO_RAD;

            float xOffset = Mth.cos(stackAngleR) * distFromCenter;
            float zOffset = -Mth.sin(stackAngleR) * distFromCenter;

            poseStack.translate(0.5f + xOffset, 0.15f, 0.5f + zOffset);
            poseStack.scale(1f, 1f, 1f);

            poseStack.mulPose(Axis.YP.rotationDegrees(stackAngleF));
            poseStack.mulPose(Axis.YN.rotationDegrees(90));
            poseStack.mulPose(Axis.XN.rotationDegrees(70));

            itemRenderer.renderStatic(items.get(stackIndex), ItemDisplayContext.GROUND, getLightLevel(level, blockEntity.getBlockPos()),
                    OverlayTexture.NO_OVERLAY, poseStack, bufferSource, level, 1);

            poseStack.popPose();
        }
    }
}
