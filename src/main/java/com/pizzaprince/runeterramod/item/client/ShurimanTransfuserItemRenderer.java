package com.pizzaprince.runeterramod.item.client;

import com.pizzaprince.runeterramod.item.custom.ShurimanTransfuserItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class ShurimanTransfuserItemRenderer extends GeoItemRenderer<ShurimanTransfuserItem> {
    public ShurimanTransfuserItemRenderer() {
        super(new ShurimanTransfuserItemModel());
    }
}
