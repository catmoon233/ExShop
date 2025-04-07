package net.exmo.ex_shop.network;

import net.exmo.ex_shop.render.ExOverlay;
import net.exmo.ex_shop.util.MoneyHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public record PlayerAddMoneyMessage(int money, ItemStack item, Component reason) {
    public static void encode(PlayerAddMoneyMessage msg, FriendlyByteBuf buffer) {
        buffer.writeInt(msg.money);
        buffer.writeItem(msg.item);
        buffer.writeComponent(msg.reason);

    }
    public static PlayerAddMoneyMessage decode(FriendlyByteBuf buffer) {
        return new PlayerAddMoneyMessage(
                buffer.readInt(),
                buffer.readItem()
                , buffer.readComponent()
        );
    }

    public static void handle(PlayerAddMoneyMessage msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ExOverlay.addOverlay(System.currentTimeMillis(), msg.money, msg.item, msg.reason);
        });
        ctx.get().setPacketHandled(true);
    }
}
