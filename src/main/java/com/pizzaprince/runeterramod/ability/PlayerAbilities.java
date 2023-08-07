package com.pizzaprince.runeterramod.ability;

import com.pizzaprince.runeterramod.client.ClientAbilityData;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;

public class PlayerAbilities {
	private int cooldown;
	private boolean canUseAbilities;

	private int abilityHaste = 0;

	private boolean sheenHit = false;

	private int static_cooldown = 10*20;

	private int outOfCombat = 0;

	private int tickCount = 0;

	private boolean isCrocodileAscended = false;
	private int crocodileAscendedRage = 0;

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
		this.isCrocodileAscended = source.isCrocodileAscended;
	}

	public void setOnStaticCooldown(){
		addCooldown(static_cooldown);
	}

	public void saveNBTData(CompoundTag nbt) {
		nbt.putInt("cooldown", cooldown);
		nbt.putBoolean("canuseabilities", canUseAbilities);
		nbt.putBoolean("isCrocodileAscended", isCrocodileAscended);
	}

	public void loadNBTData(CompoundTag nbt) {
		cooldown = nbt.getInt("cooldown");
		canUseAbilities = nbt.getBoolean("canuseabilities");
		isCrocodileAscended = nbt.getBoolean("isCrocodileAscended");
	}

	public void tick() {
		outOfCombat = Math.max(0, --outOfCombat);
		cooldown = Math.max(0, --cooldown);
		tickCount = ++tickCount % 20;
		if (cooldown == 0) {
			canUseAbilities = true;
		}
		if(tickCount % 2 == 0){
			addRage(-1);
		}
	}

	public void setInCombat(){
		this.outOfCombat = 160;
	}
	public int getCooldown() {
		return this.cooldown;
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

	public void setCrocodileAscended(boolean f){
		isCrocodileAscended = f;
	}

	public boolean isCrocodileAscended(){
		return isCrocodileAscended;
	}

	public void addRage(int rage){
		this.crocodileAscendedRage = Mth.clamp(rage, 0, 100);
	}

	public int getRage(){
		return this.crocodileAscendedRage;
	}

	public float getDamageMultiplierFromRage(){
		return ((float)this.crocodileAscendedRage)*0.003f;
	}

}
