
package net.exmo.ex_shop.content.items.inventory;

import net.exmo.ex_shop.content.items.MoneyBag;
import net.exmo.ex_shop.content.items.MoneyItem;
import net.exmo.ex_shop.gui.screen.MoneyBagScreen;
import net.exmo.ex_shop.init.ESItems;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class MoneyBagInventoryCapability implements ICapabilitySerializable<CompoundTag> {
	public MoneyBagInventoryCapability(int size) {
		this.inventory =  LazyOptional.of(() -> createItemHandler(size));

	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void onItemDropped(ItemTossEvent event) {
		if (event.getEntity().getItem().getItem() == ESItems.Leather_Money_Bag.get()) {
			if (Minecraft.getInstance().screen instanceof MoneyBagScreen) {
				Minecraft.getInstance().player.closeContainer();
			}
		}
	}

	private final LazyOptional<ItemStackHandler> inventory ;

	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
		return capability == ForgeCapabilities.ITEM_HANDLER ? this.inventory.cast() : LazyOptional.empty();
	}

	@Override
	public CompoundTag serializeNBT() {
		return getItemHandler().serializeNBT();
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		getItemHandler().deserializeNBT(nbt);
	}

	private ItemStackHandler createItemHandler(int size) {
		return new ItemStackHandler(size) {
			@Override
			public int getSlotLimit(int slot) {
				return 100;
			}

			@Override
			public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.getItem() instanceof MoneyItem moneyItem;
            }

			@Override
			public void setSize(int size) {
				super.setSize(size);

			}
		};
	}

	private ItemStackHandler getItemHandler() {
		return inventory.orElseThrow(RuntimeException::new);
	}
}
