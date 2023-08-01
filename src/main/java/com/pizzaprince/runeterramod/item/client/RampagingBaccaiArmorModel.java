package com.pizzaprince.runeterramod.item.client;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.item.custom.armor.AsheArmorItem;
import com.pizzaprince.runeterramod.item.custom.armor.RampagingBaccaiArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class RampagingBaccaiArmorModel extends GeoModel<RampagingBaccaiArmorItem> {
    @Override
    public ResourceLocation getAnimationResource(RampagingBaccaiArmorItem animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "animations/rampaging_baccai_armor.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(RampagingBaccaiArmorItem object) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "geo/rampaging_baccai_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RampagingBaccaiArmorItem object) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "textures/models/armor/rampaging_baccai_armor.png");
    }
}
