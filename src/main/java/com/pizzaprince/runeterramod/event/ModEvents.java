package com.pizzaprince.runeterramod.event;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.ability.*;
import com.pizzaprince.runeterramod.ability.ascendent.*;
import com.pizzaprince.runeterramod.ability.curios.*;
import com.pizzaprince.runeterramod.client.ClientAbilityData;
import com.pizzaprince.runeterramod.effect.ModAttributes;
import com.pizzaprince.runeterramod.effect.ModDamageTypes;
import com.pizzaprince.runeterramod.effect.ModEffects;
import com.pizzaprince.runeterramod.entity.ModEntityTypes;
import com.pizzaprince.runeterramod.entity.custom.*;
import com.pizzaprince.runeterramod.entity.custom.projectile.RunaansHomingBolt;
import com.pizzaprince.runeterramod.item.custom.curios.legendary.*;
import com.pizzaprince.runeterramod.mixin.MixinRangedAttribute;
import com.pizzaprince.runeterramod.networking.ModPackets;
import com.pizzaprince.runeterramod.networking.packet.CancelShaderS2CPacket;
import com.pizzaprince.runeterramod.particle.ModParticles;
import com.pizzaprince.runeterramod.particle.custom.SandParticle;
import com.pizzaprince.runeterramod.particle.custom.GlowParticle;
import com.pizzaprince.runeterramod.util.ModTags;
import com.pizzaprince.runeterramod.world.dimension.ModDimensions;
import com.pizzaprince.runeterramod.world.dimension.ShellDimCapabilityProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import top.theillusivec4.curios.api.CuriosCapability;


public class ModEvents {


