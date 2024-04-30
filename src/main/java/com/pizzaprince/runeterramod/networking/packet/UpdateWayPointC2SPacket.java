package com.pizzaprince.runeterramod.networking.packet;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.ability.ascendent.AscendantType;
import com.pizzaprince.runeterramod.ability.ascendent.EagleAscendant;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateWayPointC2SPacket {

    private String name;
    private String dimension;
    private int x;
    private int z;
    private boolean delete;

    public UpdateWayPointC2SPacket(String name, String dimension, int x, int z, boolean delete) {
        this.name = name;
        this.dimension = dimension;
        this.x = x;
        this.z = z;
        this.delete = delete;
    }

    public UpdateWayPointC2SPacket(FriendlyByteBuf buf) {
        this.name = buf.readComponent().getString();
        this.dimension = buf.readComponent().getString();
        this.x = buf.readInt();
        this.z = buf.readInt();
        this.delete = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeComponent(Component.literal(name));
        buf.writeComponent(Component.literal(dimension));
        buf.writeInt(x);
        buf.writeInt(z);
        buf.writeBoolean(delete);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();
            player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
                if(cap.getAscendantType() == AscendantType.EAGLE){
                    EagleAscendant ascendant = (EagleAscendant) cap.getAscendant();
                    if(delete){
                        ascendant.removeWaypoint(name);
                    } else {
                        ascendant.addWaypoint(name, dimension, x, z);
                    }
                }
            });
        });
        return true;
    }
}
