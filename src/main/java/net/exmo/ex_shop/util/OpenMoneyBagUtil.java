package net.exmo.ex_shop.util;

import net.exmo.ex_shop.network.ExShopVar;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class OpenMoneyBagUtil {
	public static void open(double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof ServerPlayer _serverPlayer) {
			ExShopVar.PlayerVariables v =(_serverPlayer.getCapability(ExShopVar.PLAYER_VARIABLES_CAPABILITY, null).orElse(new ExShopVar.PlayerVariables()));

			ItemStack itemStack = v.money_bag;
			if (itemStack.getItem() instanceof net.exmo.ex_shop.content.items.MoneyBag moneyBag){
				moneyBag.use(entity.level(), _serverPlayer, InteractionHand.MAIN_HAND);
			}

        }
			}
}
