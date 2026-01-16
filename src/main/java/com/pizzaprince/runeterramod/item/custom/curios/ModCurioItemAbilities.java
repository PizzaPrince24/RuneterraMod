package com.pizzaprince.runeterramod.item.custom.curios;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.ability.curios.ImmolationCapabilityProvider;
import com.pizzaprince.runeterramod.item.ModItems;
import com.pizzaprince.runeterramod.util.ModTags;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import top.theillusivec4.curios.api.CuriosCapability;

import java.util.function.Consumer;

public class ModCurioItemAbilities {

    public static final Consumer<LivingHurtEvent> IMMOLATION_HIT_EFFECT = event -> {
        event.getSource().getEntity().getCapability(CuriosCapability.INVENTORY).ifPresent(inventory -> {
            inventory.getCurios().values().forEach(curio -> {
                for (int slot = 0; slot < curio.getSlots(); slot++) {
                    curio.getStacks().getStackInSlot(slot).getCapability(ImmolationCapabilityProvider.IMMOLATION_CAPABILITY).ifPresent(immolation -> {
                        immolation.startBurn();
                    });
                }
            });
        });
    };

    public static final Consumer<LivingHurtEvent> SHEEN_HIT_EFFECT = event -> {
        if(event.getSource().getEntity() instanceof Player player && !player.getCooldowns().isOnCooldown(ModItems.SHEEN.get())){
            event.setAmount(event.getAmount() + 2);
            player.getCooldowns().addCooldown(ModItems.SHEEN.get(), 40);
        }
    };

    public static final Consumer<LivingHurtEvent> COSMIC_DRIVE_HIT_EFFECT = event -> {
        if(event.getSource().getEntity() instanceof LivingEntity entity && event.getSource().is(ModTags.DamageTypes.MAGIC)){
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 0, true, true, true));
        }
    };

    public static final Consumer<LivingHurtEvent> ECLIPSE_HIT_EFFECT = event -> {
        if(event.getSource().getEntity() instanceof Player player && !player.getCooldowns().isOnCooldown(ModItems.ECLIPSE.get())){
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 60, 0, true, true, true));
            player.getCooldowns().addCooldown(ModItems.ECLIPSE.get(), 120);
        }
    };

    public static final Consumer<LivingHurtEvent> GIANT_SLAYER_HIT_EFFECT = event -> {
        if(event.getEntity().getType().is(Tags.EntityTypes.BOSSES)){
            float healthDiff = event.getEntity().getMaxHealth() - ((LivingEntity)event.getSource().getEntity()).getMaxHealth();
            float mult = Mth.clampedLerp(1f, 1.4f, healthDiff/200);
            event.setAmount(event.getAmount()*mult);
        }
    };

    public static final Consumer<LivingHurtEvent> GUINSOOS_RAGEBLADE_HIT_EFFECT = event -> {
        event.getEntity().hurt(event.getEntity().level().damageSources().magic(), 2);
        if(event.getSource().getEntity() instanceof Player player && !player.getCooldowns().isOnCooldown(ModItems.GUINSOOS_RAGEBLADE.get())){
            player.getCooldowns().addCooldown(ModItems.GUINSOOS_RAGEBLADE.get(), 80);
            player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
                cap.applyHitEffects(event);
            });
        }
    };
}
