package com.pizzaprince.runeterramod.camera;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.Packet;

import java.util.UUID;

public class CinematicCamera extends LocalPlayer {

    public static Minecraft MC = Minecraft.getInstance();

    private float fov;

    private final CameraSequence sequence;

    private static final ClientPacketListener CONNECTION = new ClientPacketListener(MC, MC.screen, MC.getConnection().getConnection(), MC.getCurrentServer(),
            new GameProfile(UUID.randomUUID(), "RuneterramodCinematicCamera"), MC.getTelemetryManager().createWorldSessionManager(false, null, null)) {
        @Override
        public void send(Packet<?> packet) {
        }
    };
    public CinematicCamera(CameraSequence sequence) {
        super(MC, MC.level, CONNECTION, MC.player.getStats(), MC.player.getRecipeBook(), false, false);
        if(clientLevel != null){
            clientLevel.addPlayer(-10, this);
        }
        this.sequence = sequence;
        MC.options.hideGui = true;
    }

    @Override
    public void aiStep() {

    }

    @Override
    public float getViewXRot(float pPartialTick) {
        sequence.updateCamera(this);
        return super.getViewXRot(pPartialTick);
    }

    //1 for normal; 0.1f is scoping in, 1.x+ is zooming out
    @Override
    public float getFieldOfViewModifier() {
        return this.fov;
    }

    public void setFOV(float fov){
        this.fov = fov;
    }

    public void setCameraPoint(CameraPoint point){
        this.setPosRaw(point.x, point.y, point.z);
        this.setXRot(point.pitch);
        this.setYRot(point.yaw);
        this.setFOV(point.fov);
    }

    public void despawn() {
        MC.setCameraEntity(MC.player);
        MC.options.hideGui = false;
        clientLevel.removeEntity(getId(), RemovalReason.DISCARDED);
    }
}
