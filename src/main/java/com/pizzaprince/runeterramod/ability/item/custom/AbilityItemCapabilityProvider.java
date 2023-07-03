package com.pizzaprince.runeterramod.ability.item.custom;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.ability.item.custom.curios.SunfireAegisCapability;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AbilityItemCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<AbilityItemCapability> ABILITY_ITEM_CAPABILITY = CapabilityManager.get(new CapabilityToken<AbilityItemCapability>(){});

    public static ResourceLocation ABILITY_ITEM_CAPABILITY_RL = new ResourceLocation(RuneterraMod.MOD_ID, "ability_item_capability");

    private final LazyOptional<AbilityItemCapability> optional = LazyOptional.of(this::createAbilityItemCapability);

    private AbilityItemCapability capability = null;

    private ItemStack item;

    public AbilityItemCapabilityProvider(ItemStack stack){
        this.item = stack;
    }

    private AbilityItemCapability createAbilityItemCapability() {
        if(this.capability == null) {
            this.capability = new AbilityItemCapability(item);
        }

        return this.capability;
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ABILITY_ITEM_CAPABILITY) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        createAbilityItemCapability().serializeNBT(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createAbilityItemCapability().deserializeNBT(nbt);
    }
}
