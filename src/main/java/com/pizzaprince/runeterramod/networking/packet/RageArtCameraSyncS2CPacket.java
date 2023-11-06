package com.pizzaprince.runeterramod.networking.packet;

import com.pizzaprince.runeterramod.camera.CameraSequences;
import com.pizzaprince.runeterramod.client.ClientAbilityData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RageArtCameraSyncS2CPacket {

    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;
    private final double targetX;
    private final double targetY;
    private final double targetZ;
    private final float targetBbWidth;
    private final float targetBbHeight;
    private final boolean ifPlayerDoingRageArt;
    private final int targetID;
    private final float playerYRot;

    public RageArtCameraSyncS2CPacket(Entity entity, Entity target, boolean ifPlayerDoingRageArt) {
        this.x = entity.getX();
        this.y = entity.getY();
        this.z = entity.getZ();
        this.yaw = entity.getYRot();
        this.pitch = entity.getXRot();
        this.targetX = target.getX();
        this.targetY = target.getY();
        this.targetZ = target.getZ();
        this.targetBbWidth = target.getBbWidth();
        this.targetBbHeight = target.getBbHeight();
        this.ifPlayerDoingRageArt = ifPlayerDoingRageArt;
        this.targetID = target.getId();
        this.playerYRot = entity.getYRot();
    }

    public RageArtCameraSyncS2CPacket(FriendlyByteBuf buf) {
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        this.yaw = buf.readFloat();
        this.pitch = buf.readFloat();
        this.targetX = buf.readDouble();
        this.targetY = buf.readDouble();
        this.targetZ = buf.readDouble();
        this.targetBbWidth = buf.readFloat();
        this.targetBbHeight = buf.readFloat();
        this.ifPlayerDoingRageArt = buf.readBoolean();
        this.targetID = buf.readInt();
        this.playerYRot = buf.readFloat();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
        buf.writeFloat(this.yaw);
        buf.writeFloat(this.pitch);
        buf.writeDouble(this.targetX);
        buf.writeDouble(this.targetY);
        buf.writeDouble(this.targetZ);
        buf.writeFloat(this.targetBbWidth);
        buf.writeFloat(this.targetBbHeight);
        buf.writeBoolean(this.ifPlayerDoingRageArt);
        buf.writeInt(this.targetID);
        buf.writeFloat(this.playerYRot);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if(ifPlayerDoingRageArt){
                ClientAbilityData.startRageArt(this.targetID, this.playerYRot);
            }
            CameraSequences.getRageArtSequence(this.x, this.y, this.z, this.yaw, this.pitch, this.targetX, this.targetY, this.targetZ, this.targetBbWidth, this.targetBbHeight).play();
        });
        return true;
    }
}
