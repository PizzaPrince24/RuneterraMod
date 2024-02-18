package com.pizzaprince.runeterramod.client;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.block.custom.SunForge;
import com.pizzaprince.runeterramod.client.screen.SunDiskAltarMenu;
import com.pizzaprince.runeterramod.client.screen.SunForgeMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, RuneterraMod.MOD_ID);

    public static final RegistryObject<MenuType<SunDiskAltarMenu>> SUN_DISK_ALTAR_MENU = registerMenuType(SunDiskAltarMenu::new, "sun_disk_altar_menu");

    public static final RegistryObject<MenuType<SunForgeMenu>> SUN_FORGE_MENU = registerMenuType(SunForgeMenu::new, "sun_forge_menu");

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory,
                                                                                                  String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }
    public static void register(IEventBus eventBus){
        MENUS.register(eventBus);
    }
}
