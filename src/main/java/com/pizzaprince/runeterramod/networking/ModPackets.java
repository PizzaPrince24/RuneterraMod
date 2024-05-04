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
		
		net.messageBuilder(UltimateKeyPressC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
				.decoder(UltimateKeyPressC2SPacket::new)
				.encoder(UltimateKeyPressC2SPacket::toBytes)
				.consumerMainThread(UltimateKeyPressC2SPacket::handle)
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

		net.messageBuilder(AOEParticleS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
				.decoder(AOEParticleS2CPacket::new)
				.encoder(AOEParticleS2CPacket::toBytes)
				.consumerMainThread(AOEParticleS2CPacket::handle)
				.add();

		net.messageBuilder(CapSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
				.decoder(CapSyncS2CPacket::new)
				.encoder(CapSyncS2CPacket::toBytes)
				.consumerMainThread(CapSyncS2CPacket::handle)
				.add();

		net.messageBuilder(CrocRageS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
				.decoder(CrocRageS2CPacket::new)
				.encoder(CrocRageS2CPacket::toBytes)
				.consumerMainThread(CrocRageS2CPacket::handle)
				.add();

		net.messageBuilder(AscendedKeyPressC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
				.decoder(AscendedKeyPressC2SPacket::new)
				.encoder(AscendedKeyPressC2SPacket::toBytes)
				.consumerMainThread(AscendedKeyPressC2SPacket::handle)
				.add();

		net.messageBuilder(PlayerAnimationS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
				.decoder(PlayerAnimationS2CPacket::new)
				.encoder(PlayerAnimationS2CPacket::toBytes)
				.consumerMainThread(PlayerAnimationS2CPacket::handle)
				.add();

		net.messageBuilder(RageArtCameraSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
				.decoder(RageArtCameraSyncS2CPacket::new)
				.encoder(RageArtCameraSyncS2CPacket::toBytes)
				.consumerMainThread(RageArtCameraSyncS2CPacket::handle)
				.add();

		net.messageBuilder(RageArtTickSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
				.decoder(RageArtTickSyncS2CPacket::new)
				.encoder(RageArtTickSyncS2CPacket::toBytes)
				.consumerMainThread(RageArtTickSyncS2CPacket::handle)
				.add();

		net.messageBuilder(RageArtCapSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
				.decoder(RageArtCapSyncS2CPacket::new)
				.encoder(RageArtCapSyncS2CPacket::toBytes)
				.consumerMainThread(RageArtCapSyncS2CPacket::handle)
				.add();

		net.messageBuilder(EagleFastFlightC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
				.decoder(EagleFastFlightC2SPacket::new)
				.encoder(EagleFastFlightC2SPacket::toBytes)
				.consumerMainThread(EagleFastFlightC2SPacket::handle)
				.add();

		net.messageBuilder(UpdateWayPointC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
				.decoder(UpdateWayPointC2SPacket::new)
				.encoder(UpdateWayPointC2SPacket::toBytes)
				.consumerMainThread(UpdateWayPointC2SPacket::handle)
				.add();

		net.messageBuilder(UpdateCustomPoisonsC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
				.decoder(UpdateCustomPoisonsC2SPacket::new)
				.encoder(UpdateCustomPoisonsC2SPacket::toBytes)
				.consumerMainThread(UpdateCustomPoisonsC2SPacket::handle)
				.add();

		net.messageBuilder(UpdateSelectedPoisonEffectC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
				.decoder(UpdateSelectedPoisonEffectC2SPacket::new)
				.encoder(UpdateSelectedPoisonEffectC2SPacket::toBytes)
				.consumerMainThread(UpdateSelectedPoisonEffectC2SPacket::handle)
				.add();

		net.messageBuilder(MakePotionFromSelectedC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
				.decoder(MakePotionFromSelectedC2SPacket::new)
				.encoder(MakePotionFromSelectedC2SPacket::toBytes)
				.consumerMainThread(MakePotionFromSelectedC2SPacket::handle)
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
