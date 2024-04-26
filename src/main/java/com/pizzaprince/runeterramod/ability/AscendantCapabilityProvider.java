package com.pizzaprince.runeterramod.ability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AscendantCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<AscendantCapability> ASCENDENT_CAPABILITY = CapabilityManager.get(new CapabilityToken<AscendantCapability>() {	});

    private AscendantCapability abilities = null;
    private final LazyOptional<AscendantCapability> optional = LazyOptional.of(this::createCapability);

    private AscendantCapability createCapability() {
        if(this.abilities == null) {
            this.abilities = new AscendantCapability();
        }

        return this.abilities;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createCapability().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createCapability().loadNBTData(nbt);
    }

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ASCENDENT_CAPABILITY) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }
}
