package com.pizzaprince.runeterramod.item.client;

import com.pizzaprince.runeterramod.item.custom.SunDiskAltarItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class SunDiskAltarItemRenderer extends GeoItemRenderer<SunDiskAltarItem> {
    public SunDiskAltarItemRenderer() {
        super(new SunDiskAltarItemModel());
    }
}
