package net.MrGise.floating.screen;

import it.unimi.dsi.fastutil.doubles.Double2BooleanFunction;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;

import java.util.function.Function;
import java.util.function.ToIntFunction;

public abstract class ScreenElement<T extends Screen> {
    @FunctionalInterface
    public interface XAlignFunction {
        int alignment(AlignmentContext context);
    }
    @FunctionalInterface
    public interface YAlignFunction {
        int alignment(AlignmentContext context);
    }

    public enum XAlignment {
        LEFT(ctx -> 0),
        CENTER(ctx -> (ctx.screenWidth() - ctx.elementWidth()) / 2),
        RIGHT(ctx -> ctx.screenWidth() - ctx.elementWidth()),

        RELATIVE_LEFT(AlignmentContext::parentX),
        RELATIVE_CENTER(ctx -> ctx.parentX() + (ctx.parentWidth() - ctx.elementWidth()) / 2),
        RELATIVE_RIGHT(ctx -> ctx.parentX() + ctx.parentWidth() - ctx.elementWidth()),

        RELATIVE_LEFT_ATTACHED(ctx -> ctx.parentX() - ctx.elementWidth()),
        RELATIVE_RIGHT_ATTACHED(ctx -> ctx.parentX() + ctx.parentWidth()),;

        /**
         * This field determines an X position depending on the {@link net.MrGise.floating.screen.ScreenElement.AlignmentContext} provided.
         */
        public final XAlignFunction function;
        XAlignment(XAlignFunction function) {
            this.function = function;
        }
    }
    public enum YAlignment {
        TOP(ctx -> 0),
        CENTER(ctx -> (ctx.screenHeight() - ctx.elementHeight()) / 2),
        BOTTOM(ctx -> ctx.screenHeight() - ctx.elementHeight()),

        RELATIVE_TOP(AlignmentContext::parentY),
        RELATIVE_CENTER(ctx -> ctx.parentY() + (ctx.parentHeight() - ctx.elementHeight()) / 2),
        RELATIVE_BOTTOM(ctx -> ctx.parentY() + ctx.parentHeight() - ctx.elementHeight()),

        RELATIVE_ABOVE(ctx -> ctx.parentY() - ctx.elementHeight()),
        RELATIVE_BELOW(ctx -> ctx.parentY() + ctx.parentHeight());

        /**
         * This function determines a Y position depending on the screen's height (first int)
         * and the height of the element that needs to be aligned (second int).
         */
        public final YAlignFunction function;
        YAlignment(YAlignFunction function) {
            this.function = function;
        }
    }

    final XAlignment xAlignment;
    final YAlignment yAlignment;
    final ToIntFunction<Double> xOffset;
    final ToIntFunction<Double> yOffset;

    final Double2BooleanFunction displayCondition;

    public Function<T, AlignmentContext> context() {return this.context;}

    public Double2BooleanFunction displayCondition() {return this.displayCondition;}
    public ToIntFunction<Double> yOffset() {return this.yOffset;}
    public ToIntFunction<Double> xOffset() {return this.xOffset;}

    public YAlignment yAlignment() {return this.yAlignment;}
    public XAlignment xAlignment() {return this.xAlignment;}

    final Function<T, AlignmentContext> context;

    public ScreenElement(XAlignment xAlignment, YAlignment yAlignment,
                         ToIntFunction<Double> xOffset, ToIntFunction<Double> yOffset,
                         Double2BooleanFunction displayCondition, Function<T, AlignmentContext> context) {
        this.xAlignment = xAlignment;
        this.yAlignment = yAlignment;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.displayCondition = displayCondition;
        this.context = context;
    }

    public int xPos(AlignmentContext context, double guiScale) {
        return this.xAlignment.function.alignment(context) + this.xOffset.applyAsInt(guiScale);
    };
    public int yPos(AlignmentContext context, double guiScale) {
        return this.yAlignment.function.alignment(context) + this.yOffset.applyAsInt(guiScale);
    };

    public abstract void render(GuiGraphics gui, T screen, int mouseX, int mouseY, float partialTick);

    public record AlignmentContext(int screenWidth, int screenHeight, int parentX, int parentY,
                                   int parentWidth, int parentHeight, int elementWidth, int elementHeight) {
        public AlignmentContext(int screenWidth, int screenHeight, int elementWidth, int elementHeight) {
            this(screenWidth, screenHeight, 0, 0, screenWidth, screenHeight, elementWidth, elementHeight);
        }
    }

    public abstract int width(T screen);
    public abstract int height(T screen);
}
