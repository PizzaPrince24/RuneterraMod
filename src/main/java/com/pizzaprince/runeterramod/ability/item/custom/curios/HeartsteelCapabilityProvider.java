package com.pizzaprince.runeterramod.ability.item.custom.curios;

import com.pizzaprince.runeterramod.RuneterraMod;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HeartsteelCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<HeartsteelCapability> HEARTSTEEL_CAPABILITY = CapabilityManager.get(new CapabilityToken<HeartsteelCapability>(){});

    public static ResourceLocation HEARTSTEEL_CAPABILITY_RL = new ResourceLocation(RuneterraMod.MOD_ID, "heartsteel_capability");

    private final LazyOptional<HeartsteelCapability> optional = LazyOptional.of(this::createCapability);

    private HeartsteelCapability capability = null;

    private HeartsteelCapability createCapability() {
        if(this.capability == null) {
            this.capability = new HeartsteelCapability();
        }

        return this.capability;
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == HEARTSTEEL_CAPABILITY) {
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
