package com.pizzaprince.runeterramod.entity.custom;

import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.effect.ModDamageTypes;
import com.pizzaprince.runeterramod.effect.ModEffects;
import com.pizzaprince.runeterramod.entity.custom.behavior.EnragedBehavior;
import com.pizzaprince.runeterramod.item.ModItems;
import com.pizzaprince.runeterramod.networking.ModPackets;
import com.pizzaprince.runeterramod.networking.packet.AOEParticleS2CPacket;
import com.pizzaprince.runeterramod.particle.ModParticles;
import com.pizzaprince.runeterramod.sound.ModSounds;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.bossevents.CustomBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.common.Mod;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableRangedAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.CustomDelayedBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.*;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.object.SquareRadius;
import net.tslat.smartbrainlib.util.BrainUtils;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleType;

import java.util.Comparator;
import java.util.List;

public class RampagingBaccaiEntity extends Monster implements GeoEntity, SmartBrainOwner<RampagingBaccaiEntity>, RangedAttackMob {

    public boolean flagNoMove = false;

    public boolean enraged = false;

    public boolean isBattling = false;

    private DamageSource killedBy;

    private int timeToNextEnrageDmg = 40;

    public AttributeModifier enragedModifier = new AttributeModifier("enraged_damage", 0.5, AttributeModifier.Operation.MULTIPLY_TOTAL);

    public AttributeModifier hardModeModifier = new AttributeModifier("enraged_damage", 8, AttributeModifier.Operation.ADDITION);
    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public static final EntityDataAccessor<Boolean> enragedData = SynchedEntityData.defineId(RampagingBaccaiEntity.class, EntityDataSerializers.BOOLEAN);

    private final CustomBossEvent bossEvent;
    public RampagingBaccaiEntity(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        this.setCustomName(Component.literal("Kaboskos, the Rampaging").withStyle(ChatFormatting.GOLD));
        this.bossEvent = new CustomBossEvent(new ResourceLocation(RuneterraMod.MOD_ID, "baccai_boss_event"), this.getCustomName());
        this.bossEvent.setMax((int)this.getMaxHealth());
        this.bossEvent.setVisible(true);
        this.bossEvent.setColor(BossEvent.BossBarColor.YELLOW);
        if(this.level().getDifficulty() == Difficulty.HARD){
            this.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(this.hardModeModifier);
        }
        setMaxUpStep(2f);
        setPersistenceRequired();
    }

