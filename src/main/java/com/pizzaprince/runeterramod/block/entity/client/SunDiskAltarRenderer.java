package com.pizzaprince.runeterramod.block.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3d;
import com.mojang.math.Vector3f;
import com.pizzaprince.runeterramod.block.custom.SunDiskAltar;
import com.pizzaprince.runeterramod.block.entity.custom.SunDiskAltarEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class SunDiskAltarRenderer extends GeoBlockRenderer<SunDiskAltarEntity> {
    public SunDiskAltarRenderer(BlockEntityRendererProvider.Context rendererProvider) {
        super(rendererProvider, new SunDiskAltarModel());
    }

    @Override
    public RenderType getRenderType(SunDiskAltarEntity animatable, float partialTick, PoseStack poseStack,
                                    @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer,
                                    int packedLight, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
/*
    @Override
    public void render(SunDiskAltarEntity tile, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack itemStack = tile.getRenderStack();
        poseStack.pushPose();
        poseStack.translate(0.5f, 0.65f, 0.5f);
        poseStack.scale(0.25f, 0.25f, 0.25f);
        poseStack.mulPose(Vector3f.XP.rotationDegrees(90));

        switch (tile.getBlockState().getValue(SunDiskAltar.FACING)) {
            case NORTH -> poseStack.mulPose(Vector3f.ZP.rotationDegrees(0));
            case EAST -> poseStack.mulPose(Vector3f.ZP.rotationDegrees(90));
            case SOUTH -> poseStack.mulPose(Vector3f.ZP.rotationDegrees(180));
            case WEST -> poseStack.mulPose(Vector3f.ZP.rotationDegrees(270));
        }

        itemRenderer.renderStatic(itemStack, ItemTransforms.TransformType.GUI, getLightLevel(tile.getLevel(),
                        tile.getBlockPos()),
                OverlayTexture.NO_OVERLAY, poseStack, bufferSource, 1);
        poseStack.popPose();
        super.render(tile, partialTick, poseStack, bufferSource, packedLight);
    }

 */

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }

    @Override
    public void render(GeoModel model, SunDiskAltarEntity animatable, float partialTick, RenderType type, PoseStack poseStack,
                       @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack itemStack = animatable.getRenderStack();
        model.getBone("smallDisk").ifPresent(smallDisk -> {
            poseStack.pushPose();
            poseStack.translate(smallDisk.getPositionX(), .5 + smallDisk.getPositionY() / 7, smallDisk.getPositionZ());
            poseStack.scale(0.25f, 0.25f, 0.25f);
            //poseStack.mulPose(Vector3f.XP.rotationDegrees(90));
/*
            switch (animatable.getBlockState().getValue(SunDiskAltar.FACING)) {
                case NORTH -> poseStack.mulPose(Vector3f.ZP.rotationDegrees(0));
                case EAST -> poseStack.mulPose(Vector3f.ZP.rotationDegrees(90));
                case SOUTH -> poseStack.mulPose(Vector3f.ZP.rotationDegrees(180));
                case WEST -> poseStack.mulPose(Vector3f.ZP.rotationDegrees(270));
            }

 */
            itemRenderer.renderStatic(itemStack, ItemTransforms.TransformType.GUI, getLightLevel(animatable.getLevel(),
                            animatable.getBlockPos()),
                    OverlayTexture.NO_OVERLAY, poseStack, bufferSource, 1);
            poseStack.popPose();

            if(animatable.hasRecipe(animatable)){
                model.getBone("disks").ifPresent(parentBone -> {
                    parentBone.childBones.forEach(bigDisk -> {
                        animatable.getLevel().addParticle(ParticleTypes.BUBBLE,
                                bigDisk.getWorldPosition().x + bigDisk.getPositionX() / 8,
                                bigDisk.getWorldPosition().y + bigDisk.getPositionY() / 8 + .5,
                                bigDisk.getWorldPosition().z + bigDisk.getPositionZ() / 8,
                                0, 0, 0);
                        System.out.println(parentBone.getRotationX());
                    });
                });
            }
        });

        super.render(model, animatable, partialTick, type, poseStack, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
