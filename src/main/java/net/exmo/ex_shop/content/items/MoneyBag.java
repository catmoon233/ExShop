package net.exmo.ex_shop.content.items;

import io.netty.buffer.Unpooled;
import net.exmo.ex_shop.content.items.inventory.MoneyBagInventoryCapability;
import net.exmo.ex_shop.gui.MoneyBagMenu;
import net.exmo.ex_shop.network.ExShopVar;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.items.IItemHandler;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.SlotContext;

import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.nbt.CompoundTag;

import javax.annotation.Nullable;

public class MoneyBag extends Item implements ICurioItem {
    public int slotCount;
    public MoneyBag(Properties p_41383_,int slotCount) {
        super(p_41383_);
        this.slotCount = slotCount;
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        ItemStack itemstack = ar.getObject();
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();
        if (entity instanceof ServerPlayer serverPlayer) {
            NetworkHooks.openScreen(serverPlayer, new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return getName(itemstack);
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                    FriendlyByteBuf packetBuffer = new FriendlyByteBuf(Unpooled.buffer());
                    packetBuffer.writeBlockPos(entity.blockPosition());
                    packetBuffer.writeVarInt(slotCount);
                    packetBuffer.writeByte(hand == InteractionHand.MAIN_HAND ? 0 : 1);
                    MoneyBagMenu moneyBagMenu = new MoneyBagMenu(id, inventory, packetBuffer);

                    return moneyBagMenu;
                }
            }, buf -> {
                buf.writeBlockPos(entity.blockPosition());
                buf.writeVarInt(slotCount);
                buf.writeByte(hand == InteractionHand.MAIN_HAND ? 0 : 1);

            });
        }
        return ar;
    }


    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        {
            extracted(stack, slotContext);
        }
    }
    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        extracted(ItemStack.EMPTY, slotContext);
    }
// 在 MoneyBag 类中添加以下方法

    /**
     * 判断钱袋是否已满
     */
    public boolean isFull(ItemStack bagStack) {
        return bagStack.getCapability(ForgeCapabilities.ITEM_HANDLER)
                .map(handler -> {
                    for (int i = 0; i < handler.getSlots(); i++) {
                        if (handler.getStackInSlot(i).isEmpty()) {
                            return false; // 发现空槽位
                        }
                    }
                    return true; // 所有槽位已满
                })
                .orElse(true); // 没有能力时视为已满
    }

    /**
     * 尝试添加物品到钱袋（自动堆叠）
     * @return 是否添加成功
     */
    public boolean tryAddItem(ItemStack bagStack, ItemStack toAdd) {
        return bagStack.getCapability(ForgeCapabilities.ITEM_HANDLER)
                .map(handler -> {
                    // 先尝试合并到已有堆叠
                    for (int i = 0; i < handler.getSlots() && !toAdd.isEmpty(); i++) {
                        ItemStack slotStack = handler.getStackInSlot(i);

                        if (ItemStack.isSameItemSameTags(slotStack, toAdd)) {
                            int maxSize = slotStack.getMaxStackSize();
                            int canAccept = maxSize - slotStack.getCount();
                            int transfer = Math.min(canAccept, toAdd.getCount());

                            if (transfer > 0) {
                                slotStack.grow(transfer);
                                toAdd.shrink(transfer);
                               // handler.onContentsChanged(i);
                            }
                        }
                    }

                    // 剩余物品放入空槽
                    if (!toAdd.isEmpty()) {
                        for (int i = 0; i < handler.getSlots(); i++) {
                            if (handler.getStackInSlot(i).isEmpty()) {
                                handler.insertItem(i, toAdd, false);
                                return toAdd.isEmpty(); // 完全插入返回true
                            }
                        }
                    }

                    return toAdd.isEmpty(); // 是否完全添加成功
                })
                .orElse(false);
    }


    public static void extracted(ItemStack empty, SlotContext slotContext) {
        ItemStack _setval = empty;
        LivingEntity entity = slotContext.entity();
        extracted(entity, _setval);
    }

    public static void extracted(LivingEntity entity, ItemStack _setval) {
        entity.getCapability(ExShopVar.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
            capability.money_bag = _setval;
            capability.syncPlayerVariables(entity);
        });
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag compound) {
        return new MoneyBagInventoryCapability(slotCount);
    }

    @Override
    public CompoundTag getShareTag(ItemStack stack) {
        CompoundTag nbt = super.getShareTag(stack);
        if (nbt != null)
            stack.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> nbt.put("Inventory", ((ItemStackHandler) capability).serializeNBT()));
        return nbt;
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
        super.readShareTag(stack, nbt);
        if (nbt != null)
            stack.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> ((ItemStackHandler) capability).deserializeNBT((CompoundTag) nbt.get("Inventory")));
    }
}
