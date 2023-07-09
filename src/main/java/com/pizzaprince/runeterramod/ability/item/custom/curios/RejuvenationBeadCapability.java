package com.pizzaprince.runeterramod.ability.item.custom.curios;

import net.minecraft.world.entity.LivingEntity;

public class RejuvenationBeadCapability {

    private int timeToNextHeal = 200;

    public void tick(LivingEntity entity){
        timeToNextHeal = Math.max(0, --timeToNextHeal);
        if(timeToNextHeal == 0){
            entity.heal(1);
        }
    }

}
