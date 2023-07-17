package com.pizzaprince.runeterramod.item.client;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.item.custom.ShurimanTransfuserItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ShurimanTransfuserItemModel extends GeoModel<ShurimanTransfuserItem> {
    @Override
    public ResourceLocation getModelResource(ShurimanTransfuserItem object) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "geo/shuriman_transfuser.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ShurimanTransfuserItem object) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "textures/block/shuriman_transfuser_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ShurimanTransfuserItem animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "animations/shuriman_transfuser.animation.json");
    }
}
