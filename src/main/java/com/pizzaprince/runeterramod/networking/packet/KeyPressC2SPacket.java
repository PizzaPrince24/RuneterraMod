package com.pizzaprince.runeterramod.networking.packet;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.ability.item.custom.AbilityItemCapabilityProvider;
import com.pizzaprince.runeterramod.client.ClientAbilityData;
import com.pizzaprince.runeterramod.item.custom.curios.legendary.RadiantVirtue;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import top.theillusivec4.curios.api.CuriosCapability;

import java.util.function.Supplier;

public class KeyPressC2SPacket {
	
	public KeyPressC2SPacket() {
		
	}
	
	public KeyPressC2SPacket(FriendlyByteBuf buf) {

	}
	
	public void toBytes(FriendlyByteBuf buf) {

	}
	
	public boolean handle(Supplier<NetworkEvent.Context> supplier) {
		NetworkEvent.Context context = supplier.get();
		context.enqueueWork(() -> {
			ServerPlayer player = context.getSender();
			ServerLevel level = player.serverLevel();
			
			
			player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(abilities -> {
				if(abilities.canUseAbilities()) {
					ItemStack stack = player.getMainHandItem();
					stack.getCapability(AbilityItemCapabilityProvider.ABILITY_ITEM_CAPABILITY).ifPresent(cap -> {
						if(cap.fireSelectedAbility(level, player)){
							player.getCapability(CuriosCapability.INVENTORY).ifPresent(inventory -> {
								inventory.getCurios().values().forEach(curio -> {
									for (int slot = 0; slot < curio.getSlots(); slot++) {
										if (curio.getStacks().getStackInSlot(slot).getItem() instanceof RadiantVirtue) {
											int maxHealth = (int) player.getAttribute(Attributes.MAX_HEALTH).getValue();
											int percent = (int) (maxHealth * 0.2);
											player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, percent*6, 3, true, true, true));
										}
									}
								});
							});
						}
						abilities.addCooldown(ClientAbilityData.STATIC_COOLDOWN);
						abilities.setSheenHit(true);
					});
				} else {
					player.sendSystemMessage(Component.literal("Static Cooldown has " + ((double)abilities.getCooldown() / 20) + " seconds left"));
				}
			});
		});
		return true;
	}

}
