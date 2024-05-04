package com.pizzaprince.runeterramod.networking.packet;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.ability.ascendent.AscendantType;
import com.pizzaprince.runeterramod.ability.ascendent.EagleAscendant;
import com.pizzaprince.runeterramod.ability.ascendent.ScorpionAscendant;
import com.pizzaprince.runeterramod.effect.ModAttributes;
import com.pizzaprince.runeterramod.util.CustomPoisonEffect;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateCustomPoisonsC2SPacket {

    private String name;
    private ResourceLocation[] effects;
    private boolean delete;

    public UpdateCustomPoisonsC2SPacket(String name, ResourceLocation[] effects, boolean delete) {
        this.name = name;
        this.effects = effects;
        this.delete = delete;
    }

    public UpdateCustomPoisonsC2SPacket(FriendlyByteBuf buf) {
        this.name = buf.readComponent().getString();
        this.delete = buf.readBoolean();
        if(!delete) {
            int length = buf.readInt();
            effects = new ResourceLocation[length];
            for (int i = 0; i < length; i++) {
                effects[i] = buf.readResourceLocation();
            }
        }
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeComponent(Component.literal(name));
        buf.writeBoolean(delete);
        if(!delete) {
            buf.writeInt(effects.length);
            for (ResourceLocation location : effects) {
                buf.writeResourceLocation(location);
            }
        }
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();
            player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
                if(cap.getAscendantType() == AscendantType.SCORPION){
                    ScorpionAscendant ascendant = (ScorpionAscendant) cap.getAscendant();
                    if(delete){
                        ascendant.removeCustomPoisonEffect(name);
                    } else {
                        CustomPoisonEffect effect = new CustomPoisonEffect((int)(player.getAttributeValue(ModAttributes.ABILITY_POWER.get()) / 5f), effects, name);
                        ascendant.addCustomPoisonEffect(effect);
                    }
                }
            });
        });
        return true;
    }
}
