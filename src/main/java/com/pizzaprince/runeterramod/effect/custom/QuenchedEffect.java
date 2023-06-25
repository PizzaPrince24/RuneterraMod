package com.pizzaprince.runeterramod.effect.custom;

import com.pizzaprince.runeterramod.effect.ModEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.monster.Vex;
import net.minecraftforge.client.EntitySpectatorShaderManager;

public class QuenchedEffect extends MobEffect {
    private ResourceLocation creeperShader = new ResourceLocation("minecraft:shaders/post/creeper.json");
    public QuenchedEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if(pLivingEntity.level().isClientSide()) {
            if (pLivingEntity.getEffect(ModEffects.QUENCHED.get()).getDuration() <= 1) {
                Minecraft.getInstance().gameRenderer.shutdownEffect();
            } else {
                Minecraft.getInstance().gameRenderer.loadEffect(creeperShader);
            }
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
