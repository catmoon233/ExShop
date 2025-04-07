package net.exmo.ex_shop.datagen;

import net.exmo.ex_shop.Exshop;
import net.exmo.ex_shop.init.ESItems;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraft.data.PackOutput;

public class ItemLang2Gen extends LanguageProvider {
    public ItemLang2Gen(PackOutput output, String locale) {
        super(output, Exshop.MODID, locale);
    }


    @Override
    protected void addTranslations() {
        // 已有的物品翻译
        add(ESItems.IRON_COIN.get(), "铁币");
        add(ESItems.COPPER_COIN.get(), "铜币");
        add(ESItems.GOLDEN_COIN.get(), "金币");
        add(ESItems.DIAMOND_COIN.get(), "钻石币");
        add(ESItems.EMERALD_COIN.get(), "绿宝石币");
        add(ESItems.ENDER_COIN.get(), "末影币");
        add(ESItems.NETHER_STAR_COIN.get(), "下界星币");
        add(ESItems.Leather_Money_Bag.get(), "皮革钱包");
        add(ESItems.Iron_Money_Bag.get(), "铁钱包");
        add(ESItems.Golden_Money_Bag.get(), "金钱包");
        add(ESItems.Diamond_Money_Bag.get(), "钻石钱包");
        add(ESItems.Ender_Money_Bag.get(), "末影钱包");
        add(ESItems.End_Money_Bag.get(), "终极钱包");

        // 新增的翻译内容
        add("ex_shop.overlay.get_money", "获得 %s 金币");
        add("ex_shop.reason.none", "无原因");
        add("itemGroup.ex_shop_tab", "极墨市场");
        add("tooltip.ex_shop.money_item.value", "§e价值: %s");
        add("curios.identifier.money_bag", "钱包");
    }

}