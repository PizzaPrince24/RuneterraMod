package com.pizzaprince.runeterramod.ability.curios;

import com.pizzaprince.runeterramod.RuneterraMod;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JakShoCapabilityProvider implements ICapabilityProvider {
    public static Capability<JakShoCapability> JAKSHO_CAPABILITY = CapabilityManager.get(new CapabilityToken<JakShoCapability>(){});

    public static ResourceLocation JAKSHO_CAPABILITY_RL = new ResourceLocation(RuneterraMod.MOD_ID, "jaksho_capability");

    private final LazyOptional<JakShoCapability> optional = LazyOptional.of(this::createCapability);

    private JakShoCapability capability = null;

    private JakShoCapability createCapability() {
        if(this.capability == null) {
            this.capability = new JakShoCapability();
        }

        return this.capability;
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == JAKSHO_CAPABILITY) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }
}
