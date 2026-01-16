package com.pizzaprince.runeterramod.entity.client.custom;

import com.pizzaprince.runeterramod.entity.custom.BlueCasterMinionEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BlueCasterMinionRenderer extends GeoEntityRenderer<BlueCasterMinionEntity> {
    public BlueCasterMinionRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BlueCasterMinionModel());
        withScale(1.4f);
        this.shadowRadius = 0.7f;
    }
}
