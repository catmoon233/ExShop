package net.exmo.ex_shop.content.moeny;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class MoneyHandle {

    @Mod.EventBusSubscriber
    public static class CommonEventHandle{
        @SubscribeEvent
        public static void onPlayerGetNewItem(
                net.minecraftforge.event.entity.player.PlayerEvent.ItemPickupEvent event
        ){

        }
    }
}
