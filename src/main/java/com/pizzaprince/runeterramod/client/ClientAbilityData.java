package com.pizzaprince.runeterramod.client;

import com.pizzaprince.runeterramod.item.ModItems;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ClientAbilityData {
	private static boolean isStunned = false;
	private static int stunDuration;
	
	public static boolean isStunned() {
		return ClientAbilityData.isStunned;
	}
	
	public static void setStunned() {
		ClientAbilityData.isStunned = true;
		ClientAbilityData.stunDuration = 5;
	}
	
	public static void tick() {
		ClientAbilityData.stunDuration--;
		ClientAbilityData.stunDuration = Math.max(0, ClientAbilityData.stunDuration);
		if(ClientAbilityData.stunDuration == 0) {
			ClientAbilityData.isStunned = false;
		} 
	}
	
	public static int numArmorPieces(Player player, ArmorMaterial material) {
		int num = 0;
		for (ItemStack armorStack: player.getInventory().armor) {
            if(armorStack.getItem() instanceof ArmorItem armor) {
                if(armor.getMaterial() == material) {
                	num++;
                }
            }
        }
		
		return num;
	}
	
	public static boolean hasFullArmorSetOn(Player player, ArmorMaterial material) {
		ItemStack boots = player.getInventory().getArmor(0);
	    ItemStack leggings = player.getInventory().getArmor(1);
	    ItemStack breastplate = player.getInventory().getArmor(2);
	    ItemStack helmet = player.getInventory().getArmor(3);
	    
	    if(!helmet.isEmpty() && !breastplate.isEmpty() && !leggings.isEmpty() && !boots.isEmpty()) {
	    	for (ItemStack armorStack: player.getInventory().armor) {
	            if(!(armorStack.getItem() instanceof ArmorItem)) {
	                return false;
	            }
	        }

	        ArmorItem _boots = ((ArmorItem)player.getInventory().getArmor(0).getItem());
	        ArmorItem _leggings = ((ArmorItem)player.getInventory().getArmor(1).getItem());
	        ArmorItem _breastplate = ((ArmorItem)player.getInventory().getArmor(2).getItem());
	        ArmorItem _helmet = ((ArmorItem)player.getInventory().getArmor(3).getItem());
	        
	        return _helmet.getMaterial() == material && _breastplate.getMaterial() == material &&
	                _leggings.getMaterial() == material && _boots.getMaterial() == material;
	        
	    } else {
	    	return false;
	    }
	}
	
}
