package com.pizzaprince.runeterramod.entity.client.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pizzaprince.runeterramod.entity.custom.projectile.CasterMinionProjectile;
import com.pizzaprince.runeterramod.particle.other.GlowParticleData;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class CasterMinionProjectileRenderer extends EntityRenderer<CasterMinionProjectile> {
    public CasterMinionProjectileRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public void render(CasterMinionProjectile pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pEntity.level().addParticle(new GlowParticleData(0f, 0f, 255f, 0.6f), true, pEntity.getX(), pEntity.getY(), pEntity.getZ(), 0,0,0);
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(CasterMinionProjectile pEntity) {
        return null;
    }
}
