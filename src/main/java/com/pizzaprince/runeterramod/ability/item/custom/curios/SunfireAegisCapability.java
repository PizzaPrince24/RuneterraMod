package com.pizzaprince.runeterramod.ability.item.custom.curios;

import dev._100media.capabilitysyncer.core.ItemStackCapability;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class SunfireAegisCapability extends ItemStackCapability {

    private int timeForBurn = 0;
    private int timeToNextBurn = 20;
    public SunfireAegisCapability(ItemStack itemStack) {
        super(itemStack);
    }

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

    public int getBurnTime(){
        return timeForBurn;
    }

    @Override
    public CompoundTag serializeNBT(boolean savingToDisk) {
        CompoundTag nbt = new CompoundTag();

        nbt.putInt("burn", timeForBurn);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt, boolean readingFromDisk) {
        timeForBurn = nbt.getInt("burn");
    }

}
