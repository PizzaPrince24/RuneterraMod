package com.pizzaprince.runeterramod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.entity.custom.RekSaiEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RekSaiRenderer extends GeoEntityRenderer<RekSaiEntity> {

	public RekSaiRenderer(Context renderManager) {
		super(renderManager, new RekSaiModel());
		this.shadowRadius = 1f;
	}

	@Override
    public ResourceLocation getTextureLocation(RekSaiEntity instance) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "textures/entity/reksai_texture.png");
    }

    @Override
    public void render(RekSaiEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        poseStack.scale(40f, 40f, 40f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

}
