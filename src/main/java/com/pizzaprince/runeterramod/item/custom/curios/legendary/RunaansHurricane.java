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

public class RunaansHurricane extends Item implements ICurioItem {

    private static AttributeModifier RUNAAN_ATTACK_SPEED = new AttributeModifier("runaans_hurricane_attack_speed",
            0.4, AttributeModifier.Operation.ADDITION);

    private static AttributeModifier RUNAAN_CRIT_CHANCE = new AttributeModifier("runaans_hurricane_crit_chance",
            0.2, AttributeModifier.Operation.ADDITION);

    private static AttributeModifier RUNAAN_MOVE_SPEED = new AttributeModifier("runaans_hurricane_move_speed",
            0.07, AttributeModifier.Operation.MULTIPLY_TOTAL);
    public RunaansHurricane(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(Attributes.ATTACK_SPEED).hasModifier(RUNAAN_ATTACK_SPEED)) {
            slotContext.entity().getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(RUNAAN_ATTACK_SPEED);
        }
        if(!slotContext.entity().getAttribute(ModAttributes.CRIT_CHANCE.get()).hasModifier(RUNAAN_CRIT_CHANCE)) {
            slotContext.entity().getAttribute(ModAttributes.CRIT_CHANCE.get()).addTransientModifier(RUNAAN_CRIT_CHANCE);
        }
        if(!slotContext.entity().getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(RUNAAN_MOVE_SPEED)) {
            slotContext.entity().getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(RUNAAN_MOVE_SPEED);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(Attributes.ATTACK_SPEED).hasModifier(RUNAAN_ATTACK_SPEED)){
            slotContext.entity().getAttribute(Attributes.ATTACK_SPEED).removeModifier(RUNAAN_ATTACK_SPEED);
        }
        if(!slotContext.entity().getAttribute(ModAttributes.CRIT_CHANCE.get()).hasModifier(RUNAAN_CRIT_CHANCE)) {
            slotContext.entity().getAttribute(ModAttributes.CRIT_CHANCE.get()).addTransientModifier(RUNAAN_CRIT_CHANCE);
        }
        if(!slotContext.entity().getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(RUNAAN_MOVE_SPEED)) {
            slotContext.entity().getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(RUNAAN_MOVE_SPEED);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("Fires 2 additional homing arrows from bows").withStyle(ChatFormatting.RED));
        pTooltipComponents.add(Component.literal("+0.4 Attack Speed").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.literal("+20% Crit Chance").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.literal("+7% Movement Speed").withStyle(ChatFormatting.GOLD));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
