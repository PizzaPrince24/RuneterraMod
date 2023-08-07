package com.pizzaprince.runeterramod.networking.packet;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static com.pizzaprince.runeterramod.camera.CinematicCamera.MC;

public class CapSyncS2CPacket {

    private final int entity;
    private final CompoundTag nbt;

    public CapSyncS2CPacket(LivingEntity entity) {
        this.entity = entity.getId();
        CompoundTag nbt = new CompoundTag();
        entity.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
            cap.saveNBTData(nbt);
        });
        this.nbt = nbt;
    }

    public CapSyncS2CPacket(FriendlyByteBuf buf) {
        this.entity = buf.readInt();
        this.nbt = buf.readNbt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.entity);
        buf.writeNbt(this.nbt);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            MC.level.getEntity(this.entity).getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
                cap.loadNBTData(this.nbt);
            });
        });
        return true;
    }
}
