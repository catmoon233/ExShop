package net.exmo.ex_shop.datagen;

import net.exmo.ex_shop.Exshop;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.exmo.ex_shop.Exshop;
import net.exmo.ex_shop.init.ESItems;

public class ModItemModelGen extends ItemModelProvider{
    public static final String GENERATED = "item/generated";
    public static final String HANDHELD = "item/handheld";
    public static final String EGG_TEMPLATE = "item/template_spawn_egg";

    public ModItemModelGen(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Exshop.MODID, existingFileHelper);
    }
    @Override
    protected void registerModels() {
        //itemGeneratedModel(ModItems.WITHERED.get(), resourceItem(itemName(ModItems.WITHERED.get())));
        itemGeneratedModel(ESItems.IRON_COIN.get(), resourceItem(itemName(ESItems.IRON_COIN.get())));
        itemGeneratedModel(ESItems.COPPER_COIN.get(), resourceItem(itemName(ESItems.COPPER_COIN.get())));
        itemGeneratedModel(ESItems.GOLDEN_COIN.get(), resourceItem(itemName(ESItems.GOLDEN_COIN.get())));
        itemGeneratedModel(ESItems.DIAMOND_COIN.get(), resourceItem(itemName(ESItems.DIAMOND_COIN.get())));
        itemGeneratedModel(ESItems.EMERALD_COIN.get(), resourceItem(itemName(ESItems.EMERALD_COIN.get())));
        itemGeneratedModel(ESItems.ENDER_COIN.get(), resourceItem(itemName(ESItems.ENDER_COIN.get())));
        itemGeneratedModel(ESItems.NETHER_STAR_COIN.get(), resourceItem(itemName(ESItems.NETHER_STAR_COIN.get())));
        itemGeneratedModel(ESItems.Leather_Money_Bag.get(), resourceItem(itemName(ESItems.Leather_Money_Bag.get())));
        itemGeneratedModel(ESItems.Iron_Money_Bag.get(), resourceItem(itemName(ESItems.Iron_Money_Bag.get())));
        itemGeneratedModel(ESItems.Golden_Money_Bag.get(), resourceItem(itemName(ESItems.Golden_Money_Bag.get())));
        itemGeneratedModel(ESItems.Diamond_Money_Bag.get(), resourceItem(itemName(ESItems.Diamond_Money_Bag.get())));
        itemGeneratedModel(ESItems.Ender_Money_Bag.get(), resourceItem(itemName(ESItems.Ender_Money_Bag.get())));
        itemGeneratedModel(ESItems.End_Money_Bag.get(), resourceItem(itemName(ESItems.End_Money_Bag.get())));
    }
    public void itemGeneratedModel(Item item, ResourceLocation texture) {
        withExistingParent(itemName(item), GENERATED).texture("layer0", texture);
    }

    private String itemName(Item item) {
        return ForgeRegistries.ITEMS.getKey(item).getPath();
    }

    public ResourceLocation resourceItem(String path) {
        return ResourceLocation.tryBuild(Exshop.MODID, "item/" + path);
    }
}