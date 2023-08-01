package com.pizzaprince.runeterramod.item.custom;

import com.pizzaprince.runeterramod.ability.IAbilityItem;
import com.pizzaprince.runeterramod.ability.item.AbstractAbility;
import com.pizzaprince.runeterramod.ability.item.custom.SandBlastAbility;
import com.pizzaprince.runeterramod.effect.ModDamageTypes;
import com.pizzaprince.runeterramod.item.client.BaccaiStaffRenderer;
import com.pizzaprince.runeterramod.particle.ModParticles;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;

public class BaccaiStaff extends Item implements GeoAnimatable, IAbilityItem {

    public AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public BaccaiStaff(Properties pProperties) {
        super(pProperties);
    }

    //@Override
    //public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
    //    if(!pLevel.isClientSide()){
    //        pPlayer.swing(pUsedHand, true);
//
    //        Vec3 origin = new Vec3(pPlayer.getX() + (pPlayer.getBbWidth() / 2), pPlayer.getY() + pPlayer.getEyeHeight(), pPlayer.getZ() + (pPlayer.getBbWidth() / 2));
    //        Vec3 direction = pPlayer.getForward();
    //        Vec3 target = origin.add(direction.scale(40));
    //        Vec3 ray = target.subtract(origin);
    //        AABB bb = pPlayer.getBoundingBox().inflate(40);
    //        EntityHitResult hit = ProjectileUtil.getEntityHitResult(pPlayer, origin, target, bb, entity -> !entity.isSpectator() && entity instanceof LivingEntity, ray.lengthSqr());
    //        if(hit != null){
    //            if(hit.getEntity() instanceof LivingEntity pTarget) {
    //                pTarget.hurt(ModDamageTypes.getDamageSource(ModDamageTypes.SAND_BLAST, pPlayer), (float) pPlayer.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * 0.2f + 2);
    //                ray = ray.scale(pPlayer.distanceTo(pTarget) / ray.length());
    //            }
    //        }
    //        Vec3 dir = ray;
    //        for (int i = 0; i < (int) (dir.length() * 10); i++) {
    //            double randProgress = Math.random();
    //            Vec3 dirProgress = dir.multiply(randProgress, randProgress, randProgress);
    //            pPlayer.getServer().getLevel(pPlayer.level().dimension()).sendParticles(ModParticles.SAND_PARTICLE.get(),
    //                    origin.x + dirProgress.x, origin.y + dirProgress.y, origin.z + dirProgress.z, 2, 0, 0, 0, 0.1);
    //        }
    //    }
    //    return super.use(pLevel, pPlayer, pUsedHand);
    //}

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController(this, "controller", 20, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getTick(Object o) {
        return 0;
    }

    private PlayState predicate(AnimationState state) {
        return PlayState.CONTINUE;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);

        consumer.accept(new IClientItemExtensions() {

            private final BlockEntityWithoutLevelRenderer renderer = new BaccaiStaffRenderer();
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer;
            }
        });
    }


    @Override
    public List<AbstractAbility> getAbilities() {
        return List.of(new SandBlastAbility());
    }
}
