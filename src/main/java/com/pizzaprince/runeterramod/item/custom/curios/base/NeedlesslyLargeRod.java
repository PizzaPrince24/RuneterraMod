package com.pizzaprince.runeterramod.item.custom.curios.base;

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

public class NeedlesslyLargeRod extends Item implements ICurioItem {

    private static AttributeModifier NEEDLESSLY_LARGE_ROD_DAMAGE = new AttributeModifier("needlessly_large_rod_damage",
            3.5, AttributeModifier.Operation.ADDITION);
    public NeedlesslyLargeRod(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(ModAttributes.ABILITY_POWER.get()).hasModifier(NEEDLESSLY_LARGE_ROD_DAMAGE)) {
            slotContext.entity().getAttribute(ModAttributes.ABILITY_POWER.get()).addTransientModifier(NEEDLESSLY_LARGE_ROD_DAMAGE);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(ModAttributes.ABILITY_POWER.get()).hasModifier(NEEDLESSLY_LARGE_ROD_DAMAGE)){
            slotContext.entity().getAttribute(ModAttributes.ABILITY_POWER.get()).removeModifier(NEEDLESSLY_LARGE_ROD_DAMAGE);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("+3.5 Ability Power").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
