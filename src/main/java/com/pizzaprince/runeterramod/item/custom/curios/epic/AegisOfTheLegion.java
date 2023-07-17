package com.pizzaprince.runeterramod.item.custom.curios.epic;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class AegisOfTheLegion extends Item implements ICurioItem {

    private static AttributeModifier AEGIS_ARMOR = new AttributeModifier("aegis_armor",
            3, AttributeModifier.Operation.ADDITION);
    public AegisOfTheLegion(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(Attributes.ARMOR).hasModifier(AEGIS_ARMOR)) {
            slotContext.entity().getAttribute(Attributes.ARMOR).addTransientModifier(AEGIS_ARMOR);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(Attributes.ARMOR).hasModifier(AEGIS_ARMOR)) {
            slotContext.entity().getAttribute(Attributes.ARMOR).removeModifier(AEGIS_ARMOR);
        }
    }
}
