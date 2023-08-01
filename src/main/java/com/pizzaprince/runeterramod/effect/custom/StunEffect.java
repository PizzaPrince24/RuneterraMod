package com.pizzaprince.runeterramod.effect.custom;

import java.util.List;
import java.util.Set;

import com.pizzaprince.runeterramod.client.ClientAbilityData;
import com.pizzaprince.runeterramod.effect.ModEffects;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class StunEffect extends MobEffect{

	public StunEffect(MobEffectCategory p_19451_, int p_19452_) {
		super(p_19451_, p_19452_);
	}

	@Override
	public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
		return true;
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		if(!entity.level().isClientSide()) {
			if(entity instanceof Mob mob) {
				mob.getNavigation().stop();
				mob.setTarget(null);
				mob.getBrain().clearMemories();
				mob.goalSelector.getRunningGoals().forEach(goal -> goal.stop());
				mob.targetSelector.getRunningGoals().forEach(goal -> goal.stop());
			}

			entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 2, 128, false, false, false));
			entity.addEffect(new MobEffectInstance(MobEffects.JUMP, 2, 128, false, false, false));
			if(entity instanceof Player player){
				player.stopUsingItem();
			}
		}
		if(entity instanceof Player player) {
			if(player.level().isClientSide()) {
				ClientAbilityData.setStunned();
				player.closeContainer();
			}
		}

		super.applyEffectTick(entity, amplifier);
	}


}
