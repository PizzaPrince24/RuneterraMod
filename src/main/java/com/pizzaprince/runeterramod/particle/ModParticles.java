package com.pizzaprince.runeterramod.particle;

import com.mojang.serialization.Codec;
import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.particle.other.GlowParticleData;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, RuneterraMod.MOD_ID);

    public static final RegistryObject<SimpleParticleType> SAND_PARTICLE = PARTICLE_TYPES.register("sand_particle", () -> new SimpleParticleType(true));

    public static final RegistryObject<ParticleType<GlowParticleData>> GLOW_PARTICLE = PARTICLE_TYPES.register("glow_particle",
            () -> new ParticleType<GlowParticleData>(true, GlowParticleData.DESERIALIZER) {
                @Override
                public Codec<GlowParticleData> codec() {
                    return GlowParticleData.CODEC;
                }
            });

    public static void register(IEventBus eventBus){
        PARTICLE_TYPES.register(eventBus);
    }
}
