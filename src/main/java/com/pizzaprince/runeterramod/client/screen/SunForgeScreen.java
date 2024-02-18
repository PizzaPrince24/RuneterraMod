package com.pizzaprince.runeterramod.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.pizzaprince.runeterramod.RuneterraMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SunForgeScreen extends AbstractContainerScreen<SunForgeMenu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(RuneterraMod.MOD_ID,"textures/gui/sun_forge.png");
    public SunForgeScreen(SunForgeMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        pGuiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderSunPower(pGuiGraphics, x, y);

        renderSunEnergy(pGuiGraphics, x, y);

        renderGUIAnim(pGuiGraphics, x, y);
    }

    private void renderGUIAnim(GuiGraphics g, int x, int y) {
        g.blit(TEXTURE,x+80, y+35, 176, 31, 41, menu.getGUIAnim());
    }

    private void renderSunEnergy(GuiGraphics g, int x, int y) {
        for (int i = 0; i <= menu.getScaledSunEnergy(); i++) {
            g.blit(TEXTURE, x + 150, y + 72 - i, 176, 107 - i, 16, 1);
        }
    }

    private void renderSunPower(GuiGraphics g, int x, int y) {
        if(menu.sunForge.getSunPower() > 0) {
            for (int i = 0; i <= menu.sunForge.getSunPower(); i++) {
                g.blit(TEXTURE, x + 10, y + 58 - i, 176, 30 - i, 65, 1);
            }
        }
    }

    @Override
    public void render(GuiGraphics g, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(g);
        super.render(g, pMouseX, pMouseY, pPartialTick);
        renderTooltip(g, pMouseX, pMouseY);
    }
}
