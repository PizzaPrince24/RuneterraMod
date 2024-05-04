package com.pizzaprince.runeterramod.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.ability.ascendent.ScorpionAscendant;
import com.pizzaprince.runeterramod.client.screen.CustomPoisonCreationScreen;
import com.pizzaprince.runeterramod.effect.ModAttributes;
import com.pizzaprince.runeterramod.util.CustomPoisonEffect;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;

public class MobEffectListEntry extends ObjectSelectionList.Entry<MobEffectListEntry> {

    private final MobEffectList list;
    private final CustomPoisonCreationScreen screen;
    private final ResourceLocation location;

    public MobEffectListEntry(MobEffectList list, ResourceLocation location){
        this.list = list;
        this.screen = list.getParentScreen();
        this.location = location;
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if(pButton == 0){
            list.setSelected(this);
            if(list.type == 0){
                screen.addEffectToCurrentList(location);
            } else if(list.type == 1) {
                list.removeEntryFromList(this);
            } else if(list.type == 2){
                screen.player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
                    ScorpionAscendant ascendant = (ScorpionAscendant)cap.getAscendant();
                    CustomPoisonEffect effect = ascendant.getPoisonEffectByName(location.getPath());
                    screen.fillCurrentPoisonListWithEffect(effect == null ? new CustomPoisonEffect((int)(screen.player.getAttributeValue(ModAttributes.ABILITY_POWER.get()) / 5f))
                            : new CustomPoisonEffect((int)(screen.player.getAttributeValue(ModAttributes.ABILITY_POWER.get()) / 5f), effect.getEffects(), effect.getName()));
                    screen.sendSelected();
                    if(effect != null) screen.setNameField(effect.getName());
                    effect.toString();
                });
            }
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            return true;
        }
        return false;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pIndex, int pTop, int pLeft, int pWidth, int pHeight, int pMouseX, int pMouseY, boolean p_93531_, float pPartialTick) {
        if(location == null) return;
        if(location.equals(CustomPoisonEffect.EMPTY)) return;
        if(list.type == 2){
            pGuiGraphics.drawString(Minecraft.getInstance().font, Component.literal(location.getPath()), pLeft + 1, pTop + 1, 0xffffff);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            return;
        }
        if(location.equals(CustomPoisonEffect.AMP)){
            pGuiGraphics.drawString(Minecraft.getInstance().font, Component.literal("Amplifier"), pLeft + 1, pTop + 1, 0xffffff);
        } else if(location.equals(CustomPoisonEffect.LENGTHEN)){
            pGuiGraphics.drawString(Minecraft.getInstance().font, Component.literal("Duration Up"), pLeft + 1, pTop + 1, 0xffffff);
        } else {
            pGuiGraphics.drawString(Minecraft.getInstance().font, Component.translatable("effect." + location.getNamespace() + "." + location.getPath()), pLeft + 1, pTop + 1, 0xffffff);
            pGuiGraphics.drawString(Minecraft.getInstance().font, Component.literal(location.toString()), pLeft + 1, pTop + Minecraft.getInstance().font.lineHeight + 3, 0x808080);
        }
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public Component getNarration() {
        return Component.literal(location == null ? "" : location.toString());
    }

    public ResourceLocation getLocation(){
        return location;
    }


}
