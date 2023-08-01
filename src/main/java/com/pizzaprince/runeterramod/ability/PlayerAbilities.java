package com.pizzaprince.runeterramod.ability;

import com.pizzaprince.runeterramod.client.ClientAbilityData;

import net.minecraft.nbt.CompoundTag;

public class PlayerAbilities {
	private int cooldown;
	private boolean canUseAbilities;
	private int cooldownTracker;
	private boolean trackingCooldown;

	private int abilityHaste = 0;

	private boolean sheenHit = false;

	private int static_cooldown = 10*20;
	
	public boolean canUseAbilities() {
		if(cooldown <= 0) {
			cooldown = 0;
			canUseAbilities = true;
		}
		return canUseAbilities;
	}
	
	public void addCooldown(int cooldown) {
		this.cooldown = (int) Math.max(0, (cooldown));
		if(this.cooldown != 0) {
			this.canUseAbilities = false;
		}
	}
	
	public void resetCooldown() {
		this.cooldown = 0;
		this.canUseAbilities = true;
	}
	
	public void copyFrom(PlayerAbilities source) {
		this.cooldown = source.cooldown;
		this.canUseAbilities = source.canUseAbilities;
	}

	public void setOnStaticCooldown(){
		addCooldown(static_cooldown);
	}
	
	public void saveNBTData(CompoundTag nbt) {
		nbt.putInt("cooldown", cooldown);
		nbt.putBoolean("canuseabilities", canUseAbilities);
	}
	
	public void loadNBTData(CompoundTag nbt) {
		cooldown = nbt.getInt("cooldown");
		canUseAbilities = nbt.getBoolean("canuseabilities");
	}
	
	public void tick() {
		cooldown--;
		cooldown = Math.max(0, cooldown);
		if (cooldown == 0) {
			canUseAbilities = true;
		}
	}
	
	public int getCooldown() {
		return this.cooldown;
	}
	
	public void trackCooldown(int count) {
		this.cooldownTracker = count;
	}
	
	public int getTrackerCooldown() {
		return this.cooldownTracker;
	}
	
	public void resetTrackerCooldown() {
		this.cooldownTracker = 0;
	}
	
	public void setTrackingCooldown(boolean is) {
		this.trackingCooldown = is;
	}
	
	public boolean isTrackingCooldown() {
		return this.trackingCooldown;
	}

	public void addAbilityHaste(int num){
		this.abilityHaste += num;
	}

	public void removeAbilityHaste(int num){
		this.abilityHaste -= num;
		if(this.abilityHaste < 0){
			this.abilityHaste = 0;
		}
	}

	public int getAbilityHaste(){
		return this.abilityHaste;
	}

	public void setSheenHit(boolean set){
		this.sheenHit = set;
	}

	public boolean isSheenHit(){
		return this.sheenHit;
	}

}
