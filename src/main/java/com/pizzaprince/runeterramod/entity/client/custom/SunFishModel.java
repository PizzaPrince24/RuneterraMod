package com.pizzaprince.runeterramod.entity.client.custom;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.entity.custom.SunFishEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SunFishModel extends GeoModel<SunFishEntity> {
    @Override
    public ResourceLocation getModelResource(SunFishEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "geo/sunfish.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SunFishEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "textures/entity/sunfish.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SunFishEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "animations/sunfish.animation.json");
    }
}
