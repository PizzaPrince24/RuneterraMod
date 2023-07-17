package com.pizzaprince.runeterramod.block.entity.client;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.block.entity.custom.ShurimanTransfuserEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ShurimanTransfuserModel extends GeoModel<ShurimanTransfuserEntity> {
    @Override
    public ResourceLocation getModelResource(ShurimanTransfuserEntity object) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "geo/shuriman_transfuser.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ShurimanTransfuserEntity object) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "textures/block/shuriman_transfuser_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ShurimanTransfuserEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "animations/shuriman_transfuser.animation.json");
    }
}
