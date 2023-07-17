package com.pizzaprince.runeterramod.ability.item.custom.curios;

import net.minecraft.world.entity.LivingEntity;

public class RegenerationCapability {

    private int timeToNextHeal;

    private int healAmount;

    private int maxTime;

    public RegenerationCapability(int amount, int ticksForHeal){
        this.healAmount = amount;
        this.timeToNextHeal = ticksForHeal;
        this.maxTime = ticksForHeal;
    }

    public void tick(LivingEntity entity){
        timeToNextHeal = Math.max(0, --timeToNextHeal);
        if(timeToNextHeal == 0){
            entity.heal(healAmount);
            timeToNextHeal = maxTime;
        }
    }

}
