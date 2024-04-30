package com.pizzaprince.runeterramod.client.screen;

import com.pizzaprince.runeterramod.client.gui.TransparentButton;
import com.pizzaprince.runeterramod.client.gui.TransparentTextField;
import com.pizzaprince.runeterramod.client.gui.WaypointList;
import com.pizzaprince.runeterramod.networking.ModPackets;
import com.pizzaprince.runeterramod.networking.packet.EagleFastFlightC2SPacket;
import com.pizzaprince.runeterramod.networking.packet.UpdateWayPointC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class FastFlightScreen extends Screen {

    public Level level;
    private Player player;
    private Button backButton;
    private Button flyButton;
    private Button makeWaypointButton;
    private Button deleteWaypointButton;
    private TransparentTextField xField;
    private TransparentTextField zField;
    private TransparentTextField nameField;
    private WaypointList waypointList;
    public FastFlightScreen(Level level, Player player) {
        super(Component.literal("Select Place to Fast Fly"));
        this.level = level;
        this.player = player;
    }

    @Override
    public void tick() {
        boolean active = true;
        if(!xField.getValue().isBlank()) {
            try {
                int xVal = Integer.parseInt(xField.getValue());
                if(xVal > 29999983 || xVal < -29999983){
                    xField.setTextColor(16711680);
                    active = false;
                } else {
                    xField.setTextColor(16777215);
                }
            } catch (Exception e) {
                xField.setTextColor(16711680);
                active = false;
            }
        } else {
            xField.setTextColor(16777215);
            active = false;
        }

        if(!zField.getValue().isBlank()) {
            try {
                int zVal = Integer.parseInt(zField.getValue());
                if(zVal > 29999983 || zVal < -29999983){
                    zField.setTextColor(16711680);
                    active = false;
                } else {
                    zField.setTextColor(16777215);
                }
            } catch (Exception e) {
                zField.setTextColor(16711680);
                active = false;
            }
        } else {
            zField.setTextColor(16777215);
            active = false;
        }
        flyButton.active = active;

        if(nameField.getValue().isBlank()){
            makeWaypointButton.active = false;
            deleteWaypointButton.active = false;
        } else if(active || (xField.getValue().isBlank() && zField.getValue().isBlank())) {
            makeWaypointButton.active = true;
            deleteWaypointButton.active = true;
        } else {
            makeWaypointButton.active = false;
            deleteWaypointButton.active = true;
        }
    }

    public void setFieldValues(int x, int z, String name){
        xField.setValue("" + x);
        zField.setValue("" + z);
        nameField.setValue(name);
    }

    public void fastFlyToLocation(){
        try{
            int x = Integer.parseInt(xField.getValue());
            int z = Integer.parseInt(zField.getValue());
            if(x > 29999983 || x < -29999983 || z > 29999983 || z < -29999983) return;
            ModPackets.sendToServer(new EagleFastFlightC2SPacket(x, z, ""));
        } catch (Exception e){
            return;
        }
        Minecraft.getInstance().setScreen(null);
    }

    public void fastFlyToLocationWithDimension(String path){
        try{
            int x = Integer.parseInt(xField.getValue());
            int z = Integer.parseInt(zField.getValue());
            if(x > 29999983 || x < -29999983 || z > 29999983 || z < -29999983) return;
            ModPackets.sendToServer(new EagleFastFlightC2SPacket(x, z, path));
        } catch (Exception e){
            return;
        }
        Minecraft.getInstance().setScreen(null);
    }

    private void makeNewWaypoint(){
        if(nameField.getValue().isBlank()) return;
        boolean xParsable;
        boolean zParsable;
        try{
            Integer.parseInt(xField.getValue());
            xParsable = true;
        } catch (Exception e) {xParsable = false;}
        try{
            Integer.parseInt(zField.getValue());
            zParsable = true;
        } catch (Exception e) {zParsable = false;}
        if(xField.getValue().isBlank()) xParsable = false;
        if(zField.getValue().isBlank()) zParsable = false;
        ModPackets.sendToServer(new UpdateWayPointC2SPacket(nameField.getValue(), player.level().dimension().location().toString(),
                xParsable ? Integer.parseInt(xField.getValue()) : player.getBlockX(),
                zParsable ? Integer.parseInt(zField.getValue()) : player.getBlockZ(), false));
    }

    private void deleteWayPoint(){
        if(nameField.getValue().isBlank()) return;
        ModPackets.sendToServer(new UpdateWayPointC2SPacket(nameField.getValue(), "", 0, 0, true));
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        pGuiGraphics.drawCenteredString(font, "Fast Fly", width/2, 20, 16777215);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
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
        flyButton = addRenderableWidget(new TransparentButton(width - 140, 15, 110, 20, Component.literal("Fly"), (onPress) -> {
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            fastFlyToLocation();
        }));
        makeWaypointButton = addRenderableWidget(new TransparentButton(width - 140, 70, 110, 20, Component.literal("Make Waypoint"), (onPress) -> {
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            makeNewWaypoint();
            minecraft.setScreen(null);
        }));
        deleteWaypointButton = addRenderableWidget(new TransparentButton(width - 140, 100, 110, 20, Component.literal("Delete Waypoint"), (onPress) -> {
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            deleteWayPoint();
            minecraft.setScreen(null);
        }));

        flyButton.active = false;

        makeWaypointButton.active = false;

        deleteWaypointButton.active = false;

        xField = addRenderableWidget(new TransparentTextField(font, width/2 - 190, 40, 140, 20, Component.literal("Input X")));

        zField = addRenderableWidget(new TransparentTextField(font, width/2 + 50, 40, 140, 20, Component.literal("Input Z")));

        nameField = addRenderableWidget(new TransparentTextField(font, width/2 - 70, 70, 140, 20, Component.literal("Name")));

        if(waypointList == null){
            waypointList = new WaypointList(this, minecraft, width, height, 100, height, 45);
        }
        addRenderableWidget(waypointList);
    }
}
