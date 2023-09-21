package com.pizzaprince.runeterramod.event;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.ability.*;
import com.pizzaprince.runeterramod.ability.curios.*;
import com.pizzaprince.runeterramod.client.ClientAbilityData;
import com.pizzaprince.runeterramod.effect.ModEffects;
import com.pizzaprince.runeterramod.entity.ModEntityTypes;
import com.pizzaprince.runeterramod.entity.custom.RampagingBaccaiEntity;
import com.pizzaprince.runeterramod.entity.custom.RekSaiEntity;
import com.pizzaprince.runeterramod.entity.custom.SunFishEntity;
import com.pizzaprince.runeterramod.entity.custom.projectile.RunaansHomingBolt;
import com.pizzaprince.runeterramod.item.custom.curios.base.AgilityCloak;
import com.pizzaprince.runeterramod.item.custom.curios.base.RejuvenationBead;
import com.pizzaprince.runeterramod.item.custom.curios.base.Sheen;
import com.pizzaprince.runeterramod.item.custom.curios.epic.BamisCinder;
import com.pizzaprince.runeterramod.item.custom.curios.epic.CrystallineBracer;
import com.pizzaprince.runeterramod.item.custom.curios.epic.VampiricScepter;
import com.pizzaprince.runeterramod.item.custom.curios.epic.Zeal;
import com.pizzaprince.runeterramod.item.custom.curios.legendary.*;
import com.pizzaprince.runeterramod.networking.ModPackets;
import com.pizzaprince.runeterramod.networking.packet.CancelShaderS2CPacket;
import com.pizzaprince.runeterramod.particle.ModParticles;
import com.pizzaprince.runeterramod.particle.custom.SandParticle;
import com.pizzaprince.runeterramod.world.dimension.ModDimensions;
import com.pizzaprince.runeterramod.world.dimension.ShellDimCapabilityProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosCapability;


public class ModEvents {


