package com.pizzaprince.runeterramod.networking.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AOEParticleS2CPacket {
    private double xPos;
    private double zPos;
    private float radius;

    public AOEParticleS2CPacket(double x, double z, float radius){
        this.xPos = x;
        this.zPos = z;
        this.radius = radius;
    }

    public AOEParticleS2CPacket(FriendlyByteBuf friendlyByteBuf) {
        this.xPos = friendlyByteBuf.readDouble();
        this.zPos = friendlyByteBuf.readDouble();
        this.radius = friendlyByteBuf.readFloat();
    }

    public void toBytes(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeDouble(xPos);
        friendlyByteBuf.writeDouble(zPos);
        friendlyByteBuf.writeFloat(radius);
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {

            float radius = this.radius;
            float angle = 0f;
            float radiusStep = this.radius * 0.05f;
            float angleStep = (float)Math.PI / (this.radius*15);

            while(angle < (Math.PI*2)){
                for(float r = radius; r > 0; r -= radiusStep){
                    double velY = Math.pow(Math.E, ((10-r)*0.2));
                    double x = xPos + (Math.cos(angle)*r);
                    double z = zPos + (Math.sin(angle)*r);
                    int y = Minecraft.getInstance().level.getHeight(Heightmap.Types.WORLD_SURFACE, (int)x, (int)z);

                    Minecraft.getInstance().level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, Minecraft.getInstance().level.getBlockState(new BlockPos((int)x, y-1, (int)z))),
                            true, x + Math.random()*0.4, y + Math.random()*(this.radius/2.5), z + Math.random()*0.4, 0, 20, 0);
                }
                angle += angleStep;
            }
        });
    }
}
