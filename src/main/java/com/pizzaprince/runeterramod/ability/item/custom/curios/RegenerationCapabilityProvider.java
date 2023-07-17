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

public class RegenerationCapabilityProvider implements ICapabilityProvider {

    public static Capability<RegenerationCapability> REGENERATION_CAPABILITY = CapabilityManager.get(new CapabilityToken<RegenerationCapability>(){});

    public static ResourceLocation REGENERATION_CAPABILITY_RL = new ResourceLocation(RuneterraMod.MOD_ID, "regeneration_capability");

    private final LazyOptional<RegenerationCapability> optional = LazyOptional.of(this::createCapability);

    private RegenerationCapability capability = null;

    private int healAmount;

    private int timeToHeal;

    public RegenerationCapabilityProvider(int amount, int ticks){
        this.healAmount = amount;
        this.timeToHeal = ticks;
    }

    private RegenerationCapability createCapability() {
        if(this.capability == null) {
            this.capability = new RegenerationCapability(healAmount, timeToHeal);
        }

        return this.capability;
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == REGENERATION_CAPABILITY) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }
}
