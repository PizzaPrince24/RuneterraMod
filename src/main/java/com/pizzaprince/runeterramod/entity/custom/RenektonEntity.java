package com.pizzaprince.runeterramod.entity.custom;

import com.mojang.datafixers.util.Pair;
import com.pizzaprince.runeterramod.effect.ModEffects;
import com.pizzaprince.runeterramod.entity.custom.ai.FixedGroundPathNavigation;
import com.pizzaprince.runeterramod.networking.ModPackets;
import com.pizzaprince.runeterramod.networking.packet.AOEParticleS2CPacket;
import com.pizzaprince.runeterramod.util.ModUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
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
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.*;
import net.tslat.smartbrainlib.api.core.navigation.SmoothGroundNavigation;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Comparator;
import java.util.List;

public class RenektonEntity extends Monster implements GeoEntity, SmartBrainOwner<RenektonEntity> {

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public static final EntityDataAccessor<Boolean> flagNoMove = SynchedEntityData.defineId(RenektonEntity.class, EntityDataSerializers.BOOLEAN);
    //public boolean flagNoMove = false;
    //dont touch on server only on client
    private int clientSideStompVar = 0;
    private boolean clientSideFinishStomp = false;
    private final AnimationController<RenektonEntity> controller = new AnimationController<RenektonEntity>(this, "controller", 5, state -> {
        AnimationController<RenektonEntity> c = state.getController();

        //deal with any animation that not triggered
        if(!c.isPlayingTriggeredAnimation()) {
            if (state.isMoving() || clientSideFinishStomp) {
                c.setAnimation(RawAnimation.begin().then("animation.renekton.walk", Animation.LoopType.LOOP));
            } else {
                c.setAnimation(RawAnimation.begin().then("animation.renekton.idle", Animation.LoopType.LOOP));
            }
        } else {
            clientSideFinishStomp = false;
        }

        //deal with the speeds of animations
        AnimationProcessor.QueuedAnimation anim = state.getController().getCurrentAnimation();
        if(anim != null){
            c.setAnimationSpeed(switch(anim.animation().name()){
                case "animation.renekton.walk" -> 2.2;
                case "animation.renekton.basic_attack" -> 1.7;
                default -> 1;
            });
        } else {
            c.setAnimationSpeed(1);
        }

        return PlayState.CONTINUE;
    }).triggerableAnim("basicAttack", RawAnimation.begin().then("animation.renekton.basic_attack", Animation.LoopType.PLAY_ONCE))
            .triggerableAnim("aoeJump", RawAnimation.begin().then("animation.renekton.basic_attack_aoe", Animation.LoopType.PLAY_ONCE))
            .setCustomInstructionKeyframeHandler(event -> {
        String i = event.getKeyframeData().getInstructions();
        if(i.equals("stompRight;")){
            event.getAnimatable().clientSideStompVar = 1;
        }
        if(i.equals("stompLeft;")){
            event.getAnimatable().clientSideStompVar = 2;
        }
        if(i.equals("finishStomp;")){
            event.getAnimatable().clientSideFinishStomp = true;
        }
        if(i.equals("canIdle;")){
            event.getAnimatable().clientSideFinishStomp = false;
        }
    }).receiveTriggeredAnimations();

