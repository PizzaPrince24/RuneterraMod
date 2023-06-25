package com.pizzaprince.runeterramod.block.entity.client;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.block.entity.custom.SunDiskAltarEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SunDiskAltarModel extends GeoModel<SunDiskAltarEntity> {
    @Override
    public ResourceLocation getModelResource(SunDiskAltarEntity object) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "geo/sun_disk_altar.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SunDiskAltarEntity object) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "textures/block/sun_disk_altar_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SunDiskAltarEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "animations/sun_disk_altar.animation.json");
    }
}
