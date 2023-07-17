package com.pizzaprince.runeterramod.item.custom.curios.epic;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class VampiricScepter extends Item implements ICurioItem {

    private static AttributeModifier VAMPIRIC_SCEPTER_DAMAGE = new AttributeModifier("vampiric_scepter_damage",
            1, AttributeModifier.Operation.ADDITION);
    public VampiricScepter(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(VAMPIRIC_SCEPTER_DAMAGE)) {
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(VAMPIRIC_SCEPTER_DAMAGE);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(VAMPIRIC_SCEPTER_DAMAGE)){
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(VAMPIRIC_SCEPTER_DAMAGE);
        }
    }
}
