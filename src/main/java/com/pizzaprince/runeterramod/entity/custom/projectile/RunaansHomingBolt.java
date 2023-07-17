package com.pizzaprince.runeterramod.entity.custom.projectile;

import com.pizzaprince.runeterramod.entity.ModEntityTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.function.Predicate;

public class RunaansHomingBolt extends AbstractArrow {
    public RunaansHomingBolt(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public RunaansHomingBolt(Level level, LivingEntity entity) {
        super(ModEntityTypes.RUNAANS_HOMING_BOLT.get(), entity, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.inGround) {
            AABB range = new AABB(this.getX() - 6.0D, this.getY() - 3.0D, this.getZ() - 6.0D, this.getX() + 6.0D, this.getY() + 3.0D, this.getZ() + 6.0D);
            Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and((entity) -> {
                if (entity instanceof LivingEntity) {
                    return true;
                }
                return false;
            });
            List<? extends LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, range, ENTITY_PREDICATE);
            boolean found = false;
            for (LivingEntity entity : entities) {
                if (entity != null && entity != getOwner()) {
                    found = true;
                    double x = entity.getX() - this.getX();
                    double y = entity.getY() - this.getY();
                    double z = entity.getZ() - this.getZ();
                    double sqrt = Mth.sqrt((float) (x * x +  z * z));
                    shoot(x, y + sqrt * 0.2, z, 1.0F, 2.0F);
                    break;
                }
            }
        }
    }

    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }
}
