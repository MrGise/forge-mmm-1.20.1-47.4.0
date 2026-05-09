package net.MrGise.floating.screen;

import it.unimi.dsi.fastutil.doubles.Double2BooleanFunction;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class TextScreenElement<T extends Screen> extends ScreenElement<T>{
    private final Supplier<Component> text;
    private final int color;

    private final float scale;

    public TextScreenElement(XAlignment xAlignment, YAlignment yAlignment,
                             ToIntFunction<Double> xOffset, ToIntFunction<Double> yOffset,
                             Double2BooleanFunction displayCondition, Supplier<Component> text, int color, float scale,
                             Function<T, AlignmentContext> context) {
        super(xAlignment, yAlignment, xOffset, yOffset, displayCondition, context);
        this.text = text;
        this.color = color;
        this.scale = scale;
    }
    public TextScreenElement(XAlignment xAlignment, YAlignment yAlignment,
                             ToIntFunction<Double> xOffset, ToIntFunction<Double> yOffset,
                             Double2BooleanFunction displayCondition, Supplier<Component> text, int color,
                             Function<T, AlignmentContext> context) {
        super(xAlignment, yAlignment, xOffset, yOffset, displayCondition, context);
        this.text = text;
        this.color = color;
        this.scale = 1f;
    }
    public TextScreenElement(XAlignment xAlignment, YAlignment yAlignment,
                             ToIntFunction<Double> xOffset, ToIntFunction<Double> yOffset,
                             Double2BooleanFunction displayCondition, Supplier<Component> text, float scale,
                             Function<T, AlignmentContext> context) {
        super(xAlignment, yAlignment, xOffset, yOffset, displayCondition, context);
        this.text = text;
        this.color = 0xFFFFFF;
        this.scale = scale;
    }
    public TextScreenElement(XAlignment xAlignment, YAlignment yAlignment,
                             ToIntFunction<Double> xOffset, ToIntFunction<Double> yOffset,
                             Double2BooleanFunction displayCondition,Supplier<Component> text,
                             Function<T, AlignmentContext> context) {
        super(xAlignment, yAlignment, xOffset, yOffset, displayCondition, context);
        this.text = text;
        this.color = 0xFFFFFF;
        this.scale = 1f;
    }

    @Override
    public void render(GuiGraphics gui, T screen, int mouseX, int mouseY, float partialTick) {
        double guiScale = screen.getMinecraft().getWindow().getGuiScale();
        if (this.displayCondition.test(guiScale)) {
            AlignmentContext usableContext = this.context.apply(screen);
            if (scale != 1f) {
                gui.pose().pushPose();

                gui.pose().scale(scale, scale, 1f);
                gui.drawString(screen.getMinecraft().font, this.text.get(),
                        (int)(this.xPos(usableContext, guiScale) / scale),
                        (int)(this.yPos(usableContext, guiScale) / scale), color);

                gui.pose().popPose();
            } else {
                gui.drawString(screen.getMinecraft().font, this.text.get(), this.xPos(usableContext, guiScale), this.yPos(usableContext, guiScale), color);
            }
        }
    }
}
