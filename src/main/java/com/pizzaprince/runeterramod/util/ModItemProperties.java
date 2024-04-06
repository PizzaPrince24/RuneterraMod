package com.pizzaprince.runeterramod.util;

import com.pizzaprince.runeterramod.client.ClientAbilityData;
import com.pizzaprince.runeterramod.item.ModArmorMaterials;
import com.pizzaprince.runeterramod.item.ModItems;

import com.pizzaprince.runeterramod.item.custom.SunStoneSpear;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class ModItemProperties {
	public static void addCustomItemProperties() {
		makeBow(ModItems.ASHE_BOW.get());
		broken(ModItems.INFUSED_SUN_STONE_SPEAR.get());
		broken(ModItems.PURIFIED_SUN_STONE_SPEAR.get());
		charging(ModItems.INFUSED_SUN_STONE_SPEAR.get());
		charging(ModItems.PURIFIED_SUN_STONE_SPEAR.get());
	}
	
	private static void makeBow(Item item) {
		ItemProperties.register(ModItems.ASHE_BOW.get(), new ResourceLocation("pull"), (p_174635_, p_174636_, p_174637_, p_174638_) -> {
	         if (p_174637_ == null) {
	            return 0.0F;
	         } else {
	        	 if(p_174637_ instanceof Player player) {
	        		 return p_174637_.getUseItem() != p_174635_ ? 0.0F : (float)(p_174635_.getUseDuration() - p_174637_.getUseItemRemainingTicks()) / (20.0F - ((float)1.4 * ClientAbilityData.numArmorPieces(player, ModArmorMaterials.ASHE_ARMOR)));
	        	 }
	            return p_174637_.getUseItem() != p_174635_ ? 0.0F : (float)(p_174635_.getUseDuration() - p_174637_.getUseItemRemainingTicks()) / 20.0F;
	         }
	      });
		
		ItemProperties.register(ModItems.ASHE_BOW.get(), new ResourceLocation("pulling"), (p_174630_, p_174631_, p_174632_, p_174633_) -> {
	         return p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F;
	      });
	}

	private static void broken(Item item){
		ItemProperties.register(item, new ResourceLocation("broken"), (stack, level, entity, seed) -> SunStoneSpear.isAboutToBreak(stack) ? 1f : 0f);
	}

	private static void charging(Item item){
		ItemProperties.register(item, new ResourceLocation("charging"), ((pStack, pLevel, pEntity, pSeed) -> pEntity != null && pEntity.isUsingItem() && pEntity.getUseItem() == pStack ? 1.0F : 0.0F));
	}
	
}
