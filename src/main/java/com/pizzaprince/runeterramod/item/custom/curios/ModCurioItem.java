package com.pizzaprince.runeterramod.item.custom.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import oshi.util.tuples.Pair;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.UUID;

public class ModCurioItem extends Item implements ICurioItem {

    private List<Pair<Attribute, AttributeModifier>> attributes;

    public ModCurioItem(List<Pair<Attribute, AttributeModifier>> attributes) {
        super(new Item.Properties().stacksTo(1));
        this.attributes = attributes;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = HashMultimap.create();
        attributes.forEach(pair -> {
            modifiers.put(pair.getA(), pair.getB());
        });
        return modifiers;
    }
}
