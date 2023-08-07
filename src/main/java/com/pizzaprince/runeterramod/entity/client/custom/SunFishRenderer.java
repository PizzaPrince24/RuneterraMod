package com.pizzaprince.runeterramod.entity.client.custom;

import com.pizzaprince.runeterramod.entity.custom.SunFishEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SunFishRenderer extends GeoEntityRenderer<SunFishEntity> {
    public SunFishRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SunFishModel());
        this.shadowRadius = 1f;
    }
}
