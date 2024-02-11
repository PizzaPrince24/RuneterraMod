package com.pizzaprince.runeterramod.item.custom.curios.base;

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

public class LongSword extends Item implements ICurioItem {

    private static AttributeModifier LONG_SWORD_DAMAGE = new AttributeModifier("long_sword_damage",
            0.5, AttributeModifier.Operation.ADDITION);
    public LongSword(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(LONG_SWORD_DAMAGE)) {
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(LONG_SWORD_DAMAGE);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(LONG_SWORD_DAMAGE)){
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(LONG_SWORD_DAMAGE);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("+0.5 Attack Damage").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
