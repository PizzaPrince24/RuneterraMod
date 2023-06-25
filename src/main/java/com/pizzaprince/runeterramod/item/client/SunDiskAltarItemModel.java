package com.pizzaprince.runeterramod.item.client;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.item.custom.SunDiskAltarItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SunDiskAltarItemModel extends GeoModel<SunDiskAltarItem> {
    @Override
    public ResourceLocation getModelResource(SunDiskAltarItem object) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "geo/sun_disk_altar.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SunDiskAltarItem object) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "textures/block/sun_disk_altar_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SunDiskAltarItem animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "animations/sun_disk_altar.animation.json");
    }
}
