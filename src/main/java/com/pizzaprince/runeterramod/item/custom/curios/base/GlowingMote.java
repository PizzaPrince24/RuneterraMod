package com.pizzaprince.runeterramod.item.custom.curios.base;

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

public class GlowingMote extends Item implements ICurioItem {

    private static AttributeModifier GLOWING_MOTE_ABILITY_HASTE = new AttributeModifier("glowing_mote_ability_haste",
            5, AttributeModifier.Operation.ADDITION);
    public GlowingMote(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(ModAttributes.ABILITY_HASTE.get()).hasModifier(GLOWING_MOTE_ABILITY_HASTE)) {
            slotContext.entity().getAttribute(ModAttributes.ABILITY_HASTE.get()).addTransientModifier(GLOWING_MOTE_ABILITY_HASTE);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(ModAttributes.ABILITY_HASTE.get()).hasModifier(GLOWING_MOTE_ABILITY_HASTE)){
            slotContext.entity().getAttribute(ModAttributes.ABILITY_HASTE.get()).removeModifier(GLOWING_MOTE_ABILITY_HASTE);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("+5 Ability Haste").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
