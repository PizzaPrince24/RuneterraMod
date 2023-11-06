package com.pizzaprince.runeterramod.networking.packet;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.client.ClientAbilityData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static com.pizzaprince.runeterramod.camera.CinematicCamera.MC;

public class CrocRageS2CPacket {

    private final int rage;

    public CrocRageS2CPacket(int newRage) {
        this.rage = newRage;
    }

    public CrocRageS2CPacket(FriendlyByteBuf buf) {
        this.rage = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(rage);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientAbilityData.setRage(rage);
        });
        return true;
    }
}

