package com.pizzaprince.runeterramod.item.custom.curios.legendary;

import com.pizzaprince.runeterramod.ability.curios.ImmolationCapabilityProvider;
import com.pizzaprince.runeterramod.ability.curios.JakShoCapabilityProvider;
import com.pizzaprince.runeterramod.effect.ModAttributes;
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

public class JakSho extends Item implements ICurioItem {

    private static AttributeModifier JAKSHO_HEALTH = new AttributeModifier("jaksho_health",
            6, AttributeModifier.Operation.ADDITION);

    private static AttributeModifier JAKSHO_ARMOR = new AttributeModifier("jaksho_armor",
            5, AttributeModifier.Operation.ADDITION);

    private static AttributeModifier JAKSHO_MR = new AttributeModifier("jaksho_mr",
            5, AttributeModifier.Operation.ADDITION);
    public JakSho(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        stack.getCapability(JakShoCapabilityProvider.JAKSHO_CAPABILITY).ifPresent(cap -> {
            cap.tick(slotContext.entity());
        });
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(Attributes.MAX_HEALTH).hasModifier(JAKSHO_HEALTH)) {
            slotContext.entity().getAttribute(Attributes.MAX_HEALTH).addTransientModifier(JAKSHO_HEALTH);
        }
        if(!slotContext.entity().getAttribute(Attributes.ARMOR).hasModifier(JAKSHO_ARMOR)) {
            slotContext.entity().getAttribute(Attributes.ARMOR).addTransientModifier(JAKSHO_ARMOR);
        }
        if(!slotContext.entity().getAttribute(ModAttributes.MAGIC_RESIST.get()).hasModifier(JAKSHO_MR)) {
            slotContext.entity().getAttribute(ModAttributes.MAGIC_RESIST.get()).addTransientModifier(JAKSHO_MR);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(Attributes.MAX_HEALTH).hasModifier(JAKSHO_HEALTH)){
            slotContext.entity().getAttribute(Attributes.MAX_HEALTH).removeModifier(JAKSHO_HEALTH);
        }
        if(slotContext.entity().getAttribute(Attributes.ARMOR).hasModifier(JAKSHO_ARMOR)){
            slotContext.entity().getAttribute(Attributes.ARMOR).removeModifier(JAKSHO_ARMOR);
        }
        if(slotContext.entity().getAttribute(ModAttributes.MAGIC_RESIST.get()).hasModifier(JAKSHO_MR)){
            slotContext.entity().getAttribute(ModAttributes.MAGIC_RESIST.get()).removeModifier(JAKSHO_MR);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("After 10 seconds in combat, gain 30% Armor and Magic Resist until the end of combat").withStyle(ChatFormatting.RED));
        pTooltipComponents.add(Component.literal("+3 Hearts").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.literal("+5 Armor").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.literal("+5 Magic Resist").withStyle(ChatFormatting.GOLD));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
