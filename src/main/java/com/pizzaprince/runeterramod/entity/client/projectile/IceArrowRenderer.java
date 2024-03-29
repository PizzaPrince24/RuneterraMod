package com.pizzaprince.runeterramod.entity.client.projectile;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.entity.custom.projectile.IceArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IceArrowRenderer extends ArrowRenderer<IceArrow>{

	public static final ResourceLocation ICE_ARROW_LOCATION = new ResourceLocation(RuneterraMod.MOD_ID+":textures/entity/projectiles/ice_arrow.png");

	   public IceArrowRenderer(EntityRendererProvider.Context p_174399_) {
	      super(p_174399_);
	   }

	   public ResourceLocation getTextureLocation(IceArrow p_116001_) {
	      return ICE_ARROW_LOCATION;
	   }
	   
}
