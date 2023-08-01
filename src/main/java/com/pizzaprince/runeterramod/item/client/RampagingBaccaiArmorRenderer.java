package com.pizzaprince.runeterramod.item.client;

import com.pizzaprince.runeterramod.item.custom.armor.RampagingBaccaiArmorItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class RampagingBaccaiArmorRenderer extends GeoArmorRenderer<RampagingBaccaiArmorItem> {
    public RampagingBaccaiArmorRenderer() {
        super(new RampagingBaccaiArmorModel());
    }
}
