package com.pizzaprince.runeterramod.entity.custom;

import com.pizzaprince.runeterramod.block.entity.custom.SunDiskAltarEntity;
import com.pizzaprince.runeterramod.entity.ModEntityTypes;
import com.pizzaprince.runeterramod.particle.other.GlowParticleData;
import com.pizzaprince.runeterramod.world.dimension.ModDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.List;
import java.util.Set;

public class SunDiskEntity extends Entity implements GeoEntity {

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final AnimationController controller = new AnimationController(this, "controller", 8, state -> {
        state.getController().setAnimation(RawAnimation.begin().then("animation.sundisk.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    });
    private boolean isComplete = false;
    private BlockPos sunDiskAltarPair;
    private BlockPos originalSpawn;
    private int maxProgress;
    private int currentProgress = 0;
    private BlockPos portalPos;
    public SunDiskEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public SunDiskEntity(Level pLevel, int altarX, int altarY, int altarZ, int maxProgress) {
        this(ModEntityTypes.SUN_DISK.get(), pLevel);
        this.setPos(altarX, altarY + 50, altarZ);
        this.originalSpawn = new BlockPos(altarX, altarY+50, altarZ);
        this.sunDiskAltarPair = new BlockPos(altarX, altarY, altarZ);
        this.maxProgress = maxProgress;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        this.isComplete = pCompound.getBoolean("SunDiskEntity.isComplete");
        this.sunDiskAltarPair = new BlockPos(pCompound.getInt("SunDiskEntity.sunDiskAltarPair.x"), pCompound.getInt("SunDiskEntity.sunDiskAltarPair.y"),
                pCompound.getInt("SunDiskEntity.sunDiskAltarPair.z"));
        this.originalSpawn = new BlockPos(pCompound.getInt("SunDiskEntity.originalSpawn.x"), pCompound.getInt("SunDiskEntity.originalSpawn.y"),
                pCompound.getInt("SunDiskEntity.originalSpawn.z"));
        this.maxProgress = pCompound.getInt("SunDiskEntity.maxProgress");
        this.currentProgress = pCompound.getInt("SunDiskEntity.currentProgress");
        if(isComplete) portalPos = new BlockPos(pCompound.getInt("SunDiskEntity.portalPos.x"), pCompound.getInt("SunDiskEntity.portalPos.y"),
                pCompound.getInt("SunDiskEntity.portalPos.z"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putBoolean("SunDiskEntity.isComplete", this.isComplete);
        pCompound.putInt("SunDiskEntity.sunDiskAltarPair.x", sunDiskAltarPair.getX());
        pCompound.putInt("SunDiskEntity.sunDiskAltarPair.y", sunDiskAltarPair.getY());
        pCompound.putInt("SunDiskEntity.sunDiskAltarPair.z", sunDiskAltarPair.getZ());
        pCompound.putInt("SunDiskEntity.originalSpawn.x", originalSpawn.getX());
        pCompound.putInt("SunDiskEntity.originalSpawn.y", originalSpawn.getY());
        pCompound.putInt("SunDiskEntity.originalSpawn.z", originalSpawn.getZ());
        pCompound.putInt("SunDiskEntity.maxProgress", maxProgress);
        pCompound.putInt("SunDiskEntity.currentProgress", currentProgress);
        if(isComplete){
            pCompound.putInt("SunDiskEntity.portalPos.x", portalPos.getX());
            pCompound.putInt("SunDiskEntity.portalPos.y", portalPos.getY());
            pCompound.putInt("SunDiskEntity.portalPos.z", portalPos.getZ());
        }
    }

    @Override
    public void tick() {
        super.tick();
        if(originalSpawn != null) this.setPos(originalSpawn.getX(), originalSpawn.getY(), originalSpawn.getZ());
        pushEntities();
        checkForAltarPair();
        if(isComplete){
            if(!this.level().isClientSide) {
                ServerLevel level = (ServerLevel) this.level();
                double x = portalPos.getX()+Math.random();
                double y = portalPos.getY()+Math.random()*2;
                double z = portalPos.getZ()+Math.random();
                level.sendParticles(new GlowParticleData(255f, 152f, 0f, 0.1f), x, y, z, 1, 0,0,0,0);
                level.getEntitiesOfClass(Player.class, new AABB(portalPos)).forEach(player -> {
                    player.teleportTo(level.getServer().getLevel(ModDimensions.DISK_FIGHT_DIM_KEY), 0, 200, 0, Set.of(), player.getYHeadRot(), player.getXRot());
                });
            }
        }
    }

    private void checkForAltarPair() {
        if(this.sunDiskAltarPair == null) return;
        BlockEntity altar = this.level().getBlockEntity(this.sunDiskAltarPair);
        if(!(altar instanceof SunDiskAltarEntity) || this.distanceToSqr(sunDiskAltarPair.getX(), sunDiskAltarPair.getY(), sunDiskAltarPair.getZ()) > 3600){
            this.kill();
            this.discard();
        }
    }

    private void pushEntities() {
        if (this.level().isClientSide()) {
            this.level().getEntities(EntityTypeTest.forClass(Player.class), this.getBoundingBox(), EntitySelector.pushableBy(this)).forEach(this::push);
        } else {
            List<Entity> list = this.level().getEntities(this, this.getBoundingBox(), EntitySelector.pushableBy(this));
            if (!list.isEmpty()) {
                int i = this.level().getGameRules().getInt(GameRules.RULE_MAX_ENTITY_CRAMMING);
                if (i > 0 && list.size() > i - 1 && this.random.nextInt(4) == 0) {
                    int j = 0;

                    for(int k = 0; k < list.size(); ++k) {
                        if (!list.get(k).isPassenger()) {
                            ++j;
                        }
                    }

                    if (j > i - 1) {
                        this.hurt(this.damageSources().cramming(), 6.0F);
                    }
                }

                for(int l = 0; l < list.size(); ++l) {
                    Entity entity = list.get(l);
                    this.push(entity);
                }
            }

        }
    }

    public void setProgress(int progress){
        if(this.currentProgress >= this.maxProgress){
            return;
        }
        this.currentProgress = progress;
        if(this.currentProgress >= this.maxProgress){
            this.isComplete =  true;
        }
        ScaleTypes.BASE.getScaleData(this).setTargetScale(getScale());
        if(this.isComplete){
            portalPos = new BlockPos(this.blockPosition().getX()+20,
                    this.level().getHeight(Heightmap.Types.OCEAN_FLOOR_WG, this.blockPosition().getX()+20, this.blockPosition().getZ()),
                    this.blockPosition().getZ());
        }
    }

    private float getScale(){
        return Mth.clampedLerp(1f, 20f, (float)currentProgress/(float)maxProgress);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(this.controller);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
