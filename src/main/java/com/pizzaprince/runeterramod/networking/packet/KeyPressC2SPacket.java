package com.pizzaprince.runeterramod.networking.packet;

import com.pizzaprince.runeterramod.ability.IAbilityItem;
import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.client.ClientAbilityData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

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
					Item item = stack.getItem();
					if(item instanceof IAbilityItem abilityItem) {
						abilityItem.fireAbility(level, player, stack);
						abilities.addCooldown(ClientAbilityData.STATIC_COOLDOWN);
					}
				} else {
					player.sendSystemMessage(Component.literal("Cooldown is " + ((double)abilities.getCooldown() / 20) + " seconds"));
				}
			});
		});
		return true;
	}

}
