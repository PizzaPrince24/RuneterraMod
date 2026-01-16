package com.pizzaprince.runeterramod.entity.client.custom;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.entity.custom.BlueCasterMinionEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BlueCasterMinionModel extends GeoModel<BlueCasterMinionEntity> {
    @Override
    public ResourceLocation getModelResource(BlueCasterMinionEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "geo/blue_caster_minion.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BlueCasterMinionEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "textures/entity/blue_minion_caster_tex.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BlueCasterMinionEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "animations/blue_caster.animation.json");
    }
}
