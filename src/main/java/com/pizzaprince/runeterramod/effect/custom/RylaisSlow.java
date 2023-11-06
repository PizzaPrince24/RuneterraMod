package com.pizzaprince.runeterramod.effect.custom;

import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;

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
            double r = entity.getBbWidth() / 2;
            double angle = Math.random() * 2 * Math.PI;
            double randHeight = Math.random() * entity.getBbHeight();

            entity.getServer().getLevel(entity.getCommandSenderWorld().dimension())
                    .sendParticles(ParticleTypes.SNOWFLAKE, entity.getX() + (Math.cos(angle) * r), entity.getY() + randHeight,
                            entity.getZ() + (Math.sin(angle) * r), 2, 0, 0, 0, 0);

        }

        super.applyEffectTick(entity, amplifier);
    }
}
