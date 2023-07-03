package com.pizzaprince.runeterramod.ability.item.custom.curios;

import net.minecraft.world.entity.LivingEntity;

public class SunfireAegisCapability {

    private int timeForBurn = 0;
    private int timeToNextBurn = 20;

    public void startBurn(){
        timeForBurn = 60;
    }

    public void tick(LivingEntity entity){
        if(timeForBurn > 0) {
            timeForBurn = Math.max(0, --timeForBurn);
            timeToNextBurn = Math.max(0, --timeToNextBurn);

            if (timeToNextBurn == 0) {
                timeToNextBurn = 20;
                immolateAroundEntity(entity);
            }
        }
        if(timeForBurn == 0){
            timeToNextBurn = 20;
        }
    }

    private void immolateAroundEntity(LivingEntity entity) {
        if(!entity.level().isClientSide()){
            entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(3)).forEach(target -> {
                if(!target.is(entity)) {
                    target.hurt(entity.level().damageSources().onFire(), 2);
                }
            });
        }
    }
}
