package com.pizzaprince.runeterramod.block.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pizzaprince.runeterramod.block.entity.custom.ShurimanTransfuserEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

public class ShurimanTransfuserRenderer extends GeoBlockRenderer<ShurimanTransfuserEntity> {
    public ShurimanTransfuserRenderer(BlockEntityRendererProvider.Context context) {
        super(new ShurimanTransfuserModel());

        addRenderLayer(new BlockAndItemGeoLayer<>(this){
            @Nullable
            @Override
            protected ItemStack getStackForBone(GeoBone bone, ShurimanTransfuserEntity animatable) {
                if(bone.getName().equals("itemstack1")){
                    return animatable.getRenderStack(0);
                } else if(bone.getName().equals("itemstack2")){
                    return animatable.getRenderStack(1);
                } else if(bone.getName().equals("itemstack3")){
                    return animatable.getRenderStack(2);
                } else if(bone.getName().equals("itemstack4")){
                    return animatable.getRenderStack(3);
                } else {
                    return null;
                }
            }

            @Override
            protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, ShurimanTransfuserEntity animatable, MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
                if(bone.getName().equals("itemstack1") || bone.getName().equals("itemstack2") || bone.getName().equals("itemstack3") || bone.getName().equals("itemstack4")){
                    poseStack.scale(.12f, .12f, .12f);
                }

                super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
            }
        });
    }
}
