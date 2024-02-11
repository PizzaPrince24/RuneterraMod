package com.pizzaprince.runeterramod.util;

import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;

public class ModCombatRules {

    public static float getDamageAfterMRAbsorb(float pDamage, float mr, float mPen){
        float magicResist = mr - mPen;
        float f1 = Mth.clamp(magicResist - pDamage / 2f, magicResist * 0.2F, 2048f);
        return pDamage * (1.0F - f1 / 25.0F);
    }
}
