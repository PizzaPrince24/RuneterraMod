package com.pizzaprince.runeterramod.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.pizzaprince.runeterramod.RuneterraMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SunDiskAltarScreen extends AbstractContainerScreen<SunDiskAltarMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(RuneterraMod.MOD_ID,"textures/gui/sun_disk_altar.png");
    public SunDiskAltarScreen(SunDiskAltarMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics g, float p_97788_, int p_97789_, int p_97790_) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        g.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderDiskProgress(g, x, y);

        renderSunPower(g, x, y);
    }

    private void renderDiskProgress(GuiGraphics g, int x, int y) {
        if(menu.getScaledDiskProgress() > 0) {
            for (int i = 0; i <= menu.getScaledDiskProgress(); i++) {
                g.blit(TEXTURE, x + 109, y + 71 - i, 176, 82 - i, 50, 1);
            }
        }
        //blit(pPoseStack, x + 109, y + 20, 176, 31, 50, menu.getScaledDiskProgress());
    }

    private void renderSunPower(GuiGraphics g, int x, int y) {
        if(menu.sunDiskAltar.getSunPower() > 0) {
            for (int i = 0; i <= menu.sunDiskAltar.getSunPower(); i++) {
                g.blit(TEXTURE, x + 10, y + 58 - i, 176, 30 - i, 64, 1);
            }
        }
        //blit(pPoseStack, x + 10, y + 29, 176, 0, 64, menu.sunDiskAltar.getSunPower());
    }

    @Override
    public void render(GuiGraphics g, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(g);
        super.render(g, pMouseX, pMouseY, pPartialTick);
        renderTooltip(g, pMouseX, pMouseY);
    }
}
