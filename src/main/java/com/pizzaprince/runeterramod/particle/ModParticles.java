package com.pizzaprince.runeterramod.particle;

import com.mojang.serialization.Decoder;
import com.pizzaprince.runeterramod.RuneterraMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, RuneterraMod.MOD_ID);

    public static final RegistryObject<SimpleParticleType> SAND_PARTICLE = PARTICLE_TYPES.register("sand_particle", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus){
        PARTICLE_TYPES.register(eventBus);
    }
}
