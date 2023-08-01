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

public class VampiricScepter extends Item implements ICurioItem {

    private static AttributeModifier VAMPIRIC_SCEPTER_DAMAGE = new AttributeModifier("vampiric_scepter_damage",
            1, AttributeModifier.Operation.ADDITION);
    public VampiricScepter(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(VAMPIRIC_SCEPTER_DAMAGE)) {
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(VAMPIRIC_SCEPTER_DAMAGE);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(VAMPIRIC_SCEPTER_DAMAGE)){
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(VAMPIRIC_SCEPTER_DAMAGE);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("+5% Life Steal").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.literal("+1 Attack Damage").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
