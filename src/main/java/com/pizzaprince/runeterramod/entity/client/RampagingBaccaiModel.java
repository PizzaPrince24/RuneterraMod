package com.pizzaprince.runeterramod.entity.client;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.entity.custom.RampagingBaccaiEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class RampagingBaccaiModel extends GeoModel<RampagingBaccaiEntity> {
    @Override
    public ResourceLocation getModelResource(RampagingBaccaiEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "geo/rampaging_baccai.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RampagingBaccaiEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "textures/entity/rampaging_baccai_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(RampagingBaccaiEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "animations/rampaging_baccai.animation.json");
    }
}
