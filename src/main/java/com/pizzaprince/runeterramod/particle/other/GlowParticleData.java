package com.pizzaprince.runeterramod.particle.other;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.pizzaprince.runeterramod.particle.ModParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.registries.ForgeRegistries;

public class GlowParticleData implements ParticleOptions {

    public static final Codec<GlowParticleData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Codec.FLOAT.fieldOf("r").forGetter(d -> d.r),
                    Codec.FLOAT.fieldOf("g").forGetter(d -> d.g),
                    Codec.FLOAT.fieldOf("b").forGetter(d -> d.b),
                    Codec.FLOAT.fieldOf("size").forGetter(d -> d.size)
            )
            .apply(instance, GlowParticleData::new));

    public static final ParticleOptions.Deserializer<GlowParticleData> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        @Override
        public GlowParticleData fromCommand(ParticleType<GlowParticleData> type, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            return new GlowParticleData(reader.readFloat(), reader.readFloat(), reader.readFloat(), reader.readFloat());
        }

        @Override
        public GlowParticleData fromNetwork(ParticleType<GlowParticleData> type, FriendlyByteBuf buffer) {
            return new GlowParticleData(buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
        }
    };

    public float r;
    public float g;
    public float b;
    public float size;

    public GlowParticleData(float red, float green, float blue, float size){
        this.r = red;
        this.g = green;
        this.b = blue;
        this.size = size;
    }

    @Override
    public ParticleType<?> getType() {
        return ModParticles.GLOW_PARTICLE.get();
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf pBuffer) {
        pBuffer.writeFloat(r);
        pBuffer.writeFloat(g);
        pBuffer.writeFloat(b);
        pBuffer.writeFloat(size);
    }

    @Override
    public String writeToString() {
        return String.format("%s %.2f %.2f %.2f %.2f", ForgeRegistries.PARTICLE_TYPES.getKey(this.getType()), this.r, this.g, this.b, this.size);
    }
}