	@Mod.EventBusSubscriber(modid = RuneterraMod.MOD_ID)
	public class ForgeEvents {
		@SubscribeEvent
		public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof Player) {
				if (!event.getObject().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).isPresent()) {
					event.addCapability(new ResourceLocation(RuneterraMod.MOD_ID, "player_properties"), new PlayerAbilitiesProvider());
				}
			}
			if(event.getObject() instanceof LivingEntity entity){
				if (!event.getObject().getCapability(LivingEntityCapabilityProvider.CAPABILITY).isPresent()) {
					event.addCapability(new ResourceLocation(RuneterraMod.MOD_ID, "living_entity_properties"), new LivingEntityCapabilityProvider());
				}
			}
		}

		@SubscribeEvent
		public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
			event.register(PlayerAbilities.class);
			event.register(LivingEntityCapability.class);
			event.register(ImmolationCapability.class);
			event.register(AbilityItemCapability.class);
			event.register(HeartsteelCapability.class);
			event.register(WarmogsCapability.class);
			event.register(RegenerationCapability.class);
			event.register(JakShoCapability.class);
			event.register(RodOfAgesCapability.class);
		}

		@SubscribeEvent
		public static void attachItemCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
			//System.out.println(event.getObject().getDisplayName().getString());
			//System.out.println(BuiltInRegistries.ITEM.getKey(event.getObject().getItem()));
			if (BuiltInRegistries.ITEM.getKey(event.getObject().getItem()).toString().equals("runeterramod:sunfire_aegis")) {
				event.addCapability(ImmolationCapabilityProvider.IMMOLATION_CAPABILITY_RL, new ImmolationCapabilityProvider(2, 20));
			}
			if (event.getObject().getItem() instanceof IAbilityItem) {
				event.addCapability(AbilityItemCapabilityProvider.ABILITY_ITEM_CAPABILITY_RL, new AbilityItemCapabilityProvider(event.getObject()));
			}
			if(BuiltInRegistries.ITEM.getKey(event.getObject().getItem()).toString().equals("runeterramod:heartsteel")){
				event.addCapability(HeartsteelCapabilityProvider.HEARTSTEEL_CAPABILITY_RL, new HeartsteelCapabilityProvider());
			}
			if(event.getObject().getItem() instanceof Warmogs){
				event.addCapability(WarmogsCapabilityProvider.WARMOGS_CAPABILITY_RL, new WarmogsCapabilityProvider());
			}
			if(BuiltInRegistries.ITEM.getKey(event.getObject().getItem()).toString().equals("runeterramod:rejuvenation_bead")){
				event.addCapability(RegenerationCapabilityProvider.REGENERATION_CAPABILITY_RL, new RegenerationCapabilityProvider(1, 200));
			}
			if(BuiltInRegistries.ITEM.getKey(event.getObject().getItem()).toString().equals("runeterramod:bamis_cinder")){
				event.addCapability(ImmolationCapabilityProvider.IMMOLATION_CAPABILITY_RL, new ImmolationCapabilityProvider(1, 20));
			}
			if(BuiltInRegistries.ITEM.getKey(event.getObject().getItem()).toString().equals("runeterramod:crystalline_bracer")){
				event.addCapability(RegenerationCapabilityProvider.REGENERATION_CAPABILITY_RL, new RegenerationCapabilityProvider(1, 100));
			}
			if(event.getObject().getItem() instanceof JakSho){
				event.addCapability(JakShoCapabilityProvider.JAKSHO_CAPABILITY_RL, new JakShoCapabilityProvider());
			}
			if(event.getObject().getItem() instanceof RodOfAges){
				event.addCapability(RodOfAgesCapabilityProvider.CAPABILITY_RL, new RodOfAgesCapabilityProvider());
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
		public static void mobEffectEvent(MobEffectEvent event) {
			if(event instanceof MobEffectEvent.Remove || event instanceof MobEffectEvent.Expired) {
				if (event.getEffectInstance().getEffect() == ModEffects.QUENCHED.get()) {
					if (event.getEntity() instanceof ServerPlayer player)
						ModPackets.sendToPlayer(new CancelShaderS2CPacket(), player);
				}
			}
		}

		@SubscribeEvent
		public static void onCriticalHit(CriticalHitEvent event) {
			event.setResult(Event.Result.DENY);
			Player player = event.getEntity();
			if (event.isVanillaCritical()) {
				player.getCapability(CuriosCapability.INVENTORY).ifPresent(inventory -> {
					inventory.getCurios().values().forEach(curio -> {
						for (int slot = 0; slot < curio.getSlots(); slot++) {
							if (curio.getStacks().getStackInSlot(slot).getItem() instanceof InfinityEdge) {
								event.setDamageModifier(event.getDamageModifier() + 0.5f);
							}
						}
					});
				});
			}
		}

		@SubscribeEvent
		public static void onHit(LivingHurtEvent event) {
			if (event.getEntity() instanceof Player player) {
				player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
					cap.setInCombat();
					cap.applyOnDamageEffects(event);
					if(cap.getAscendantType() == AscendantType.CROCODILE){
						if(((CrocodileAscendant)cap.getAscendant()).getRageArtTargetID() != -1){
							event.setCanceled(true);
						}
					}
				});
			}
			if (event.getSource().getEntity() instanceof LivingEntity entity) {
				if(entity.getAttributeValue(ModAttributes.CRIT_CHANCE.get()) > Math.random()){
					event.setAmount(event.getAmount()*(float)entity.getAttributeValue(ModAttributes.CRIT_DAMAGE.get()));
				}

				if(entity instanceof Player player) {
					player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
						cap.setInCombat();
						cap.applyHitEffects(event);
						if (cap.getAscendantType() == AscendantType.CROCODILE) {
							CrocodileAscendant ascendant = (CrocodileAscendant) cap.getAscendant();
							event.setAmount(event.getAmount() * (1f + ascendant.getDamageMultiplierFromRage()));
							ascendant.addRage(5, (ServerPlayer) player);
						}
						if(cap.getAscendantType() == AscendantType.EAGLE){
							if(player.isFallFlying()){
								event.setAmount(event.getAmount() * 1.5f);
							}
						}
					});
				}
			}
			if(event.getEntity().hasEffect(ModEffects.STUN.get())){
				event.getEntity().level().getServer().getPlayerList().getPlayers().forEach(player -> {
					player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
						if(cap.getAscendantType() == AscendantType.CROCODILE){
							CrocodileAscendant ascendant = (CrocodileAscendant) cap.getAscendant();
							if(event.getEntity().getId() == ascendant.getRageArtTargetID()){
								if(event.getSource().type() != ModDamageTypes.getDamageSource(player.level(), ModDamageTypes.RAGE_ART).type()){
									event.setCanceled(true);
								}
							}
						}
					});
				});
			}
		}

		@SubscribeEvent
		public static void onDamage(LivingDamageEvent event){
			if(event.getSource().getEntity() instanceof LivingEntity entity){
				if(entity.hasEffect(ModEffects.EXHAUSTED.get())){
					event.setAmount(event.getAmount() * (float)Math.pow(0.8, event.getEntity().getEffect(ModEffects.EXHAUSTED.get()).getAmplifier()+1));
				}
				entity.heal(event.getAmount()*(float)entity.getAttributeValue(ModAttributes.OMNIVAMP.get()));
			}
			if (event.getEntity() instanceof Player player) {
				player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
					if(cap.getAscendantType() == AscendantType.TURTLE){
						((TurtleAscendant)cap.getAscendant()).calculateShellDamage(event);
					}
				});
			}
			if(event.getEntity().hasEffect(ModEffects.VULNERABILITY.get())){
				event.setAmount(event.getAmount()*(1 + 0.2f*(event.getEntity().getEffect(ModEffects.VULNERABILITY.get()).getAmplifier()+1)));
			}
			if(event.getSource().is(ModTags.DamageTypes.NO_INVULN)){
				event.getEntity().invulnerableTime = 0;
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
		public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
			if(event.phase == TickEvent.Phase.START) {
				if (event.side == LogicalSide.SERVER) {
					if (event.player instanceof ServerPlayer player) {
						event.player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(abilities -> {
							abilities.tick(player);
						});
						/*
						event.player.getCapability(AscendantCapabilityProvider.ASCENDENT_CAPABILITY).ifPresent(cap -> {
							cap.tick(player);
						});

						 */
					}
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
		}

		@SubscribeEvent
		public static void onServerTick(TickEvent.ServerTickEvent event){
			if(event.phase == TickEvent.Phase.START && event.side == LogicalSide.SERVER){
				event.getServer().getAllLevels().forEach(level -> {
					level.getAllEntities().forEach(entity -> {
						if(entity instanceof LivingEntity livingEntity){
							livingEntity.getCapability(LivingEntityCapabilityProvider.CAPABILITY).ifPresent(cap -> {
								cap.tick(livingEntity);
							});
						}
					});
				});
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
								if (BuiltInRegistries.ITEM.getKey(curio.getStacks().getStackInSlot(slot).getItem()).toString().equals("runeterramod:heartsteel")) {
									curio.getStacks().getStackInSlot(slot).getCapability(HeartsteelCapabilityProvider.HEARTSTEEL_CAPABILITY).ifPresent(cap -> {
										cap.addStacks(player);
									});
								}
							}
						});
					});
				}
				player.getCapability(CuriosCapability.INVENTORY).ifPresent(inventory -> {
					inventory.getCurios().values().forEach(curio -> {
						for (int slot = 0; slot < curio.getSlots(); slot++) {
							if (BuiltInRegistries.ITEM.getKey(curio.getStacks().getStackInSlot(slot).getItem()).toString().equals("runeterramod:opportunity")) {
								player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60, 1, true, true, true));
							}
						}
					});
				});
				player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
					if(cap.getAscendantType() == AscendantType.CROCODILE){
						CrocodileAscendant ascendant = (CrocodileAscendant) cap.getAscendant();
						ascendant.updateStatsOnKill(player, event.getEntity());
					}
				});
			}
		}

		@SubscribeEvent
		public static void addMobEffectInstance(MobEffectEvent.Added event){
			MobEffectInstance oldInstance = event.getEffectInstance();
			if(oldInstance.getEffect().getCategory() != MobEffectCategory.HARMFUL) return;
			int newDuration = oldInstance.getDuration() - (int)(oldInstance.getDuration()*event.getEntity().getAttributeValue(ModAttributes.TENACITY.get()));
			MobEffectInstance newInstance = new MobEffectInstance(oldInstance.getEffect(), newDuration, oldInstance.getAmplifier(), oldInstance.isAmbient(),
					oldInstance.isVisible(), oldInstance.showIcon(), null, oldInstance.getFactorData());
			newInstance.setCurativeItems(oldInstance.getCurativeItems());
			oldInstance.setDetailsFrom(newInstance);
		}

		@SubscribeEvent
		public static void entityJump(LivingEvent.LivingJumpEvent event){
			if(event.getEntity() instanceof Player player){
				player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
					if(cap.getAscendantType() == AscendantType.EAGLE){
						EagleAscendant ascendant = (EagleAscendant) cap.getAscendant();
						int crouchTicks = ascendant.getCrouchTicks();
						if(crouchTicks > 0){
							Vec3 movement = player.getDeltaMovement();
							double y = (double)crouchTicks/10d;
							player.setDeltaMovement(movement.x, movement.y + y, movement.z);
							ascendant.resetCrouchTicks();
							if(player.level() instanceof ClientLevel level){
								for (int i = 0; i < 30; i++) {
									double randX = Math.random() * player.getBbWidth() - player.getBbWidth() / 2;
									double randZ = Math.random() * player.getBbWidth() - player.getBbWidth() / 2;
									level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, level.getBlockState(player.getOnPos())),
											player.getX()+randX, player.getY(), player.getZ()+randZ, 0, 0, 0);
								}
							}
						}
					}
				});
			}
		}

		@SubscribeEvent
		public static void fallDamage(LivingFallEvent event){
			if(event.getEntity() instanceof Player player){
				player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
					if(cap.getAscendantType() == AscendantType.EAGLE){
						EagleAscendant ascendant = (EagleAscendant) cap.getAscendant();
						ascendant.shockwave(player, event.getDistance());
						event.setDistance(0);
					}
				});
			}
			if(event.getEntity() instanceof RenektonEntity){
				event.setDistance(0);
				event.setDamageMultiplier(0);
			}
		}


	}

	@Mod.EventBusSubscriber(modid = RuneterraMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class ModEventBusEvents {

		@SubscribeEvent
		public static void EntityAttributeEvent(EntityAttributeCreationEvent event) {
			event.put(ModEntityTypes.REKSAI.get(), RekSaiEntity.setAttributes());
			event.put(ModEntityTypes.RAMPAGING_BACCAI.get(), RampagingBaccaiEntity.setAttributes());
			event.put(ModEntityTypes.SUNFISH.get(), SunFishEntity.setAttributes());
			event.put(ModEntityTypes.RENEKTON.get(), RenektonEntity.setAttributes());
			event.put(ModEntityTypes.BLUE_CASTER_MINION.get(), BlueCasterMinionEntity.setAttributes());
			event.put(ModEntityTypes.BLUE_MELEE_MINION.get(), BlueMeleeMinionEntity.setAttributes());
			event.put(ModEntityTypes.BLUE_CANNON_MINION.get(), BlueCannonMinionEntity.setAttributes());
			event.put(ModEntityTypes.BLUE_SUPER_MINION.get(), BlueSuperMinionEntity.setAttributes());
		}

		@SubscribeEvent
		public static void registerParticles(RegisterParticleProvidersEvent event){
			Minecraft.getInstance().particleEngine.register(ModParticles.SAND_PARTICLE.get(), SandParticle.Provider::new);
			Minecraft.getInstance().particleEngine.register(ModParticles.GLOW_PARTICLE.get(), GlowParticle.Provider::new);
		}

		@SubscribeEvent
		public static void newAttributes(EntityAttributeModificationEvent event){
			event.getTypes().forEach(type -> {
				event.add(type, ModAttributes.ABILITY_POWER.get());
				event.add(type, ModAttributes.ABILITY_HASTE.get());
				event.add(type, ModAttributes.MAGIC_RESIST.get());
				event.add(type, ModAttributes.MAGIC_PENETRATION.get());
				event.add(type, ModAttributes.LETHALITY.get());
				event.add(type, ModAttributes.OMNIVAMP.get());
				event.add(type, ModAttributes.TENACITY.get());
				event.add(type, ModAttributes.CRIT_CHANCE.get());
				event.add(type, ModAttributes.CRIT_DAMAGE.get());
			});
		}

		@SubscribeEvent
		public static void modifyAttributeRanges(FMLLoadCompleteEvent event){
			Attribute armor = BuiltInRegistries.ATTRIBUTE.get(ResourceLocation.tryParse("minecraft:generic.armor"));
			MixinRangedAttribute ranged = (MixinRangedAttribute) ((Object) armor);
			ranged.setMaxValue(1028);
		}


	}


}
