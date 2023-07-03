package com.pizzaprince.runeterramod.item.custom.curios;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class Rylais extends Item implements ICurioItem {

    private static AttributeModifier RYLAIS_DAMAGE = new AttributeModifier("rylais_damage",
            2, AttributeModifier.Operation.ADDITION);
    public Rylais(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(RYLAIS_DAMAGE)) {
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(RYLAIS_DAMAGE);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(RYLAIS_DAMAGE)){
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(RYLAIS_DAMAGE);
        }
    }

}
