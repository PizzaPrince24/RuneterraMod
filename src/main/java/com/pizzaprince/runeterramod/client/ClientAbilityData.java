package com.pizzaprince.runeterramod.client;

public class ClientAbilityData {
	private static int cooldown;
	private static boolean canUseAbilities;
	private static boolean isStunned = false;
	private static int stunDuration;
	
	public static void addCooldown(int cooldown) {
		ClientAbilityData.cooldown = Math.max(0, cooldown*20);
		if(cooldown != 0) {
			ClientAbilityData.canUseAbilities = false;
		}
	}
	
	public static void resetCooldown() {
		ClientAbilityData.cooldown = 0;
		ClientAbilityData.canUseAbilities = true;
	}
	
	public static boolean canUseAbilities() {
		return ClientAbilityData.canUseAbilities;
	}
	
	public static void addStun(float seconds) {
		ClientAbilityData.isStunned = true;
		ClientAbilityData.stunDuration = (int)(seconds*20);
	}
	
	public static boolean isStunned() {
		return ClientAbilityData.isStunned;
	}
	
	public static void setStunned() {
		ClientAbilityData.isStunned = true;
		ClientAbilityData.stunDuration = 5;
	}
	
	public static void tick() {
		ClientAbilityData.cooldown--;
		ClientAbilityData.cooldown = Math.max(0, ClientAbilityData.cooldown);
		if(ClientAbilityData.cooldown == 0) {
			ClientAbilityData.canUseAbilities = true;
		} else {
			ClientAbilityData.canUseAbilities = false;
		}
		ClientAbilityData.stunDuration--;
		ClientAbilityData.stunDuration = Math.max(0, ClientAbilityData.stunDuration);
		if(ClientAbilityData.stunDuration == 0) {
			ClientAbilityData.isStunned = false;
		} 
	}
}
