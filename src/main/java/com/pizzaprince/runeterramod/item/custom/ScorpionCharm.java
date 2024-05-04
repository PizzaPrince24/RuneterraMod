package com.pizzaprince.runeterramod.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ScorpionCharm extends Item {
    public ScorpionCharm(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(pLevel.isClientSide) return super.use(pLevel, pPlayer, pUsedHand);
        ItemStack charm = pPlayer.getItemInHand(pUsedHand);
        charm.getOrCreateTag().putBoolean("activated", !charm.getOrCreateTag().getBoolean("activated"));
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if(pLevel.isClientSide) return;
        if(pEntity instanceof LivingEntity entity) {
            if (pStack.getOrCreateTag().getBoolean("activated")) {
                PotionUtils.getCustomEffects(pStack).forEach(entity::addEffect);
            }
        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        PotionUtils.addPotionTooltip(pStack, pTooltipComponents, 1.0F);
    }
}
