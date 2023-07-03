package com.pizzaprince.runeterramod.ability.item;

import com.pizzaprince.runeterramod.ability.IAbilityItem;
import dev._100media.capabilitysyncer.core.CapabilityAttacher;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;

import javax.annotation.Nullable;

public class AbilityItemCapabilityAttacher extends CapabilityAttacher {

    private static final Class<AbilityItemCapability> CAPABILITY_CLASS = AbilityItemCapability.class;
    public static final Capability<AbilityItemCapability> ABILITY_ITEM_CAPABILITY = getCapability(new CapabilityToken<AbilityItemCapability>() {});
    public static final ResourceLocation ABILITY_ITEM_CAPABILITY_RL = new ResourceLocation("ability_item", "ability_item_capability");

    @SuppressWarnings("ConstantConditions")
    @Nullable
    public static AbilityItemCapability getAbilityItemCapabilityUnwrap(ItemStack itemStack) {
        return getAbilityItemCapability(itemStack).orElse(null);
    }

    public static LazyOptional<AbilityItemCapability> getAbilityItemCapability(ItemStack itemStack) {
        return itemStack.getCapability(ABILITY_ITEM_CAPABILITY);
    }

    private static void attach(AttachCapabilitiesEvent<ItemStack> event, ItemStack itemStack) {
        if(itemStack.getItem() instanceof IAbilityItem) {
            genericAttachCapability(event, new AbilityItemCapability(itemStack), ABILITY_ITEM_CAPABILITY, ABILITY_ITEM_CAPABILITY_RL);
        }
    }

    public static void register() {
        CapabilityAttacher.registerCapability(CAPABILITY_CLASS);
        CapabilityAttacher.registerItemStackAttacher(AbilityItemCapabilityAttacher::attach, AbilityItemCapabilityAttacher::getAbilityItemCapability);
    }
}
