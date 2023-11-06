package com.pizzaprince.runeterramod.client.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.client.ClientAbilityData;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import virtuoel.pehkui.api.ScaleEasings;

public class CrocAscendentRageOverlay {

    public static final ResourceLocation RAGE_BAR = new ResourceLocation(RuneterraMod.MOD_ID, "textures/gui/rage_bar.png");

    private static boolean isOnScreen = false;

    private static int moveX = 0;

    private static boolean hasTicked = false;

    public static final IGuiOverlay RAGE_GUI = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        int rage = ClientAbilityData.getRage();

        if(partialTick < 0.5f){
            hasTicked = false;
        } else if(partialTick > 0.5f && hasTicked == false){
            if(rage > 0 && isOnScreen == false){
                float app = ((float)moveX + 1f) / 20f;
                moveX = Math.min(20, (int)(ScaleEasings.QUADRATIC_OUT.apply(app)*20f));
                if(moveX == 20){
                    isOnScreen = true;
                }
            } else if(rage == 0 && isOnScreen == true){
                float app = ((float)moveX - 1f) / 20f;
                moveX = Math.max(0, (int)(ScaleEasings.QUADRATIC_IN.apply(app)*20f));
                if(moveX == 0){
                    isOnScreen = false;
                }
            }
            hasTicked = true;
        }


        int x = screenWidth;
        int y = screenHeight;

        RenderSystem.setShader(GameRenderer::getPositionShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, RAGE_BAR);

        if(isOnScreen == true || rage > 0) {
            guiGraphics.blit(RAGE_BAR, x - moveX, y / 2 - 45, 0, 0, 10, 90, 128, 128);

            int height = (int) ((float) rage * (86f / 100f));
            for (int i = 0; i < height; i++) {
                guiGraphics.blit(RAGE_BAR, x - moveX + 2, y / 2 + 42 - i, 13, 87 - i, 6, 1, 128, 128);
            }
        }
    });
}
