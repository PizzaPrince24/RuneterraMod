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

public class ImmolationCapabilityProvider implements ICapabilityProvider {

    public static Capability<ImmolationCapability> IMMOLATION_CAPABILITY = CapabilityManager.get(new CapabilityToken<ImmolationCapability>(){});

    public static ResourceLocation IMMOLATION_CAPABILITY_RL = new ResourceLocation(RuneterraMod.MOD_ID, "immolation_capability");

    private final LazyOptional<ImmolationCapability> optional = LazyOptional.of(this::createImmolationCapability);

    private ImmolationCapability capability = null;

    private int damage;

    private int ticks;

    public ImmolationCapabilityProvider(int damage, int ticks){
        this.damage = damage;
        this.ticks = ticks;
    }

    private ImmolationCapability createImmolationCapability() {
        if(this.capability == null) {
            this.capability = new ImmolationCapability(damage, ticks);
        }

        return this.capability;
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == IMMOLATION_CAPABILITY) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }
}
