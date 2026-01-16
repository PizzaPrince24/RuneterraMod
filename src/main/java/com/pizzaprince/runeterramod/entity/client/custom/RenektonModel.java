package com.pizzaprince.runeterramod.entity.client.custom;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.entity.custom.RenektonEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationProcessor;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class RenektonModel extends GeoModel<RenektonEntity> {
    @Override
    public ResourceLocation getModelResource(RenektonEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "geo/renekton.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RenektonEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "textures/entity/renekton_tex.png");
    }

    @Override
    public ResourceLocation getAnimationResource(RenektonEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "animations/renekton.animation.json");
    }

    @Override
    public void setCustomAnimations(RenektonEntity animatable, long instanceId, AnimationState<RenektonEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        head.setRotX((entityData.headPitch() * -Mth.DEG_TO_RAD));
        float yaw = entityData.netHeadYaw();
        yaw = Mth.clamp(yaw, -60, 60);
        float num = (yaw * Mth.DEG_TO_RAD) + 0.32f;
        head.setRotY(num);
    }
}
