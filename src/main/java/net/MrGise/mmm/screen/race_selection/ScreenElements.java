package net.MrGise.mmm.screen.race_selection;

import it.unimi.dsi.fastutil.doubles.Double2BooleanFunction;
import net.MrGise.floating.screen.ScreenElement;
import net.MrGise.floating.screen.ScreenElement.XAlignment;
import net.MrGise.floating.screen.ScreenElement.YAlignment;
import net.MrGise.floating.screen.TextScreenElement;
import net.MrGise.floating.screen.TextureScreenElement;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import static net.MrGise.floating.helper.Methods.*;

public class ScreenElements {
    protected enum TEXTURES {
        ELEMENTS(mmm("textures/gui/race_selection/race_selection_gui.png")),
        TITLE(mmm("textures/gui/general/mod_title.png"));

        private final ResourceLocation texture;

        TEXTURES(ResourceLocation textureLocation) {
            this.texture = textureLocation;
        }
        public ResourceLocation get() {return this.texture;}
    }

    public class RSScreenElements {
        public static TextureScreenElement<RaceSelectionScreen> title = new TextureScreenElement<>(
                XAlignment.CENTER, YAlignment.TOP, d -> 0, d -> swapForScale(d, 30, 20, 10, 0, 0),
                d -> d != 4.0, TEXTURES.TITLE.get(), 0, 0, 128, 50, 128, 50,
                screen -> new ScreenElement.AlignmentContext(screen.width, screen.height, 128, 50));

        public static TextureScreenElement<RaceSelectionScreen> raceCard = new TextureScreenElement<>(
                XAlignment.CENTER, YAlignment.CENTER, d -> 0, d -> swapForScale(d, 0, 0, 10, 0, 0), d -> true,
                TEXTURES.ELEMENTS.get(), 0, 0, 129, 166, 256, 256,
                screen -> new ScreenElement.AlignmentContext(screen.width, screen.height, 129, 166));
        public static TextureScreenElement<RaceSelectionScreen> raceNameplate = new TextureScreenElement<>(
                XAlignment.CENTER, YAlignment.RELATIVE_TOP, d -> 0, d -> 3, d -> true,
                TEXTURES.ELEMENTS.get(), 0, raceCard.vEnd(), 126, 18, 256, 256,
                screen -> {
                    double guiScale = screen.getMinecraft().getWindow().getGuiScale();
                    ScreenElement.AlignmentContext parentContext = raceCard.context().apply(screen);
                    return new ScreenElement.AlignmentContext(screen.width, screen.height,
                            raceCard.xPos(parentContext, guiScale), raceCard.yPos(parentContext, guiScale),
                            raceCard.elementWidth(), raceCard.elementHeight(), 126, 25);
                });

        public static TextureScreenElement<RaceSelectionScreen> screenNameplate = new TextureScreenElement<>(
                XAlignment.CENTER, YAlignment.RELATIVE_BELOW, d -> 0, d -> 0, d -> true,
                TEXTURES.ELEMENTS.get(), 0, raceNameplate.vEnd(), 139, 30, 256, 256,
                screen -> {
                    double guiScale = screen.getMinecraft().getWindow().getGuiScale();
                    ScreenElement.AlignmentContext parentContext = title.context().apply(screen);
                    return new ScreenElement.AlignmentContext(screen.width, screen.height,
                            title.xPos(parentContext, guiScale), title.displayCondition().apply(guiScale) ? title.yPos(parentContext, guiScale) : 0,
                            title.elementWidth(), title.elementHeight(), 139, 30);
                });


        public static TextScreenElement<RaceSelectionScreen> raceName = new TextScreenElement<>(
                XAlignment.CENTER, YAlignment.RELATIVE_CENTER, s -> 0, s -> 0, d -> true,
                s(Component.translatable("menu.mmm.race_selection.no_race")),
                screen -> {
                    double guiScale = screen.getMinecraft().getWindow().getGuiScale();
                    ScreenElement.AlignmentContext parentContext = raceNameplate.context().apply(screen);
                    Font font = screen.getMinecraft().font;
                    return new ScreenElement.AlignmentContext(screen.width, screen.height,
                            raceNameplate.xPos(parentContext, guiScale), raceNameplate.yPos(parentContext, guiScale),
                            raceNameplate.elementWidth(), raceNameplate.elementHeight(),
                            font.width(Component.translatable("menu.mmm.race_selection.no_race")), font.lineHeight);
                });

        public static TextScreenElement<RaceSelectionScreen> screenName = new TextScreenElement<>(
                XAlignment.CENTER, YAlignment.RELATIVE_CENTER, s -> 0, s -> 0, d -> true,
                s(Component.translatable("menu.mmm.race_selection")), 1.5f,
                screen -> {
                    double guiScale = screen.getMinecraft().getWindow().getGuiScale();
                    ScreenElement.AlignmentContext parentContext = screenNameplate.context().apply(screen);
                    Font font = screen.getMinecraft().font;
                    float scale = 1.5f;
                    return new ScreenElement.AlignmentContext(screen.width, screen.height,
                            screenNameplate.xPos(parentContext, guiScale), screenNameplate.yPos(parentContext, guiScale),
                            screenNameplate.elementWidth(), screenNameplate.elementHeight(),
                            Math.round(font.width(Component.translatable("menu.mmm.race_selection")) * scale),
                            Math.round(font.lineHeight * scale));
                });


        public static TextureScreenElement<RaceSelectionScreen> title() {return title;}
        public static TextureScreenElement<RaceSelectionScreen> screenNameplate() {return screenNameplate;}
        public static TextureScreenElement<RaceSelectionScreen> raceNameplate() {return raceNameplate;}
        public static TextureScreenElement<RaceSelectionScreen> raceCard() {return raceCard;}

        public static TextScreenElement<RaceSelectionScreen> raceName() {return raceName;}
        public static TextScreenElement<RaceSelectionScreen> screenName() {return screenName;}
    }

    private static <T> T swapForScale(Double scale, T first, T second, T third, T fourth, T def) {
        T toReturn = def;
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
                    ScreenElements.class.getName() +
                    " found it isn't!");
        }
        return toReturn;
    }
}
