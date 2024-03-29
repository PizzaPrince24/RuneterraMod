package com.pizzaprince.runeterramod.entity.client.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.entity.custom.projectile.EnchantedCrystalArrow;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class EnchantedCrystalArrowRenderer extends ArrowRenderer<EnchantedCrystalArrow>{
	private float scale = 4;

	public static final ResourceLocation ICE_ARROW_LOCATION = new ResourceLocation(RuneterraMod.MOD_ID+":textures/entity/projectiles/ice_arrow.png");

	   public EnchantedCrystalArrowRenderer(EntityRendererProvider.Context p_174399_) {
	      super(p_174399_);
	   }

	   public ResourceLocation getTextureLocation(EnchantedCrystalArrow p_116001_) {
	      return ICE_ARROW_LOCATION;
	   }
	   
	   @Override
	   public void render(EnchantedCrystalArrow p_113839_, float p_113840_, float p_113841_, PoseStack p_113842_, MultiBufferSource p_113843_, int p_113844_) {
		   p_113842_.pushPose();
		   p_113842_.mulPose(Axis.YP.rotationDegrees(Mth.lerp(p_113841_, p_113839_.yRotO, p_113839_.getYRot()) - 90.0F));
		   p_113842_.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(p_113841_, p_113839_.xRotO, p_113839_.getXRot())));
		   int i = 0;
		   float f = 0.0F;
		   float f1 = 0.5F;
		   float f2 = 0.0F;
		   float f3 = 0.15625F;
		   float f4 = 0.0F;
		   float f5 = 0.15625F;
		   float f6 = 0.15625F;
		   float f7 = 0.3125F;
		   float f8 = 0.05625F;
		   float f9 = (float)p_113839_.shakeTime - p_113841_;
		   if (f9 > 0.0F) {
			   float f10 = -Mth.sin(f9 * 3.0F) * f9;
			   p_113842_.mulPose(Axis.ZP.rotationDegrees(f10));
		   }

		   p_113842_.mulPose(Axis.XP.rotationDegrees(45.0F));
		   p_113842_.scale((0.05625f *scale), (0.05625f*scale), (0.05625f*scale));
		   p_113842_.translate(-4.0D, 0.0D, 0.0D);
		   VertexConsumer vertexconsumer = p_113843_.getBuffer(RenderType.entityCutout(this.getTextureLocation(p_113839_)));
		   PoseStack.Pose posestack$pose = p_113842_.last();
		   Matrix4f matrix4f = posestack$pose.pose();
		   Matrix3f matrix3f = posestack$pose.normal();
		   this.vertex(matrix4f, matrix3f, vertexconsumer, -7, -2, -2, 0.0F, 0.15625F, -1, 0, 0, p_113844_);
		   this.vertex(matrix4f, matrix3f, vertexconsumer, -7, -2, 2, 0.15625F, 0.15625F, -1, 0, 0, p_113844_);
		   this.vertex(matrix4f, matrix3f, vertexconsumer, -7, 2, 2, 0.15625F, 0.3125F, -1, 0, 0, p_113844_);
		   this.vertex(matrix4f, matrix3f, vertexconsumer, -7, 2, -2, 0.0F, 0.3125F, -1, 0, 0, p_113844_);
		   this.vertex(matrix4f, matrix3f, vertexconsumer, -7, 2, -2, 0.0F, 0.15625F, 1, 0, 0, p_113844_);
		   this.vertex(matrix4f, matrix3f, vertexconsumer, -7, 2, 2, 0.15625F, 0.15625F, 1, 0, 0, p_113844_);
		   this.vertex(matrix4f, matrix3f, vertexconsumer, -7, -2, 2, 0.15625F, 0.3125F, 1, 0, 0, p_113844_);
		   this.vertex(matrix4f, matrix3f, vertexconsumer, -7, -2, -2, 0.0F, 0.3125F, 1, 0, 0, p_113844_);

		   for(int j = 0; j < 4; ++j) {
			   p_113842_.mulPose(Axis.XP.rotationDegrees(90.0F));
		       this.vertex(matrix4f, matrix3f, vertexconsumer, -8, -2, 0, 0.0F, 0.0F, 0, 1, 0, p_113844_);
		       this.vertex(matrix4f, matrix3f, vertexconsumer, 8, -2, 0, 0.5F, 0.0F, 0, 1, 0, p_113844_);
		       this.vertex(matrix4f, matrix3f, vertexconsumer, 8, 2, 0, 0.5F, 0.15625F, 0, 1, 0, p_113844_);
		       this.vertex(matrix4f, matrix3f, vertexconsumer, -8, 2, 0, 0.0F, 0.15625F, 0, 1, 0, p_113844_);
		   }

		   p_113842_.popPose();
		   super.render(p_113839_, p_113840_, p_113841_, p_113842_, p_113843_, p_113844_);
	   }

}
