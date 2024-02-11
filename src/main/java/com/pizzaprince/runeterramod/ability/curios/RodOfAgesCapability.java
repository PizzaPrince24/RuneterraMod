package com.pizzaprince.runeterramod.ability.curios;

import com.pizzaprince.runeterramod.effect.ModAttributes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class RodOfAgesCapability {

    int timerForCombat = 120;

    int stacks = 0;

    int timerForStacks = 0;

    private AttributeModifier healthMod;

    private AttributeModifier apMod;

    private AttributeModifier hasteMod;

    private boolean maxStacks = false;

    public RodOfAgesCapability(){
        remakeAttributes();
    }

    public void serializeNBT(CompoundTag nbt) {
        nbt.putInt("stacks", stacks);
        nbt.putInt("timerForStacks", timerForStacks);
        nbt.putBoolean("maxStacks", maxStacks);
        nbt.put("healthMod", healthMod.save());
        nbt.put("apMod", apMod.save());
        nbt.put("hasteMod", hasteMod.save());
    }

    public void deserializeNBT(CompoundTag nbt) {
        this.stacks = nbt.getInt("stacks");
        this.timerForStacks = nbt.getInt("timerForStacks");
        this.maxStacks = nbt.getBoolean("maxStacks");
        this.healthMod = AttributeModifier.load(nbt.getCompound("healthMod"));
        this.apMod = AttributeModifier.load(nbt.getCompound("apMod"));
        this.hasteMod = AttributeModifier.load(nbt.getCompound("hasteMod"));
    }

    public void tick(LivingEntity entity){
        if(maxStacks) return;
        timerForCombat = Math.max(0, --timerForCombat);
        if(timerForCombat > 0){
            timerForStacks++;
            if(timerForStacks >= 1200){
                if(entity.getAttribute(Attributes.MAX_HEALTH).hasModifier(healthMod)){
                    entity.getAttribute(Attributes.MAX_HEALTH).removeModifier(healthMod);
                }
                if(entity.getAttribute(ModAttributes.ABILITY_POWER.get()).hasModifier(apMod)){
                    entity.getAttribute(ModAttributes.ABILITY_POWER.get()).removeModifier(apMod);
                }
                if(entity.getAttribute(ModAttributes.ABILITY_HASTE.get()).hasModifier(hasteMod)){
                    entity.getAttribute(ModAttributes.ABILITY_HASTE.get()).removeModifier(hasteMod);
                }
                stacks = Math.min(10, ++stacks);
                remakeAttributes();
                entity.getAttribute(Attributes.MAX_HEALTH).addTransientModifier(healthMod);
                entity.getAttribute(ModAttributes.ABILITY_POWER.get()).addTransientModifier(apMod);
                entity.getAttribute(ModAttributes.ABILITY_HASTE.get()).addTransientModifier(hasteMod);
                if(stacks == 10){
                    maxStacks = true;
                }
            }
        }
    }

    public AttributeModifier getHealthMod(){
        return healthMod;
    }

    public AttributeModifier getApMod(){
        return apMod;
    }

    public AttributeModifier getHasteMod(){
        return hasteMod;
    }

    public int getStacks(){
        return stacks;
    }

    private void remakeAttributes() {
        healthMod = new AttributeModifier("rod_of_ages_health", stacks, AttributeModifier.Operation.ADDITION);
        apMod = new AttributeModifier("rod_of_ages_ap", ((double)stacks) / 2.0, AttributeModifier.Operation.ADDITION);
        hasteMod = new AttributeModifier("rod_of_ages_haste", stacks*2, AttributeModifier.Operation.ADDITION);
    }

    public void setInCombat(){
        timerForCombat = 120;
    }
}
