package com.pizzaprince.runeterramod.entity.custom;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.navigation.SmoothGroundNavigation;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class BlueSuperMinionEntity extends Monster implements GeoEntity, SmartBrainOwner<BlueSuperMinionEntity> {

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private final AnimationController<BlueSuperMinionEntity> controller = new AnimationController<BlueSuperMinionEntity>(this, "controller", 8, state -> {
        AnimationController<BlueSuperMinionEntity> c = state.getController();
        if(c.isPlayingTriggeredAnimation()) return PlayState.CONTINUE;
        if(state.isMoving()){
            c.setAnimation(RawAnimation.begin().then("animation.blue_super_minion.run", Animation.LoopType.LOOP));
        } else {
            c.setAnimation(RawAnimation.begin().then("animation.blue_super_minion.idle", Animation.LoopType.LOOP));
        }

        //deal with the speeds of animations
        AnimationProcessor.QueuedAnimation anim = c.getCurrentAnimation();
        if(anim != null){
            c.setAnimationSpeed(switch(anim.animation().name()){
                case "animation.blue_super_minion.run" -> 1.3;
                default -> 1;
            });
        } else {
            c.setAnimationSpeed(1);
        }

        return PlayState.CONTINUE;
    }).triggerableAnim("attack", RawAnimation.begin().then("animation.blue_super_minion.attack1", Animation.LoopType.PLAY_ONCE))
            .receiveTriggeredAnimations();
    public BlueSuperMinionEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 500)
                .add(Attributes.ARMOR, 20)
                .add(Attributes.ATTACK_DAMAGE, 20f)
                .add(Attributes.MOVEMENT_SPEED, .35f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8f)
                .add(Attributes.FOLLOW_RANGE, 40f)
                .build();
    }

    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    public List<ExtendedSensor<BlueSuperMinionEntity>> getSensors() {
        return ObjectArrayList.of(
                new NearbyLivingEntitySensor<>(),
                new HurtBySensor<>()
        );
    }

    @Override
    public BrainActivityGroup<BlueSuperMinionEntity> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new MoveToWalkTarget<>()
        );
    }

    @Override
    public BrainActivityGroup<BlueSuperMinionEntity> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<BlueSuperMinionEntity>(
                        new TargetOrRetaliate<BlueSuperMinionEntity>().attackablePredicate(entity -> entity.isAlive() && (entity instanceof Player)),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()
                ),
                new OneRandomBehaviour<BlueSuperMinionEntity>(
                        new SetRandomWalkTarget<>(),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(100, 300))
                )
        );
    }

    @Override
    public BrainActivityGroup<BlueSuperMinionEntity> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new SetWalkTargetToAttackTarget<>(){
                    @Override
                    protected void start(Mob entity) {
                        Brain<?> brain = entity.getBrain();
                        LivingEntity target = BrainUtils.getTargetOfEntity(entity);

                        if (entity.getSensing().hasLineOfSight(target) && (entity.getPerceivedTargetDistanceSquareForMeleeAttack(target) <= entity.getMeleeAttackRangeSqr(target) * 0.5d)) {
                            BrainUtils.clearMemory(brain, MemoryModuleType.WALK_TARGET);
                        }
                        else {
                            BrainUtils.setMemory(brain, MemoryModuleType.LOOK_TARGET, new EntityTracker(target, true));
                            BrainUtils.setMemory(brain, MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityTracker(target, false), this.speedMod.apply(entity, target), this.closeEnoughWhen.apply(entity, target)));
                        }
                    }
                },
                new AnimatableMeleeAttack<>(14).attackInterval(entity -> 30)
                        .whenStarting(entity -> {
                            triggerAnim("controller", "attack");
                        })
                        .startCondition(minion -> minion.getTarget() == null || minion.getPerceivedTargetDistanceSquareForMeleeAttack(minion.getTarget()) <= minion.getMeleeAttackRangeSqr(minion.getTarget()) * 0.5d)
        );
    }

    @Override
    protected PathNavigation createNavigation(Level pLevel) {
        return new SmoothGroundNavigation(this, pLevel);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(controller);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    protected void customServerAiStep() {
        tickBrain(this);
    }
}
