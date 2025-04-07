package net.exmo.ex_shop.util;

import net.exmo.ex_shop.Exshop;
import net.exmo.ex_shop.init.ESSoundRegistry;
import net.exmo.ex_shop.network.ExShopVar;
import net.exmo.ex_shop.network.PlayerAddMoneyMessage;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.network.PacketDistributor;

public class MoneyHelper {
    private ExShopVar.PlayerVariables playerVariables;

    public Player getPlayer() {
        return player;
    }

    public ExShopVar.PlayerVariables getPlayerVariables() {
        return playerVariables;
    }

    private Player player;
    public static MoneyHelper of(Player player){
        return new MoneyHelper(player);
    }
    public MoneyHelper(Player player){
        this.player = player;
        playerVariables =  player.getCapability(ExShopVar.PLAYER_VARIABLES_CAPABILITY, null).orElse(new ExShopVar.PlayerVariables());

    }
    public MoneyHelper setMoney(int money){
        playerVariables.money = money;
        player.getCapability(ExShopVar.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(data -> {
            data.money = money;
        });
        playerVariables.syncPlayerVariables(player);
        return this;
    }
    public MoneyHelper addMoney(int money, Item item,boolean display,Component reasonLocation){
        playerVariables.money += money;
        player.getCapability(ExShopVar.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(data -> {
            data.money += money;
        });
        playerVariables.syncPlayerVariables(player);
        if (display) {
            if (player instanceof ServerPlayer) {
                ItemStack defaultInstance = item.getDefaultInstance();
                sendAddMoneyMessage((ServerPlayer) player, money, defaultInstance,reasonLocation);
            }
        }
        return this;
    }
    public MoneyHelper addMoney(int money,boolean display,Component reasonLocation){
        addMoney(money, Items.AIR,display,reasonLocation);
        return this;
    }
    public MoneyHelper addMoney(int money,boolean display){
        addMoney(money, Items.AIR,display,Component.translatable("ex_shop.reason.none"));
        return this;
    }
    public  void sendAddMoneyMessage(ServerPlayer player, int money, ItemStack item,Component reason) {
        PlayerAddMoneyMessage message = new PlayerAddMoneyMessage(money,item,reason);
        if (player.level().isClientSide)return;
        player.playNotifySound(ESSoundRegistry.GET_MONEY.get(), player.getSoundSource(), 1.0F, 1.0F);
        Exshop.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), message);
    }

}
