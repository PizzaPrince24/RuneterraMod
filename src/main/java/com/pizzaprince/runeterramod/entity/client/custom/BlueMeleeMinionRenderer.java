package com.pizzaprince.runeterramod.entity.client.custom;

import com.pizzaprince.runeterramod.entity.custom.BlueMeleeMinionEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BlueMeleeMinionRenderer extends GeoEntityRenderer<BlueMeleeMinionEntity> {
    public BlueMeleeMinionRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BlueMeleeMinionModel());
        withScale(1.4f);
        this.shadowRadius = 0.7f;
    }
}
