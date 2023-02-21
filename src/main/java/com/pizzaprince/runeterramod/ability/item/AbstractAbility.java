package com.pizzaprince.runeterramod.ability.item;

import javax.annotation.Nullable;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public abstract class AbstractAbility {
	private SoundEvent soundEvent = SoundEvents.BLAZE_SHOOT;
	private ResourceLocation icon;
	private int cooldown;
	private int id;
	
	protected AbstractAbility(SoundEvent sound, ResourceLocation location, int cooldown, int id) {
		this.soundEvent = sound;
		this.icon = location;
		this.cooldown = cooldown;
		this.id = id;
	}
	
	public abstract void fireAbility(LivingEntity entity, Level level);
	
	public void setSoundEvent(SoundEvent sound) {
		this.soundEvent = sound;
	}
	
	public void setIcon(ResourceLocation location) {
		this.icon = location;
	}
	
	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}
	
	public SoundEvent getSoundEvent() {
		return this.soundEvent;
	}
	
	public ResourceLocation getIcon() {
		return this.icon;
	}
	
	public int getCooldown() {
		return this.cooldown;
	}
	
	public int getId() {
		return this.id;
	}

}
