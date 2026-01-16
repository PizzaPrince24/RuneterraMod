package com.pizzaprince.runeterramod.entity.client.custom;

import com.pizzaprince.runeterramod.entity.custom.BlueSuperMinionEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BlueSuperMinionRenderer extends GeoEntityRenderer<BlueSuperMinionEntity> {
    public BlueSuperMinionRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BlueSuperMinionModel());
        withScale(1.4f);
    }
}
