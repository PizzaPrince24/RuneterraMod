package com.pizzaprince.runeterramod.item.custom.curios.epic;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class ChainVest extends Item implements ICurioItem {

    private static AttributeModifier CHAIN_VEST_ARMOR = new AttributeModifier("chain_vest_armor",
            2, AttributeModifier.Operation.ADDITION);
    public ChainVest(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(Attributes.ARMOR).hasModifier(CHAIN_VEST_ARMOR)) {
            slotContext.entity().getAttribute(Attributes.ARMOR).addTransientModifier(CHAIN_VEST_ARMOR);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(Attributes.ARMOR).hasModifier(CHAIN_VEST_ARMOR)) {
            slotContext.entity().getAttribute(Attributes.ARMOR).removeModifier(CHAIN_VEST_ARMOR);
        }
    }
}
