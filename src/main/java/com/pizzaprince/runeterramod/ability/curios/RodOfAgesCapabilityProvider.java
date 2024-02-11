package com.pizzaprince.runeterramod.ability.curios;

import com.pizzaprince.runeterramod.RuneterraMod;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RodOfAgesCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<RodOfAgesCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<RodOfAgesCapability>(){});

    public static ResourceLocation CAPABILITY_RL = new ResourceLocation(RuneterraMod.MOD_ID, "rod_of_ages_capability");

    private final LazyOptional<RodOfAgesCapability> optional = LazyOptional.of(this::createCapability);

    private RodOfAgesCapability capability = null;

    private RodOfAgesCapability createCapability() {
        if(this.capability == null) {
            this.capability = new RodOfAgesCapability();
        }

        return this.capability;
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == CAPABILITY) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createCapability().serializeNBT(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createCapability().deserializeNBT(nbt);
    }
}
