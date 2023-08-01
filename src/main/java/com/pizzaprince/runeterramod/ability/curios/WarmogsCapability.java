package com.pizzaprince.runeterramod.ability.curios;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class WarmogsCapability {

    private int outOfCombat = 0;

    private int timeToNextHeal = 20;

    public void setInCombat(){
        outOfCombat = 120;
    }

    public void tick(LivingEntity entity){
        outOfCombat = Math.max(0, --outOfCombat);
        timeToNextHeal = Math.max(0, --timeToNextHeal);
        if(outOfCombat == 0 && timeToNextHeal == 0){
            if(entity.level().isClientSide()) return;
            entity.heal(1);
            timeToNextHeal = 20;
        }
    }

}
