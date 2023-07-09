package com.pizzaprince.runeterramod.ability.item.custom.curios;

import com.pizzaprince.runeterramod.RuneterraMod;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WarmogsCapabilityProvider implements ICapabilityProvider {

    public static Capability<WarmogsCapability> WARMOGS_CAPABILITY = CapabilityManager.get(new CapabilityToken<WarmogsCapability>(){});

    public static ResourceLocation WARMOGS_CAPABILITY_RL = new ResourceLocation(RuneterraMod.MOD_ID, "warmogs_capability");

    private final LazyOptional<WarmogsCapability> optional = LazyOptional.of(this::createCapability);

    private WarmogsCapability capability = null;

    private WarmogsCapability createCapability() {
        if(this.capability == null) {
            this.capability = new WarmogsCapability();
        }

        return this.capability;
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == WARMOGS_CAPABILITY) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }
}
