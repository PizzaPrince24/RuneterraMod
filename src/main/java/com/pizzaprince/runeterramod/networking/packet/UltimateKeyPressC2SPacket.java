package com.pizzaprince.runeterramod.networking.packet;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.ability.AbilityItemCapabilityProvider;
import com.pizzaprince.runeterramod.effect.ModEffects;
import com.pizzaprince.runeterramod.item.custom.curios.legendary.RadiantVirtue;
import com.pizzaprince.runeterramod.networking.ModPackets;
import com.pizzaprince.runeterramod.util.KeyBinding;
import net.minecraft.nbt.CompoundTag;
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
import virtuoel.pehkui.api.ScaleTypes;

import java.util.function.Supplier;

public class UltimateKeyPressC2SPacket {

	public UltimateKeyPressC2SPacket() {
	}

	public UltimateKeyPressC2SPacket(FriendlyByteBuf buf) {
	}

	public void toBytes(FriendlyByteBuf buf) {
	}

	public boolean handle(Supplier<NetworkEvent.Context> supplier) {
		NetworkEvent.Context context = supplier.get();
		context.enqueueWork(() -> {
			ServerPlayer player = context.getSender();
			ServerLevel level = player.serverLevel();
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
		});
		return true;
	}

}