package com.pizzaprince.runeterramod.ability.item.custom.curios;

import com.pizzaprince.runeterramod.RuneterraMod;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RejuvenationBeadCapabilityProvider implements ICapabilityProvider {

    public static Capability<RejuvenationBeadCapability> REJUVENATION_BEAD_CAPABILITY = CapabilityManager.get(new CapabilityToken<RejuvenationBeadCapability>(){});

    public static ResourceLocation REJUVENATION_BEAD_CAPABILITY_RL = new ResourceLocation(RuneterraMod.MOD_ID, "rejuvenation_bead_capability");

    private final LazyOptional<RejuvenationBeadCapability> optional = LazyOptional.of(this::createCapability);

    private RejuvenationBeadCapability capability = null;

    private RejuvenationBeadCapability createCapability() {
        if(this.capability == null) {
            this.capability = new RejuvenationBeadCapability();
        }

        return this.capability;
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == REJUVENATION_BEAD_CAPABILITY) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }
}
