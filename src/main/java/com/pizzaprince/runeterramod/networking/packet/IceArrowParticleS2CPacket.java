package com.pizzaprince.runeterramod.networking.packet;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;

public class IceArrowParticleS2CPacket {
	private final double x;
	private final double y;
	private final double z; 
	
	public IceArrowParticleS2CPacket(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public IceArrowParticleS2CPacket(FriendlyByteBuf buf) {
		this.x = buf.readDouble();
		this.y = buf.readDouble();
		this.z = buf.readDouble();
	}
	
	public void toBytes(FriendlyByteBuf buf) {
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
	}
	
	public boolean handle(Supplier<NetworkEvent.Context> supplier) {
		NetworkEvent.Context context = supplier.get();
		context.enqueueWork(() -> {
			Minecraft m = Minecraft.getInstance();
			for(int i = 0; i < 20; i++) {
				m.level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.BLUE_ICE.defaultBlockState()), true, x, y, z, 0d, 0d, 0d);
			}
		});
		return true;
	}

}
