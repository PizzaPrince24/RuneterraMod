package com.pizzaprince.runeterramod.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.pizzaprince.runeterramod.RuneterraMod;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SunDiskAltarScreen extends AbstractContainerScreen<SunDiskAltarMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(RuneterraMod.MOD_ID,"textures/gui/gem_infusing_station_gui.png");
    public SunDiskAltarScreen(SunDiskAltarMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);

        renderDiskProgress(pPoseStack, x, y);

        renderSunPower(pPoseStack, x, y);
    }

    private void renderDiskProgress(PoseStack pPoseStack, int x, int y) {
        blit(pPoseStack, x + 109, y + 20, 176, 31, 50, menu.getScaledDiskProgress());
    }

    private void renderSunPower(PoseStack pPoseStack, int x, int y) {
        blit(pPoseStack, x + 10, y + 29, 176, 0, 64, menu.sunDiskAltar.getSunPower());
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pPoseStack, pMouseX, pMouseY);
    }
}
