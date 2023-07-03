package com.pizzaprince.runeterramod.effect.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class RylaisSlow extends MobEffect {
    public RylaisSlow(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
    }

    @Override
    public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if(!entity.level().isClientSide()){
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 2, 1, false, false, false));

            entity.level().addParticle(ParticleTypes.SNOWFLAKE, entity.getX(), entity.getY()+0.5, entity.getZ(), 0, 0, 0);
        }

        super.applyEffectTick(entity, amplifier);
    }
}
