package net.MrGise.mmm.registry.variables;

import net.MrGise.mmm.MMM;
import net.MrGise.mmm.screen.bowyery_table.BowyeryTableMenu;
import net.MrGise.mmm.screen.thingamajig.ThingamajigMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.MrGise.floating.helper.Methods.*;

// GUI types
public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, MMM.MOD_ID);


    public static final RegistryObject<MenuType<ThingamajigMenu>> THINGAMAJIG_MENU =
            registerMenuType(ThingamajigMenu::new, "thingamajig_menu");

    public static final RegistryObject<MenuType<BowyeryTableMenu>> BOWYERY_TABLE_MENU =
            registerMenuType(BowyeryTableMenu::new, "bowyery_table_menu");


    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
        return MENUS.register(name, s(IForgeMenuType.create(factory)));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
