package com.pizzaprince.runeterramod.entity.client.custom;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.entity.custom.BlueSuperMinionEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BlueSuperMinionModel extends GeoModel<BlueSuperMinionEntity> {
    @Override
    public ResourceLocation getModelResource(BlueSuperMinionEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "geo/blue_super_minion.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BlueSuperMinionEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "textures/entity/blue_super_minion_tex.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BlueSuperMinionEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "animations/blue_super_minion.animation.json");
    }
}
