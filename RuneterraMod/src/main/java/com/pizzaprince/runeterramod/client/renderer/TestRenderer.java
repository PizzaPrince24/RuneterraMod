package com.pizzaprince.runeterramod.client.renderer;

import com.pizzaprince.runeterramod.item.custom.Test;

import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class TestRenderer extends GeoItemRenderer<Test>{

	public TestRenderer() {
		super(new TestModel());
	}

}
