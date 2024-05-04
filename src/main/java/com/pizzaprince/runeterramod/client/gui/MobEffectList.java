package com.pizzaprince.runeterramod.client.gui;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.ability.ascendent.AscendantType;
import com.pizzaprince.runeterramod.ability.ascendent.EagleAscendant;
import com.pizzaprince.runeterramod.client.screen.CustomPoisonCreationScreen;
import com.pizzaprince.runeterramod.client.screen.FastFlightScreen;
import com.pizzaprince.runeterramod.effect.ModAttributes;
import com.pizzaprince.runeterramod.util.CustomPoisonEffect;
import com.pizzaprince.runeterramod.util.WaypointNBTEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MobEffectList extends ObjectSelectionList<MobEffectListEntry> {

    private final CustomPoisonCreationScreen screen;
    public int type;
    public MobEffectList(CustomPoisonCreationScreen screen, Minecraft mc, int width, int height, int y0, int y1, int itemHeight) {
        super(mc, width, height, y0, y1, itemHeight);
        this.screen = screen;
        fillEntries();
        type = 0;
    }

    public MobEffectList(CustomPoisonCreationScreen screen, Minecraft mc, int width, int height, int y0, int y1, int itemHeight, CustomPoisonEffect effect){
        super(mc, width, height, y0, y1, itemHeight);
        this.screen = screen;
        fillEntriesWithEffect(effect);
        type = 1;
    }

    public MobEffectList(CustomPoisonCreationScreen screen, Minecraft mc, int width, int height, int y0, int y1, int itemHeight, List<CustomPoisonEffect> effects){
        super(mc, width, height, y0, y1, itemHeight);
        this.screen = screen;
        fillEntriesWithCustomEffects(effects);
        type = 2;
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
                MobEffectListEntry entry = getEntry(i);
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
        ForgeRegistries.MOB_EFFECTS.forEach(effect -> {
            addEntry(new MobEffectListEntry(this, ForgeRegistries.MOB_EFFECTS.getKey(effect)));
        });
    }

    public void addEntry(ResourceLocation effect){
        for(int i = 0; i < this.children().size(); i++){
            if(this.children().get(i).getLocation().equals(CustomPoisonEffect.EMPTY)){
                this.children().set(i, new MobEffectListEntry(this, effect));
                return;
            }
        }
    }

    public void removeEntryFromList(MobEffectListEntry effect){
        for(int i = this.children().size()-1; i >= 0; i--){
            if(this.children().get(i).getLocation().equals(effect.getLocation())){
                this.children().set(i, new MobEffectListEntry(this, CustomPoisonEffect.EMPTY));
                removeEmptySpots();
                return;
            }
        }
    }

    private void removeEmptySpots() {
        for(int i = 0; i < this.children().size(); i++){
            if(this.children().get(i).getLocation().equals(CustomPoisonEffect.EMPTY)){
                for(int j = i; j < this.children().size(); j++){
                    if(j+1 >= this.children().size()){
                        this.children().set(j, new MobEffectListEntry(this, CustomPoisonEffect.EMPTY));
                    } else {
                        this.children().set(j, this.children().get(j + 1));
                    }
                }
            }
        }
    }

    public void fillEntriesWithEffect(CustomPoisonEffect poison){
        clearEntries();
        for(ResourceLocation effect : poison.getEffects()){
            addEntry(new MobEffectListEntry(this, effect));
        }
    }
    public void fillEntriesWithCustomEffects(List<CustomPoisonEffect> effects){
        clearEntries();
        for(CustomPoisonEffect effect : effects){
            addEntry(new MobEffectListEntry(this, new ResourceLocation(RuneterraMod.MOD_ID, effect.getName())));
        }
    }

    public ResourceLocation[] getPoisonEffects(){
        ResourceLocation[] effects = new ResourceLocation[this.children().size()];
        for(int i = 0; i < this.children().size(); i++){
            effects[i] = this.children().get(i).getLocation();
        }
        for(int i = 0; i < effects.length; i++){
            if(effects[i] == null){
                effects[i] = CustomPoisonEffect.EMPTY;
            }
        }
        return effects;
    }

    protected int getRowBottom(int itemIndex) {
        return getRowTop(itemIndex) + itemHeight;
    }

    public CustomPoisonCreationScreen getParentScreen() {
        return screen;
    }
}
