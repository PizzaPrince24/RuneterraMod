package com.pizzaprince.runeterramod.ability.item.custom;

import javax.annotation.Nullable;

import com.pizzaprince.runeterramod.ability.item.AbstractAbility;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class EnchantedCrystalArrowAbility extends AbstractAbility{

	public EnchantedCrystalArrowAbility(SoundEvent sound, ResourceLocation location, int cooldown) {
		super(sound, location, cooldown);
	}

	@Override
	public void fireAbility(Player player, Level level) {
		
	}
	
	

}
