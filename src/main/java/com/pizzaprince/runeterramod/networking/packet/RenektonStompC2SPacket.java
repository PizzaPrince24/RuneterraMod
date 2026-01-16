package com.pizzaprince.runeterramod.networking.packet;

import com.pizzaprince.runeterramod.effect.ModDamageTypes;
import com.pizzaprince.runeterramod.entity.custom.RenektonEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import org.joml.Vector3f;

import java.util.function.Supplier;

public class RenektonStompC2SPacket {
    private Vector3f origin;
    private double width;
    private double height;
    private float damage;
    private int entityID;
    public RenektonStompC2SPacket(Vector3f origin, double width, double height, float damage, int entityID) {
        this.origin = origin;
        this.width = width;
        this.height = height;
        this.damage = damage;
        this.entityID = entityID;
    }

    public RenektonStompC2SPacket(FriendlyByteBuf buf) {
        this.origin = buf.readVector3f();
        this.width = buf.readDouble();
        this.height = buf.readDouble();
        this.damage = buf.readFloat();
        this.entityID = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeVector3f(origin);
        buf.writeDouble(width);
        buf.writeDouble(height);
        buf.writeFloat(damage);
        buf.writeInt(entityID);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();
            AABB hitbox = new AABB(origin.x-(width/2d), origin.y, origin.z-(width/2d), origin.x+(width/2d),
                    origin.y+height, origin.z+(width/2d));
            Entity renekton = level.getEntity(entityID);
            if(renekton instanceof LivingEntity){
                level.getEntitiesOfClass(LivingEntity.class, hitbox, entity -> !(entity instanceof RenektonEntity)).forEach(entity -> {
                    entity.hurt(ModDamageTypes.getEntityDamageSource(level, ModDamageTypes.STOMP, renekton), damage);
                });
            } else {
                level.getEntitiesOfClass(LivingEntity.class, hitbox, entity -> !(entity instanceof RenektonEntity)).forEach(entity -> {
                    entity.hurt(ModDamageTypes.getDamageSource(level, ModDamageTypes.STOMP), damage);
                });
            }
        });
        return true;
    }
}
