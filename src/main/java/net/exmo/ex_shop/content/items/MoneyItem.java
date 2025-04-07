package net.exmo.ex_shop.content.items;

import net.exmo.ex_shop.util.MoneyHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class MoneyItem extends Item {
    private final int money ;
    public MoneyItem(Properties p_41383_, int money) {
        super(p_41383_);
        this.money = money;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level p_41405_, Entity p_41406_, int p_41407_, boolean p_41408_) {
        super.inventoryTick(stack, p_41405_, p_41406_, p_41407_, p_41408_);
        if (p_41406_ instanceof ServerPlayer serverPlayer) {
            Optional<ItemStack> first = serverPlayer.getInventory().items.stream().filter(
                    itemStack -> itemStack.getItem() instanceof MoneyBag
            ).findFirst();
            if (first.isPresent()) {
                ItemStack itemStack = first.get();
                if (itemStack.getItem() instanceof MoneyBag moneyBag) {
                    boolean b = moneyBag.tryAddItem(itemStack, stack);
                    if (!b) {
                        return;
                    }
                    Component displayName = getName(stack);
                    displayName = Component.empty().append(displayName).append("x" + stack.getCount());
                    MoneyHelper.of(serverPlayer).addMoney(money * stack.getCount(), Items.AIR, true, displayName);
                    stack.shrink(stack.getCount());
                }
            }
        }

    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, level, components, tooltipFlag);
        components.add(Component.translatable("tooltip.ex_shop.money_item.value", money));
    }
}
