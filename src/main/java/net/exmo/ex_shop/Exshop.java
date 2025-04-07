package net.exmo.ex_shop;

import com.mojang.logging.LogUtils;
import net.exmo.ex_shop.init.ESItems;
import net.exmo.ex_shop.init.ESMenus;
import net.exmo.ex_shop.init.ESSoundRegistry;
import net.exmo.ex_shop.network.PlayerAddMoneyMessage;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Exshop.MODID)
public class Exshop {
    public static final String MODID = "ex_shop";
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel( ResourceLocation.tryBuild(MODID, MODID), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
    private static int messageID = 0;
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public final static  RegistryObject<CreativeModeTab> ExModifierTab =  CREATIVE_MODE_TABS.register("legendary_slash_tab", () -> CreativeModeTab.builder()
            .icon(Exshop::getTabIcon)
            .withSearchBar()
            .title(Component.translatable("itemGroup.ex_shop_tab"))
            .displayItems((parameters, output) -> {
                for (var a : ESItems.ITEMS.getEntries().stream().toList()){
                    output.accept(a.get());
                }
            }).build());

    private static ItemStack getTabIcon() {

        return new ItemStack(ESItems.IRON_COIN.get());
    }
    public Exshop() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();


        CREATIVE_MODE_TABS.register(modEventBus);
        networkInit();
        ESSoundRegistry.register(modEventBus);
        ESMenus.REGISTRY.register(modEventBus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
    public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
        PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
        messageID++;
    }
    public static void networkInit(){
        PACKET_HANDLER.messageBuilder(PlayerAddMoneyMessage.class, messageID++)
                .encoder(PlayerAddMoneyMessage::encode)
                .decoder(PlayerAddMoneyMessage::decode)
                .consumerMainThread(PlayerAddMoneyMessage::handle)
                .add();
    }

}
