package com.pizzaprince.runeterramod.item.custom.curios.epic;

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

public class RecurveBow extends Item implements ICurioItem {

    private static AttributeModifier RECURVE_BOW_ATTACK_SPEED = new AttributeModifier("recurve_bow_attack_speed",
            0.20, AttributeModifier.Operation.ADDITION);
    public RecurveBow(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(Attributes.ATTACK_SPEED).hasModifier(RECURVE_BOW_ATTACK_SPEED)) {
            slotContext.entity().getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(RECURVE_BOW_ATTACK_SPEED);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(Attributes.ATTACK_SPEED).hasModifier(RECURVE_BOW_ATTACK_SPEED)) {
            slotContext.entity().getAttribute(Attributes.ATTACK_SPEED).removeModifier(RECURVE_BOW_ATTACK_SPEED);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("+0.2 Attack Speed").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
