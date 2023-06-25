package com.pizzaprince.runeterramod.item.client;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.item.custom.armor.AsheArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AsheArmorModel extends GeoModel<AsheArmorItem> {

	@Override
	public ResourceLocation getAnimationResource(AsheArmorItem animatable) {
		return new ResourceLocation(RuneterraMod.MOD_ID, "animations/ashe_armor.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(AsheArmorItem object) {
		return new ResourceLocation(RuneterraMod.MOD_ID, "geo/ashe_armor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(AsheArmorItem object) {
		return new ResourceLocation(RuneterraMod.MOD_ID, "textures/models/armor/ashe_armor.png");
	}

}
