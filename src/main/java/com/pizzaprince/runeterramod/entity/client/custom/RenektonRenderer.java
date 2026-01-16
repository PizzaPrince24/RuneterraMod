package com.pizzaprince.runeterramod.entity.client.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pizzaprince.runeterramod.entity.custom.RenektonEntity;
import com.pizzaprince.runeterramod.networking.ModPackets;
import com.pizzaprince.runeterramod.networking.packet.RenektonStompC2SPacket;
import com.pizzaprince.runeterramod.particle.other.GlowParticleData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.level.levelgen.Heightmap;
import org.joml.Vector3f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RenektonRenderer extends GeoEntityRenderer<RenektonEntity> {
    public RenektonRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new RenektonModel());
        this.shadowRadius = 7f;
        this.withScale(8f);
    }


    @Override
    public void postRender(PoseStack poseStack, RenektonEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.postRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        if(animatable.getClientSideStompVar() == 1){
            animatable.setClientSideStompVar(0);
            if(model.getBone("rightfootclaw2").isPresent()){
                GeoBone foot = model.getBone("rightfootclaw2").get();
                Vector3f pos = new Vector3f().set(foot.getWorldPosition().x, foot.getWorldPosition().y, foot.getWorldPosition().z);
                pos.set(pos.x, Minecraft.getInstance().level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, (int)pos.x, (int)pos.z), pos.z);
                //Minecraft.getInstance().level.addParticle(new GlowParticleData(255f, 152f, 0f, 8f), true, pos.x, pos.y, pos.z, 0,0,0);
                ModPackets.sendToServer(new RenektonStompC2SPacket(pos, 12d, 4d, 10f, animatable.getId()));
            }
        }
        if(animatable.getClientSideStompVar() == 2){
            animatable.setClientSideStompVar(0);
            if(model.getBone("leftfootclaw2").isPresent()){
                GeoBone foot = model.getBone("leftfootclaw2").get();
                Vector3f pos = new Vector3f().set(foot.getWorldPosition().x, foot.getWorldPosition().y, foot.getWorldPosition().z);
                pos.set(pos.x, Minecraft.getInstance().level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, (int)pos.x, (int)pos.z), pos.z);
                //Minecraft.getInstance().level.addParticle(new GlowParticleData(255f, 152f, 0f, 8f), true, pos.x, pos.y, pos.z, 0,0,0);
                ModPackets.sendToServer(new RenektonStompC2SPacket(pos, 12d, 4d, 10f, animatable.getId()));
            }
        }
    }
}
