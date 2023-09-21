package com.pizzaprince.runeterramod.networking.packet;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.ability.AbilityItemCapabilityProvider;
import com.pizzaprince.runeterramod.block.ModBlocks;
import com.pizzaprince.runeterramod.item.custom.curios.legendary.RadiantVirtue;
import com.pizzaprince.runeterramod.networking.ModPackets;
import com.pizzaprince.runeterramod.util.KeyBinding;
import com.pizzaprince.runeterramod.world.dimension.ModDimensions;
import com.pizzaprince.runeterramod.world.dimension.ShellDimCapabilityProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import top.theillusivec4.curios.api.CuriosCapability;
import virtuoel.pehkui.api.ScaleTypes;

import java.awt.*;
import java.util.function.Supplier;

public class KeyPressC2SPacket {

	private final String key;

	public KeyPressC2SPacket(String key) {
		this.key = key;
	}

	public KeyPressC2SPacket(FriendlyByteBuf buf) {
		this.key = buf.readNbt().getString("key");
	}

	public void toBytes(FriendlyByteBuf buf) {
		CompoundTag nbt = new CompoundTag();
		nbt.putString("key", key);
		buf.writeNbt(nbt);
	}

	public boolean handle(Supplier<NetworkEvent.Context> supplier) {
		NetworkEvent.Context context = supplier.get();
		context.enqueueWork(() -> {
			ServerPlayer player = context.getSender();
			ServerLevel level = player.serverLevel();
			if(key.equals(KeyBinding.ULTIMATE_KEY.getName())) {
				player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(abilities -> {
					if (abilities.canUseAbilities()) {
						ItemStack stack = player.getMainHandItem();
						stack.getCapability(AbilityItemCapabilityProvider.ABILITY_ITEM_CAPABILITY).ifPresent(cap -> {
							if (cap.fireSelectedAbility(level, player)) {
								player.getCapability(CuriosCapability.INVENTORY).ifPresent(inventory -> {
									inventory.getCurios().values().forEach(curio -> {
										for (int slot = 0; slot < curio.getSlots(); slot++) {
											if (curio.getStacks().getStackInSlot(slot).getItem() instanceof RadiantVirtue) {
												int maxHealth = (int) player.getAttribute(Attributes.MAX_HEALTH).getValue();
												int percent = (int) (maxHealth * 0.2);
												player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, percent * 6, 3, true, true, true));
											}
										}
									});
								});
								abilities.setSheenHit(true);
							}
						});
					} else {
						player.sendSystemMessage(Component.literal("Static Cooldown has " + ((double) abilities.getCooldown() / 20) + " seconds left"));
					}
				});
			} else if(key.equals(KeyBinding.ASCENDED_KEY.getName())){
				player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(abilities -> {
					if(abilities.isTurtleAscended()){
						System.out.println(player.getPose().name());
						player.setPose(Pose.SWIMMING);
						//player.setForcedPose(Pose.SWIMMING);
						//ScaleTypes.BASE.getScaleData(player).setTargetScale(0.33f);
						abilities.setIsRetracting(true);
						ModPackets.sendToClients(new CapSyncS2CPacket(player));
						/*
							if(player.level().dimension() == ModDimensions.TURTLE_SHELL_SPACE_DIM){
								player.getServer().levelKeys().forEach(dim -> {
									if(dim.toString().equals(abilities.getLastDimension())){
										player.teleportTo(player.getServer().getLevel(dim).getLevel(), abilities.getLastPos().getX(), abilities.getLastPos().getY(), abilities.getLastPos().getZ(),
												player.getYHeadRot(), player.getXRot());
										level.setChunkForced(level.getChunk(abilities.getLastPos()).getPos().x, level.getChunk(abilities.getLastPos()).getPos().z, false);
										level.getChunkSource().removeRegionTicket(TicketType.FORCED, level.getChunk(abilities.getLastPos()).getPos(), 3, level.getChunk(abilities.getLastPos()).getPos());
									}
								});
							} else {

							abilities.setLastDimension(player.level().dimension().toString());
							abilities.setLastPos(player.getOnPos().above());
							level.setChunkForced(player.chunkPosition().x, player.chunkPosition().z, true);
							level.getChunkSource().addRegionTicket(TicketType.FORCED, player.chunkPosition(), 3, player.chunkPosition());
							boolean hasAccessedBefore;
							if(abilities.getShellNum() == -1){
								player.getServer().getLevel(ModDimensions.TURTLE_SHELL_SPACE_DIM).getLevel().getCapability(ShellDimCapabilityProvider.SHELL_DIM_CAPABILITY).ifPresent(shell -> {
									System.out.println(abilities.getShellNum());
									abilities.setShellNum(shell.callNewHolder());
									System.out.println(abilities.getShellNum());
								});
								hasAccessedBefore = false;
							} else {
								hasAccessedBefore = true;
							}

							if(!hasAccessedBefore){
								int num = abilities.getShellNum();
								for(int x = 1 + 1000*num; x <= 7 + 1000*num; x++){
									for(int y = 99; y < 106; y++){
										for(int z = 1; z <= 7; z++){
											if(y==99 || y==105){
												player.getServer().getLevel(ModDimensions.TURTLE_SHELL_SPACE_DIM).getLevel().setBlockAndUpdate(new BlockPos(x, y, z), ModBlocks.SHELL_BLOCK.get().defaultBlockState());
											} else if(x % 1000 == 1 || z == 1 || x % 1000 == 7 || z == 7){
												player.getServer().getLevel(ModDimensions.TURTLE_SHELL_SPACE_DIM).getLevel().setBlockAndUpdate(new BlockPos(x, y, z), ModBlocks.SHELL_BLOCK.get().defaultBlockState());
											}
										}
									}
								}
							}
							player.teleportTo(player.getServer().getLevel(ModDimensions.TURTLE_SHELL_SPACE_DIM).getLevel(), 4 + 1000*abilities.getShellNum(), 100, 4, player.getYHeadRot(), player.getXRot());
							*/
					}
				});
			} else {
				System.out.println(key + " does not match " + KeyBinding.ULTIMATE_KEY.getName() + " or " + KeyBinding.ASCENDED_KEY.getName());
			}
		});
		return true;
	}

}
