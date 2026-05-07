package net.MrGise.mmm.screen.race_selection;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.ToIntFunction;

import static net.MrGise.floating.helper.Methods.*;

public class RaceSelectionScreenElements {
    protected enum XAlignment {
        LEFT((screenWidth, width) -> 0),
        CENTER((screenWidth, width) -> (screenWidth - width) / 2),
        RIGHT((screenWidth, width) -> screenWidth - width);

        final BiFunction<Integer, Integer, Integer> function;
        XAlignment(BiFunction<Integer, Integer, Integer> function) {
            this.function = function;
        }
    }
    protected enum YAlignment {
        BOTTOM((screenHeight, height) -> screenHeight - height),
        CENTER((screenHeight, height) -> (screenHeight - height) / 2), TOP((screenHeight, height) -> 0);

        final BiFunction<Integer, Integer, Integer> function;
        YAlignment(BiFunction<Integer, Integer, Integer> function) {
            this.function = function;
        }
    }

    protected enum TEXTURES {
        ELEMENTS(mmm("textures/gui/race_selection/race_selection_gui.png")),
        TITLE(mmm("textures/general/mod_title.png"));

        private final ResourceLocation texture;

        TEXTURES(ResourceLocation textureLocation) {
            this.texture = textureLocation;
        }

        public ResourceLocation get() {
            return this.texture;
        }
    }

    protected enum TEXTURED_ELEMENTS {
        TITLE(TEXTURES.TITLE.get(), 1024, 400, 0, 0,
                XAlignment.CENTER, YAlignment.TOP, d -> 0, d -> swapForScale(d, 30, 20, 10, 0)),
        CARD(TEXTURES.ELEMENTS.get(), 129, 166, 0, 0, 256, 256,
                XAlignment.CENTER, YAlignment.CENTER, d -> 0, d -> 0),
        NAMEPLATE(TEXTURES.ELEMENTS.get(), 139, 30, 0, CARD.height,
                XAlignment.CENTER, YAlignment.TOP, d -> 0, d -> 10 + swapForScale(d, 30, 20, 10, 0));


        final ResourceLocation texture;
        final int width;
        final int height;

        final int texStartX;
        final int texStartY;
        final int texWidth;
        final int texHeight;

        final XAlignment xAlignment;
        final YAlignment yAlignment;
        final ToIntFunction<Double> xOffset;
        final ToIntFunction<Double> yOffset;

        public ResourceLocation texture() {return texture;}
        public int width() {return width;}
        public int height() {return height;}

        public int texStartX() {return texStartX;}
        public int texStartY() {return texStartY;}
        public int texWidth() {return texWidth;}
        public int texHeight() {return texHeight;}

        public XAlignment xAlignment() {return xAlignment;}
        public YAlignment yAlignment() {return yAlignment;}
        public ToIntFunction<Double> xOffset() {return xOffset;}
        public ToIntFunction<Double> yOffset() {return yOffset;}

        public int xPos(int screenWidth, double guiScale) {
            return xAlignment().function.apply(screenWidth, texWidth()) + xOffset().applyAsInt(guiScale);
        }
        public int yPos(int screenHeight, double guiScale) {
            return yAlignment().function.apply(screenHeight, texHeight()) + yOffset().applyAsInt(guiScale);
        }

        TEXTURED_ELEMENTS(ResourceLocation texture,
                          int width, int height,
                          int texStartX, int texStartY, int texWidth, int texHeight,
                          XAlignment xAlignment, YAlignment yAlignment,
                          ToIntFunction<Double> xOffset, ToIntFunction<Double> yOffset) {
            this.texture = texture;
            this.width = width;
            this.height = height;
            this.texStartX = texStartX;
            this.texStartY = texStartY;
            this.texWidth = texWidth;
            this.texHeight = texHeight;
            this.xAlignment = xAlignment;
            this.yAlignment = yAlignment;
            this.xOffset = xOffset;
            this.yOffset = yOffset;
        }
        TEXTURED_ELEMENTS(ResourceLocation texture,
                          int width, int height,
                          int texStartX, int texStartY,
                          XAlignment xAlignment, YAlignment yAlignment,
                          ToIntFunction<Double> xOffset, ToIntFunction<Double> yOffset) {
            this.texture = texture;
            this.width = width;
            this.height = height;
            this.texStartX = texStartX;
            this.texStartY = texStartY;
            this.texWidth = width;
            this.texHeight = height;
            this.xAlignment = xAlignment;
            this.yAlignment = yAlignment;
            this.xOffset = xOffset;
            this.yOffset = yOffset;
        }

        public void blit(GuiGraphics gui, int screenWidth, int screenHeight, double guiScale) {
            gui.blit(this.texture(), this.xPos(screenWidth, guiScale), this.yPos(screenHeight, guiScale),
                    this.texStartX(), this.texStartY(), this.width(), this.height(), this.texWidth(), this.texHeight());
        }
    }
    protected enum TEXT_ELEMENTS {

    }

    private static int swapForScale(Double scale, int first, int second, int third, int fourth) {
        int toReturn = 0;
        if (scale == 1.0) {
            toReturn = first;
        } else if (scale == 2.0) {
            toReturn = second;
        } else if (scale == 3.0) {
            toReturn = third;
        } else if (scale == 4.0) {
            toReturn = fourth;
        } else {
            throw new IllegalArgumentException("GUI scale should only be 1.0, 2.0, 3.0 or 4.0, but " +
                    RaceSelectionScreenElements.class.getName() +
                    " found it isn't!");
        }
        return toReturn;
    }
}
