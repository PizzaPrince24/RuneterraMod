package com.pizzaprince.runeterramod.networking.packet;

import java.util.function.Supplier;

import com.pizzaprince.runeterramod.ability.PlayerAbilities;
import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.client.ClientAbilityData;
import com.pizzaprince.runeterramod.entity.ModEntityTypes;
import com.pizzaprince.runeterramod.entity.custom.projectile.EnchantedCrystalArrow;
import com.pizzaprince.runeterramod.item.ModItems;
import com.pizzaprince.runeterramod.item.custom.AsheBow;
import com.pizzaprince.runeterramod.networking.ModPackets;
import com.pizzaprince.runeterramod.util.CooldownItem;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;

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
			ServerLevel level = player.getLevel();
			
			
			player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(abilities -> {
				if(abilities.canUseAbilities()) {
					ItemStack stack = player.getMainHandItem();
					Item item = stack.getItem();
					if(stack.is(ModItems.ASHE_BOW.get())) {
						if(stack.hasTag()) {
							if(stack.getTag().getInt("cooldown") == 0) {
								EnchantedCrystalArrow arrow = new EnchantedCrystalArrow(level, player);
								arrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.8F, 1.0F);
								level.addFreshEntity(arrow);
								abilities.addCooldown(ClientAbilityData.STATIC_COOLDOWN);
								ModPackets.sendToPlayer(new SyncCooldownsS2CPacket(ClientAbilityData.STATIC_COOLDOWN*20), player);
								stack.getTag().putInt("cooldown", AsheBow.COOLDOWN*20);
							} else {
								player.sendSystemMessage(Component.literal("Cooldown is " + ((double)(stack.getTag().getInt("cooldown") / 20) + " seconds")));
							}
						} 
					}
				} else {
					player.sendSystemMessage(Component.literal("Cooldown is " + ((double)abilities.getCooldown() / 20) + " seconds"));
				}
			});
		});
		return true;
	}

}
