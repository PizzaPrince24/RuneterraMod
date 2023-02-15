package com.pizzaprince.runeterramod.networking.packet;

import java.util.function.Supplier;

import com.pizzaprince.runeterramod.ability.PlayerAbilities;
import com.pizzaprince.runeterramod.client.ClientAbilityData;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;

public class SyncCooldownsS2CPacket {
	private int cooldown;
	
	public SyncCooldownsS2CPacket(int cooldown) {
		this.cooldown = cooldown;
	}
	
	public SyncCooldownsS2CPacket(FriendlyByteBuf buf) {
		cooldown = buf.readInt();
	}
	
	public void toBytes(FriendlyByteBuf buf) {
		buf.writeInt(cooldown);
	}
	
	public boolean handle(Supplier<NetworkEvent.Context> supplier) {
		NetworkEvent.Context context = supplier.get();
		context.enqueueWork(() -> {
			ClientAbilityData.addCooldown(cooldown);
		});
		return true;
	}

}
