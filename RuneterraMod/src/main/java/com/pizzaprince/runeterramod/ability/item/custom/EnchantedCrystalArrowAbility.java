package com.pizzaprince.runeterramod.ability.item.custom;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.ability.item.AbstractAbility;
import com.pizzaprince.runeterramod.client.ClientAbilityData;
import com.pizzaprince.runeterramod.entity.custom.projectile.EnchantedCrystalArrow;
import com.pizzaprince.runeterramod.networking.ModPackets;
import com.pizzaprince.runeterramod.networking.packet.SyncCooldownsS2CPacket;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class EnchantedCrystalArrowAbility extends AbstractAbility{

	public EnchantedCrystalArrowAbility(SoundEvent sound, ResourceLocation location, int cooldown, int id) {
		super(sound, location, cooldown, id);
	}

	@Override
	public void fireAbility(LivingEntity entity, Level level) {
		EnchantedCrystalArrow arrow = new EnchantedCrystalArrow(level, entity);
		arrow.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 1.8F, 1.0F);
		level.addFreshEntity(arrow);
		if(entity instanceof ServerPlayer player) {
			player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(abilities -> {
				abilities.addCooldown(ClientAbilityData.STATIC_COOLDOWN);
				ModPackets.sendToPlayer(new SyncCooldownsS2CPacket(ClientAbilityData.STATIC_COOLDOWN*20), player);
			});
		}
	}

}
