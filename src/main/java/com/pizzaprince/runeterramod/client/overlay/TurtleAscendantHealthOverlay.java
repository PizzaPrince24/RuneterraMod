package com.pizzaprince.runeterramod.client.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.ability.ascendent.AscendantType;
import com.pizzaprince.runeterramod.ability.ascendent.TurtleAscendant;
import com.pizzaprince.runeterramod.client.ClientAbilityData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import virtuoel.pehkui.api.ScaleEasings;

public class TurtleAscendantHealthOverlay {

    public static final ResourceLocation SHELL_BAR = new ResourceLocation(RuneterraMod.MOD_ID, "textures/gui/shell_bar.png");

    private static boolean isOnScreen = false;

    private static int moveX = 0;

    private static boolean hasTicked = false;

    public static final IGuiOverlay SHELL_HEALTH_GUI = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Minecraft.getInstance().player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
            if(cap.getAscendantType() != AscendantType.TURTLE) return;
            TurtleAscendant ascendant = (TurtleAscendant) cap.getAscendant();
            if(ascendant.isShellBroken()){
                RenderSystem.setShader(GameRenderer::getPositionShader);
                RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
                RenderSystem.setShaderTexture(0, SHELL_BAR);
                guiGraphics.blit(SHELL_BAR, screenWidth - 20, screenHeight / 2 - 45, 22, 0, 10, 90, 128, 128);
            } else {
                float health = ascendant.getShellHealth();

                if (partialTick < 0.5f) {
                    hasTicked = false;
                } else if (partialTick > 0.5f && !hasTicked) {
                    if (health < 500 && !isOnScreen) {
                        float app = ((float) moveX + 1f) / 20f;
                        moveX = Math.min(20, (int) (ScaleEasings.QUADRATIC_OUT.apply(app) * 20f));
                        if (moveX == 20) {
                            isOnScreen = true;
                        }
                    } else if (health == 500 && isOnScreen) {
                        float app = ((float) moveX - 1f) / 20f;
                        moveX = Math.max(0, (int) (ScaleEasings.QUADRATIC_IN.apply(app) * 20f));
                        if (moveX == 0) {
                            isOnScreen = false;
                        }
                    }
                    hasTicked = true;
                }

                int x = screenWidth;
                int y = screenHeight;

                RenderSystem.setShader(GameRenderer::getPositionShader);
                RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
                RenderSystem.setShaderTexture(0, SHELL_BAR);

                if (isOnScreen || health < 500) {
                    guiGraphics.blit(SHELL_BAR, x - moveX, y / 2 - 45, 0, 0, 10, 90, 128, 128);

                    int height = (int) (health * (86f / 500f));
                    for (int i = 0; i < height; i++) {
                        guiGraphics.blit(SHELL_BAR, x - moveX + 2, y / 2 + 42 - i, 13, 87 - i, 6, 1, 128, 128);
                    }
                }
            }
        });

    });
}
