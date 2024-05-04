package com.pizzaprince.runeterramod.networking.packet;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.ability.ascendent.AscendantType;
import com.pizzaprince.runeterramod.ability.ascendent.ScorpionAscendant;
import com.pizzaprince.runeterramod.util.CustomPoisonEffect;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateSelectedPoisonEffectC2SPacket {

    private int selected;

    public UpdateSelectedPoisonEffectC2SPacket(int selected) {
        this.selected = selected;
    }

    public UpdateSelectedPoisonEffectC2SPacket(FriendlyByteBuf buf) {
        this.selected = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(selected);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();
            player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
                if(cap.getAscendantType() == AscendantType.SCORPION){
                    ScorpionAscendant ascendant = (ScorpionAscendant) cap.getAscendant();
                    ascendant.setSelectedPoison(selected);
                }
            });
        });
        return true;
    }
}
