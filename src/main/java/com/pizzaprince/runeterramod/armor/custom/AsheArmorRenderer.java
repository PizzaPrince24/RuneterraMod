package com.pizzaprince.runeterramod.armor.custom;

import com.pizzaprince.runeterramod.item.custom.armor.AsheArmorItem;

import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class AsheArmorRenderer extends GeoArmorRenderer<AsheArmorItem>{

	public AsheArmorRenderer() {
		super(new AsheArmorModel());
		
		this.headBone = "armorHead";
        this.bodyBone = "armorBody";
        this.rightArmBone = "armorRightArm";
        this.leftArmBone = "armorLeftArm";
        this.rightLegBone = "armorRightLeg";
        this.leftLegBone = "armorLeftLeg";
        this.rightBootBone = "armorRightBoot";
        this.leftBootBone = "armorLeftBoot";
	}

}
