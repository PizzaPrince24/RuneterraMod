package com.pizzaprince.runeterramod.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.entity.custom.projectile.EnchantedCrystalArrow;
import com.pizzaprince.runeterramod.entity.custom.projectile.IceArrow;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.SpectralArrow;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.entity.SpectralArrowRenderer;
import net.minecraft.client.renderer.entity.TippableArrowRenderer;

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
