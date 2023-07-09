package com.pizzaprince.runeterramod.entity.client;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.entity.custom.projectile.RunaansHomingBolt;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class RunaansHomingBoltRenderer extends ArrowRenderer<RunaansHomingBolt> {

    public static final ResourceLocation HOMING_BOLT_RL = new ResourceLocation(RuneterraMod.MOD_ID+":textures/entity/projectiles/runaans_homing_bolt.png");
    public RunaansHomingBoltRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(RunaansHomingBolt pEntity) {
        return HOMING_BOLT_RL;
    }
}
