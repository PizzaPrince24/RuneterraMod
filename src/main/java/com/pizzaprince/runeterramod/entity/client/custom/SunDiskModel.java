package com.pizzaprince.runeterramod.entity.client.custom;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.entity.custom.RenektonEntity;
import com.pizzaprince.runeterramod.entity.custom.SunDiskEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SunDiskModel extends GeoModel<SunDiskEntity> {
    @Override
    public ResourceLocation getModelResource(SunDiskEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "geo/sundisk.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SunDiskEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "textures/entity/sun_disk_tex.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SunDiskEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "animations/sundisk.animation.json");
    }
}
