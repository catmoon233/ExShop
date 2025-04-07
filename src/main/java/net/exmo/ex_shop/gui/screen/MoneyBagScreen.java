package net.exmo.ex_shop.gui.screen;

import java.util.HashMap;

import net.exmo.ex_shop.gui.MoneyBagMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.GuiGraphics;
import com.mojang.blaze3d.systems.RenderSystem;

public class MoneyBagScreen  extends AbstractContainerScreen<MoneyBagMenu> {
    private final static HashMap<String, Object> guistate = MoneyBagMenu.guistate;
    private final Level world;
    private final int x, y, z;
    private final Player entity;
    private final HashMap<String, String> textstate = new HashMap<>();
    private final int slotCount ;

    public MoneyBagScreen(MoneyBagMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.world = container.world;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.entity = container.entity;
        this.imageWidth = 176;
        this.imageHeight = 166;
        slotCount = container.slotCount;
    }

    private static final ResourceLocation back_round = ResourceLocation.tryParse("ex_shop:textures/screens/money_bag_gui.png");
    private static final ResourceLocation slot = ResourceLocation.tryParse("ex_shop:textures/screens/slot.png");

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        guiGraphics.blit(back_round, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        int sx = leftPos;
        int sy = topPos;
        for (int i = 0; i < slotCount; i++){
            guiGraphics.blit(slot,sx,sy,0,0,18,18,18,18);
            if ((i+1)%9 ==0){
                sx = leftPos;
                sy+=18;
            }else {
                sx+=18;
            }
        }
        RenderSystem.disableBlend();
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == 256) {
            this.minecraft.player.closeContainer();
            return true;
        }
        return super.keyPressed(key, b, c);
    }

    @Override
    public void containerTick() {
        super.containerTick();
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    }

    @Override
    public void init() {
        super.init();
    }
}
