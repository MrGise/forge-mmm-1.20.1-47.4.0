package net.MrGise.mmm.screen.thingamajig;

import com.mojang.blaze3d.systems.RenderSystem;
import net.MrGise.floating.helper.MouseUtil;
import net.MrGise.mmm.MMM;
import net.MrGise.mmm.screen.renderer.EnergyDisplayTooltipArea;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.Optional;

public class ThingamajigScreen extends AbstractContainerScreen<ThingamajigMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(MMM.MOD_ID, "textures/gui/thingamajig/thingamajig_gui.png");
    private EnergyDisplayTooltipArea energyDisplayArea;

    public ThingamajigScreen(ThingamajigMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
    }

    @Override
    protected void init() {
        this.imageHeight = 174;
        super.init();
        this.titleLabelY = 4;
        this.inventoryLabelY = this.imageHeight - 92;

        energyDisplayArea = new EnergyDisplayTooltipArea(((width - imageWidth) / 2) + 156,
                ((height - imageHeight) / 2) + 17, 8, 64, menu.blockEntity.getEnergyStorage());
    }

    @Override
    protected void renderLabels(GuiGraphics gui, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderEnergyTooltip(gui, mouseX, mouseY, x, y);
    }

    private void renderEnergyTooltip(GuiGraphics gui, int mouseX, int mouseY, int x, int y) {
        if (isMouseAboveArea(mouseX, mouseY, x, y, 156, 17, 8, 64)) {
            gui.renderTooltip(this.font, energyDisplayArea.getTooltips(),
                    Optional.empty(), mouseX - x, mouseY - y);
        }
    }

    @Override
    protected void renderBg(GuiGraphics gui, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        gui.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(gui, x, y);

        energyDisplayArea.render(gui);
    }

    private void renderProgressArrow(GuiGraphics gui, int x, int y) {
        if (menu.isCrafting()) {
            gui.blit(TEXTURE, x + 85, y + 36, 176, 0, 8, menu.getScaledProgress());
        }
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        renderBackground(gui);
        super.render(gui, mouseX, mouseY, delta);
        renderTooltip(gui, mouseX, mouseY);
    }

    private boolean isMouseAboveArea(int mouseX, int mouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(mouseX, mouseY, x + offsetX, y + offsetY, width, height);
    }
}
