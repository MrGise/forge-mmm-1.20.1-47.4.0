package net.MrGise.floating.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public class ImageButton extends net.minecraft.client.gui.components.ImageButton {
    protected final int optionalYOff;
    public ImageButton(int x, int y, int width, int height, int textureOffX, int textureOffY, int optionalYOff, int offsetX,
                       ResourceLocation texture, int textureSizeX, int textureSizeY, Button.OnPress onPress) {
        super(x, y, width, height, textureOffX, textureOffY, offsetX, texture, textureSizeX, textureSizeY, onPress);
        this.optionalYOff = optionalYOff;
    }

    public ImageButton(int x, int y, int width, int height, int textureOffX, int textureOffY, int optionalYOff, int offsetX, ResourceLocation texture, int textureSizeX, int textureSizeY, OnPress onPress, Component title) {
        super(x, y, width, height, textureOffX, textureOffY, offsetX, texture, textureSizeX, textureSizeY, onPress, title);
        this.optionalYOff = optionalYOff;
    }

    @Override
    public void renderTexture(GuiGraphics gui, ResourceLocation texture, int posX, int posY, int texPosX, int texPosY, int hoverOff, int width, int height, int texWidth, int texHeight) {
        int newTexPosX = texPosX;
        int newTexPosY = texPosY;
        if (!this.isActive()) {
            newTexPosX = texPosX + hoverOff * 2;
            newTexPosY = texPosY + this.optionalYOff * 2;
        } else if (this.isHoveredOrFocused()) {
            newTexPosX = texPosX + hoverOff;
            newTexPosY = texPosY + this.optionalYOff;
        }

        RenderSystem.enableDepthTest();
        gui.blit(texture, posX, posY, (float)newTexPosX, (float)newTexPosY, width, height, texWidth, texHeight);

    }
}
