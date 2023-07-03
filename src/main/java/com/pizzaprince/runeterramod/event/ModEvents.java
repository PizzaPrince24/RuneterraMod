package com.pizzaprince.runeterramod.event;

import com.pizzaprince.runeterramod.ability.IAbilityItem;
import com.pizzaprince.runeterramod.ability.item.custom.AbilityItemCapability;
import com.pizzaprince.runeterramod.ability.item.custom.AbilityItemCapabilityProvider;
import com.pizzaprince.runeterramod.ability.item.custom.curios.SunfireAegisCapability;
import com.pizzaprince.runeterramod.ability.item.custom.curios.SunfireAegisCapabilityProvider;
import com.pizzaprince.runeterramod.client.ClientAbilityData;
import com.pizzaprince.runeterramod.entity.custom.RampagingBaccaiEntity;
import com.pizzaprince.runeterramod.item.ModItems;
import com.pizzaprince.runeterramod.item.custom.curios.InfinityEdge;
import com.pizzaprince.runeterramod.item.custom.curios.Rylais;
import com.pizzaprince.runeterramod.item.custom.curios.SunfireAegis;
import com.pizzaprince.runeterramod.networking.packet.CancelShaderS2CPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.level.BlockEvent;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.ability.PlayerAbilities;
import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.effect.ModEffects;
import com.pizzaprince.runeterramod.entity.ModEntityTypes;
import com.pizzaprince.runeterramod.entity.custom.RekSaiEntity;
import com.pizzaprince.runeterramod.networking.ModPackets;
import com.pizzaprince.runeterramod.networking.packet.SyncCooldownsS2CPacket;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosCapability;


public class ModEvents {


	@Mod.EventBusSubscriber(modid = RuneterraMod.MOD_ID)
	public class ForgeEvents{
		@SubscribeEvent
		public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
			if(event.getObject() instanceof Player) {
				if(!event.getObject().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).isPresent()) {
					event.addCapability(new ResourceLocation(RuneterraMod.MOD_ID, "properties"), new PlayerAbilitiesProvider());
				}
			}
		}

		@SubscribeEvent
		public static void attachItemCapabilities(AttachCapabilitiesEvent<ItemStack> event){
			if(event.getObject().getItem() instanceof SunfireAegis){
				event.addCapability(SunfireAegisCapabilityProvider.SUNFIRE_AEGIS_CAPABILITY_RL, new SunfireAegisCapabilityProvider());
			}
			if(event.getObject().getItem() instanceof IAbilityItem){
				event.addCapability(AbilityItemCapabilityProvider.ABILITY_ITEM_CAPABILITY_RL, new AbilityItemCapabilityProvider(event.getObject()));
			}
		}

		@SubscribeEvent
		public static void onBlockBreak(BlockEvent.BreakEvent event){
			if(event.getState().is(Blocks.CACTUS) && event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem()
					instanceof SwordItem){
				event.getLevel().addFreshEntity(new ItemEntity(event.getPlayer().level(), event.getPos().getX(), event.getPos().getY(),
						event.getPos().getZ(), new ItemStack(ModItems.CACTUS_JUICE.get())));
			}
		}

		@SubscribeEvent
		public static void mobEffectEvent(MobEffectEvent.Remove event){
			if(event.getEffectInstance().getEffect() == ModEffects.QUENCHED.get()){
				if(event.getEntity() instanceof ServerPlayer player)
					ModPackets.sendToPlayer(new CancelShaderS2CPacket(), player);
			}
		}

		@SubscribeEvent
		public static void onCriticalHit(CriticalHitEvent event){
			Player player = event.getEntity();
			if(event.isVanillaCritical()) {
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
		public static void onHit(LivingHurtEvent event){
			if(event.getEntity() instanceof Player player){
				player.getCapability(CuriosCapability.INVENTORY).ifPresent(inventory -> {
					inventory.getCurios().values().forEach(curio -> {
						for(int slot = 0; slot < curio.getSlots(); slot++){
							if(curio.getStacks().getStackInSlot(slot).getItem() instanceof SunfireAegis){
								curio.getStacks().getStackInSlot(slot).getCapability(SunfireAegisCapabilityProvider.SUNFIRE_AEGIS_CAPABILITY).ifPresent(cap -> {
									cap.startBurn();
								});
							}
						}
					});
				});
			}

			if(event.getSource().getEntity() instanceof Player player){
				player.getCapability(CuriosCapability.INVENTORY).ifPresent(inventory -> {
					inventory.getCurios().values().forEach(curio -> {
						for(int slot = 0; slot < curio.getSlots(); slot++){
							if(curio.getStacks().getStackInSlot(slot).getItem() instanceof SunfireAegis){
								curio.getStacks().getStackInSlot(slot).getCapability(SunfireAegisCapabilityProvider.SUNFIRE_AEGIS_CAPABILITY).ifPresent(cap -> {
									cap.startBurn();
								});
							}
							if(curio.getStacks().getStackInSlot(slot).getItem() instanceof Rylais){
								event.getEntity().addEffect(new MobEffectInstance(ModEffects.RYLAIS_SLOW.get(),
										30, 1, true, true, true));
							}
						}
					});
				});
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
			event.register(SunfireAegisCapability.class);
			event.register(AbilityItemCapability.class);
		}


		@SubscribeEvent
		public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
			if(event.side == LogicalSide.SERVER) {
				event.player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(abilities -> {
					abilities.tick();
				});
				for(ItemStack item : event.player.getInventory().items){
					item.getCapability(AbilityItemCapabilityProvider.ABILITY_ITEM_CAPABILITY).ifPresent(cap -> {
						cap.tick();
					});
				}
			}
			if(event.side == LogicalSide.CLIENT){
				ClientAbilityData.tick();
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
	}

	@Mod.EventBusSubscriber(modid = RuneterraMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class ModEventBusEvents {

		@SubscribeEvent
		public static void EntityAttributeEvent(EntityAttributeCreationEvent event) {
			event.put(ModEntityTypes.REKSAI.get(), RekSaiEntity.setAttributes());
			event.put(ModEntityTypes.RAMPAGING_BACCAI.get(), RampagingBaccaiEntity.setAttributes());
		}


	}


}
