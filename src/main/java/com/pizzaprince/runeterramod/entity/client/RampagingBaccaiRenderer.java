package com.pizzaprince.runeterramod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.entity.custom.RampagingBaccaiEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RampagingBaccaiRenderer extends GeoEntityRenderer<RampagingBaccaiEntity> {
    public RampagingBaccaiRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new RampagingBaccaiModel());
        this.shadowRadius = 3f;
        this.withScale(8f);
    }

    @Override
    public ResourceLocation getTextureLocation(RampagingBaccaiEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "textures/entity/rampaging_baccai_texture.png");
    }
}
