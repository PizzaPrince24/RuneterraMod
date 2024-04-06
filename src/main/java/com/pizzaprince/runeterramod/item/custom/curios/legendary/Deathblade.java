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

public class Deathblade extends Item implements ICurioItem {

    private static AttributeModifier DEATHBLADE_DAMAGE = new AttributeModifier("deathblade_damage",
            4, AttributeModifier.Operation.ADDITION);

    private static AttributeModifier DEATHBLADE_DAMAGE_MULT = new AttributeModifier("deathblade_damage_multiplied",
            0.35, AttributeModifier.Operation.MULTIPLY_TOTAL);
    public Deathblade(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(DEATHBLADE_DAMAGE)) {
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(DEATHBLADE_DAMAGE);
        }
        if(!slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(DEATHBLADE_DAMAGE_MULT)) {
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(DEATHBLADE_DAMAGE_MULT);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(DEATHBLADE_DAMAGE)){
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(DEATHBLADE_DAMAGE);
        }
        if(slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(DEATHBLADE_DAMAGE_MULT)){
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(DEATHBLADE_DAMAGE_MULT);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("+4 Attack Damage").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.literal("+35% Attack Damage").withStyle(ChatFormatting.GOLD));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
