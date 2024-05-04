package com.pizzaprince.runeterramod.client.gui;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.ability.ascendent.ScorpionAscendant;
import com.pizzaprince.runeterramod.client.screen.CustomPoisonCreationScreen;
import com.pizzaprince.runeterramod.util.CustomPoisonEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class CustomPoisonList extends ObjectSelectionList<CustomPoisonEntry> {
    private final CustomPoisonCreationScreen screen;
    public CustomPoisonList(CustomPoisonCreationScreen screen, Minecraft mc, int width, int height, int y0, int y1, int itemHeight) {
        super(mc, width, height, y0, y1, itemHeight);
        this.screen = screen;
        fillEntries();
    }

    @Override
    protected int getScrollbarPosition() {
        return super.getScrollbarPosition() + 20;
    }

    @Override
    public int getRowWidth() {
        return super.getRowWidth() + 50;
    }

    @Override
    protected boolean isSelectedItem(int slotIndex) {
        return slotIndex >= 0 && slotIndex < children().size() ? children().get(slotIndex).equals(getSelected()) : false;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        renderList(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    protected void renderList(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        for (int i = 0; i < getItemCount(); ++i) {
            int top = getRowTop(i);
            int bottom = getRowBottom(i);
            if (bottom >= y0 && top <= y1) {
                CustomPoisonEntry entry = getEntry(i);
                if (isSelectedItem(i)) {
                    final int insideLeft = x0 + width / 2 - getRowWidth() / 2 + 2;
                    guiGraphics.fill(insideLeft - 4, top - 4, insideLeft + getRowWidth() + 4, top + itemHeight, 255 / 2 << 24);
                }
                entry.render(guiGraphics, i, top, getRowLeft(), getRowWidth(), itemHeight - 4, mouseX, mouseY, isMouseOver((double) mouseX, (double) mouseY) && Objects.equals(getEntryAtPosition((double) mouseX, (double) mouseY), entry), partialTicks);
            }
        }

        if (getMaxScroll() > 0) {
            int left = getScrollbarPosition();
            int right = left + 6;
            int height = (int) ((float) ((y1 - y0) * (y1 - y0)) / (float) getMaxPosition());
            height = Mth.clamp(height, 32, y1 - y0 - 8);
            int top = (int) getScrollAmount() * (y1 - y0 - height) / getMaxScroll() + y0;
            if (top < y0) {
                top = y0;
            }

            guiGraphics.fill(left, y0, right, y1, (int) (2.35F * 255.0F) / 2 << 24);
            guiGraphics.fill(left, top, right, top + height, (int) (1.9F * 255.0F) / 2 << 24);
        }
    }

    public void fillEntries(){
        clearEntries();
        screen.player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
            ScorpionAscendant ascendant = (ScorpionAscendant) cap.getAscendant();
            ascendant.getAllCustomPoisonEffects().forEach(effect -> {
                addEntry(new CustomPoisonEntry(this, effect));
                effect.toString();
            });
        });
    }

    protected int getRowBottom(int itemIndex) {
        return getRowTop(itemIndex) + itemHeight;
    }

    public CustomPoisonCreationScreen getParentScreen() {
        return screen;
    }
}
