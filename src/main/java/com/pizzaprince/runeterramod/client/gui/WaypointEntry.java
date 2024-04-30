package com.pizzaprince.runeterramod.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.pizzaprince.runeterramod.client.screen.FastFlightScreen;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;

public class WaypointEntry extends ObjectSelectionList.Entry<WaypointEntry> {
    private final WaypointList list;
    private final FastFlightScreen screen;
    private final int x;
    private final int z;
    private final String name;
    private final String dimension;
    private long lastClickTime;

    public WaypointEntry(WaypointList list, int x, int z, String name, String dimension){
        this.list = list;
        this.x = x;
        this.z = z;
        this.name = name;
        this.screen = list.getParentScreen();
        this.dimension = dimension;
    }

    @Override
    public Component getNarration() {
        return Component.literal(name);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pIndex, int pTop, int pLeft, int pWidth, int pHeight, int pMouseX, int pMouseY, boolean p_93531_, float pPartialTick) {
        pGuiGraphics.drawString(Minecraft.getInstance().font, Component.literal(name), pLeft + 1, pTop + 1, 0xffffff);
        pGuiGraphics.drawString(Minecraft.getInstance().font, Component.literal(dimension), pLeft + 1, pTop + Minecraft.getInstance().font.lineHeight + 3, 0x808080);
        pGuiGraphics.drawString(Minecraft.getInstance().font, Component.literal("X: " + x), pLeft + 1, pTop + Minecraft.getInstance().font.lineHeight + 14, 0x808080);
        pGuiGraphics.drawString(Minecraft.getInstance().font, Component.literal("Z: " + z), pLeft + 1, pTop + Minecraft.getInstance().font.lineHeight + 25, 0x808080);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if(pButton == 0){
            list.setSelected(this);
            screen.setFieldValues(x, z, name);
            if (Util.getMillis() - lastClickTime < 250L) {
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                screen.fastFlyToLocationWithDimension(dimension);
                return true;
            } else {
                lastClickTime = Util.getMillis();
                return false;
            }
        }
        return false;
    }
}
