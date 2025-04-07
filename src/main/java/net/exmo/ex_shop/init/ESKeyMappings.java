
package net.exmo.ex_shop.init;


import net.exmo.ex_shop.Exshop;
import net.exmo.ex_shop.network.OpenMoneyBagMessage;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class ESKeyMappings {
	public static final KeyMapping OPEN_MONEY_BAG = new KeyMapping("key.ex_shop.open_money_bag", GLFW.GLFW_KEY_LEFT_SUPER, "key.categories.misc") {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				Exshop.PACKET_HANDLER.sendToServer(new OpenMoneyBagMessage(0, 0));
				OpenMoneyBagMessage.pressAction(Minecraft.getInstance().player, 0, 0);
			}
			isDownOld = isDown;
		}
	};


	@SubscribeEvent
	public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
		event.register(OPEN_MONEY_BAG);
	}

	@Mod.EventBusSubscriber({Dist.CLIENT})
	public static class KeyEventListener {
		@SubscribeEvent
		public static void onClientTick(TickEvent.ClientTickEvent event) {
			if (Minecraft.getInstance().screen == null) {
				OPEN_MONEY_BAG.consumeClick();
			}
		}
	}
}
