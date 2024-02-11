package com.pizzaprince.runeterramod.mixin;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalDoubleRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.pizzaprince.runeterramod.effect.ModAttributes;
import com.pizzaprince.runeterramod.util.ModCombatRules;
import com.pizzaprince.runeterramod.util.ModTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.checkerframework.checker.units.qual.A;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {

    @Inject(at = @At("RETURN"), method = "Lnet/minecraft/world/entity/LivingEntity;getDamageAfterArmorAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F")
    private void reapplyArmor(DamageSource pDamageSource, float pDamageAmount, CallbackInfoReturnable<Float> cir){
        pDamageAmount = Math.max(0.1f, pDamageAmount);
    }

    @Inject(at = @At("HEAD"), method = "Lnet/minecraft/world/entity/LivingEntity;getDamageAfterArmorAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F", cancellable = true)
    private void getLethalityAndCalculateMagicDamage(DamageSource pDamageSource, float pDamageAmount, CallbackInfoReturnable<Float> cir, @Share("lethality") LocalDoubleRef lethality){
        if(pDamageSource.is(ModTags.DamageTypes.MAGIC)){
            ((LivingEntity)((Object)this)).hurtArmor(pDamageSource, pDamageAmount);
            float newDamage = ModCombatRules.getDamageAfterMRAbsorb(pDamageAmount, (float) ((LivingEntity)((Object)this)).getAttributeValue(ModAttributes.MAGIC_RESIST.get()),
                    (float) (pDamageSource.getEntity() instanceof LivingEntity entity ? entity.getAttributeValue(ModAttributes.MAGIC_PENETRATION.get()) : 0.0));
            newDamage = Math.max(0.1f, newDamage);
            cir.setReturnValue(newDamage);
        }
        lethality.set(pDamageSource.getEntity() instanceof LivingEntity entity ? entity.getAttributeValue(ModAttributes.LETHALITY.get()) : 0.0);
    }

    @ModifyArg(method = "Lnet/minecraft/world/entity/LivingEntity;getDamageAfterArmorAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/damagesource/CombatRules;getDamageAfterAbsorb(FFF)F"),
            index = 1)
    private float lethality(float pDamageAmount, float armorValue, float armorToughness, @Share("lethality") LocalDoubleRef lethality){
        return armorValue - (float)lethality.get();
    }
}
