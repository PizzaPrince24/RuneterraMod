package com.pizzaprince.runeterramod.entity.custom;

import com.pizzaprince.runeterramod.entity.custom.behavior.EnragedBehavior;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class RenektonEntity extends Monster implements GeoEntity, SmartBrainOwner<RenektonEntity> {

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private final AnimationController controller = new AnimationController(this, "controller", 8, state -> {
        if(state.isMoving()){
            state.getController().setAnimation(RawAnimation.begin().then("animation.renekton.walk", Animation.LoopType.LOOP));
        } else {
            state.getController().setAnimation(RawAnimation.begin().then("animation.renekton.idle", Animation.LoopType.LOOP));
        }
        return PlayState.CONTINUE;
    });
    public RenektonEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        setMaxUpStep(4f);
    }

    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 600.0D)
                .add(Attributes.ARMOR, 14f)
                .add(Attributes.ATTACK_DAMAGE, 28.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.45f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0f)
                .add(Attributes.FOLLOW_RANGE, 100f)
                .build();
    }

    @Override
    public BrainActivityGroup<? extends RenektonEntity> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<RampagingBaccaiEntity>().setRadius(60f),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(40, 160))
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
                new HurtBySensor<>()
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

    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    protected void customServerAiStep() {
        tickBrain(this);
    }
}