    @Override
    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return false;
    }

    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 500.0D)
                .add(Attributes.ARMOR, 12f)
                .add(Attributes.ATTACK_DAMAGE, 28.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.55f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0f)
                .add(Attributes.FOLLOW_RANGE, 100f)
                .build();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController(this, "controller", 8, this::predicate)
                .triggerableAnim("swing", RawAnimation.begin().then("animation.rampaging_baccai.swing", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("beam", RawAnimation.begin().then("animation.rampaging_baccai.beam", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("two_hand_swing", RawAnimation.begin().then("animation.rampaging_baccai.two_hand_swing", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("stun", RawAnimation.begin().then("animation.rampaging_baccai.staff_stun", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("enrage", RawAnimation.begin().then("animation.rampaging_baccai.enrage", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("death", RawAnimation.begin().then("animation.rampaging_baccai.death", Animation.LoopType.PLAY_ONCE)));
    }

    private PlayState predicate(AnimationState state) {
        if(state.isMoving()){
            state.getController().setAnimation(RawAnimation.begin().then("animation.rampaging_baccai.run", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        state.getController().setAnimation(RawAnimation.begin().then("animation.rampaging_baccai.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    protected void tickDeath() {
        ++this.deathTime;
        if (this.deathTime >= 210 && !this.level().isClientSide() && !this.isRemoved()) {
            this.level().broadcastEntityEvent(this, (byte)60);
            this.actuallyDie(killedBy);
            this.remove(Entity.RemovalReason.KILLED);
        } else if(this.deathTime > 130 && !this.level().isClientSide() && !this.isRemoved()){
            for(int i = 0; i < 60; i++) {
                level().getServer().getLevel(this.level().dimension()).sendParticles(ModParticles.SAND_PARTICLE.get(),
                        this.getX() + (Math.random()-0.5)*2.5*this.getBbWidth(), this.getY() + Math.random(), this.getZ() + (Math.random()-0.5)*2.5*this.getBbWidth(),
                        1, Math.random()-0.5, -1, Math.random()-0.5, 0.03);
            }
        }
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource pSource, int pLooting, boolean pRecentlyHit) {
        this.spawnAtLocation(new ItemStack(ModItems.BACCAI_STAFF.get()));
        this.spawnAtLocation(new ItemStack(Items.GOLD_INGOT).copyWithCount((int)(Math.random()*7) + 13));
        this.spawnAtLocation(new ItemStack(ModItems.RAMPAGING_BACCAI_ARMOR.get()));
        super.dropCustomDeathLoot(pSource, pLooting, pRecentlyHit);
    }

    @Override
    public void die(DamageSource pDamageSource) {
        if (!this.isRemoved() && !this.dead) {
            this.killedBy = pDamageSource;
            playSound(ModSounds.BACCAI_PENANCE.get(), 1f, 0.7f);
        }
    }

    public void actuallyDie(DamageSource pDamageSource) {
        if (net.minecraftforge.common.ForgeHooks.onLivingDeath(this, pDamageSource)) return;
        if (!this.isRemoved() && !this.dead) {
            Entity entity = pDamageSource.getEntity();
            LivingEntity livingentity = this.getKillCredit();
            if (this.deathScore >= 0 && livingentity != null) {
                livingentity.awardKillScore(this, this.deathScore, pDamageSource);
            }

            if (this.isSleeping()) {
                this.stopSleeping();
            }

            if (!this.level().isClientSide && this.hasCustomName()) {
                LogUtils.getLogger().info("Named entity {} died: {}", this, this.getCombatTracker().getDeathMessage().getString());
            }

            this.dead = true;
            this.getCombatTracker().recheckStatus();
            Level level = this.level();
            if (level instanceof ServerLevel) {
                ServerLevel serverlevel = (ServerLevel)level;
                if (entity == null || entity.killedEntity(serverlevel, this)) {
                    this.gameEvent(GameEvent.ENTITY_DIE);
                    this.dropAllDeathLoot(pDamageSource);
                    this.createWitherRose(livingentity);
                }

                this.level().broadcastEntityEvent(this, (byte)3);
            }

            this.setPose(Pose.DYING);
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(enragedData, false);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    protected void customServerAiStep() {
        tickBrain(this);
        if(this.flagNoMove){
            getNavigation().stop();
        }
        bossEvent.setValue((int)this.getHealth());
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return ModSounds.BACCAI_GRUNT.get();
    }

    @Override
    public void tick() {
        super.tick();
        if(this.level().isClientSide()) return;
        if(enraged){
            timeToNextEnrageDmg = Math.max(0, --timeToNextEnrageDmg);
            if (timeToNextEnrageDmg == 0) {
                this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(10, 3, 10)).forEach(entity -> {
                    if(!entity.is(this)){
                        entity.hurt(ModDamageTypes.getDamageSource(ModDamageTypes.SAND_BLAST, this), this.level().getDifficulty() == Difficulty.HARD ? 2 : 1);
                    }
                });
                timeToNextEnrageDmg = 40;
            }
        }
        if(this.isDeadOrDying()){
            triggerAnim("controller", "death");
        }
    }

    @Override
    public void startSeenByPlayer(ServerPlayer pServerPlayer) {
        super.startSeenByPlayer(pServerPlayer);
        if(isBattling) {
            bossEvent.addPlayer(pServerPlayer);
        }
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer pServerPlayer) {
        super.stopSeenByPlayer(pServerPlayer);
        if(isBattling) {
            bossEvent.removePlayer(pServerPlayer);
        }
    }


    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    public List<ExtendedSensor<RampagingBaccaiEntity>> getSensors() {
        return ObjectArrayList.of(
                new NearbyLivingEntitySensor<>(),
                new HurtBySensor<>()
        );
    }

    @Override
    public BrainActivityGroup<RampagingBaccaiEntity> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new MoveToWalkTarget<>(),
                new EnragedBehavior<>(13)
        );
    }

    @Override
    public BrainActivityGroup<RampagingBaccaiEntity> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<RampagingBaccaiEntity>(
                        new TargetOrRetaliate<RampagingBaccaiEntity>(){
                            @Override
                            protected boolean checkExtraStartConditions(ServerLevel level, RampagingBaccaiEntity owner) {
                                Brain<?> brain = owner.getBrain();
                                this.toTarget = BrainUtils.getMemory(brain, this.priorityTargetMemory);

                                if (this.toTarget == null) {
                                    this.toTarget = BrainUtils.getMemory(brain, MemoryModuleType.HURT_BY_ENTITY);

                                    if (this.toTarget != null && this.canAttackPredicate.test(this.toTarget)) {
                                        if (this.alertAlliesPredicate.test(owner, this.toTarget))
                                            alertAllies(level, owner);

                                        return true;
                                    }
                                    else {
                                        NearestVisibleLivingEntities nearbyEntities = BrainUtils.getMemory(brain, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES);

                                        if (nearbyEntities != null){
                                            List<LivingEntity> nearbyEntitiesFromRange = level.getEntitiesOfClass(LivingEntity.class, owner.getBoundingBox().inflate(owner.getAttributeValue(Attributes.FOLLOW_RANGE)),
                                                    entity -> entity.isAlive() && !entity.is(owner) && entity instanceof Monster && owner.hasLineOfSight(entity));
                                            nearbyEntitiesFromRange.sort(Comparator.comparingDouble(owner::distanceToSqr));
                                            if(nearbyEntitiesFromRange.size() > 0) {
                                                this.toTarget = nearbyEntitiesFromRange.get(0);
                                            }
                                        }

                                        return this.toTarget != null && this.canAttackPredicate.test(this.toTarget);
                                    }
                                }

                                return this.canAttackPredicate.test(this.toTarget);
                            }
                        }.attackablePredicate(entity -> entity.isAlive() && (entity instanceof Monster)),
                        new SetRetaliateTarget<>(),
                        new SetPlayerLookTarget<RampagingBaccaiEntity>(){
                            @Override
                            protected void start(RampagingBaccaiEntity entity) {
                                super.start(entity);
                                entity.bossEvent.removeAllPlayers();
                                entity.isBattling = false;
                                entity.setTarget(null);
                            }
                        },
                        new SetRandomLookTarget<>()
                ),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<RampagingBaccaiEntity>(){
                            @Override
                            protected void start(RampagingBaccaiEntity entity) {
                                super.start(entity);
                                entity.bossEvent.removeAllPlayers();
                                entity.isBattling = false;
                                entity.setTarget(null);
                            }
                        },
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(20, 80))
                )
        );
    }

    @Override
    public BrainActivityGroup<RampagingBaccaiEntity> getFightTasks() { // These are the tasks that handle fighting
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(), // Cancel fighting if the target is no longer valid
                new SetWalkTargetToAttackTarget<RampagingBaccaiEntity>(){
                    @Override
                    protected void start(RampagingBaccaiEntity entity) {
                        super.start(entity);
                        if(!entity.isBattling){
                            entity.level().playSound(null, entity.blockPosition(), ModSounds.BACCAI_NO_MERCY.get(), SoundSource.HOSTILE, 1f, 1f);
                        }
                        entity.isBattling = true;
                        entity.level().getEntitiesOfClass(ServerPlayer.class, entity.getBoundingBox().inflate(150)).forEach(player -> {
                            entity.bossEvent.addPlayer(player);
                        });
                    }
                }, // Set the walk target to the attack target
                new OneRandomBehaviour<>(
                        new Pair<>(new AnimatableMeleeAttack<RampagingBaccaiEntity>(30){
                            @Override
                            protected void start(RampagingBaccaiEntity entity) {
                                super.start(entity);
                                triggerAnim("controller", "swing");
                                entity.flagNoMove = true;
                                entity.playAttackSound();
                            }

                            @Override
                            protected void stop(RampagingBaccaiEntity entity) {
                                super.stop(entity);
                                entity.flagNoMove = false;
                            }

                            @Override
                            protected void doDelayedAction(RampagingBaccaiEntity entity) {
                                BrainUtils.setForgettableMemory(entity, MemoryModuleType.ATTACK_COOLING_DOWN, true, this.attackIntervalSupplier.apply(entity));
                                boolean hitTarget = false;
                                if (this.target != null && (entity.getSensing().hasLineOfSight(this.target) && entity.isWithinMeleeAttackRange(this.target))) {
                                    entity.doHurtTarget(this.target);
                                    hitTarget = true;
                                }

                                entity.doAOE(entity, this.target, hitTarget);
                            }
                        }, 5),
                        new Pair<>(new AnimatableMeleeAttack<RampagingBaccaiEntity>(42){
                            @Override
                            protected void start(RampagingBaccaiEntity entity) {
                                super.start(entity);
                                triggerAnim("controller","two_hand_swing");
                                entity.flagNoMove = true;
                                entity.playAttackSound();
                            }

                            @Override
                            protected void stop(RampagingBaccaiEntity entity) {
                                super.stop(entity);
                                entity.flagNoMove = false;
                            }

                            @Override
                            protected void doDelayedAction(RampagingBaccaiEntity entity) {
                                BrainUtils.setForgettableMemory(entity, MemoryModuleType.ATTACK_COOLING_DOWN, true, this.attackIntervalSupplier.apply(entity));

                                entity.cleave(entity, this.target);
                            }
                        }, 4),
                        new Pair<>(new AnimatableMeleeAttack<RampagingBaccaiEntity>(32){
                            @Override
                            protected void start(RampagingBaccaiEntity entity) {
                                super.start(entity);
                                triggerAnim("controller", "stun");
                                entity.flagNoMove = true;
                                entity.playAttackSound();
                            }

                            @Override
                            protected void stop(RampagingBaccaiEntity entity) {
                                super.stop(entity);
                                entity.flagNoMove = false;
                            }

                            @Override
                            protected void doDelayedAction(RampagingBaccaiEntity entity) {
                                BrainUtils.setForgettableMemory(entity, MemoryModuleType.ATTACK_COOLING_DOWN, true, this.attackIntervalSupplier.apply(entity));

                                entity.stunAOE(entity);
                            }
                        }.cooldownFor(entity -> 100), 3),
                        new Pair<>(new AnimatableRangedAttack<RampagingBaccaiEntity>(107){
                            @Override
                            protected void start(RampagingBaccaiEntity entity) {
                                super.start(entity);
                                triggerAnim("controller", "beam");
                                entity.flagNoMove = true;
                                entity.playAttackSound();
                            }

                            @Override
                            protected void stop(RampagingBaccaiEntity entity) {
                                super.stop(entity);
                                entity.flagNoMove = false;
                            }

                            @Override
                            protected boolean checkExtraStartConditions(ServerLevel level, RampagingBaccaiEntity entity) {
                                this.target = BrainUtils.getTargetOfEntity(entity);

                                return entity.distanceToSqr(this.target) <= this.attackRadius;
                            }

                            @Override
                            protected void tick(RampagingBaccaiEntity entity) {
                                super.tick(entity);
                                long gameTime = entity.level().getGameTime();
                                long timeIntoAnim = 95 - (this.delayFinishedAt - gameTime);
                                if(timeIntoAnim == 30){
                                    if((this.target != null && !this.target.isAlive()) || this.target == null) {
                                        this.target = BrainUtils.getMemory(brain, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)
                                                .findClosest(pTarget -> pTarget.isAlive() && entity.hasLineOfSight(pTarget) && (!(pTarget instanceof Player player) || !player.isCreative() || pTarget instanceof Monster)).orElse(null);
                                    }
                                    entity.performRangedAttack(this.target, 0.8f);
                                }
                                if(timeIntoAnim == 50){
                                    if((this.target != null && !this.target.isAlive()) || this.target == null) {
                                        this.target = BrainUtils.getMemory(brain, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)
                                                .findClosest(pTarget -> pTarget.isAlive() && entity.hasLineOfSight(pTarget) && (!(pTarget instanceof Player player) || !player.isCreative() || pTarget instanceof Monster)).orElse(null);
                                    }
                                    entity.performRangedAttack(this.target, 0.8f);
                                }
                                if(timeIntoAnim == 70){
                                    if((this.target != null && !this.target.isAlive()) || this.target == null) {
                                        this.target = BrainUtils.getMemory(brain, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)
                                                .findClosest(pTarget -> pTarget.isAlive() && entity.hasLineOfSight(pTarget) && (!(pTarget instanceof Player player) || !player.isCreative() || pTarget instanceof Monster)).orElse(null);
                                    }
                                    entity.performRangedAttack(this.target, 0.8f);
                                }
                            }

                            @Override
                            protected void doDelayedAction(RampagingBaccaiEntity entity) {
                                if (this.target == null)
                                    return;

                                BrainUtils.setForgettableMemory(entity, MemoryModuleType.ATTACK_COOLING_DOWN, true, this.attackIntervalSupplier.apply(entity));
                            }
                        }.attackRadius(100).cooldownFor(entity -> 160), 1)
                )
        );
    }

    private void playAttackSound() {
        if(Math.random() < 0.25){
            playSound(ModSounds.BACCAI_ATTACK.get());
        }
    }

    private void stunAOE(RampagingBaccaiEntity baccai) {
        Level level = baccai.level();
        level.getEntitiesOfClass(LivingEntity.class, baccai.getBoundingBox().inflate(enraged ? 20 : 10, 0, enraged ? 20 : 10)).forEach(entity -> {
            if(!entity.is(baccai)) {
                entity.hurt(level.damageSources().indirectMagic(baccai, null), (float)(baccai.getAttribute(Attributes.ATTACK_DAMAGE).getValue()*0.5f));
                entity.addEffect(new MobEffectInstance(ModEffects.STUN.get(), 100, 0, true, true, true));
            }
        });
        ModPackets.sendToNearbyPlayers(new AOEParticleS2CPacket(baccai.position().x, baccai.position().z, enraged ? 20 : 10), level, baccai.blockPosition());
    }

    private void cleave(RampagingBaccaiEntity baccai, LivingEntity target) {
        Level level = baccai.level();
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, baccai.getBoundingBox().inflate(enraged ? 14 : 7));
        for(LivingEntity entity : entities){
            if(entity.is(baccai)) continue;
            if(shouldBeCleaved(baccai, target)){
                entity.hurt(level.damageSources().mobAttack(baccai), (float)(baccai.getAttribute(Attributes.ATTACK_DAMAGE).getValue()*1f));
                entity.knockback(5, Math.sin(Math.toRadians((baccai.yBodyRot % 360) - 90)), -Math.cos(Math.toRadians((baccai.yBodyRot % 360) - 90)));
            }
        }
    }

    private boolean shouldBeCleaved(RampagingBaccaiEntity baccai, LivingEntity target) {
        float angle = 90f;
        float entityHitAngle = (float) ((Math.atan2(target.getZ() - baccai.getZ(), target.getX() - baccai.getX()) * (180 / Math.PI) - 90) % 360);
        float entityAttackingAngle = baccai.yBodyRot % 360;
        if (entityHitAngle < 0) {
            entityHitAngle += 360;
        }
        if (entityAttackingAngle < 0) {
            entityAttackingAngle += 360;
        }
        float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
        //float entityHitDistance = (float) Math.sqrt((target.getZ() - baccai.getZ()) * (target.getZ() - baccai.getZ()) + (target.getX() - baccai.getX()) * (target.getX() - baccai.getX())) - target.getBbWidth() / 2f;
        if ((entityRelativeAngle <= angle && entityRelativeAngle >= -angle / 2) || (entityRelativeAngle >= 360 - angle || entityRelativeAngle <= -360 + angle / 2)) {
            return true;
        }
        return false;
    }

    private void doAOE(RampagingBaccaiEntity baccai, LivingEntity target, boolean hitTarget) {
        Level level = baccai.level();
        if(hitTarget) {
            level.getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(enraged ? 8 : 4, 0, enraged ? 8 : 4)).forEach(entity -> {
                if (!entity.is(baccai) && !entity.is(target)) {
                    entity.hurt(level.damageSources().mobAttack(baccai), (float)(baccai.getAttribute(Attributes.ATTACK_DAMAGE).getValue()*0.66f));
                }
            });
            ModPackets.sendToNearbyPlayers(new AOEParticleS2CPacket(target.position().x, target.position().z, enraged ? 8 : 4), level, target.blockPosition());
        } else {
            double x = baccai.position().x + (enraged ? 10 : 5)*Math.sin(Math.toRadians((baccai.yBodyRot % 360) + 20));
            double z = baccai.position().z + (enraged ? 10 : 5)*Math.cos(Math.toRadians((baccai.yBodyRot % 360) + 20));
            double y = level.getHeight(Heightmap.Types.WORLD_SURFACE, (int)x, (int)z) + 1;
            AABB box = new AABB(x, y, z, x, y, z).inflate(enraged ? 8 : 4, 1, enraged ? 8 : 4);
            level.getEntitiesOfClass(LivingEntity.class, box).forEach(entity -> {
                if (!entity.is(baccai)) {
                    entity.hurt(level.damageSources().mobAttack(baccai), (float)(baccai.getAttribute(Attributes.ATTACK_DAMAGE).getValue()*0.66f));
                }
            });
            ModPackets.sendToNearbyPlayers(new AOEParticleS2CPacket(x, z, enraged ? 8 : 4), level, new BlockPos((int)x, (int) y, (int) z));
        }
    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pVelocity) {
        if(pTarget != null && !this.level().isClientSide()) {
            if(!this.level().isClientSide){
                double disToTarget = Math.sqrt((Math.pow(pTarget.getX() - this.getX(), 2)) + (Math.pow(pTarget.getY() - (this.getY() + (enraged ? 24 : 12)), 2)) + (Math.pow(pTarget.getZ() - this.getZ(), 2)));
                Vec3 dir = new Vec3(pTarget.getX() - this.getX(), pTarget.getY() - (this.getY() + (enraged ? 24 : 12)), pTarget.getZ() - this.getZ()).normalize().scale(disToTarget);
                for(int i = 0; i < (int)(disToTarget*10); i++){
                    double randProgress = Math.random();
                    Vec3 dirProgress = dir.multiply(randProgress, randProgress, randProgress);
                    this.getServer().getLevel(this.level().dimension()).sendParticles(ModParticles.SAND_PARTICLE.get(),
                            this.getX() + dirProgress.x, (this.getY() + (enraged ? 24 : 12)) + dirProgress.y, this.getZ() + dirProgress.z, 2, 0, 0, 0, 0.1);
                }
                pTarget.hurt(ModDamageTypes.getDamageSource(ModDamageTypes.SAND_BLAST, this), (float)this.getAttribute(Attributes.ATTACK_DAMAGE).getValue()*0.15f);
            }
        }
    }

    public void knockbackNearbyEntities(double strength) {
        Level level = this.level();
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(5));
        for(LivingEntity entity : entities){
            if(entity.is(this)) continue;
            entity.knockback(strength, entity.getX()-this.getX(), entity.getZ()-this.getZ());
            entity.hurt(ModDamageTypes.getDamageSource(ModDamageTypes.SAND_BLAST, this), 2);
        }

    }

    @Override
    public double getMeleeAttackRangeSqr(LivingEntity pEntity) {
        return enraged ? 14*14 : 7*7;
    }

}
