package com.pizzaprince.runeterramod.client.renderer.entity;

import com.pizzaprince.runeterramod.entity.custom.projectile.IceArrow;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class IceArrowRenderer extends ArrowRenderer<IceArrow>{
	
	public static final ResourceLocation ICE_ARROW_LOCATION = new ResourceLocation("textures/entity/projectiles/ice_arrow.png");

	public IceArrowRenderer(EntityRendererProvider.Context p_173917_) {
		super(p_173917_);
	}

	@Override
	public ResourceLocation getTextureLocation(IceArrow p_114482_) {
		return ICE_ARROW_LOCATION;
	}

}
