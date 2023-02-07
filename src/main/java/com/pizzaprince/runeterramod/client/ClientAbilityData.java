package com.pizzaprince.runeterramod.client;

public class ClientAbilityData {
	private static int cooldown;
	private static boolean canUseAbilities;
	
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
	
	public static void tick() {
		ClientAbilityData.cooldown = Math.max(0, ClientAbilityData.cooldown--);
		if(ClientAbilityData.cooldown == 0) {
			ClientAbilityData.canUseAbilities = true;
		} else {
			ClientAbilityData.canUseAbilities = false;
		}
	}
}
