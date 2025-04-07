package net.exmo.ex_shop.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mod.EventBusSubscriber({Dist.CLIENT})
public class ExOverlay {
    static int screenHeight;
    static int screenWidth;
    private static final Map<Long, Map.Entry<Integer, Map.Entry<Component,ItemStack>>> overlayMap = new HashMap<>();
    private static final Random random = new Random();
    public static void addOverlay( long time,int y, ItemStack item,Component reason) {
        overlayMap.put(time, Map.entry(y, Map.entry(reason,item)));
    }
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void eventHandler(RenderGuiEvent.Pre event) {
        int w = event.getWindow().getGuiScaledWidth();
        int h = event.getWindow().getGuiScaledHeight();

        Map<Long, Map.Entry<Integer, Map.Entry<Component,ItemStack>>> toRemove = new HashMap<>();
        overlayMap.forEach((k,v) -> {
            if (System.currentTimeMillis() - k > 3200) {
                toRemove.put(k,v);
            }
        });
        toRemove.forEach((k,v) -> overlayMap.remove(k));
        Level world = null;
        double x = 0;
        double y = 0;
        double z = 0;
        LocalPlayer entity = Minecraft.getInstance().player;
        if (entity != null) {
            world = entity.level();
            x = entity.getX();
            y = entity.getY();
            z = entity.getZ();
        }
        GuiGraphics gg = event.getGuiGraphics();
        Font font = Minecraft.getInstance().font;
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        //to render
        screenWidth = w;
        screenHeight = h;
        AtomicInteger line = new AtomicInteger();
        overlayMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey()) // 按时间戳排序
                .forEach(entry -> {
                    //long k = entry.getKey();
                    ItemStack itemStack = entry.getValue().getValue().getValue();
                    MutableComponent toWrite = Component.translatable("ex_shop.overlay.get_money",entry.getValue().getKey());
                    Component reason = entry.getValue().getValue().getKey();
                    if (reason != null && !reason.contains(Component.translatable("ex_shop.reason.none"))) {
                        toWrite = toWrite.append(reason);
                    }
                    float p281752 = 1 - ((System.currentTimeMillis() - entry.getKey()) / 3200f);
                    gg.setColor(1, 1, 1, p281752);
                    gg.drawCenteredString(font, toWrite,
                            screenWidth /2 ,
                            (int) (screenHeight /5  + (font.lineHeight + 2) * line.get()),
                            0xFFFFFF);
                    if (!itemStack.isEmpty()){
                        gg.pose().pushPose();
                        gg.pose().scale(0.2f,0.2f,0.2f);
                        gg.renderItem(itemStack,
                                (screenWidth /2  - 10)*5,
                                (int) (screenHeight /5 + (font.lineHeight + 2) * line.get())*5);
                        gg.pose().popPose();
                    }
                    gg.setColor(1, 1, 1, 1);
                    line.getAndIncrement();

                });

        RenderSystem.depthMask(true);
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }
}
