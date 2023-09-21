package com.pizzaprince.runeterramod.entity.client.layer;

import com.pizzaprince.runeterramod.RuneterraMod;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class ShellModel extends GeoModel {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(RuneterraMod.MOD_ID, "shellmodel"), "main");
    @Override
    public ResourceLocation getModelResource(GeoAnimatable animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "geo/shell.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GeoAnimatable animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "textures/entity/layer/shell.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GeoAnimatable animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "animations/shell.animation.json");
    }
}
