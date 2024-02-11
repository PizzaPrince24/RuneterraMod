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

public class JeweledGauntlet extends Item implements ICurioItem {

    private static AttributeModifier JEWELED_GAUNTLET_ABILITY_POWER = new AttributeModifier("jeweled_gauntlet_ability_power",
            4, AttributeModifier.Operation.ADDITION);

    private static AttributeModifier JEWELED_GAUNTLET_CRIT_CHANCE = new AttributeModifier("jeweled_gauntlet_crit_chance",
            0.2, AttributeModifier.Operation.ADDITION);

    private static AttributeModifier JEWELED_GAUNTLET_CRIT_DAMAGE = new AttributeModifier("jeweled_gauntlet_crit_damage",
            0.4, AttributeModifier.Operation.ADDITION);
    public JeweledGauntlet(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(ModAttributes.ABILITY_POWER.get()).hasModifier(JEWELED_GAUNTLET_ABILITY_POWER)) {
            slotContext.entity().getAttribute(ModAttributes.ABILITY_POWER.get()).addTransientModifier(JEWELED_GAUNTLET_ABILITY_POWER);
        }
        if(!slotContext.entity().getAttribute(ModAttributes.CRIT_CHANCE.get()).hasModifier(JEWELED_GAUNTLET_CRIT_CHANCE)) {
            slotContext.entity().getAttribute(ModAttributes.CRIT_CHANCE.get()).addTransientModifier(JEWELED_GAUNTLET_CRIT_CHANCE);
        }
        if(!slotContext.entity().getAttribute(ModAttributes.CRIT_DAMAGE.get()).hasModifier(JEWELED_GAUNTLET_CRIT_DAMAGE)) {
            slotContext.entity().getAttribute(ModAttributes.CRIT_DAMAGE.get()).addTransientModifier(JEWELED_GAUNTLET_CRIT_DAMAGE);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(ModAttributes.ABILITY_POWER.get()).hasModifier(JEWELED_GAUNTLET_ABILITY_POWER)){
            slotContext.entity().getAttribute(ModAttributes.ABILITY_POWER.get()).removeModifier(JEWELED_GAUNTLET_ABILITY_POWER);
        }
        if(!slotContext.entity().getAttribute(ModAttributes.CRIT_CHANCE.get()).hasModifier(JEWELED_GAUNTLET_CRIT_CHANCE)) {
            slotContext.entity().getAttribute(ModAttributes.CRIT_CHANCE.get()).addTransientModifier(JEWELED_GAUNTLET_CRIT_CHANCE);
        }
        if(!slotContext.entity().getAttribute(ModAttributes.CRIT_DAMAGE.get()).hasModifier(JEWELED_GAUNTLET_CRIT_DAMAGE)) {
            slotContext.entity().getAttribute(ModAttributes.CRIT_DAMAGE.get()).addTransientModifier(JEWELED_GAUNTLET_CRIT_DAMAGE);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("+4 Ability Power").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.literal("+20% Critical Hit Chance").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.literal("+40% Critical Hit Damage").withStyle(ChatFormatting.GOLD));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
