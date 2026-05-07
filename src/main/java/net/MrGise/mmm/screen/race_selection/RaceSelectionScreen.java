package net.MrGise.mmm.screen.race_selection;

import com.mojang.blaze3d.systems.RenderSystem;
import net.MrGise.floating.screen.ImageButton;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import static net.MrGise.floating.helper.Methods.*;

public class RaceSelectionScreen extends Screen {
    private ResourceLocation backgroundTile = mcr("textures/block/dirt.png");
    private ResourceLocation prevBackgroundTile;
    private ResourceLocation newBackgroundTile;
    private float fadeProgress = 1.0f;

    private float bGBrightness = 0.35f;

    private ResourceLocation cardTexture = mmm("textures/gui/race_selection/race_selection_gui.png");

    public RaceSelectionScreen() {
        super(Component.translatable("menu.mmm.race_selection"));
    }

    @Override
    protected void init() {
        int buttonOff = 75;

        int buttonWidth = 12;
        int buttonHeight = 16;

        int centerX = middlePivotX(buttonWidth);
        int centerY = middlePivotY(buttonHeight);

        ImageButton nextButton = new ImageButton(centerX + buttonOff, centerY,
                buttonWidth, buttonHeight,
                129, 0, 0, 12,
                cardTexture, 256, 256,
                button -> {
                    button.playDownSound(this.minecraft.getSoundManager());
                });
        ImageButton previousButton = new ImageButton(centerX - buttonOff, centerY,
                buttonWidth, buttonHeight,
                129, buttonHeight, 0, 12,
                cardTexture, 256, 256,
                button -> {
                    button.playDownSound(this.minecraft.getSoundManager());
                });
        nextButton.setTooltip(Tooltip.create(Component.translatable("menu.mmm.race_selection.next_button.tooltip")));
        previousButton.setTooltip(Tooltip.create(Component.translatable("menu.mmm.race_selection.previous_button.tooltip")));

        this.addRenderableWidget(nextButton);
        this.addRenderableWidget(previousButton);
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        renderTiledBackground(gui);

        card(gui, cardTexture);

        super.render(gui, mouseX, mouseY, partialTick);
    }

    private void card(GuiGraphics gui, ResourceLocation texture) {
        double guiScale = this.minecraft.getWindow().getGuiScale();

        int cardWidth = 129;
        int cardHeight = 166;
        gui.blit(texture, middlePivotX(cardWidth), middlePivotY(cardHeight), 0, 0, cardWidth, cardHeight);

        int titleOffset = 70;

        int titleWidth = 126;
        int titleHeight = 18;
        gui.blit(texture, middlePivotX(titleWidth), middlePivotY(titleHeight) - titleOffset,
                0, cardHeight, titleWidth, titleHeight);

        Component title = Component.translatable("menu.mmm.race_selection.no_race");
        gui.drawString(this.font, title, middlePivotX(this.font.width(title.getString())),
                middlePivotY(this.font.lineHeight) - titleOffset, 0xFFFFFF);

        int nameOffset = switch((int) guiScale) {
            case 1 -> 40;
            case 2 -> 30;
            case 4 -> 10;
            default -> 20;
        };

        int nameWidth = 139;
        int nameHeight = 30;
        gui.blit(texture, middlePivotX(nameWidth), nameOffset,
                0, cardHeight + titleHeight, nameWidth, nameHeight);

        Component name = Component.translatable("menu.mmm.race_selection").withStyle(ChatFormatting.AQUA);
        gui.pose().pushPose();
        float scale = 1.5f;

        gui.pose().translate(middlePivotX(this.font.width(name.getString()) * scale),
                nameOffset + ((nameHeight - (this.font.lineHeight * scale)) / 2), 0);
        gui.pose().scale(scale, scale, 1f);

        gui.drawString(this.font, name, 0, 0, 0xFFFFFF);

        gui.pose().popPose();
    }

    private void renderTiledBackground(GuiGraphics gui) {
        int tileSize = 32;

        if (fadeProgress < 1.0f && prevBackgroundTile != null) {
            fadeProgress += 0.02f;

            tileBackground(gui, prevBackgroundTile, tileSize, 1.0f - fadeProgress);

            tileBackground(gui, backgroundTile, tileSize, fadeProgress);

            if (fadeProgress >= 1.0f) {
                backgroundTile = newBackgroundTile;
                prevBackgroundTile = null;
            }
        } else {
            tileBackground(gui, backgroundTile, tileSize, 1.0f);
        }
    }

    private void tileBackground(GuiGraphics gui, ResourceLocation texture, int tileSize, float transparency) {
        RenderSystem.setShaderTexture(0, texture);
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(bGBrightness, bGBrightness, bGBrightness, transparency);

        for (int x = 0; x < width; x += tileSize) {

            for (int y = 0; y < height; y += tileSize) {

                gui.blit(texture,
                        x, y,
                        0, 0,
                        tileSize, tileSize,
                        tileSize, tileSize);
            }
        }

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1f);
    }

    public void setBackground(ResourceLocation texture) {
        if (!texture.equals(backgroundTile)) {
            prevBackgroundTile = backgroundTile;
            newBackgroundTile = texture;
        }
    }

    private int middlePivotX(int textureWidth) {
        return (this.width - textureWidth) / 2;
    }

    private float middlePivotX(float textureWidth) {
        return (this.width - textureWidth) / 2;
    }

    private int middlePivotY(int textureHeight) {
        return (this.height - textureHeight) / 2;
    }

    private int rightPivotX(int textureWidth) {
        return this.width - (textureWidth / 2);
    }

    private int bottomPivotY(int textureHeight) {
        return this.height - (textureHeight / 2);
    }
}