	@Mod.EventBusSubscriber(modid = RuneterraMod.MOD_ID)
	public class ForgeEvents {
		@SubscribeEvent
		public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof Player) {
				if (!event.getObject().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).isPresent()) {
					event.addCapability(new ResourceLocation(RuneterraMod.MOD_ID, "properties"), new PlayerAbilitiesProvider());
				}
			}
		}

		@SubscribeEvent
		public static void attachItemCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
			if (event.getObject().getItem() instanceof SunfireAegis) {
				event.addCapability(ImmolationCapabilityProvider.IMMOLATION_CAPABILITY_RL, new ImmolationCapabilityProvider(2, 20));
			}
			if (event.getObject().getItem() instanceof IAbilityItem) {
				event.addCapability(AbilityItemCapabilityProvider.ABILITY_ITEM_CAPABILITY_RL, new AbilityItemCapabilityProvider(event.getObject()));
			}
			if(event.getObject().getItem() instanceof Heartsteel){
				event.addCapability(HeartsteelCapabilityProvider.HEARTSTEEL_CAPABILITY_RL, new HeartsteelCapabilityProvider());
			}
			if(event.getObject().getItem() instanceof Warmogs){
				event.addCapability(WarmogsCapabilityProvider.WARMOGS_CAPABILITY_RL, new WarmogsCapabilityProvider());
			}
			if(event.getObject().getItem() instanceof RejuvenationBead){
				event.addCapability(RegenerationCapabilityProvider.REGENERATION_CAPABILITY_RL, new RegenerationCapabilityProvider(1, 200));
			}
			if(event.getObject().getItem() instanceof BamisCinder){
				event.addCapability(ImmolationCapabilityProvider.IMMOLATION_CAPABILITY_RL, new ImmolationCapabilityProvider(1, 20));
			}
			if(event.getObject().getItem() instanceof CrystallineBracer){
				event.addCapability(RegenerationCapabilityProvider.REGENERATION_CAPABILITY_RL, new RegenerationCapabilityProvider(1, 100));
			}
		}

		@SubscribeEvent
		public static void attachLevelCapabilities(AttachCapabilitiesEvent<Level> event){
			if(event.getObject().dimension() == ModDimensions.TURTLE_SHELL_SPACE_DIM){
				event.addCapability(ShellDimCapabilityProvider.SHELL_DIM_CAPABILITY_RL, new ShellDimCapabilityProvider());
			}
		}

		@SubscribeEvent
		public static void onBlockBreak(BlockEvent.BreakEvent event) {
			if(event.getPlayer().level().dimension() == ModDimensions.DISK_FIGHT_DIM_KEY && !event.getPlayer().isCreative()){
				event.setCanceled(true);
			}
		}

		@SubscribeEvent
		public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
			if (event.getEntity().level().dimension() == ModDimensions.DISK_FIGHT_DIM_KEY) {
				if(event.getEntity() instanceof Player player){
					if(!player.isCreative()){
						event.setCanceled(true);
					}
				} else {
					event.setCanceled(true);
				}
			}
		}

		@SubscribeEvent
		public static void mobEffectEvent(MobEffectEvent.Remove event) {
			if (event.getEffectInstance().getEffect() == ModEffects.QUENCHED.get()) {
				if (event.getEntity() instanceof ServerPlayer player)
					ModPackets.sendToPlayer(new CancelShaderS2CPacket(), player);
			}
		}

		@SubscribeEvent
		public static void onCriticalHit(CriticalHitEvent event) {
			Player player = event.getEntity();
			if (event.isVanillaCritical()) {
				player.getCapability(CuriosCapability.INVENTORY).ifPresent(inventory -> {
					inventory.getCurios().values().forEach(curio -> {
						for (int slot = 0; slot < curio.getSlots(); slot++) {
							if (curio.getStacks().getStackInSlot(slot).getItem() instanceof InfinityEdge) {
								event.setDamageModifier(event.getDamageModifier() + 0.5f);
							}
							if (curio.getStacks().getStackInSlot(slot).getItem() instanceof AgilityCloak) {
								event.setDamageModifier(event.getDamageModifier() + 0.1f);
							}
							if (curio.getStacks().getStackInSlot(slot).getItem() instanceof Zeal) {
								event.setDamageModifier(event.getDamageModifier() + 0.15f);
							}
						}
					});
				});
			}
		}

		@SubscribeEvent
		public static void onHit(LivingHurtEvent event) {
			if (event.getEntity() instanceof Player player) {
				//player.getCapability(CuriosCapability.INVENTORY).ifPresent(inventory -> {
				//	inventory.getCurios().values().forEach(curio -> {
				//		for (int slot = 0; slot < curio.getSlots(); slot++) {
				//			//if (curio.getStacks().getStackInSlot(slot).getItem() instanceof SunfireAegis || curio.getStacks().getStackInSlot(slot).getItem() instanceof BamisCinder) {
				//			//	curio.getStacks().getStackInSlot(slot).getCapability(ImmolationCapabilityProvider.IMMOLATION_CAPABILITY).ifPresent(cap -> {
				//			//		cap.startBurn();
				//			//	});
				//			//}
				//			//if (curio.getStacks().getStackInSlot(slot).getItem() instanceof Warmogs) {
				//			//	curio.getStacks().getStackInSlot(slot).getCapability(WarmogsCapabilityProvider.WARMOGS_CAPABILITY).ifPresent(cap -> {
				//			//		cap.setInCombat();
				//			//	});
				//			//}
				//		}
				//	});
				//});
				player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
					cap.setInCombat();
					cap.applyOnDamageEffects(event);
					if(cap.isTurtleAscended()){
						cap.calculateShellDamage(event);
					}
				});
			}

			if (event.getSource().getEntity() instanceof Player player) {
				//player.getCapability(CuriosCapability.INVENTORY).ifPresent(inventory -> {
				//	inventory.getCurios().values().forEach(curio -> {
				//		for (int slot = 0; slot < curio.getSlots(); slot++) {
				//			if (curio.getStacks().getStackInSlot(slot).getItem() instanceof SunfireAegis || curio.getStacks().getStackInSlot(slot).getItem() instanceof BamisCinder) {
				//				curio.getStacks().getStackInSlot(slot).getCapability(ImmolationCapabilityProvider.IMMOLATION_CAPABILITY).ifPresent(cap -> {
				//					cap.startBurn();
				//				});
				//			}
				//			if (curio.getStacks().getStackInSlot(slot).getItem() instanceof RylaisCrystalScepter) {
				//				event.getEntity().addEffect(new MobEffectInstance(ModEffects.RYLAIS_SLOW.get(),
				//						30, 1, true, true, true));
				//			}
				//			if (curio.getStacks().getStackInSlot(slot).getItem() instanceof BloodThirster) {
				//				player.heal(event.getAmount() * 0.2f);
				//			}
				//			if (curio.getStacks().getStackInSlot(slot).getItem() instanceof Warmogs) {
				//				curio.getStacks().getStackInSlot(slot).getCapability(WarmogsCapabilityProvider.WARMOGS_CAPABILITY).ifPresent(cap -> {
				//					cap.setInCombat();
				//				});
				//			}
				//			if (curio.getStacks().getStackInSlot(slot).getItem() instanceof Sheen) {
				//				player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
				//					if(cap.isSheenHit()){
				//						event.setAmount(event.getAmount() + 1);
				//						cap.setSheenHit(false);
				//					}
				//				});
				//			}
				//			if (curio.getStacks().getStackInSlot(slot).getItem() instanceof VampiricScepter) {
				//				player.heal(event.getAmount() * 0.05f);
				//			}
				//		}
				//	});
				//});
				player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
					cap.setInCombat();
					cap.applyHitEffects(event);
					if(cap.isCrocodileAscended()){
						event.setAmount(event.getAmount() * (1f + cap.getDamageMultiplierFromRage()));
						cap.addRage(5);
					}
				});
			}
		}

		@SubscribeEvent
		public static void onPlayerCloned(PlayerEvent.Clone event) {
			if (event.isWasDeath()) {
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
			event.register(ImmolationCapability.class);
			event.register(AbilityItemCapability.class);
			event.register(HeartsteelCapability.class);
			event.register(WarmogsCapability.class);
			event.register(RegenerationCapability.class);
		}


		@SubscribeEvent
		public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
			if (event.side == LogicalSide.SERVER) {
				event.player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(abilities -> {
					abilities.tick(event.player);
				});
				for (ItemStack item : event.player.getInventory().items) {
					item.getCapability(AbilityItemCapabilityProvider.ABILITY_ITEM_CAPABILITY).ifPresent(cap -> {
						cap.tick();
					});
				}
			}
			if (event.side == LogicalSide.CLIENT) {
				ClientAbilityData.tick();
			}
		}

		@SubscribeEvent
		public static void livingTick(LivingTickEvent event) {
			if (event.getEntity() instanceof Mob mob) {
				if (mob.hasEffect(ModEffects.STUN.get())) {
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
		public static void fireArrow(ArrowLooseEvent event) {
			Player player = event.getEntity();
			player.getCapability(CuriosCapability.INVENTORY).ifPresent(inventory -> {
				inventory.getCurios().values().forEach(curio -> {
					for (int slot = 0; slot < curio.getSlots(); slot++) {
						if (curio.getStacks().getStackInSlot(slot).getItem() instanceof RunaansHurricane) {
							Level level = event.getLevel();
							AbstractArrow arrow1 = new RunaansHomingBolt(level, player);
							AbstractArrow arrow2 = new RunaansHomingBolt(level, player);
							float scale = BowItem.getPowerForTime(event.getCharge());
							scale = scale / 2;
							arrow1.shootFromRotation(player, player.getXRot(), player.getYRot() + 12, 0.0F, scale * 3.0F, 1.0F);
							arrow2.shootFromRotation(player, player.getXRot(), player.getYRot() - 12, 0.0F, scale * 3.0F, 1.0F);
							if(scale == 1.0f){
								arrow1.setCritArrow(true);
								arrow2.setCritArrow(true);
							}
							level.addFreshEntity(arrow1);
							level.addFreshEntity(arrow2);
						}
					}
				});
			});

		}

		@SubscribeEvent
		public static void onEntityDeath(LivingDeathEvent event){
			if(event.getSource().getEntity() instanceof Player player){
				if(event.getEntity().getAttributeBaseValue(Attributes.MAX_HEALTH) > 200) {
					player.getCapability(CuriosCapability.INVENTORY).ifPresent(inventory -> {
						inventory.getCurios().values().forEach(curio -> {
							for (int slot = 0; slot < curio.getSlots(); slot++) {
								if (curio.getStacks().getStackInSlot(slot).getItem() instanceof Heartsteel) {
									curio.getStacks().getStackInSlot(slot).getCapability(HeartsteelCapabilityProvider.HEARTSTEEL_CAPABILITY).ifPresent(cap -> {
										cap.addStacks(player);
									});
								}
							}
						});
					});
				}
			}
		}

		@SubscribeEvent
		public static void onRightClickItem(PlayerInteractEvent.RightClickItem event){
			Player player = event.getEntity();
			player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
				cap.addRage(-20);
				if(cap.isCrocodileAscended() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof SwordItem){
					cap.addTempHitEffect("rage_stun", e -> {
						e.getEntity().addEffect(new MobEffectInstance(ModEffects.STUN.get(),
								40, 1, true, true, true));
						e.setAmount(e.getAmount() * 1.2f);
					});
				}
			});
		}


	}

	@Mod.EventBusSubscriber(modid = RuneterraMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class ModEventBusEvents {

		@SubscribeEvent
		public static void EntityAttributeEvent(EntityAttributeCreationEvent event) {
			event.put(ModEntityTypes.REKSAI.get(), RekSaiEntity.setAttributes());
			event.put(ModEntityTypes.RAMPAGING_BACCAI.get(), RampagingBaccaiEntity.setAttributes());
			event.put(ModEntityTypes.SUNFISH.get(), SunFishEntity.setAttributes());
		}

		@SubscribeEvent
		public static void registerParticles(RegisterParticleProvidersEvent event){
			Minecraft.getInstance().particleEngine.register(ModParticles.SAND_PARTICLE.get(), SandParticle.Provider::new);
		}


	}


}
