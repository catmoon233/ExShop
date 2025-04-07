package net.exmo.ex_shop.datagen;

import net.exmo.ex_shop.Exshop;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;


@Mod.EventBusSubscriber(modid = Exshop.MODID,bus= Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        ExistingFileHelper helper = event.getExistingFileHelper();
        generator.addProvider(event.includeClient(), new ModItemModelGen(output, helper));//物品模型
        generator.addProvider(event.includeClient(), new ItemLangGen(output, "en_us"));//英语语言文件生成
        generator.addProvider(event.includeClient(), new ItemLang2Gen(output, "zh_cn"));//中文语言文件生成
    }
}
