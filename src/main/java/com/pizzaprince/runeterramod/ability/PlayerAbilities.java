package com.pizzaprince.runeterramod.ability;

import com.pizzaprince.runeterramod.client.ClientAbilityData;

import net.minecraft.nbt.CompoundTag;

public class PlayerAbilities {
	private int cooldown;
	private boolean canUseAbilities;
	
	public boolean canUseAbilities() {
		if(cooldown <= 0) {
			cooldown = 0;
			canUseAbilities = true;
		}
		return canUseAbilities;
	}
	
	public void addCooldown(int cooldown) {
		this.cooldown = Math.max(0, (cooldown*20));
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
		if(cooldown == 0) {
			canUseAbilities = true;
		}
		ClientAbilityData.tick();
	}
	
	public int getCooldown() {
		return this.cooldown;
	}

}
