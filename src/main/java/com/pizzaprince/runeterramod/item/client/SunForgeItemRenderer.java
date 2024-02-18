package com.pizzaprince.runeterramod.item.client;

import com.pizzaprince.runeterramod.item.custom.SunForgeItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class SunForgeItemRenderer extends GeoItemRenderer<SunForgeItem> {
    public SunForgeItemRenderer() {
        super(new SunForgeItemModel());
    }
}
