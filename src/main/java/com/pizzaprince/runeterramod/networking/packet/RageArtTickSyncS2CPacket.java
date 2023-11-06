package com.pizzaprince.runeterramod.networking.packet;

import com.pizzaprince.runeterramod.client.ClientAbilityData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RageArtTickSyncS2CPacket {

    private final int rageArtTicks;
    public RageArtTickSyncS2CPacket(int ticks) {
        this.rageArtTicks = ticks;
    }

    public RageArtTickSyncS2CPacket(FriendlyByteBuf buf) {
        this.rageArtTicks = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.rageArtTicks);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientAbilityData.setRageArtTicks(this.rageArtTicks);
        });
        return true;
    }
}
