package com.pizzaprince.runeterramod.entity.client.custom;

import com.pizzaprince.runeterramod.entity.custom.SunDiskEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SunDiskRenderer extends GeoEntityRenderer<SunDiskEntity> {
    public SunDiskRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SunDiskModel());
    }
}
