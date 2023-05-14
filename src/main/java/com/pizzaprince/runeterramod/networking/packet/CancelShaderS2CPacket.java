package com.pizzaprince.runeterramod.networking.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CancelShaderS2CPacket {

	public CancelShaderS2CPacket() {

	}

	public CancelShaderS2CPacket(FriendlyByteBuf buf) {
	}
	
	public void toBytes(FriendlyByteBuf buf) {
	}
	
	public boolean handle(Supplier<NetworkEvent.Context> supplier) {
		NetworkEvent.Context context = supplier.get();
		context.enqueueWork(() -> {
			Minecraft.getInstance().gameRenderer.shutdownEffect();
		});
		return true;
	}

}
