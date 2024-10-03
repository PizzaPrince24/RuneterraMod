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

public class LivingEntityCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<LivingEntityCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<LivingEntityCapability>() {	});

    private LivingEntityCapability abilities = null;
    private final LazyOptional<LivingEntityCapability> optional = LazyOptional.of(this::createCapability);

    private LivingEntityCapability createCapability() {
        if(this.abilities == null) {
            this.abilities = new LivingEntityCapability();
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
        if(cap == CAPABILITY) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }
}
