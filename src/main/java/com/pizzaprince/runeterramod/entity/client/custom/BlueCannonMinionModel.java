package com.pizzaprince.runeterramod.entity.client.custom;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.entity.custom.BlueCannonMinionEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BlueCannonMinionModel extends GeoModel<BlueCannonMinionEntity> {
    @Override
    public ResourceLocation getModelResource(BlueCannonMinionEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "geo/blue_cannon_minion.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BlueCannonMinionEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "textures/entity/blue_cannon_minion_tex.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BlueCannonMinionEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "animations/blue_cannon_minion.animation.json");
    }
}
