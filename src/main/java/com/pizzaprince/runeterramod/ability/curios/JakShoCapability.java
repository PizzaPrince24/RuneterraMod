package com.pizzaprince.runeterramod.ability.curios;

import com.pizzaprince.runeterramod.effect.ModAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class JakShoCapability {

    int combatTicks = 0;

    int timer = 0;

    private static AttributeModifier JAKSHO_ARMOR = new AttributeModifier("jaksho_armor",
            0.3, AttributeModifier.Operation.MULTIPLY_TOTAL);

    private static AttributeModifier JAKSHO_MR = new AttributeModifier("jaksho_mr",
            0.3, AttributeModifier.Operation.MULTIPLY_TOTAL);

    public void tick(LivingEntity entity){
        combatTicks = Math.max(0, --combatTicks);
        if(combatTicks > 0){
            timer = Math.min(200, ++timer);
            if(timer == 200){
                if(!entity.getAttribute(Attributes.ARMOR).hasModifier(JAKSHO_ARMOR)) {
                    entity.getAttribute(Attributes.ARMOR).addTransientModifier(JAKSHO_ARMOR);
                }
                if(!entity.getAttribute(ModAttributes.MAGIC_RESIST.get()).hasModifier(JAKSHO_MR)) {
                    entity.getAttribute(ModAttributes.MAGIC_RESIST.get()).addTransientModifier(JAKSHO_MR);
                }
            }
        } else {
            timer = Math.max(0, --timer);
            if(timer == 0){
                if(entity.getAttribute(Attributes.ARMOR).hasModifier(JAKSHO_ARMOR)) {
                    entity.getAttribute(Attributes.ARMOR).removeModifier(JAKSHO_ARMOR);
                }
                if(entity.getAttribute(ModAttributes.MAGIC_RESIST.get()).hasModifier(JAKSHO_MR)) {
                    entity.getAttribute(ModAttributes.MAGIC_RESIST.get()).removeModifier(JAKSHO_MR);
                }
            }
        }
    }

    public void setInCombat(){
        combatTicks = 120;
    }
}
