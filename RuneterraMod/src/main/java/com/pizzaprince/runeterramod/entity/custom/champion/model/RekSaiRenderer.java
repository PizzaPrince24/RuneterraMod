package com.pizzaprince.runeterramod.entity.custom.champion.model;

import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.entity.custom.champion.RekSaiEntity;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RekSaiRenderer extends GeoEntityRenderer<RekSaiEntity>{

	public RekSaiRenderer(Context renderManager) {
		super(renderManager, new RekSaiModel());
		this.shadowRadius = 1f;
	}

	@Override
    public ResourceLocation getTextureLocation(RekSaiEntity instance) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "textures/entity/reksai_texture.png");
    }
	
	@Override
    public RenderType getRenderType(RekSaiEntity animatable, float partialTicks, PoseStack stack,
                                    @Nullable MultiBufferSource renderTypeBuffer,
                                    @Nullable VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        stack.scale(40f, 40f, 40f);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
