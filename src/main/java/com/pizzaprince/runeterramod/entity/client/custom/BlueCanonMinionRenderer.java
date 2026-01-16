package com.pizzaprince.runeterramod.entity.client.custom;

import com.pizzaprince.runeterramod.entity.custom.BlueCannonMinionEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BlueCanonMinionRenderer extends GeoEntityRenderer<BlueCannonMinionEntity> {
    public BlueCanonMinionRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BlueCannonMinionModel());
        this.shadowRadius = 0.9f;
        withScale(1.4f);
    }
}
