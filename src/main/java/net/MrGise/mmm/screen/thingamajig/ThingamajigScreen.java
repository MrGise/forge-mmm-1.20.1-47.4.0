package net.MrGise.mmm.screen.thingamajig;

import com.mojang.blaze3d.systems.RenderSystem;
import net.MrGise.mmm.MMM;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ThingamajigScreen extends AbstractContainerScreen<ThingamajigMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(MMM.MOD_ID, "textures/gui/thingamajig_gui.png");


    public ThingamajigScreen(ThingamajigMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
    }

    @Override
    protected void init() {
        super.init();
        this.imageHeight = 174;
        this.inventoryLabelY += 6;
        this.titleLabelY -= 4;
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
    }

    private void renderProgressArrow(GuiGraphics gui, int x, int y) {
        if (menu.isCrafting()) {
            gui.blit(TEXTURE, x + 86, y + 31, 176, 0, 8, menu.getScaledProgress());
        }
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        renderBackground(gui);
        super.render(gui, mouseX, mouseY, delta);
        renderTooltip(gui, mouseX, mouseY);
    }
}
