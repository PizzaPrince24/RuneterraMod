package com.pizzaprince.runeterramod.networking.packet;

import com.pizzaprince.runeterramod.ability.PlayerAbilities;
import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.ability.ascendent.AscendantType;
import com.pizzaprince.runeterramod.ability.ascendent.CrocodileAscendant;
import com.pizzaprince.runeterramod.client.ClientAbilityData;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RageArtCapSyncS2CPacket {

    private final int rageArtTicks;
    private final int entityID;
    public RageArtCapSyncS2CPacket(int ticks, int id) {
        this.rageArtTicks = ticks;
        this.entityID = id;
    }

    public RageArtCapSyncS2CPacket(FriendlyByteBuf buf) {
        this.rageArtTicks = buf.readInt();
        this.entityID = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.rageArtTicks);
        buf.writeInt(this.entityID);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Minecraft.getInstance().level.getEntity(entityID).getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
                cap.ascend((Player) Minecraft.getInstance().level.getEntity(entityID), AscendantType.CROCODILE);
                ((CrocodileAscendant)cap.getAscendant()).setRageArtTicks(this.rageArtTicks);
            });
        });
        return true;
    }
}
