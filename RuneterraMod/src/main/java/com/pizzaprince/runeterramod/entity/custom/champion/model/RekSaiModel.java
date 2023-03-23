package com.pizzaprince.runeterramod.entity.custom.champion.model;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.entity.custom.champion.RekSaiEntity;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RekSaiModel extends AnimatedGeoModel<RekSaiEntity>{

	@Override
	public ResourceLocation getAnimationResource(RekSaiEntity animatable) {
		return new ResourceLocation(RuneterraMod.MOD_ID, "animations/reksai.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(RekSaiEntity object) {
		return new ResourceLocation(RuneterraMod.MOD_ID, "geo/reksai.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(RekSaiEntity object) {
		return new ResourceLocation(RuneterraMod.MOD_ID, "textures/entity/reksai_texture.png");
	}

}
