package com.pizzaprince.runeterramod.block.entity.client;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.block.entity.custom.SunForgeEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SunForgeModel extends GeoModel<SunForgeEntity> {
    @Override
    public ResourceLocation getModelResource(SunForgeEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "geo/sun_forge.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SunForgeEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "textures/block/sun_forge_tex.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SunForgeEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "animations/sun_forge.animation.json");
    }
}
