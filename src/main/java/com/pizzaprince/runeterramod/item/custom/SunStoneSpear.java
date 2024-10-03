package com.pizzaprince.runeterramod.item.custom;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.pizzaprince.runeterramod.effect.ModDamageTypes;
import com.pizzaprince.runeterramod.item.ModItems;
import com.pizzaprince.runeterramod.particle.other.GlowParticleData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.function.Consumer;

public class SunStoneSpear extends SwordItem {
    public SunStoneSpear(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        boolean isInfused = pStack.is(ModItems.INFUSED_SUN_STONE_SPEAR.get());
        Vec3 origin = new Vec3(pLivingEntity.getX(), pLivingEntity.getY() + pLivingEntity.getEyeHeight(), pLivingEntity.getZ());
        Vec3 direction = pLivingEntity.getForward();
        double scale = 1;
        int damage = 2;
        if(isInfused){
            scale = 15;
            damage += (int)(((double)pStack.getUseDuration() - pTimeCharged) / 5d);
        } else {
            scale = 25;
            damage += 3;
            damage += (int)(((double)pStack.getUseDuration() - pTimeCharged) / 2d);
        }
        Vec3 target = origin.add(direction.scale(scale));
        Vec3 ray = target.subtract(origin);
        AABB bb = pLivingEntity.getBoundingBox().inflate(scale);
        if(isInfused){
            for(int i = 0; i < 10; i++){
                EntityHitResult hit = ProjectileUtil.getEntityHitResult(pLivingEntity, origin, target, bb, entity -> !entity.isSpectator() && entity instanceof LivingEntity && entity.invulnerableTime == 0 && entity != pLivingEntity, ray.lengthSqr());
                if(hit != null){
                    if(hit.getEntity() instanceof LivingEntity pTarget) {
                        pTarget.hurt(ModDamageTypes.getEntityDamageSource(pLevel, ModDamageTypes.SUN_ENERGY, pLivingEntity), damage);
                    }
                }
            }
        } else {
            for(int i = 0; i < 100; i++){
                double dx = Math.random()*2-1;
                double dy = Math.random()*2-1;
                double dz = Math.random()*2-1;
                Vec3 newOrigin = origin.add(dx, dy, dz);
                Vec3 newTarget = target.add(dx, dy, dz);
                EntityHitResult hit = ProjectileUtil.getEntityHitResult(pLivingEntity, newOrigin, newTarget, bb, entity -> !entity.isSpectator() && entity instanceof LivingEntity && entity.invulnerableTime == 0 && entity != pLivingEntity, ray.lengthSqr());
                if(hit != null){
                    if(hit.getEntity() instanceof LivingEntity pTarget) {
                        pTarget.hurt(ModDamageTypes.getEntityDamageSource(pLevel, ModDamageTypes.SUN_ENERGY, pLivingEntity), damage);
                    }
                }
            }
        }
        if(pLevel.isClientSide()){
            for (int i = 0; i < (int) (ray.length() * 10); i++) {
                double randProgress = Math.random();
                Vec3 dirProgress = ray.multiply(randProgress, randProgress, randProgress);
                pLevel.addParticle(new GlowParticleData(255f, 152f, 0f, isInfused ? 0.5f : 2.5f), true,
                        origin.x + dirProgress.x, origin.y + dirProgress.y, origin.z + dirProgress.z, 0,0,0);
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        if(itemstack.is(ModItems.SUN_STONE_SPEAR.get()) || pUsedHand == InteractionHand.OFF_HAND) return super.use(pLevel, pPlayer, pUsedHand);
        if(!pPlayer.isUsingItem() && !isAboutToBreak(itemstack, 10)){
            pPlayer.startUsingItem(pUsedHand);
            itemstack.hurtAndBreak(10, pPlayer, entity -> {});
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        if(isAboutToBreak(pStack)){
            pLivingEntity.releaseUsingItem();
            return;
        }
        spawnEnergyParticles(pLevel, pLivingEntity);
        pStack.hurtAndBreak(2, pLivingEntity, (entity -> {}));
        super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);
    }

    private void spawnEnergyParticles(Level pLevel, LivingEntity pPlayer){
        if(pLevel.isClientSide()){
            for(int i = 0; i < 2; i++) {
                Vec3 origin = new Vec3(pPlayer.getX(), pPlayer.getY() + pPlayer.getBbHeight()*3f/4f, pPlayer.getZ());
                double angle = Math.toRadians((pPlayer.yBodyRot+360)%360);
                double dx = -Math.sin(angle)*pPlayer.getBbWidth();
                double dz = Math.cos(angle)*pPlayer.getBbWidth();
                Vec3 target = origin.add(dx, 0, dz);
                Vec3 startPos = new Vec3(pPlayer.position().x + Math.random()*3-1.5, pPlayer.position().y + pPlayer.getBbHeight()/2f + Math.random()*3-1.5, pPlayer.position().z + Math.random()*3-1.5);
                Vec3 speed = target.subtract(startPos).scale(0.035);
                pLevel.addParticle(new GlowParticleData(255f, 152f, 0f, 0.25f), true,
                        startPos.x, startPos.y, startPos.z, speed.x, speed.y, speed.z);
            }
        }
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return (pStack.is(ModItems.INFUSED_SUN_STONE_SPEAR.get()) || pStack.is(ModItems.PURIFIED_SUN_STONE_SPEAR.get())) ? 72000 : 0;
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isValidRepairItem(ItemStack pToRepair, ItemStack pRepair) {
        return false;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return (pStack.is(ModItems.INFUSED_SUN_STONE_SPEAR.get()) || pStack.is(ModItems.PURIFIED_SUN_STONE_SPEAR.get())) ? UseAnim.BLOCK : super.getUseAnimation(pStack);
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return (stack.is(ModItems.INFUSED_SUN_STONE_SPEAR.get()) || stack.is(ModItems.PURIFIED_SUN_STONE_SPEAR.get())) && isAboutToBreak(stack) ? 0 : amount;
    }

    public static boolean isAboutToBreak(ItemStack stack){
        return isAboutToBreak(stack, 2);
    }

    public static boolean isAboutToBreak(ItemStack stack, int leeway){
        return stack.getDamageValue() > stack.getMaxDamage() - (leeway+1);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if((stack.is(ModItems.INFUSED_SUN_STONE_SPEAR.get()) || stack.is(ModItems.PURIFIED_SUN_STONE_SPEAR.get())) && isAboutToBreak(stack)){
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            return builder.build();
        }
        return super.getAttributeModifiers(slot, stack);
    }
}
