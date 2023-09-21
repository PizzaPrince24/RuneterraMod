package com.pizzaprince.runeterramod.world.dimension;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.ability.AbilityItemCapability;
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

public class ShellDimCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<ShellDimCapability> SHELL_DIM_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

    public static ResourceLocation SHELL_DIM_CAPABILITY_RL = new ResourceLocation(RuneterraMod.MOD_ID, "shell_dim_capability");

    private final LazyOptional<ShellDimCapability> optional = LazyOptional.of(this::createShellDimCapability);

    private ShellDimCapability capability = null;

    private ShellDimCapability createShellDimCapability() {
        if(this.capability == null) {
            this.capability = new ShellDimCapability();
        }

        return this.capability;
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == SHELL_DIM_CAPABILITY) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        createShellDimCapability().serializeNBT(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createShellDimCapability().deserializeNBT(nbt);
    }
}
