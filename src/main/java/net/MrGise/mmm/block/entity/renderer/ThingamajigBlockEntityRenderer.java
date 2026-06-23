package net.MrGise.mmm.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.MrGise.mmm.block.ThingamajigBlock;
import net.MrGise.mmm.block.entity.ThingamajigBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

import static java.lang.Math.PI;

public class ThingamajigBlockEntityRenderer implements BlockEntityRenderer<ThingamajigBlockEntity> {
    private static final float SPIN_SPEED = 2.0f;
    private static final float BOB_SPEED = 0.1f;
    private static final float BOB_HEIGHT = 0.1f;

    public ThingamajigBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(ThingamajigBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack renderStack = blockEntity.getRenderStack();
        Level level = blockEntity.getLevel();

        float age = level.getGameTime() + partialTick;

        float posOffset = (blockEntity.getBlockPos().hashCode() % 1000) / 1000f * 360f;

        float spinAngle = (age * SPIN_SPEED) % 360f;
        float bobOffset = Mth.sin(age * BOB_SPEED + posOffset) * BOB_HEIGHT;

        poseStack.pushPose();
        poseStack.translate(0.5f, 0.75f + bobOffset, 0.5f);
        poseStack.scale(0.5f, 0.5f, 0.5f);

        poseStack.mulPose(Axis.YN.rotationDegrees(blockEntity.getBlockState().getValue(ThingamajigBlock.FACING).toYRot() + spinAngle));

        itemRenderer.renderStatic(renderStack, ItemDisplayContext.FIXED, getLightLevel(level, blockEntity.getBlockPos()),
                OverlayTexture.NO_OVERLAY, poseStack, bufferSource, level, 1);

        poseStack.popPose();

    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}
