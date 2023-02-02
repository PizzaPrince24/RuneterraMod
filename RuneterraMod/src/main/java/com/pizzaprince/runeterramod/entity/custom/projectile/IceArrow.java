package com.pizzaprince.runeterramod.entity.custom.projectile;

import com.pizzaprince.runeterramod.entity.ModEntityTypes;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.projectile.Arrow;

public class IceArrow extends AbstractArrow{
	
	
	public IceArrow(EntityType<? extends AbstractArrow> type, Level level) {
		super(type, level);
	}
	
	public IceArrow(Level level, LivingEntity entity) {
		super(ModEntityTypes.ICE_ARROW.get(), entity, level);
	}
	

	@Override
	protected ItemStack getPickupItem() {
	      return ItemStack.EMPTY;
	   }

}
