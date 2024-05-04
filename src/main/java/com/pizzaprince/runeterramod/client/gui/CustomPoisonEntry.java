package com.pizzaprince.runeterramod.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.pizzaprince.runeterramod.client.screen.CustomPoisonCreationScreen;
import com.pizzaprince.runeterramod.util.CustomPoisonEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;

public class CustomPoisonEntry extends ObjectSelectionList.Entry<CustomPoisonEntry> {

    private final CustomPoisonList list;
    private final CustomPoisonCreationScreen screen;
    private final CustomPoisonEffect effect;

    public CustomPoisonEntry(CustomPoisonList list, CustomPoisonEffect effect){
        this.list = list;
        this.screen = list.getParentScreen();
        this.effect = effect;
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        System.out.println("has clicked");
        if(pButton == 0){
            screen.fillCurrentPoisonListWithEffect(effect);
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            return true;
        }
        return false;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pIndex, int pTop, int pLeft, int pWidth, int pHeight, int pMouseX, int pMouseY, boolean p_93531_, float pPartialTick) {
        pGuiGraphics.drawString(Minecraft.getInstance().font, Component.literal(effect.getName()), pLeft + 1, pTop + 1, 0xffffff);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public Component getNarration() {
        return Component.literal(effect.getName());
    }

}
