package com.pizzaprince.runeterramod.item.client;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.item.custom.BaccaiStaff;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BaccaiStaffModel extends GeoModel<BaccaiStaff> {
    @Override
    public ResourceLocation getModelResource(BaccaiStaff object) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "geo/baccai_staff.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BaccaiStaff object) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "textures/item/baccai_staff.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BaccaiStaff animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "animations/baccai_staff.animation.json");
    }
}
