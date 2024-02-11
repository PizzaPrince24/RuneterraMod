package com.pizzaprince.runeterramod.item.custom.curios.legendary;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
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

public class Opportunity extends Item implements ICurioItem {

    private static AttributeModifier OPPORTUNITY_LETHALITY = new AttributeModifier("opportunity_lethality",
            3, AttributeModifier.Operation.ADDITION);

    private static AttributeModifier OPPORTUNITY_DAMAGE = new AttributeModifier("opportunity_damage",
            3, AttributeModifier.Operation.ADDITION);

    private static AttributeModifier OPPORTUNITY_MOVE_SPEED = new AttributeModifier("opportunity_move_speed",
            0.05, AttributeModifier.Operation.MULTIPLY_TOTAL);
    public Opportunity(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(ModAttributes.LETHALITY.get()).hasModifier(OPPORTUNITY_LETHALITY)) {
            slotContext.entity().getAttribute(ModAttributes.LETHALITY.get()).addTransientModifier(OPPORTUNITY_LETHALITY);
        }
        if(!slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(OPPORTUNITY_DAMAGE)) {
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(OPPORTUNITY_DAMAGE);
        }
        if(!slotContext.entity().getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(OPPORTUNITY_MOVE_SPEED)) {
            slotContext.entity().getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(OPPORTUNITY_MOVE_SPEED);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(ModAttributes.LETHALITY.get()).hasModifier(OPPORTUNITY_LETHALITY)){
            slotContext.entity().getAttribute(ModAttributes.LETHALITY.get()).removeModifier(OPPORTUNITY_LETHALITY);
        }
        if(slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(OPPORTUNITY_DAMAGE)) {
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(OPPORTUNITY_DAMAGE);
        }
        if(slotContext.entity().getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(OPPORTUNITY_MOVE_SPEED)) {
            slotContext.entity().getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(OPPORTUNITY_MOVE_SPEED);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("Killing any mob will grant Speed II for 3 seconds").withStyle(ChatFormatting.RED));
        pTooltipComponents.add(Component.literal("+3 Attack Damage").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.literal("+3 Lethality").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.literal("+5% Movement Speed").withStyle(ChatFormatting.GOLD));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
