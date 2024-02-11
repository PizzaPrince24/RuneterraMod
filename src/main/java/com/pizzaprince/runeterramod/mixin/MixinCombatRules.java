package com.pizzaprince.runeterramod.mixin;

import net.minecraft.world.damagesource.CombatRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CombatRules.class)
public class MixinCombatRules {

    @ModifyArg(method = "Lnet/minecraft/world/damagesource/CombatRules;getDamageAfterAbsorb(FFF)F",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(FFF)F"),
            index = 2)
    private static float changeArmorCapForDamage(float pValue, float pMin, float pMax){
        return 2048f;
    }
}
