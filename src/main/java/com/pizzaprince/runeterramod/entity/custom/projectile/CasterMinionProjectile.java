package com.pizzaprince.runeterramod.entity.custom.projectile;

import com.pizzaprince.runeterramod.entity.ModEntityTypes;
import com.pizzaprince.runeterramod.entity.custom.BlueCasterMinionEntity;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.UUID;

public class CasterMinionProjectile extends Projectile {
    private int maxLifeTime = 200;
    private int currentLife = 0;
    public CasterMinionProjectile(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public CasterMinionProjectile(Level pLevel, BlueCasterMinionEntity minion){
        super(ModEntityTypes.CASTER_MINION_PROJECTILE.get(), pLevel);
        this.setOwner(minion);
        this.setPos(minion.getX() - (double)(minion.getBbWidth() + 1.0F) * 0.5D * (double) Mth.sin(minion.yBodyRot * ((float)Math.PI / 180F)), minion.getEyeY() - (double)0.1F, minion.getZ() + (double)(minion.getBbWidth() + 1.0F) * 0.5D * (double)Mth.cos(minion.yBodyRot * ((float)Math.PI / 180F)));
    }

    @Override
    public void tick() {
        super.tick();
        if(++currentLife >= maxLifeTime){
            this.discard();
        }
        Vec3 vec3 = this.getDeltaMovement();
        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (hitresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult))
            this.onHit(hitresult);
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        this.updateRotation();
        float f = 0.99F;
        float f1 = 0.06F;
        if (this.level().getBlockStates(this.getBoundingBox()).noneMatch(BlockBehaviour.BlockStateBase::isAir)) {
            this.discard();
        } else if (this.isInWaterOrBubble()) {
            this.discard();
        } else {
            this.setDeltaMovement(vec3.scale((double)0.99F));
            this.setPos(d0, d1, d2);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = this.getOwner();
        if (entity instanceof LivingEntity livingentity) {
            pResult.getEntity().hurt(this.damageSources().mobProjectile(this, livingentity), 12);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        if (!this.level().isClientSide) {
            this.discard();
        }
    }

    @Override
    protected void defineSynchedData() {

    }
}
