package com.pizzaprince.runeterramod.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

public class ModUtils {
    public static boolean isFacingWithinAngle(Mob mob, LivingEntity target, float angle) {
        float entityHitAngle = (float) ((Math.atan2(target.getZ() - mob.getZ(), target.getX() - mob.getX()) * (180 / Math.PI) - 90) % 360);
        float entityAttackingAngle = mob.yBodyRot % 360;
        if (entityHitAngle < 0) {
            entityHitAngle += 360;
        }
        if (entityAttackingAngle < 0) {
            entityAttackingAngle += 360;
        }
        float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
        if ((entityRelativeAngle <= angle && entityRelativeAngle >= -angle / 2) || (entityRelativeAngle >= 360 - angle || entityRelativeAngle <= -360 + angle / 2)) {
            return true;
        }
        return false;
    }
}
