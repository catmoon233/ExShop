package net.exmo.ex_shop.init;

import net.exmo.ex_shop.Exshop;
import net.exmo.ex_shop.content.items.MoneyBag;
import net.exmo.ex_shop.content.items.MoneyItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ESItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Exshop.MODID);
    public static final RegistryObject<MoneyItem> COPPER_COIN = ITEMS.register("copper_coin", () ->
            new MoneyItem(new Item.Properties().stacksTo(100).rarity(Rarity.COMMON).fireResistant(),1));

    public static final RegistryObject<MoneyItem> IRON_COIN = ITEMS.register("iron_coin", () ->
            new MoneyItem(new Item.Properties().stacksTo(100).rarity(Rarity.COMMON).fireResistant(),10));

    public static final RegistryObject<MoneyItem> GOLDEN_COIN = ITEMS.register("golden_coin", () ->
            new MoneyItem(new Item.Properties().stacksTo(100).rarity(Rarity.COMMON).fireResistant(),100));

    public static final RegistryObject<MoneyItem> DIAMOND_COIN = ITEMS.register("diamond_coin", () ->
            new MoneyItem(new Item.Properties().stacksTo(100).rarity(Rarity.COMMON).fireResistant(),1000));

    public static final RegistryObject<MoneyItem> EMERALD_COIN = ITEMS.register("emerald_coin", () ->
            new MoneyItem(new Item.Properties().stacksTo(100).rarity(Rarity.COMMON).fireResistant(),10000));

    public static final RegistryObject<MoneyItem> ENDER_COIN = ITEMS.register("ender_coin", () ->
            new MoneyItem(new Item.Properties().stacksTo(100).rarity(Rarity.RARE).fireResistant(),100000));

    public static final RegistryObject<MoneyItem> NETHER_STAR_COIN = ITEMS.register("nether_star_coin", () ->
            new MoneyItem(new Item.Properties().stacksTo(100).rarity(Rarity.EPIC).fireResistant(),1000000));



    public static final RegistryObject<MoneyBag> Leather_Money_Bag = ITEMS.register("leather_money_bag", () ->
            new MoneyBag(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON).fireResistant(),4));

    public static final RegistryObject<MoneyBag> Iron_Money_Bag = ITEMS.register("iron_money_bag", () ->
            new MoneyBag(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON).fireResistant(),5));

    public static final RegistryObject<MoneyBag> Golden_Money_Bag = ITEMS.register("golden_money_bag", () ->
            new MoneyBag(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON).fireResistant(),6));

    public static final RegistryObject<MoneyBag> Diamond_Money_Bag = ITEMS.register("diamond_money_bag", () ->
            new MoneyBag(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON).fireResistant(),7));

    public static final RegistryObject<MoneyBag> Ender_Money_Bag = ITEMS.register("ender_money_bag", () ->
            new MoneyBag(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON).fireResistant(),8));

    public static final RegistryObject<MoneyBag> End_Money_Bag = ITEMS.register("end_money_bag", () ->
            new MoneyBag(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON).fireResistant(),9));

    @SubscribeEvent
    public static void register(FMLConstructModEvent event) {
        event.enqueueWork(() -> {
            ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        });
    }
}
