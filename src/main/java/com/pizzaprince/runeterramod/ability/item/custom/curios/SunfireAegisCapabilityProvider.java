package com.pizzaprince.runeterramod.ability.item.custom.curios;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.ability.PlayerAbilities;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SunfireAegisCapabilityProvider implements ICapabilityProvider {

    public static Capability<SunfireAegisCapability> SUNFIRE_AEGIS_CAPABILITY = CapabilityManager.get(new CapabilityToken<SunfireAegisCapability>(){});

    public static ResourceLocation SUNFIRE_AEGIS_CAPABILITY_RL = new ResourceLocation(RuneterraMod.MOD_ID, "sunfire_aegis_capability");

    private final LazyOptional<SunfireAegisCapability> optional = LazyOptional.of(this::createSunfireAegisCapability);

    private SunfireAegisCapability capability = null;

    private SunfireAegisCapability createSunfireAegisCapability() {
        if(this.capability == null) {
            this.capability = new SunfireAegisCapability();
        }

        return this.capability;
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == SUNFIRE_AEGIS_CAPABILITY) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }
}
