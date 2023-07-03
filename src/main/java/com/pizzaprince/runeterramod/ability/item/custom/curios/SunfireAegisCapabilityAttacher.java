package com.pizzaprince.runeterramod.ability.item.custom.curios;

import com.pizzaprince.runeterramod.ability.IAbilityItem;
import com.pizzaprince.runeterramod.ability.item.AbilityItemCapability;
import com.pizzaprince.runeterramod.ability.item.AbilityItemCapabilityAttacher;
import com.pizzaprince.runeterramod.item.custom.curios.SunfireAegis;
import dev._100media.capabilitysyncer.core.CapabilityAttacher;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;

import javax.annotation.Nullable;

public class SunfireAegisCapabilityAttacher extends CapabilityAttacher {

    private static final Class<SunfireAegisCapability> CAPABILITY_CLASS = SunfireAegisCapability.class;
    public static final Capability<SunfireAegisCapability> SUNFIRE_AEGIS_ITEM_CAPABILITY = getCapability(new CapabilityToken<SunfireAegisCapability>() {});
    public static final ResourceLocation SUNFIRE_AEGIS_ITEM_CAPABILITY_RL = new ResourceLocation("ability_item", "ability_item_capability");

    @SuppressWarnings("ConstantConditions")
    @Nullable
    public static SunfireAegisCapability getAbilityItemCapabilityUnwrap(ItemStack itemStack) {
        return getAbilityItemCapability(itemStack).orElse(null);
    }

    public static LazyOptional<SunfireAegisCapability> getAbilityItemCapability(ItemStack itemStack) {
        return itemStack.getCapability(SUNFIRE_AEGIS_ITEM_CAPABILITY);
    }

    private static void attach(AttachCapabilitiesEvent<ItemStack> event, ItemStack itemStack) {
        if(itemStack.getItem() instanceof SunfireAegis) {
            genericAttachCapability(event, new SunfireAegisCapability(itemStack), SUNFIRE_AEGIS_ITEM_CAPABILITY, SUNFIRE_AEGIS_ITEM_CAPABILITY_RL);
        }
    }

    public static void register() {
        CapabilityAttacher.registerCapability(CAPABILITY_CLASS);
        CapabilityAttacher.registerItemStackAttacher(SunfireAegisCapabilityAttacher::attach, SunfireAegisCapabilityAttacher::getAbilityItemCapability);
    }
}
