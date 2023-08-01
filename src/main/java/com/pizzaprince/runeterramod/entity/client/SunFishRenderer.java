package com.pizzaprince.runeterramod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pizzaprince.runeterramod.entity.custom.SunFishEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SunFishRenderer extends GeoEntityRenderer<SunFishEntity> {
    public SunFishRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SunFishModel());
        this.shadowRadius = 1f;
    }
}
