package com.pizzaprince.runeterramod.entity.client;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.entity.custom.RampagingBaccaiEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationProcessor;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import java.util.List;

public class RampagingBaccaiModel extends GeoModel<RampagingBaccaiEntity> {
    @Override
    public ResourceLocation getModelResource(RampagingBaccaiEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "geo/rampaging_baccai.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RampagingBaccaiEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "textures/entity/rampaging_baccai_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(RampagingBaccaiEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "animations/rampaging_baccai.animation.json");
    }

    @Override
    public void setCustomAnimations(RampagingBaccaiEntity animatable, long instanceId, AnimationState<RampagingBaccaiEntity> animationState) {
        CoreGeoBone neck1 = getAnimationProcessor().getBone("neck1");
        CoreGeoBone neck2 = getAnimationProcessor().getBone("neck2");
        CoreGeoBone neck3 = getAnimationProcessor().getBone("neck3");
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        boolean flag = false;

        AnimationProcessor.QueuedAnimation currentAnim = animationState.getController().getCurrentAnimation();

        if(currentAnim != null && currentAnim.animation().name().equals("animation.rampaging_baccai.run")){
            flag = true;
        }
        if(neck1 != null && !flag){
            neck1.setRotX(((entityData.headPitch()) * Mth.DEG_TO_RAD) / 3);
            neck1.setRotY((entityData.netHeadYaw() * Mth.DEG_TO_RAD) / 3);
        }
        if(neck2 != null && !flag){
            neck2.setRotX(((entityData.headPitch()) * Mth.DEG_TO_RAD) / 3);
            neck2.setRotY((entityData.netHeadYaw() * Mth.DEG_TO_RAD) / 3);
        }
        if(neck3 != null && !flag){
            neck3.setRotX(((entityData.headPitch()) * Mth.DEG_TO_RAD) / 3);
            neck3.setRotY((entityData.netHeadYaw() * Mth.DEG_TO_RAD) / 3);
        }
        if(head != null && !flag){
            head.setRotX((entityData.headPitch() * Mth.DEG_TO_RAD) / 3);
            head.setRotY((entityData.netHeadYaw() * Mth.DEG_TO_RAD) / 3);
        }

    }
}
