package com.pizzaprince.runeterramod.item.custom.curios.base;

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

public class ClothArmor extends Item implements ICurioItem {

    private static AttributeModifier CLOTH_ARMOR_ARMOR = new AttributeModifier("cloth_armor_armor",
            1, AttributeModifier.Operation.ADDITION);
    public ClothArmor(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(Attributes.ARMOR).hasModifier(CLOTH_ARMOR_ARMOR)) {
            slotContext.entity().getAttribute(Attributes.ARMOR).addTransientModifier(CLOTH_ARMOR_ARMOR);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(Attributes.ARMOR).hasModifier(CLOTH_ARMOR_ARMOR)) {
            slotContext.entity().getAttribute(Attributes.ARMOR).removeModifier(CLOTH_ARMOR_ARMOR);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("+1 Armor").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
