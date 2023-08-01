package com.pizzaprince.runeterramod.ability.item.custom;

import com.pizzaprince.runeterramod.ability.item.AbstractAbility;
import com.pizzaprince.runeterramod.entity.custom.projectile.EnchantedCrystalArrow;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class EnchantedCrystalArrowAbility extends AbstractAbility{

	private static int cooldown = 90*20;

	public EnchantedCrystalArrowAbility() {
		super(SoundEvents.ARROW_SHOOT, null, cooldown);
	}

	@Override
	public void fireAbility(Player player, Level level) {
		EnchantedCrystalArrow arrow = new EnchantedCrystalArrow(level, player);
		arrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.8F, 1.0F);
		level.addFreshEntity(arrow);
	}

}
