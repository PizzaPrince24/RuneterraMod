package com.pizzaprince.runeterramod.item.custom.curios.legendary;

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

public class RabadonsDeathcap extends Item implements ICurioItem {

    private static AttributeModifier RABADONS_AP = new AttributeModifier("rabadons_ap",
            7, AttributeModifier.Operation.ADDITION);

    private static AttributeModifier RABADONS_AP_MULT = new AttributeModifier("rabadons_ap_multiplied",
            0.35, AttributeModifier.Operation.MULTIPLY_TOTAL);
    public RabadonsDeathcap(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(ModAttributes.ABILITY_POWER.get()).hasModifier(RABADONS_AP)) {
            slotContext.entity().getAttribute(ModAttributes.ABILITY_POWER.get()).addTransientModifier(RABADONS_AP);
        }
        if(!slotContext.entity().getAttribute(ModAttributes.ABILITY_POWER.get()).hasModifier(RABADONS_AP_MULT)) {
            slotContext.entity().getAttribute(ModAttributes.ABILITY_POWER.get()).addTransientModifier(RABADONS_AP_MULT);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(ModAttributes.ABILITY_POWER.get()).hasModifier(RABADONS_AP)){
            slotContext.entity().getAttribute(ModAttributes.ABILITY_POWER.get()).removeModifier(RABADONS_AP);
        }
        if(slotContext.entity().getAttribute(ModAttributes.ABILITY_POWER.get()).hasModifier(RABADONS_AP_MULT)){
            slotContext.entity().getAttribute(ModAttributes.ABILITY_POWER.get()).removeModifier(RABADONS_AP_MULT);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("+7 Ability Power").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.literal("+35% Ability Power").withStyle(ChatFormatting.GOLD));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
