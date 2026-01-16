package com.pizzaprince.runeterramod.entity.custom;

import com.pizzaprince.runeterramod.effect.ModAttributes;
import com.pizzaprince.runeterramod.entity.custom.projectile.CasterMinionProjectile;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableRangedAttack;
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
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class BlueCasterMinionEntity extends Monster implements GeoEntity, RangedAttackMob, SmartBrainOwner<BlueCasterMinionEntity> {

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public static final EntityDataAccessor<Boolean> flagNoMove = SynchedEntityData.defineId(BlueCasterMinionEntity.class, EntityDataSerializers.BOOLEAN);

    private final AnimationController<BlueCasterMinionEntity> controller = new AnimationController<BlueCasterMinionEntity>(
            this, "controller", 4, state -> {
                if(state.getController().isPlayingTriggeredAnimation()) return PlayState.CONTINUE;
                if(state.isMoving()){
                    state.getController().setAnimation(RawAnimation.begin().then("animation.blue_caster.run", Animation.LoopType.LOOP));
                } else {
                    state.getController().setAnimation(RawAnimation.begin().then("animation.blue_caster.idle", Animation.LoopType.LOOP));
                }
                return PlayState.CONTINUE;
    }).triggerableAnim("attack", RawAnimation.begin().then("animation.blue_caster.attack", Animation.LoopType.PLAY_ONCE))
            .receiveTriggeredAnimations();

    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ATTACK_DAMAGE, 2f)
                .add(Attributes.MOVEMENT_SPEED, .30f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0f)
                .add(Attributes.FOLLOW_RANGE, 30f)
                .add(ModAttributes.ABILITY_POWER.get(), 5f)
                .build();
    }
    public BlueCasterMinionEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pVelocity) {
        CasterMinionProjectile projectile = new CasterMinionProjectile(this.level(), this);
        double d0 = pTarget.getX() - this.getX();
        double d1 = pTarget.getY() - this.getY();
        double d2 = pTarget.getZ() - this.getZ();
        //double d3 = Math.sqrt(d0 * d0 + d2 * d2) * (double) 0.2F;
        projectile.shoot(d0, d1, d2, 0.5F, 0.5F);
        this.level().addFreshEntity(projectile);
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
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    public List<ExtendedSensor<BlueCasterMinionEntity>> getSensors() {
        return ObjectArrayList.of(
                new NearbyLivingEntitySensor<>(),
                new HurtBySensor<>()
        );
    }

    @Override
    public BrainActivityGroup<BlueCasterMinionEntity> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new MoveToWalkTarget<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends BlueCasterMinionEntity> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
            new FirstApplicableBehaviour<BlueCasterMinionEntity>(
                  new TargetOrRetaliate<BlueCasterMinionEntity>().attackablePredicate(entity -> entity.isAlive() && (entity instanceof Player)),
                  new SetPlayerLookTarget<>(),
                  new SetRandomLookTarget<>()
            ),
            new OneRandomBehaviour<BlueCasterMinionEntity>(
                    new SetRandomWalkTarget<>(),
                    new Idle<>().runFor(entity -> entity.getRandom().nextInt(100, 300))
            )
        );
    }

    @Override
    public BrainActivityGroup<? extends BlueCasterMinionEntity> getFightTasks() {
        return BrainActivityGroup.fightTasks(
            new InvalidateAttackTarget<>(),
            new SetWalkTargetToAttackTarget<>().closeEnoughDist((mob, entity) -> 12),
            new AnimatableRangedAttack<>(18).attackInterval(entity -> 30)
                    .whenStarting(entity -> {
                        entityData.set(flagNoMove, true);
                        triggerAnim("controller", "attack");
                    }).whenStopping(entity -> {
                        entityData.set(flagNoMove, false);
                    })
        );
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
    protected PathNavigation createNavigation(Level pLevel) {
        return new SmoothGroundNavigation(this, pLevel);
    }
}
