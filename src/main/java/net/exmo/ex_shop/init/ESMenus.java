package net.exmo.ex_shop.init;

import net.exmo.ex_shop.Exshop;
import net.exmo.ex_shop.gui.MoneyBagMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ESMenus {
    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Exshop.MODID);
    public static final RegistryObject<MenuType<MoneyBagMenu>> MONEY_BAG_MENU = REGISTRY.register("money_bag_menu", () -> IForgeMenuType.create(MoneyBagMenu::new));
}
