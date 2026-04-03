package net.MrGise.mmm.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import net.MrGise.mmm.MMM;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class JEIMMMPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(MMM.MOD_ID, "jei_plugin");
    }
}