    public RenektonEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        setMaxUpStep(4f);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
        setPersistenceRequired();
    }

    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 600.0D)
                .add(Attributes.ARMOR, 14f)
                .add(Attributes.ATTACK_DAMAGE, 28.0f)
                .add(Attributes.MOVEMENT_SPEED, .41f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0f)
                .add(Attributes.FOLLOW_RANGE, 100f)
                .build();
    }

    @Override
    public BrainActivityGroup<? extends RenektonEntity> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new SetWalkTargetToAttackTarget<>(),
                new FirstApplicableBehaviour<>(
                        /*
                        new AnimatableMeleeAttack<RenektonEntity>(55){
                            boolean action = false;
                            @Override
                            protected void start(RenektonEntity entity) {
                                super.start(entity);
                                triggerAnim("controller", "aoeJump");
                                entity.entityData.set(flagNoMove, true);
                                action = false;
                            }
                            @Override
                            protected void stop(RenektonEntity entity) {
                                super.stop(entity);
                                entity.entityData.set(flagNoMove, false);
                            }

                            @Override
                            protected void tick(RenektonEntity entity) {
                                if(this.delayFinishedAt - 38 == entity.level().getGameTime()){
                                    Vec3 v = this.target.position().subtract(entity.position());
                                    entity.addDeltaMovement(new Vec3(v.x/7, 1.95, v.z/7));
                                }
                                super.tick(entity);
                            }

                            @Override
                            protected boolean checkExtraStartConditions(ServerLevel level, RenektonEntity entity) {
                                this.target = BrainUtils.getTargetOfEntity(entity);

                                return entity.getSensing().hasLineOfSight(this.target) && entity.distanceTo(target) <= entity.getBbWidth()*6f && entity.distanceTo(target) > entity.getBbWidth()*3;
                            }

                            @Override
                            protected void doDelayedAction(RenektonEntity entity) {
                                if(action) return;
                                action = true;
                                BrainUtils.setForgettableMemory(entity, MemoryModuleType.ATTACK_COOLING_DOWN, true, this.attackIntervalSupplier.apply(entity));

                                doBigSit(entity);
                            }
                            @Override
                            protected boolean shouldKeepRunning(RenektonEntity entity) {
                                return this.delayFinishedAt + 95 >= entity.level().getGameTime();
                            }
                        }.runFor(entity -> 300),
                        */
                        new AnimatableMeleeAttack<RenektonEntity>(24){
                            boolean action = false;
                            @Override
                            protected void start(RenektonEntity entity) {
                                super.start(entity);
                                triggerAnim("controller", "basicAttack");
                                entity.entityData.set(flagNoMove, true);
                                action = false;
                            }
                            @Override
                            protected void stop(RenektonEntity entity) {
                                super.stop(entity);
                                entity.entityData.set(flagNoMove, false);
                            }

                            @Override
                            protected boolean checkExtraStartConditions(ServerLevel level, RenektonEntity entity) {
                                LivingEntity target = BrainUtils.getTargetOfEntity(entity);
                                return super.checkExtraStartConditions(level, entity) && ModUtils.isFacingWithinAngle(entity, target, 25f);
                            }

                            @Override
                            protected void doDelayedAction(RenektonEntity entity) {
                                if(action) return;
                                action = true;
                                BrainUtils.setForgettableMemory(entity, MemoryModuleType.ATTACK_COOLING_DOWN, true, this.attackIntervalSupplier.apply(entity));

                                doAOEBasicAttack(entity);
                            }
                            @Override
                            protected boolean shouldKeepRunning(RenektonEntity entity) {
                                return this.delayFinishedAt + 38 >= entity.level().getGameTime();
                            }
                        }.runFor(entity -> 300)


                )
        );
    }

    private static void doBigSit(RenektonEntity renekton) {
        Level level = renekton.level();
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, renekton.getBoundingBox().inflate(renekton.getBbWidth()*2));
        for(LivingEntity entity : entities){
            if(entity.is(renekton)) continue;

            //"critical hit"
            if(renekton.distanceTo(entity) <= renekton.getBbWidth()){
                entity.hurt(level.damageSources().mobAttack(renekton), (float)(renekton.getAttribute(Attributes.ATTACK_DAMAGE).getValue()*3f));
                entity.addEffect(new MobEffectInstance(ModEffects.STUN.get(), 200, 0, true, true, true));
            } else if(renekton.distanceTo(entity) <= renekton.getBbWidth()*1.7f){
                entity.hurt(level.damageSources().mobAttack(renekton), (float)(renekton.getAttribute(Attributes.ATTACK_DAMAGE).getValue()*1.3f));
                entity.addDeltaMovement(new Vec3(0, renekton.getBbHeight()/10f, 0));
                entity.addEffect(new MobEffectInstance(ModEffects.STUN.get(), 100, 0, true, true, true));
            } else if(renekton.distanceTo(entity) <= renekton.getBbWidth()*2.5f){
                entity.hurt(level.damageSources().mobAttack(renekton), (float)(renekton.getAttribute(Attributes.ATTACK_DAMAGE).getValue()*0.6f));
                entity.addDeltaMovement(new Vec3(0, renekton.getBbHeight()/30f, 0));
            }
        }
    }

    private static void doAOEBasicAttack(RenektonEntity renekton) {
        Level level = renekton.level();
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, renekton.getBoundingBox().inflate(renekton.getBbWidth()*1.5));
        for(LivingEntity entity : entities){
            if(entity.is(renekton)) continue;
            if(ModUtils.isFacingWithinAngle(renekton, entity, 90)) {
                entity.hurt(level.damageSources().mobAttack(renekton), (float) (renekton.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * 1f));
                entity.knockback(2, Math.sin(Math.toRadians((renekton.yBodyRot % 360))), -Math.cos(Math.toRadians((renekton.yBodyRot % 360))));
            }
        }
    }

    @Override
    public BrainActivityGroup<? extends RenektonEntity> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<>(
                        new TargetOrRetaliate<>().attackablePredicate(entity -> entity.isAlive() && ((entity instanceof Monster) || entity instanceof Player)),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()
                ),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>().setRadius(60f),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(20, 80))
                )
        );
    }

    @Override
    public BrainActivityGroup<? extends RenektonEntity> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new MoveToWalkTarget<>()
        );
    }

    @Override
    public List<? extends ExtendedSensor<? extends RenektonEntity>> getSensors() {
        return ObjectArrayList.of(
                new NearbyLivingEntitySensor<>(),
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>()
        );
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
    public boolean isPushable() {
        return false;
    }

    public int getClientSideStompVar(){return clientSideStompVar;}
    public void setClientSideStompVar(int var){clientSideStompVar = var;}

    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    protected void customServerAiStep() {
        tickBrain(this);
        if(this.entityData.get(flagNoMove)){
            getNavigation().stop();
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(flagNoMove, false);
    }

    @Override
    public boolean isWithinMeleeAttackRange(LivingEntity pEntity) {
        double d0 = this.getPerceivedTargetDistanceSquareForMeleeAttack(pEntity);
        return d0 <= this.getMeleeAttackRangeSqr(pEntity)*0.8f;
    }

    @Override
    protected PathNavigation createNavigation(Level pLevel) {
        return new SmoothGroundNavigation(this, pLevel);
    }

    @Override
    protected BodyRotationControl createBodyControl() {
        return super.createBodyControl();
    }

    @Override
    protected float tickHeadTurn(float pYRot, float pAnimStep) {
        if(this.entityData.get(flagNoMove)) return pAnimStep;
        return super.tickHeadTurn(pYRot, pAnimStep);
    }

    @Override
    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return false;
    }
}
