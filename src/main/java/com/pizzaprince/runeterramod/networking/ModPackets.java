package com.pizzaprince.runeterramod.networking;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.networking.packet.*;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModPackets {
	private static SimpleChannel INSTANCE;
	
	private static int packetId = 0;
	private static int id() {
		return packetId++;
	}
	
	public static void register() {
		SimpleChannel net = NetworkRegistry.ChannelBuilder
				.named(new ResourceLocation(RuneterraMod.MOD_ID, "packets"))
				.networkProtocolVersion(() -> "1.0")
				.clientAcceptedVersions(s -> true)
				.serverAcceptedVersions(s -> true)
				.simpleChannel();
		
		INSTANCE = net;
		
		net.messageBuilder(KeyPressC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
				.decoder(KeyPressC2SPacket::new)
				.encoder(KeyPressC2SPacket::toBytes)
				.consumerMainThread(KeyPressC2SPacket::handle)
				.add();
		
		net.messageBuilder(SyncCooldownsS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
				.decoder(SyncCooldownsS2CPacket::new)
				.encoder(SyncCooldownsS2CPacket::toBytes)
				.consumerMainThread(SyncCooldownsS2CPacket::handle)
				.add();

		net.messageBuilder(CancelShaderS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
				.decoder(CancelShaderS2CPacket::new)
				.encoder(CancelShaderS2CPacket::toBytes)
				.consumerMainThread(CancelShaderS2CPacket::handle)
				.add();

		net.messageBuilder(BlockEntityItemStackSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
				.decoder(BlockEntityItemStackSyncS2CPacket::new)
				.encoder(BlockEntityItemStackSyncS2CPacket::toBytes)
				.consumerMainThread(BlockEntityItemStackSyncS2CPacket::handle)
				.add();
	}
	
	public static <MSG> void sendToServer(MSG message) {
		INSTANCE.sendToServer(message);
	}
	
	
	public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
		INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
	}
	
	public static <MSG> void sendToNearbyPlayers(MSG message, Level level, BlockPos pos) {
		INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(pos)), message);
	}

	public static <MSG> void sendToClients(MSG message) {
		INSTANCE.send(PacketDistributor.ALL.noArg(), message);
	}
}
