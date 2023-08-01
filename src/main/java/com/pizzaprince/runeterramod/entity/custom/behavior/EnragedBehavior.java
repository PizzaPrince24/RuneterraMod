package com.pizzaprince.runeterramod.entity.custom.behavior;

import com.mojang.brigadier.Command;
import com.mojang.datafixers.util.Pair;
import com.pizzaprince.runeterramod.entity.custom.RampagingBaccaiEntity;
import com.pizzaprince.runeterramod.sound.ModSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.tslat.smartbrainlib.api.core.behaviour.DelayedBehaviour;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleType;
import virtuoel.pehkui.api.ScaleTypes;
import virtuoel.pehkui.util.CommandUtils;
import virtuoel.pehkui.util.ScaleRenderUtils;
import virtuoel.pehkui.util.ScaleUtils;

import java.util.List;

public class EnragedBehavior<E extends RampagingBaccaiEntity> extends DelayedBehaviour<E> {
    public EnragedBehavior(int delayTicks) {
        super(delayTicks);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        return !entity.enraged && (entity.getHealth() < (entity.getMaxHealth()/2));
    }

    @Override
    protected void start(E entity) {
        super.start(entity);
        entity.triggerAnim("controller", "enrage");
        entity.level().playSound(null, entity.blockPosition(), ModSounds.BACCAI_ROAR.get(), SoundSource.HOSTILE, 1f, 1f);
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return List.of();
    }

    @Override
    protected void doDelayedAction(E entity) {
        entity.enraged = true;
        entity.getEntityData().set(RampagingBaccaiEntity.enragedData, true);
        entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 99999, entity.level().getDifficulty() == Difficulty.HARD ? 2 : 1, true, true, true));
        if(!entity.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(entity.enragedModifier)) {
            entity.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(entity.enragedModifier);
        }
        ScaleTypes.BASE.getScaleData(entity).setTargetScale(2f);
        entity.knockbackNearbyEntities(5.0);
    }
}
