package com.pizzaprince.runeterramod.block.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.cache.object.GeoCube;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

public class SunDiskAltarRenderer extends GeoBlockRenderer<SunDiskAltarEntity> {

    protected GeoBone smallDisk;

    public SunDiskAltarRenderer(BlockEntityRendererProvider.Context context) {
        super(new SunDiskAltarModel());

        addRenderLayer(new BlockAndItemGeoLayer<>(this) {
            @Nullable
            @Override
            protected ItemStack getStackForBone(GeoBone bone, SunDiskAltarEntity animatable) {
                if(bone.getName().equals("smallDisk")){
                    return animatable.getRenderStack();
                } else {
                    return null;
                }
            }

            @Override
            protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, SunDiskAltarEntity animatable,
                                              MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
                if(bone.getName().equals("smallDisk")){
                    poseStack.scale(.12f, .12f, .12f);
                    poseStack.translate(0, 0.6, 0);
                }
                super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
            }

            @Override
            public void renderForBone(PoseStack poseStack, SunDiskAltarEntity animatable, GeoBone bone, RenderType renderType,
                                      MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
                if(SunDiskAltarEntity.hasRecipe(animatable)){
                    if(bone.getName().equals("disks")){
                        bone.getChildBones().forEach(disk -> {
                            if(disk.getName().equals("disk1")) {
                                double x = animatable.getBlockPos().getX() + 0.5 + disk.getPosX() / 16 + Math.sin(bone.getRotY());
                                double y = animatable.getBlockPos().getY() + 0.5 + disk.getPosY() / 16;
                                double z = animatable.getBlockPos().getZ() + 0.5 + disk.getPosZ() / 16 + Math.cos(bone.getRotY());
                                double dx = animatable.getBlockPos().getX() + 0.5 - x;
                                double dy = animatable.getBlockPos().getY() + 0.5 - y;
                                double dz = animatable.getBlockPos().getZ() + 0.5 - z;
                                animatable.getLevel().addParticle(ParticleTypes.GLOW, true, x, y, z, dx, dy, dz);
                            }
                        });
                    }
                }
                super.renderForBone(poseStack, animatable, bone, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);
            }
        });
    }

    @Override
    public void preRender(PoseStack poseStack, SunDiskAltarEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer,
                          boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);

        this.smallDisk = model.getBone("smallDisk").get();
    }
}
