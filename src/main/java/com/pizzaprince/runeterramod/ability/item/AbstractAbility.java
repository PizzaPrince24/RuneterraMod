package com.pizzaprince.runeterramod.ability.item;

import javax.annotation.Nullable;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public abstract class AbstractAbility {
	private SoundEvent soundEvent = SoundEvents.BLAZE_SHOOT;
	private ResourceLocation icon;
	private int cooldown = 30*20;
	
	protected AbstractAbility(SoundEvent sound, ResourceLocation location, int cooldown) {
		this.soundEvent = sound;
		this.icon = location;
		this.cooldown = cooldown;
	}
	
	protected abstract void fireAbility(Player player, Level level);
	
	public void setSoundEvent(SoundEvent sound) {
		this.soundEvent = sound;
	}
	
	public void setIcon(ResourceLocation location) {
		this.icon = location;
	}
	
	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}

}
