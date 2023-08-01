package com.pizzaprince.runeterramod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.entity.custom.RampagingBaccaiEntity;
import com.pizzaprince.runeterramod.particle.ModParticles;
import com.pizzaprince.runeterramod.particle.custom.SandParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.joml.Vector3f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RampagingBaccaiRenderer extends GeoEntityRenderer<RampagingBaccaiEntity> {
    public RampagingBaccaiRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new RampagingBaccaiModel());
        this.shadowRadius = 3f;
        this.withScale(8f);
    }

    @Override
    public ResourceLocation getTextureLocation(RampagingBaccaiEntity animatable) {
        return new ResourceLocation(RuneterraMod.MOD_ID, "textures/entity/rampaging_baccai_texture.png");
    }

    @Override
    public void actuallyRender(PoseStack poseStack, RampagingBaccaiEntity animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        if(animatable.getEntityData().get(RampagingBaccaiEntity.enragedData)){
            for(int i = 0; i < 50; i++){
                double randAngle = Math.random()*Math.PI*2;
                double width = animatable.getBbWidth()*2;
                Vec3 pos = new Vec3(animatable.getX() + Math.cos(randAngle)*width*Math.random(), (animatable.getY() + Math.random()*(animatable.getBbHeight()+6))-3,
                        animatable.getZ() + Math.sin(randAngle)*width*Math.random());
                double dirAngle = randAngle + (Math.PI*0.66);
                Vec3 dir = new Vec3((animatable.getX() + Math.cos(dirAngle)*width) - pos.x, 0, (animatable.getZ() + Math.sin(dirAngle)*width) - pos.z).normalize().scale(0.15);
                animatable.level().addParticle(ModParticles.SAND_PARTICLE.get(), true, pos.x + (Math.random()-0.5)*4, pos.y,
                        pos.z + (Math.random()-0.5)*4, dir.x + (Math.random()-0.5)*0.5, dir.y, dir.z + (Math.random()-0.5)*0.5);
            }
        }
    }

    @Override
    protected float getDeathMaxRotation(RampagingBaccaiEntity animatable) {
        return 0f;
    }

    @Override
    public int getPackedOverlay(RampagingBaccaiEntity animatable, float u) {
        return OverlayTexture.pack(OverlayTexture.u(u),
                OverlayTexture.v(animatable.hurtTime > 0));
    }
}
