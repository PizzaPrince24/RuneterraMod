package com.pizzaprince.runeterramod.networking.packet;

import com.pizzaprince.runeterramod.ability.AbilityItemCapabilityProvider;
import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.item.custom.curios.base.Sheen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EagleFastFlightC2SPacket {

    private int x;
    private int z;
    private String path;

    public EagleFastFlightC2SPacket(int x, int z, String path) {
        this.x = x;
        this.z = z;
        this.path = path;
    }

    public EagleFastFlightC2SPacket(FriendlyByteBuf buf) {
        this.x = buf.readInt();
        this.z = buf.readInt();
        this.path = buf.readComponent().getString();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(z);
        buf.writeComponent(Component.literal(path));
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();
            if(path.isEmpty()){
                player.teleportTo(x, level.getMaxBuildHeight(), z);
            } else {
                level.getServer().getAllLevels().forEach(dimension -> {
                    if(dimension.dimension().location().toString().equals(path)){
                        if(path.equals("minecraft:the_nether")){
                            System.out.println("found the nether");
                            for(int y = 126; y > 4; y--){
                                if(dimension.getBlockState(new BlockPos(x, y, z)).isAir()){
                                    System.out.println("found air");
                                    player.teleportTo(dimension, x, y-1, z, player.getYHeadRot(), player.getXRot());
                                    return;
                                }
                            }
                        }
                        player.teleportTo(dimension, x, dimension.getMaxBuildHeight(), z, player.getYHeadRot(), player.getXRot());
                    }
                });
            }

        });
        return true;
    }
}
