package com.pizzaprince.runeterramod.entity.client.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pizzaprince.runeterramod.entity.custom.RenektonEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RenektonRenderer extends GeoEntityRenderer<RenektonEntity> {
    public RenektonRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new RenektonModel());
        this.shadowRadius = 5f;
        this.withScale(8f);
    }
}
