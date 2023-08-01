package com.pizzaprince.runeterramod.item.custom.curios.legendary;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class RylaisCrystalScepter extends Item implements ICurioItem {

    private static AttributeModifier RYLAIS_DAMAGE = new AttributeModifier("rylais_damage",
            2, AttributeModifier.Operation.ADDITION);

    private static AttributeModifier RYLAIS_HEALTH = new AttributeModifier("rylais_health",
            2, AttributeModifier.Operation.ADDITION);
    public RylaisCrystalScepter(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(RYLAIS_DAMAGE)) {
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(RYLAIS_DAMAGE);
        }
        if(!slotContext.entity().getAttribute(Attributes.MAX_HEALTH).hasModifier(RYLAIS_HEALTH)) {
            slotContext.entity().getAttribute(Attributes.MAX_HEALTH).addTransientModifier(RYLAIS_HEALTH);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(RYLAIS_DAMAGE)){
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(RYLAIS_DAMAGE);
        }
        if(slotContext.entity().getAttribute(Attributes.MAX_HEALTH).hasModifier(RYLAIS_HEALTH)) {
            slotContext.entity().getAttribute(Attributes.MAX_HEALTH).removeModifier(RYLAIS_HEALTH);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("+2 Attack Damage").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.literal("+1 Heart").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.literal("30% Slow On-Hit").withStyle(ChatFormatting.GOLD));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}