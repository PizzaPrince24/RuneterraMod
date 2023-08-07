package com.pizzaprince.runeterramod.entity.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class CrocodileTailRenderLayer <T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private final CrocodileTailModel<T> tailModel;

    private static final ResourceLocation TAIL_LOCATION = new ResourceLocation(RuneterraMod.MOD_ID, "textures/entity/layer/crocodile_tail.png");
    public CrocodileTailRenderLayer(RenderLayerParent<T, M> pRenderer, EntityModelSet pModelSet) {
        super(pRenderer);
        this.tailModel = new CrocodileTailModel<>(pModelSet.bakeLayer(CrocodileTailModel.LAYER_LOCATION));
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        pLivingEntity.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
            if(cap.isCrocodileAscended()){
                pPoseStack.pushPose();
                pPoseStack.translate(0.0F, 0.0F, 0.125F);
                this.getParentModel().copyPropertiesTo(this.tailModel);
                this.tailModel.setupAnim(pLivingEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
                VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.entitySolid(TAIL_LOCATION));
                //VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(pBuffer, RenderType.armorCutoutNoCull(resourcelocation), false, itemstack.hasFoil());
                this.tailModel.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                pPoseStack.popPose();
            }
        });
    }
}
