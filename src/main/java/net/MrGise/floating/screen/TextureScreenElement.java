package net.MrGise.floating.screen;

import it.unimi.dsi.fastutil.doubles.Double2BooleanFunction;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;
import java.util.function.ToIntFunction;

public class TextureScreenElement<T extends Screen> extends ScreenElement<T> {
    private final ResourceLocation texture;
    private final int uStart;
    private final int vStart;
    private final int elementWidth;
    private final int elementHeight;

    private final int textureWidth;
    private final int textureHeight;

    public ResourceLocation texture() {return this.texture;}

    public int uStart() {return this.uStart;}
    public int vStart() {return this.vStart;}
    public int uEnd() {return this.uStart + this.elementWidth;}
    public int vEnd() {return this.vStart + this.elementHeight;}

    public int elementWidth() {return this.elementWidth;}
    public int elementHeight() {return this.elementHeight;}
    public int textureWidth() {return this.textureWidth;}
    public int textureHeight() {return this.textureHeight;}

    public TextureScreenElement(XAlignment xAlignment, YAlignment yAlignment,
                                ToIntFunction<Double> xOffset, ToIntFunction<Double> yOffset,
                                Double2BooleanFunction displayCondition, ResourceLocation texture, int uStart, int vStart,
                                int elementWidth, int elementHeight,
                                int textureWidth, int textureHeight, Function<T, AlignmentContext> context) {
        super(xAlignment, yAlignment, xOffset, yOffset, displayCondition, context);
        this.texture = texture;
        this.uStart = uStart;
        this.vStart = vStart;
        this.elementWidth = elementWidth;
        this.elementHeight = elementHeight;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    @Override
    public void render(GuiGraphics gui, T screen, int mouseX, int mouseY, float partialTick) {
        double guiScale = screen.getMinecraft().getWindow().getGuiScale();

        if (displayCondition.test(guiScale)) {
            AlignmentContext context = this.context.apply(screen);
            gui.blit(this.texture, xPos(context, guiScale), yPos(context, guiScale),
                    this.uStart, this.vStart, this.elementWidth, this.elementHeight, this.textureWidth, this.textureHeight);
        }
    }
}
