package com.pizzaprince.runeterramod.ability.item;

import javax.annotation.Nullable;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.effect.ModAttributes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public abstract class AbstractAbility {
	private SoundEvent soundEvent;
	private ResourceLocation icon;
	private int maxCooldown;

	private int currentCooldown;

	private boolean shouldSetStaticCooldown;
	
	protected AbstractAbility(SoundEvent sound, ResourceLocation picture, int cooldown) {
		this.soundEvent = sound;
		this.icon = picture;
		this.maxCooldown = cooldown;
		this.currentCooldown = 0;
		this.shouldSetStaticCooldown = true;
	}
	
	public abstract void fireAbility(Player player, Level level);

	protected void setShouldSetStaticCooldown(boolean set){
		this.shouldSetStaticCooldown = set;
	}

	public boolean getShouldSetStaticCooldown(){
		return this.shouldSetStaticCooldown;
	}
	
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

	public void setOnCooldown(LivingEntity entity){
		if(entity instanceof Player player){
			this.currentCooldown = this.maxCooldown * (100 / (100 + (int)player.getAttributeValue(ModAttributes.ABILITY_HASTE.get())));
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
