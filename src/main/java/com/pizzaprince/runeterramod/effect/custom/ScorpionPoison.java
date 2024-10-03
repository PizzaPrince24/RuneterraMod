package com.pizzaprince.runeterramod.effect.custom;

import com.pizzaprince.runeterramod.effect.ModDamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ScorpionPoison extends MobEffect {
    public ScorpionPoison(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        pLivingEntity.hurt(ModDamageTypes.getDamageSource(pLivingEntity.level(), ModDamageTypes.SCORPION_POISON), 1.0F);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        int j = 25 >> pAmplifier;
        if (j > 0) {
            return pDuration % j == 0;
        } else {
            return true;
        }
    }
}
