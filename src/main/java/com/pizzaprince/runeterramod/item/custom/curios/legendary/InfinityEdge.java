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

public class InfinityEdge extends Item implements ICurioItem {

    private static AttributeModifier INFINITY_EDGE_DAMAGE = new AttributeModifier("infinity_edge_damage",
            3, AttributeModifier.Operation.ADDITION);

    private static AttributeModifier INFINITY_EDGE_CRIT_CHANCE = new AttributeModifier("infinity_edge_crit_chance",
            0.2, AttributeModifier.Operation.ADDITION);

    private static AttributeModifier INFINITY_EDGE_CRIT_DAMAGE = new AttributeModifier("infinity_edge_crit_damage",
            0.4, AttributeModifier.Operation.ADDITION);
    public InfinityEdge(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(INFINITY_EDGE_DAMAGE)) {
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(INFINITY_EDGE_DAMAGE);
        }
        if(!slotContext.entity().getAttribute(ModAttributes.CRIT_CHANCE.get()).hasModifier(INFINITY_EDGE_CRIT_CHANCE)) {
            slotContext.entity().getAttribute(ModAttributes.CRIT_CHANCE.get()).addTransientModifier(INFINITY_EDGE_CRIT_CHANCE);
        }
        if(!slotContext.entity().getAttribute(ModAttributes.CRIT_DAMAGE.get()).hasModifier(INFINITY_EDGE_CRIT_DAMAGE)) {
            slotContext.entity().getAttribute(ModAttributes.CRIT_DAMAGE.get()).addTransientModifier(INFINITY_EDGE_CRIT_DAMAGE);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(INFINITY_EDGE_DAMAGE)){
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(INFINITY_EDGE_DAMAGE);
        }
        if(slotContext.entity().getAttribute(ModAttributes.CRIT_CHANCE.get()).hasModifier(INFINITY_EDGE_CRIT_CHANCE)){
            slotContext.entity().getAttribute(ModAttributes.CRIT_CHANCE.get()).removeModifier(INFINITY_EDGE_CRIT_CHANCE);
        }
        if(slotContext.entity().getAttribute(ModAttributes.CRIT_DAMAGE.get()).hasModifier(INFINITY_EDGE_CRIT_DAMAGE)){
            slotContext.entity().getAttribute(ModAttributes.CRIT_DAMAGE.get()).removeModifier(INFINITY_EDGE_CRIT_DAMAGE);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("+3 Attack Damage").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.literal("+20% Critical Hit Chance").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.literal("+40% Critical Hit Damage").withStyle(ChatFormatting.GOLD));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
