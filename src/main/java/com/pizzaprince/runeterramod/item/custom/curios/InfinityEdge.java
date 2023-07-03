package com.pizzaprince.runeterramod.item.custom.curios;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class InfinityEdge extends Item implements ICurioItem {

    private static AttributeModifier INFINITY_EDGE_DAMAGE = new AttributeModifier("infinity_edge_damage",
            4, AttributeModifier.Operation.ADDITION);
    public InfinityEdge(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(INFINITY_EDGE_DAMAGE)) {
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(INFINITY_EDGE_DAMAGE);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(INFINITY_EDGE_DAMAGE)){
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(INFINITY_EDGE_DAMAGE);
        }
    }

}
