package com.pizzaprince.runeterramod.event;

import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.ability.PlayerAbilities;
import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.client.ClientAbilityData;
import com.pizzaprince.runeterramod.effect.ModEffects;
import com.pizzaprince.runeterramod.networking.ModPackets;
import com.pizzaprince.runeterramod.networking.packet.SyncCooldownsS2CPacket;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RuneterraMod.MOD_ID)
public class ModEvents {
	
	@SubscribeEvent
	public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
		if(event.getObject() instanceof Player) {
			if(!event.getObject().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).isPresent()) {
				event.addCapability(new ResourceLocation(RuneterraMod.MOD_ID, "properties"), new PlayerAbilitiesProvider());
			}
		}
	}
	
	@SubscribeEvent
	public static void onPlayerCloned(PlayerEvent.Clone event) {
		if(event.isWasDeath()) {
			event.getOriginal().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(oldStore -> {
				event.getOriginal().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(newStore -> {
					newStore.copyFrom(oldStore);
				});
			});
		}
	}
	
	@SubscribeEvent
	public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
		event.register(PlayerAbilities.class);
	}
	
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if(event.side == LogicalSide.SERVER) {
			event.player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(abilities -> {
				abilities.tick();
			});
		} 
	}
	
	@SubscribeEvent
	public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
		if(!event.getLevel().isClientSide()) {
			if(event.getEntity() instanceof ServerPlayer player) {
				player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(abilities -> {
					ModPackets.sendToPlayer(new SyncCooldownsS2CPacket(abilities.getCooldown()), player);
				});
			}
		}
	}
	
	@SubscribeEvent
	public static void livingTick(LivingTickEvent event) {
		if(event.getEntity() instanceof Mob mob) {
			if(mob.hasEffect(ModEffects.STUN.get())) {
				mob.goalSelector.disableControlFlag(Flag.MOVE);
	    		mob.targetSelector.disableControlFlag(Flag.MOVE);
	    		mob.goalSelector.disableControlFlag(Flag.JUMP);
	    		mob.targetSelector.disableControlFlag(Flag.JUMP);
	    		mob.goalSelector.disableControlFlag(Flag.LOOK);
	    		mob.targetSelector.disableControlFlag(Flag.LOOK);
	    		mob.goalSelector.disableControlFlag(Flag.TARGET);
	    		mob.targetSelector.disableControlFlag(Flag.TARGET);
	    		mob.getNavigation().setSpeedModifier(0);
			} else {
				mob.goalSelector.enableControlFlag(Flag.MOVE);
	    		mob.targetSelector.enableControlFlag(Flag.MOVE);
	    		mob.goalSelector.enableControlFlag(Flag.JUMP);
	    		mob.targetSelector.enableControlFlag(Flag.JUMP);
	    		mob.goalSelector.enableControlFlag(Flag.LOOK);
	    		mob.targetSelector.enableControlFlag(Flag.LOOK);
	    		mob.goalSelector.enableControlFlag(Flag.TARGET);
	    		mob.targetSelector.enableControlFlag(Flag.TARGET);
			}
		}
	}
	
	@SubscribeEvent
	public static void livingHurtEvent(LivingHurtEvent event) {
		if(event.getEntity() instanceof Mob mob) {
			if(mob.hasEffect(ModEffects.STUN.get())){
				mob.setNoAi(false);
			}
		}
		
	}

}
