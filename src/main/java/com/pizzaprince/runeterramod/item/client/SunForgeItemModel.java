package com.pizzaprince.runeterramod.item.client;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.block.entity.custom.SunForgeEntity;
import com.pizzaprince.runeterramod.item.custom.SunForgeItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SunForgeItemModel extends GeoModel<SunForgeItem> {
    @Override
    public ResourceLocation getModelResource(SunForgeItem animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "geo/sun_forge.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SunForgeItem animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "textures/block/sun_forge_tex.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SunForgeItem animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "animations/sun_forge.animation.json");
    }
}
