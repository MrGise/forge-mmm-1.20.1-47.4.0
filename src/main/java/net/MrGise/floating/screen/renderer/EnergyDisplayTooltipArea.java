package net.MrGise.floating.screen.renderer;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.List;

public class EnergyDisplayTooltipArea {
    private final int minX;
    private final int minY;
    private final int maxX;
    private final int maxY;
    private final IEnergyStorage energy;

    public EnergyDisplayTooltipArea(int minX, int minY, int maxX, int maxY, IEnergyStorage energy) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
        this.energy = energy;
    }

    public List<Component> getTooltips() {
        return List.of(Component.literal(energy.getEnergyStored() + " / " + energy.getMaxEnergyStored() + " FE"));
    }

    public void render(GuiGraphics guiGraphics) {
        int stored = (int)(maxY * (energy.getEnergyStored() / (float)energy.getMaxEnergyStored()));
        guiGraphics.fillGradient(minX, minY + (maxY - stored), minX + maxX,
                minY + maxY, 0xffb51500, 0xff600b00);
    }
}
