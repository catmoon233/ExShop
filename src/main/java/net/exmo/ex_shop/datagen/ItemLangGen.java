package net.exmo.ex_shop.datagen;

import net.exmo.ex_shop.Exshop;
import net.exmo.ex_shop.init.ESItems;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraft.data.PackOutput;

public class ItemLangGen extends LanguageProvider {
    public ItemLangGen(PackOutput output, String locale) {
        super(output, Exshop.MODID, locale);
    }


    @Override
    protected void addTranslations() {
        // 已有的物品翻译
        add(ESItems.IRON_COIN.get(), "iron coin");
        add(ESItems.COPPER_COIN.get(), "copper coin");
        add(ESItems.GOLDEN_COIN.get(), "golden coin");
        add(ESItems.DIAMOND_COIN.get(), "diamond coin");
        add(ESItems.EMERALD_COIN.get(), "emerald coin");
        add(ESItems.ENDER_COIN.get(), "ender coin");
        add(ESItems.NETHER_STAR_COIN.get(), "nether star coin");
        add(ESItems.Leather_Money_Bag.get(), "leather money bag");
        add(ESItems.Iron_Money_Bag.get(), "iron money bag");
        add(ESItems.Golden_Money_Bag.get(), "gold money bag");
        add(ESItems.Diamond_Money_Bag.get(), "diamond money bag");
        add(ESItems.Ender_Money_Bag.get(), "ender money bag");
        add(ESItems.End_Money_Bag.get(), "end money bag");

        // 新增的翻译内容
        add("ex_shop.overlay.get_money", "get money %s");
        add("ex_shop.reason.none", "no reason");
        add("itemGroup.ex_shop_tab", "exmo shop");
        add("curios.identifier.money_bag", "Money Bag");
        add("tooltip.ex_shop.money_item.value", "§eValue: %s");
    }
}
