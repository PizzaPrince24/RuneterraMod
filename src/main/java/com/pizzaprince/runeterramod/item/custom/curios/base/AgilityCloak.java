package com.pizzaprince.runeterramod.item.custom.curios.base;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.effect.ModAttributes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class AgilityCloak extends Item implements ICurioItem {

    private static AttributeModifier AGILITY_CLOAK_CRIT = new AttributeModifier("agility_cloak_crit",
            0.15, AttributeModifier.Operation.ADDITION);
    public AgilityCloak(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(ModAttributes.CRIT_CHANCE.get()).hasModifier(AGILITY_CLOAK_CRIT)) {
            slotContext.entity().getAttribute(ModAttributes.CRIT_CHANCE.get()).addTransientModifier(AGILITY_CLOAK_CRIT);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(ModAttributes.CRIT_CHANCE.get()).hasModifier(AGILITY_CLOAK_CRIT)){
            slotContext.entity().getAttribute(ModAttributes.CRIT_CHANCE.get()).removeModifier(AGILITY_CLOAK_CRIT);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("+15% Critical Hit Chance").withStyle(ChatFormatting.GOLD));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
