package com.pizzaprince.runeterramod.client.screen;

import com.pizzaprince.runeterramod.ability.PlayerAbilities;
import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.ability.ascendent.ScorpionAscendant;
import com.pizzaprince.runeterramod.client.gui.*;
import com.pizzaprince.runeterramod.effect.ModAttributes;
import com.pizzaprince.runeterramod.networking.ModPackets;
import com.pizzaprince.runeterramod.networking.packet.MakePotionFromSelectedC2SPacket;
import com.pizzaprince.runeterramod.networking.packet.UpdateCustomPoisonsC2SPacket;
import com.pizzaprince.runeterramod.networking.packet.UpdateSelectedPoisonEffectC2SPacket;
import com.pizzaprince.runeterramod.util.CustomPoisonEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class CustomPoisonCreationScreen extends Screen {

    public Level level;
    public Player player;
    private Button backButton;
    private Button savePoisonButton;
    private Button addDuration;
    private Button addAmplifier;
    private Button deletePoisonButton;
    private Button bottlePotionButton;
    private MobEffectList mobEffectList;
    private MobEffectList mobEffectList2;
    private MobEffectList currentPoisonList;
    private CustomPoisonList savedPoisonList;
    private TransparentTextField nameField;

    private int selectedPoison = -1;
    public CustomPoisonCreationScreen(Level level, Player player) {
        super(Component.literal("Create Custom Poison"));
        this.level = level;
        this.player = player;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        pGuiGraphics.drawCenteredString(font, "Create Custom Poison", width/2, 20, 16777215);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    private void makePoison(){
        ModPackets.sendToServer(new UpdateCustomPoisonsC2SPacket(nameField.getValue(), currentPoisonList.getPoisonEffects(), false));
        Minecraft.getInstance().setScreen(null);
    }

    public void refreshSelected(){
        for(int i = 0; i < mobEffectList2.children().size(); i++){
            if(mobEffectList2.getSelected() == null) continue;
            if(mobEffectList2.getSelected().getLocation().getPath().equals(mobEffectList2.children().get(i).getLocation().getPath())){
                selectedPoison = i;
                return;
            }
        }
        selectedPoison = -1;
    }

    public void sendSelected(){
        refreshSelected();
        ModPackets.sendToServer(new UpdateSelectedPoisonEffectC2SPacket(selectedPoison));
    }

    public void addEffectToCurrentList(ResourceLocation effect){
        currentPoisonList.addEntry(effect);
    }

    private void deletePoison(){
        ModPackets.sendToServer(new UpdateCustomPoisonsC2SPacket(nameField.getValue(), null, true));
        Minecraft.getInstance().setScreen(null);
    }

    private void bottlePotion(){
       ModPackets.sendToServer(new MakePotionFromSelectedC2SPacket());
       Minecraft.getInstance().setScreen(null);
    }

    public void fillCurrentPoisonListWithEffect(CustomPoisonEffect effect){
        currentPoisonList.fillEntriesWithEffect(effect);
    }

    public void setNameField(String name){
        nameField.setValue(name);
    }

    @Override
    public void tick() {
        refreshSelected();
        if(!nameField.getValue().isBlank()){
            savePoisonButton.active = true;
            deletePoisonButton.active = true;
        } else {
            savePoisonButton.active = false;
            deletePoisonButton.active = false;
        }
        player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
            ScorpionAscendant ascendant = (ScorpionAscendant) cap.getAscendant();
            if(ascendant.getVenom() >= ascendant.venomForSelectedPoison()){
                bottlePotionButton.active = true;
            } else {
                bottlePotionButton.active = false;
            }
        });
    }

    @Override
    protected void init() {
        setupWidgets();
    }

    private void setupWidgets() {
        clearWidgets();
        backButton = addRenderableWidget(new TransparentButton(10, height - 30, 110, 20, Component.literal("Cancel"), (onPress) -> {
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            minecraft.setScreen(null);
        }));
        savePoisonButton = addRenderableWidget(new TransparentButton(width - 140, 20, 110, 20, Component.literal("Save Poison"), (onPress) -> {
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            makePoison();
        }));
        addDuration = addRenderableWidget(new TransparentButton(30, 70, 110, 20, Component.literal("Add Duration"), (onPress) -> {
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            addEffectToCurrentList(CustomPoisonEffect.LENGTHEN);
        }));
        addAmplifier = addRenderableWidget(new TransparentButton(160, 70, 110, 20, Component.literal("Add Amplifier"), (onPress) -> {
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            addEffectToCurrentList(CustomPoisonEffect.AMP);
        }));
        deletePoisonButton = addRenderableWidget(new TransparentButton(width - 140, 50, 110, 20, Component.literal("Delete Poison"), (onPress) -> {
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            deletePoison();
        }));
        bottlePotionButton = addRenderableWidget(new TransparentButton(width - 140, 80, 110, 20, Component.literal("Bottle Selected Poison"), (onPress) -> {
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            bottlePotion();
        }));

        savePoisonButton.active = false;
        deletePoisonButton.active = false;
        bottlePotionButton.active = false;

        nameField = addRenderableWidget(new TransparentTextField(font, width/2 - 70, 70, 140, 20, Component.literal("Name")));

        if(mobEffectList == null){
            mobEffectList = new MobEffectList(this, minecraft, width/3, height, (int)(height*0.2), (int)(height*0.8), 25);
        }
        addRenderableWidget(mobEffectList);

        if(mobEffectList2 == null){
            player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
                ScorpionAscendant ascendant = (ScorpionAscendant) cap.getAscendant();
                mobEffectList2 = new MobEffectList(this, minecraft, (int)(width*1.7), height, (int)(height*0.2), (int)(height*0.8), 25,
                        ascendant.getAllCustomPoisonEffects());
            });
        }
        addRenderableWidget(mobEffectList2);

        if(currentPoisonList == null){
            currentPoisonList = new MobEffectList(this, minecraft, width, height, (int)(height*0.2), (int)(height*0.8), 25,
                    new CustomPoisonEffect((int)(player.getAttributeValue(ModAttributes.ABILITY_POWER.get()) / 5f)));
        }
        addRenderableWidget(currentPoisonList);

        //if(savedPoisonList == null){
        //    savedPoisonList = new CustomPoisonList(this, minecraft, (int)(width*1.7), height, (int)(height*0.3), (int)(height*0.8), 25);
        //}
        //addRenderableWidget(savedPoisonList);


    }
}
