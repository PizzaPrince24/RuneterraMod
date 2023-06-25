package com.pizzaprince.runeterramod.item.client;

import com.pizzaprince.runeterramod.item.custom.SunDiskAltarItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class SunDiskAltarItemRenderer extends GeoItemRenderer<SunDiskAltarItem> {
    public SunDiskAltarItemRenderer() {
        super(new SunDiskAltarItemModel());
    }
}
