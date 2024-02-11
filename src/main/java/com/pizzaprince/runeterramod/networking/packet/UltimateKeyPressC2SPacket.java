package com.pizzaprince.runeterramod.networking.packet;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.ability.AbilityItemCapabilityProvider;
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
