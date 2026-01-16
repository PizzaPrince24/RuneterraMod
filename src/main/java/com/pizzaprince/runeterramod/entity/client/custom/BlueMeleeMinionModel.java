package com.pizzaprince.runeterramod.entity.client.custom;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.entity.custom.BlueMeleeMinionEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BlueMeleeMinionModel extends GeoModel<BlueMeleeMinionEntity> {
    @Override
    public ResourceLocation getModelResource(BlueMeleeMinionEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "geo/blue_melee_minion.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BlueMeleeMinionEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "textures/entity/blue_melee_minion_tex.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BlueMeleeMinionEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "animations/blue_melee_minion.animation.json");
    }
}
