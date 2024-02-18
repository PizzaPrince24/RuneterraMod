package com.pizzaprince.runeterramod.block.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pizzaprince.runeterramod.block.entity.custom.ShurimanTransfuserEntity;
import com.pizzaprince.runeterramod.block.entity.custom.SunForgeEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

public class SunForgeRenderer extends GeoBlockRenderer<SunForgeEntity> {
    public SunForgeRenderer(BlockEntityRendererProvider.Context context) {
        super(new SunForgeModel());

        addRenderLayer(new BlockAndItemGeoLayer<>(this){
            @Nullable
            @Override
            protected ItemStack getStackForBone(GeoBone bone, SunForgeEntity animatable) {
                if(bone.getName().equals("itemstack1")){
                    if(animatable.getRenderStack(0).isEmpty()){
                        return animatable.getRenderStack(2);
                    }
                    return animatable.getRenderStack(0);
                } else if(bone.getName().equals("itemstack2")){
                    return animatable.getRenderStack(1);
                } else {
                    return null;
                }
            }

            @Override
            protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, SunForgeEntity animatable, MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
                if(bone.getName().equals("itemstack1") || bone.getName().equals("itemstack2")){
                    poseStack.scale(.2f, .2f, .2f);
                }
                super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
            }
        });
    }
}
