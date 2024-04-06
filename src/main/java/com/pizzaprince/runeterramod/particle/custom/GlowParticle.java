package com.pizzaprince.runeterramod.particle.custom;

import com.pizzaprince.runeterramod.particle.ModParticleRenderTypes;
import com.pizzaprince.runeterramod.particle.other.GlowParticleData;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class GlowParticle extends TextureSheetParticle {

    public float initScale;
    public float initAlpha;
    public GlowParticle(GlowParticleData data, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, SpriteSet pSprites) {
        super(pLevel, pX, pY, pZ, 0, 0, 0);
        this.hasPhysics = false;
        this.lifetime = 36;
        this.quadSize = 0;
        this.xd = pXSpeed;
        this.yd = pYSpeed;
        this.zd = pZSpeed;
        this.pickSprite(pSprites);
        initScale = data.size;
        initAlpha = 1f;
        this.setColor(data.r/255f, data.g/255f, data.b/255f);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ModParticleRenderTypes.EMBER_RENDER;
    }

    @Override
    public void tick() {
        super.tick();

        if (level.random.nextInt(6) == 0) {
            this.age++;
        }
        float f = (float) this.age / (float) this.lifetime;
        this.quadSize = initScale - initScale * f;
        this.alpha = initAlpha * (1.0f - f);

        this.oRoll = roll;
        roll += 1.0f;
    }

    @Override
    public boolean isAlive() {
        return this.age < this.lifetime;
    }

    @Override
    protected int getLightColor(float pPartialTick) {
        return 255;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<GlowParticleData> {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(GlowParticleData pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            Particle particle = new GlowParticle(pType, pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, sprites);
            return particle;
        }
    }
}
