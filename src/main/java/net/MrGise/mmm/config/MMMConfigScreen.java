package net.MrGise.mmm.config;

import com.mojang.blaze3d.systems.RenderSystem;
import net.MrGise.mmm.constants.TextComponents;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.widget.ForgeSlider;
import org.checkerframework.checker.nullness.qual.NonNull;

import static net.MrGise.floating.helper.Methods.mcr;
import static net.MrGise.floating.helper.Methods.mmm;

public class MMMConfigScreen extends Screen {
    public enum Scene {
        MAIN, CLIENT, COMMON, SERVER
    }
    private final Screen parent;
    private @NonNull Scene scene = Scene.MAIN;

    private ResourceLocation backgroundTile = mcr("textures/block/dirt.png");
    private ResourceLocation prevBackgroundTile;
    private ResourceLocation newBackgroundTile;
    private float fadeProgress = 1.0f;

    private float bGBrightness = 0.35f;


    public MMMConfigScreen(Screen parent) {
        this(parent, Scene.MAIN, mcr("textures/block/dirt.png"));
    }

    public MMMConfigScreen(Screen parent, @NonNull Scene scene, ResourceLocation parentBackground) {
        super(Component.literal("MMM Config"));
        this.parent = parent;
        this.scene = scene;
        this.prevBackgroundTile = parentBackground;
    }

    @Override
    protected void init() {
        switch (scene) {
            case MAIN -> initMainPage();
            case CLIENT -> initClientPage();
            case COMMON -> initCommonPage();
            case SERVER -> initServerPage();
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderTiledBackground(guiGraphics);

        super.render(guiGraphics, mouseX, mouseY, partialTick);

        Component title = switch (scene) {
            case MAIN -> TextComponents.CONFIG_TITLE;
            case CLIENT -> TextComponents.CLIENT_CONFIG_TITLE;
            case COMMON -> TextComponents.COMMON_CONFIG_TITLE;
            case SERVER -> TextComponents.SERVER_CONFIG_TITLE;
        };

        guiGraphics.drawCenteredString(font, title, width / 2, 15, 0xFFFFFF);
    }

    private void rebuildPage() {
        if (minecraft != null) {
            minecraft.setScreen(new MMMConfigScreen(parent, scene, backgroundTile));
        }
    }

    private void initClientPage() {
        setBackground(mmm("textures/block/skysoil.png"));

        ForgeSlider bowlYRotModSlider = new ForgeSlider(width / 2 - 100, height / 2 - 30, 200, 20,
                Component.literal("Bowl render Y rot modifier: "), Component.empty(),
                0d, 360d, ClientConfig.BOWL_YROT_OFFSET.get(), true);
        this.addRenderableWidget(bowlYRotModSlider);

        ForgeSlider bowlXRotModSlider = new ForgeSlider(width / 2 - 100, height / 2 - 10, 200, 20,
                Component.literal("Bowl render X rot modifier: "), Component.empty(),
                0d, 360d, ClientConfig.BOWL_XROT_OFFSET.get(), true);
        this.addRenderableWidget(bowlXRotModSlider);

        ForgeSlider bowlZRotModSlider = new ForgeSlider(width / 2 - 100, height / 2 + 10, 200, 20,
                Component.literal("Bowl render Z rot modifier: "), Component.empty(),
                0d, 360d, ClientConfig.BOWL_ZROT_OFFSET.get(), true);
        this.addRenderableWidget(bowlZRotModSlider);

        renderExitButton(b -> {
            ClientConfig.BOWL_YROT_OFFSET.set(bowlYRotModSlider.getValueInt());
            ClientConfig.BOWL_YROT_OFFSET.save();
            ClientConfig.BOWL_XROT_OFFSET.set(bowlXRotModSlider.getValueInt());
            ClientConfig.BOWL_XROT_OFFSET.save();
            ClientConfig.BOWL_ZROT_OFFSET.set(bowlZRotModSlider.getValueInt());
            ClientConfig.BOWL_ZROT_OFFSET.save();
        });
    }

    private void initCommonPage() {
        setBackground(mmm("textures/block/skiron_ore.png"));

        renderExitButton(b -> {});
    }

    private void initServerPage() {
        setBackground(mmm("textures/block/skysolid.png"));

        renderExitButton(b -> {});
    }

    private void initMainPage() {
        setBackground(mcr("textures/block/dirt.png"));

        Button clientSettings = Button.builder(TextComponents.CLIENT_CONFIG_TITLE,
                button -> {
                    scene = Scene.CLIENT;
                    rebuildPage();
                }).bounds(width / 2 - 100, 60, 200, 20).tooltip(TextComponents.CLIENT_CONFIG_TOOLTIP).build();

        this.addRenderableWidget(clientSettings);

        Button commonSettings = Button.builder(TextComponents.COMMON_CONFIG_TITLE,
                        button -> {
                            scene = Scene.COMMON;
                            rebuildPage();
                        })
                .bounds(width / 2 - 100, 90, 200, 20).tooltip(TextComponents.COMMON_CONFIG_TOOLTIP).build();
        this.addRenderableWidget(commonSettings);

        Button serverSettings = Button.builder(TextComponents.SERVER_CONFIG_TITLE,
                        button -> {
                            scene = Scene.SERVER;
                            rebuildPage();
                        })
                .bounds(width / 2 - 100, 120, 200, 20).tooltip(TextComponents.SERVER_CONFIG_TOOLTIP).build();
        this.addRenderableWidget(serverSettings);

        Button finish = Button.builder(TextComponents.CONFIG_EXIT,
                        button -> minecraft.setScreen(parent))
                .bounds(width / 2 - 100, height - 28, 200, 20).build();
        this.addRenderableWidget(finish);
    }

    private void renderExitButton(Button.OnPress pressFunc) {
        Button exitButton = Button.builder(TextComponents.CONFIG_PAGE_EXIT,
                button -> {
            scene = Scene.MAIN;
            pressFunc.onPress(button);
            rebuildPage();
        }).bounds(width / 2 - 100, height - 28, 200, 20).build();
        this.addRenderableWidget(exitButton);
    }


    private void renderTiledBackground(GuiGraphics gui) {
        int tileSize = 32;

        if (fadeProgress < 1.0f && prevBackgroundTile != null) {
            fadeProgress += 0.05f;

            tileBackground(gui, prevBackgroundTile, tileSize, 1.0f);

            tileBackground(gui, newBackgroundTile, tileSize, fadeProgress);

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
            fadeProgress = 0.0f;
        }
    }
}
