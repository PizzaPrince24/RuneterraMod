package com.pizzaprince.runeterramod.networking.packet;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.effect.ModEffects;
import com.pizzaprince.runeterramod.networking.ModPackets;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.network.NetworkEvent;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.UUID;
import java.util.function.Supplier;

public class AscendedKeyPressC2SPacket {

    private final int entityID;

    public AscendedKeyPressC2SPacket(int eID) {
        this.entityID = eID;
    }

    public AscendedKeyPressC2SPacket(FriendlyByteBuf buf) {
        this.entityID = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.entityID);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();
            player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(abilities -> {
                if(abilities.isCrocodileAscended()){
                    if(player.isShiftKeyDown()){
                        if(abilities.getRage() >= 50){
                            ModPackets.sendToClients(new PlayerAnimationS2CPacket(player.getUUID(), "spin"));
                            abilities.addRage(-50, player);
                            abilities.startSpin();
                            UUID randId = UUID.randomUUID();
                            player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(new AttributeModifier(randId,"spin_slow", -0.8, AttributeModifier.Operation.MULTIPLY_TOTAL));
                            abilities.setSlowUUID(randId);
                        }
                    } else {
                        if(abilities.getRage() == 0 && this.entityID != -1){
                            if(level.getEntity(this.entityID) instanceof LivingEntity target){
                                abilities.startRageArt(this.entityID);
                                ModPackets.sendToClients(new PlayerAnimationS2CPacket(player.getUUID(), "rage_art"));
                                ModPackets.sendToPlayer(new RageArtCameraSyncS2CPacket(player, target, true), player);
                                if(target instanceof ServerPlayer player1){
                                    ModPackets.sendToPlayer(new RageArtCameraSyncS2CPacket(player, target, false), player1);
                                }
                                target.addEffect(new MobEffectInstance(ModEffects.STUN.get(), 172, 0, true, true, true));
                                abilities.addRage(-100, player);
                            }
                        } else if(abilities.getRage() >= 25){
                            if(abilities.removeTempHitEffect("rage_stun")){
                                abilities.addTempHitEffect("rage_stun", e -> {
                                    e.getEntity().addEffect(new MobEffectInstance(ModEffects.STUN.get(),
                                            40, 1, true, true, true));
                                    e.setAmount(e.getAmount() * 1.2f);
                                });
                            } else {
                                abilities.addRage(-25, player);
                                abilities.addTempHitEffect("rage_stun", e -> {
                                    e.getEntity().addEffect(new MobEffectInstance(ModEffects.STUN.get(),
                                            40, 1, true, true, true));
                                    e.setAmount(e.getAmount() * 1.2f);
                                });
                            }
                        }
                    }
                }
                if(abilities.isTurtleAscended()){
                    //System.out.println(player.getPose().name());
                    //player.setPose(Pose.SWIMMING);
                    //player.setForcedPose(Pose.SWIMMING);
                    ScaleTypes.BASE.getScaleData(player).setScaleTickDelay(10);
                    if(abilities.isRetracted()){
                        ScaleTypes.BASE.getScaleData(player).setTargetScale(1f);
                    } else {
                        ScaleTypes.BASE.getScaleData(player).setTargetScale(0.33f);
                    }
                    abilities.setIsRetracting(true);
                    ModPackets.sendToClients(new CapSyncS2CPacket(player));
                    ModPackets.sendToClients(new PlayerAnimationS2CPacket(player.getUUID(), "retract"));
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
        });
        return true;
    }

}
