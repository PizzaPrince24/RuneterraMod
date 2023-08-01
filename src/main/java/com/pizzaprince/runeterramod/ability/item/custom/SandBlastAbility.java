package com.pizzaprince.runeterramod.ability.item.custom;

import com.pizzaprince.runeterramod.ability.item.AbstractAbility;
import com.pizzaprince.runeterramod.effect.ModDamageTypes;
import com.pizzaprince.runeterramod.particle.ModParticles;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class SandBlastAbility extends AbstractAbility {

    private static int cooldown = 4*20;
    public SandBlastAbility() {
        super(SoundEvents.SAND_PLACE, null, cooldown);
        setShouldSetStaticCooldown(false);
    }

    @Override
    public void fireAbility(Player player, Level pLevel) {
        if(!pLevel.isClientSide()){
            player.swing(InteractionHand.MAIN_HAND, true);

            Vec3 origin = new Vec3(player.getX() + (player.getBbWidth() / 2), player.getY() + player.getEyeHeight(), player.getZ() + (player.getBbWidth() / 2));
            Vec3 direction = player.getForward();
            Vec3 target = origin.add(direction.scale(40));
            Vec3 ray = target.subtract(origin);
            AABB bb = player.getBoundingBox().inflate(40);
            EntityHitResult hit = ProjectileUtil.getEntityHitResult(player, origin, target, bb, entity -> !entity.isSpectator() && entity instanceof LivingEntity, ray.lengthSqr());
            if(hit != null){
                if(hit.getEntity() instanceof LivingEntity pTarget) {
                    pTarget.hurt(ModDamageTypes.getDamageSource(ModDamageTypes.SAND_BLAST, player), (float) player.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * 0.2f + 2);
                    ray = ray.scale(player.distanceTo(pTarget) / ray.length());
                }
            }
            Vec3 dir = ray;
            for (int i = 0; i < (int) (dir.length() * 10); i++) {
                double randProgress = Math.random();
                Vec3 dirProgress = dir.multiply(randProgress, randProgress, randProgress);
                player.getServer().getLevel(player.level().dimension()).sendParticles(ModParticles.SAND_PARTICLE.get(),
                        origin.x + dirProgress.x, origin.y + dirProgress.y, origin.z + dirProgress.z, 2, 0, 0, 0, 0.1);
            }
        }
    }
}
