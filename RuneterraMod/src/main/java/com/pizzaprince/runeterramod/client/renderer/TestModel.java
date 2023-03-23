package com.pizzaprince.runeterramod.client.renderer;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.item.custom.Test;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TestModel extends AnimatedGeoModel<Test>{

@Override
public ResourceLocation getAnimationResource(Test animatable) {
	return new ResourceLocation(RuneterraMod.MOD_ID, "animations/test.model.animation.json");
}

@Override
public ResourceLocation getModelResource(Test object) {
	return new ResourceLocation(RuneterraMod.MOD_ID, "geo/test.geo.json");
}

@Override
public ResourceLocation getTextureResource(Test object) {
	return new ResourceLocation(RuneterraMod.MOD_ID, "textures/item/test.png");
}

}
