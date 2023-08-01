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

public class GiantsBelt extends Item implements ICurioItem {

    private static AttributeModifier GIANTS_BELT_HEALTH = new AttributeModifier("giants_belt_health",
            3, AttributeModifier.Operation.ADDITION);
    public GiantsBelt(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(Attributes.MAX_HEALTH).hasModifier(GIANTS_BELT_HEALTH)) {
            slotContext.entity().getAttribute(Attributes.MAX_HEALTH).addTransientModifier(GIANTS_BELT_HEALTH);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(Attributes.MAX_HEALTH).hasModifier(GIANTS_BELT_HEALTH)){
            slotContext.entity().getAttribute(Attributes.MAX_HEALTH).removeModifier(GIANTS_BELT_HEALTH);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("+1.5 Hearts").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
