package com.pizzaprince.runeterramod.ability.item;

import javax.annotation.Nullable;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public abstract class AbstractAbility {
	private SoundEvent soundEvent = SoundEvents.BLAZE_SHOOT;
	private ResourceLocation icon;
	private int maxCooldown;

	private int currentCooldown;
	private int id;
	
	protected AbstractAbility(SoundEvent sound, ResourceLocation location, int cooldown, int id) {
		this.soundEvent = sound;
		this.icon = location;
		this.maxCooldown = cooldown;
		this.id = id;
		this.currentCooldown = 0;
	}
	
	public abstract void fireAbility(LivingEntity entity, Level level);
	
	public void setSoundEvent(SoundEvent sound) {
		this.soundEvent = sound;
	}
	
	public void setIcon(ResourceLocation location) {
		this.icon = location;
	}
	
	public void setMaxCooldown(int cooldown) {
		this.maxCooldown = cooldown;
	}
	
	public SoundEvent getSoundEvent() {
		return this.soundEvent;
	}
	
	public ResourceLocation getIcon() {
		return this.icon;
	}
	
	public int getMaxCooldown() {
		return this.maxCooldown;
	}
	
	public int getId() {
		return this.id;
	}

	public void setOnCooldown(LivingEntity entity){
		if(entity instanceof Player player){
			player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
				this.currentCooldown = this.maxCooldown * (100 / (100 + cap.getAbilityHaste()));
			});
		} else {
			this.currentCooldown = this.maxCooldown;
		}
	}

	public void setCurrentCooldown(int ticks){
		this.currentCooldown = ticks;
	}

	public int getCurrentCooldown(){
		return this.currentCooldown;
	}

}
