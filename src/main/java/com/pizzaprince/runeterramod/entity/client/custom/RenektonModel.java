package com.pizzaprince.runeterramod.entity.client.custom;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.entity.custom.RenektonEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class RenektonModel extends GeoModel<RenektonEntity> {
    @Override
    public ResourceLocation getModelResource(RenektonEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "geo/renekton.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RenektonEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "textures/entity/renekton_tex.png");
    }

    @Override
    public ResourceLocation getAnimationResource(RenektonEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "animations/renekton.animation.json");
    }
}
