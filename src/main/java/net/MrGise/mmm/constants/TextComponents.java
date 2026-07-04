package net.MrGise.mmm.constants;

import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;

public class TextComponents {
    public static Component CONFIG_TITLE = Component.translatable("screen.config.mmm.title");
    public static Component CLIENT_CONFIG_TITLE = Component.translatable("screen.config.mmm.label.client");
    public static Component COMMON_CONFIG_TITLE = Component.translatable("screen.config.mmm.label.common");
    public static Component SERVER_CONFIG_TITLE = Component.translatable("screen.config.mmm.label.server");
    public static Component CLIENT_CONFIG_DESC = Component.translatable("screen.config.mmm.tooltip.client");
    public static Component COMMON_CONFIG_DESC = Component.translatable("screen.config.mmm.tooltip.common");
    public static Component SERVER_CONFIG_DESC = Component.translatable("screen.config.mmm.tooltip.server");
    public static Tooltip CLIENT_CONFIG_TOOLTIP = Tooltip.create(CLIENT_CONFIG_DESC);
    public static Tooltip COMMON_CONFIG_TOOLTIP = Tooltip.create(COMMON_CONFIG_DESC);
    public static Tooltip SERVER_CONFIG_TOOLTIP = Tooltip.create(SERVER_CONFIG_DESC);

    public static Component CONFIG_EXIT = Component.translatable("screen.config.mmm.label.exit");
    public static Component CONFIG_PAGE_EXIT = Component.translatable("screen.config.mmm.label.setting_exit");
}
