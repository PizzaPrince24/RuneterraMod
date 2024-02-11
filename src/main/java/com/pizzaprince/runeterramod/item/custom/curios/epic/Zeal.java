package com.pizzaprince.runeterramod.item.custom.curios.epic;

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

public class Zeal extends Item implements ICurioItem {

    private static AttributeModifier ZEAL_ATTACK_SPEED = new AttributeModifier("zeal_attack_speed",
            0.15, AttributeModifier.Operation.ADDITION);

    private static AttributeModifier ZEAL_CRIT_CHANCE = new AttributeModifier("zeal_crit_chance",
            0.15, AttributeModifier.Operation.ADDITION);

    private static AttributeModifier ZEAL_SPEED = new AttributeModifier("zeal_speed",
            0.05, AttributeModifier.Operation.MULTIPLY_TOTAL);
    public Zeal(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(Attributes.ATTACK_SPEED).hasModifier(ZEAL_ATTACK_SPEED)) {
            slotContext.entity().getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(ZEAL_ATTACK_SPEED);
        }
        if(!slotContext.entity().getAttribute(ModAttributes.CRIT_CHANCE.get()).hasModifier(ZEAL_CRIT_CHANCE)) {
            slotContext.entity().getAttribute(ModAttributes.CRIT_CHANCE.get()).addTransientModifier(ZEAL_CRIT_CHANCE);
        }
        if(!slotContext.entity().getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(ZEAL_SPEED)) {
            slotContext.entity().getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(ZEAL_SPEED);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(Attributes.ATTACK_SPEED).hasModifier(ZEAL_ATTACK_SPEED)) {
            slotContext.entity().getAttribute(Attributes.ATTACK_SPEED).removeModifier(ZEAL_ATTACK_SPEED);
        }
        if(slotContext.entity().getAttribute(ModAttributes.CRIT_CHANCE.get()).hasModifier(ZEAL_CRIT_CHANCE)) {
            slotContext.entity().getAttribute(ModAttributes.CRIT_CHANCE.get()).removeModifier(ZEAL_CRIT_CHANCE);
        }
        if(slotContext.entity().getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(ZEAL_SPEED)) {
            slotContext.entity().getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(ZEAL_SPEED);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("+15% Critical Hit Chance").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.literal("+0.15 Attack Speed").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.literal("+5% Movement Speed").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
