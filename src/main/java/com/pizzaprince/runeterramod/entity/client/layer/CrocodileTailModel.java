package com.pizzaprince.runeterramod.entity.client.layer;
// Made with Blockbench 4.8.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.util.KeyBinding;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import oshi.util.tuples.Pair;
import virtuoel.pehkui.api.ScaleEasings;

import java.util.List;

// Made with Blockbench 4.8.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class CrocodileTailModel<T extends LivingEntity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(RuneterraMod.MOD_ID, "crocodiletailmodel"), "main");
    private final ModelPart bipedHead;
    private final ModelPart bipedBody;
    private final ModelPart bipedRightArm;
    private final ModelPart bipedLeftArm;
    private final ModelPart bipedRightLeg;
    private final ModelPart bipedLeftLeg;
    private final ModelPart tail1;
    private final float tail1length = 8f;
    private final ModelPart tail2;
    private final float tail23length = 10f;
    private final ModelPart tail3;
    private final ModelPart tail4;
    private final float tail4length = 9f;
    private final ModelPart tail5;
    private final float tail5length = 7.75f;
    private final ModelPart tail6;
    private final float tail6length = 6.5f;
    private final ModelPart tail7;
    private final float tail7length = 5.25f;

    private final List<Pair<ModelPart, Float>> tailList;

    public CrocodileTailModel(ModelPart root) {
        this.bipedHead = root.getChild("bipedHead");
        this.bipedBody = root.getChild("bipedBody");
        this.bipedRightArm = root.getChild("bipedRightArm");
        this.bipedLeftArm = root.getChild("bipedLeftArm");
        this.bipedRightLeg = root.getChild("bipedRightLeg");
        this.bipedLeftLeg = root.getChild("bipedLeftLeg");
        this.tail1 = bipedBody.getChild("armorBody").getChild("tail").getChild("tail1");
        this.tail2 = tail1.getChild("tail2");
        this.tail3 = tail2.getChild("tail3");
        this.tail4 = tail3.getChild("tail4");
        this.tail5 = tail4.getChild("tail5");
        this.tail6 = tail5.getChild("tail6");
        this.tail7 = tail6.getChild("tail7");
        this.tailList = List.of(new Pair<>(tail1, tail1length), new Pair<>(tail2, tail23length), new Pair<>(tail3, tail23length), new Pair<>(tail4, tail4length),
                new Pair<>(tail5, tail5length), new Pair<>(tail6, tail6length), new Pair<>(tail7, tail7length));
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bipedHead = partdefinition.addOrReplaceChild("bipedHead", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition armorHead = bipedHead.addOrReplaceChild("armorHead", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bipedBody = partdefinition.addOrReplaceChild("bipedBody", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition armorBody = bipedBody.addOrReplaceChild("armorBody", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tail = armorBody.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition anchor5_r1 = tail.addOrReplaceChild("anchor5_r1", CubeListBuilder.create().texOffs(24, 25).addBox(-2.45F, 1.5F, -0.3F, 2.0F, 1.0F, 0.1F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -17.6386F, 1.2092F, 1.1158F, 0.1586F, 0.3123F));

        PartDefinition anchor4_r1 = tail.addOrReplaceChild("anchor4_r1", CubeListBuilder.create().texOffs(4, 26).addBox(0.45F, 1.5F, -0.3F, 2.0F, 1.0F, 0.1F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -17.6386F, 1.2092F, 1.1158F, -0.1586F, -0.3123F));

        PartDefinition anchor3_r1 = tail.addOrReplaceChild("anchor3_r1", CubeListBuilder.create().texOffs(40, 41).addBox(-1.75F, -1.525F, 2.25F, 0.5F, 1.5F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(53, 17).addBox(1.25F, -1.525F, 2.25F, 0.5F, 1.5F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(20, 41).addBox(-3.75F, -3.525F, -2.0F, 7.5F, 5.0F, 4.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -14.246F, 2.8535F, 1.0908F, 0.0F, 0.0F));

        PartDefinition tail1 = tail.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(24, 25).addBox(-4.0F, -4.0F, -1.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(52, 20).addBox(2.25F, -4.5F, 1.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(30, 51).addBox(-0.25F, -4.5F, 1.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(12, 51).addBox(-0.25F, -4.5F, 3.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(6, 51).addBox(-0.25F, -4.5F, 5.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(16, 52).addBox(2.25F, -4.5F, 3.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(52, 14).addBox(2.25F, -4.5F, 5.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(52, 5).addBox(-2.75F, -4.5F, 1.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(52, 8).addBox(-2.75F, -4.5F, 3.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(52, 11).addBox(-2.75F, -4.5F, 5.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 3.0F));

        PartDefinition tail1_15_r1 = tail1.addOrReplaceChild("tail1_15_r1", CubeListBuilder.create().texOffs(0, 4).addBox(-1.0F, -0.5F, 3.0F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 3).addBox(-1.0F, -0.5F, 1.75F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 2).addBox(-1.0F, -0.5F, 0.5F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(-1.0F, -0.5F, -0.75F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.0F, -0.5F, -2.0F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.8F, -3.9F, 2.75F, 0.0F, 0.0F, -0.4363F));

        PartDefinition tail1_10_r1 = tail1.addOrReplaceChild("tail1_10_r1", CubeListBuilder.create().texOffs(4, 0).addBox(-1.0F, -0.5F, 3.0F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 1).addBox(-1.0F, -0.5F, 4.25F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 2).addBox(-1.0F, -0.5F, 5.5F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 3).addBox(-1.0F, -0.5F, 6.75F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 4).addBox(-1.0F, -0.5F, 8.0F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.8F, -3.9F, -2.25F, 0.0F, 0.0F, 0.4363F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -3.5F, -2.0F, 7.0F, 7.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(51, 1).addBox(2.0F, -4.0F, 6.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(0, 51).addBox(2.0F, -4.0F, 4.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(50, 47).addBox(2.0F, -4.0F, 2.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(50, 44).addBox(2.0F, -4.0F, 0.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(16, 49).addBox(-2.5F, -4.0F, 0.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(20, 50).addBox(-2.5F, -4.0F, 4.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(26, 50).addBox(-2.5F, -4.0F, 2.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(50, 41).addBox(-2.5F, -4.0F, 6.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(48, 13).addBox(-0.25F, -4.0F, 0.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(48, 22).addBox(-0.25F, -4.0F, 4.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(48, 25).addBox(-0.25F, -4.0F, 2.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(48, 28).addBox(-0.25F, -4.0F, 6.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.25F, 7.0F));

        PartDefinition tail2_25_r1 = tail2.addOrReplaceChild("tail2_25_r1", CubeListBuilder.create().texOffs(4, 20).addBox(-0.5F, 0.0F, -0.5F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 20).addBox(-0.5F, 0.0F, 0.75F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 19).addBox(-0.5F, 0.0F, 2.0F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 19).addBox(-0.5F, 0.0F, 3.25F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 18).addBox(-0.5F, 0.0F, 4.5F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 18).addBox(-0.5F, 0.0F, 5.75F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.425F, -3.55F, 1.0F, 0.0F, 0.0F, -0.4363F));

        PartDefinition tail2_19_r1 = tail2.addOrReplaceChild("tail2_19_r1", CubeListBuilder.create().texOffs(0, 21).addBox(-1.5F, 0.0F, -0.5F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 21).addBox(-1.5F, 0.0F, 0.75F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 22).addBox(-1.5F, 0.0F, 2.0F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 22).addBox(-1.5F, 0.0F, 3.25F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(21, 22).addBox(-1.5F, 0.0F, 4.5F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 23).addBox(-1.5F, 0.0F, 5.75F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.425F, -3.55F, 1.0F, 0.0F, 0.0F, 0.4363F));

        PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(0, 17).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(12, 48).addBox(1.5F, -3.5F, 6.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(48, 10).addBox(1.5F, -3.5F, 4.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(48, 7).addBox(1.5F, -3.5F, 2.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(6, 48).addBox(1.5F, -3.5F, 0.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(44, 12).addBox(-2.0F, -3.5F, 0.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(44, 9).addBox(-2.0F, -3.5F, 2.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(44, 6).addBox(-2.0F, -3.5F, 4.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(44, 3).addBox(-2.0F, -3.5F, 6.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(37, 1).addBox(-0.25F, -3.5F, 0.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(40, 4).addBox(-0.25F, -3.5F, 2.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(41, 0).addBox(-0.25F, -3.5F, 4.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(42, 22).addBox(-0.25F, -3.5F, 6.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.25F, 8.0F));

        PartDefinition tail3_25_r1 = tail3.addOrReplaceChild("tail3_25_r1", CubeListBuilder.create().texOffs(4, 7).addBox(0.0F, 0.0F, -0.5F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 7).addBox(0.0F, 0.0F, 0.75F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 6).addBox(0.0F, 0.0F, 2.0F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 6).addBox(0.0F, 0.0F, 3.25F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 5).addBox(0.0F, 0.0F, 4.5F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 5).addBox(0.0F, 0.0F, 5.75F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.8F, -3.0F, 1.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition tail3_19_r1 = tail3.addOrReplaceChild("tail3_19_r1", CubeListBuilder.create().texOffs(4, 8).addBox(-2.0F, 0.0F, -0.5F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(-2.0F, 0.0F, -3.0F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 9).addBox(-2.0F, 0.0F, -1.75F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 9).addBox(-2.0F, 0.0F, 0.75F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 17).addBox(-2.0F, 0.0F, 2.0F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 17).addBox(-2.0F, 0.0F, 3.25F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.8F, -3.0F, 3.5F, 0.0F, 0.0F, 0.5236F));

        PartDefinition tail4 = tail3.addOrReplaceChild("tail4", CubeListBuilder.create().texOffs(25, 8).addBox(-2.5F, -2.5F, -2.0F, 5.0F, 5.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(48, 4).addBox(0.75F, -3.0F, 4.75F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(0, 48).addBox(0.75F, -3.0F, 2.75F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(47, 0).addBox(0.75F, -3.0F, 0.75F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(0, 45).addBox(-1.25F, -3.0F, 4.75F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(6, 45).addBox(-1.25F, -3.0F, 2.75F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(12, 45).addBox(-1.25F, -3.0F, 0.75F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.25F, 8.0F));

        PartDefinition tail4_17_r1 = tail4.addOrReplaceChild("tail4_17_r1", CubeListBuilder.create().texOffs(4, 23).addBox(-0.5F, 0.0F, -0.5F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(23, 3).addBox(-0.5F, 0.0F, 0.75F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(23, 2).addBox(-0.5F, 0.0F, 2.0F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(23, 1).addBox(-0.5F, 0.0F, 3.25F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(23, 0).addBox(-0.5F, 0.0F, 4.5F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.3F, -2.5F, 1.0F, 0.0F, 0.0F, -0.6109F));

        PartDefinition tail4_12_r1 = tail4.addOrReplaceChild("tail4_12_r1", CubeListBuilder.create().texOffs(23, 4).addBox(-1.5F, 0.0F, -0.5F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(23, 7).addBox(-1.5F, 0.0F, 0.75F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(23, 8).addBox(-1.5F, 0.0F, 2.0F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(23, 9).addBox(-1.5F, 0.0F, 3.25F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(21, 23).addBox(-1.5F, 0.0F, 4.5F, 2.0F, 0.1F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.3F, -2.5F, 1.0F, 0.0F, 0.0F, 0.6109F));

        PartDefinition tail5 = tail4.addOrReplaceChild("tail5", CubeListBuilder.create().texOffs(0, 33).addBox(-2.0F, -2.0F, -1.75F, 4.0F, 4.0F, 7.75F, new CubeDeformation(0.0F))
                .texOffs(36, 22).addBox(-1.25F, -2.5F, 4.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(16, 36).addBox(-1.25F, -2.5F, 0.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(36, 5).addBox(-1.25F, -2.5F, 2.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(33, 0).addBox(0.75F, -2.5F, 0.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(16, 33).addBox(0.75F, -2.5F, 2.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(0, 36).addBox(0.75F, -2.5F, 4.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.25F, 7.0F));

        PartDefinition tail5_18_r1 = tail5.addOrReplaceChild("tail5_18_r1", CubeListBuilder.create().texOffs(2, 24).addBox(-0.15F, 0.1F, -0.1F, 0.3F, 1.9F, 1.1F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 5.75F, -0.5236F, 0.0F, 0.0F));

        PartDefinition tail5_17_r1 = tail5.addOrReplaceChild("tail5_17_r1", CubeListBuilder.create().texOffs(0, 24).addBox(-0.15F, -0.15F, -0.1F, 0.3F, 2.15F, 1.1F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 4.5F, -0.5236F, 0.0F, 0.0F));

        PartDefinition tail5_16_r1 = tail5.addOrReplaceChild("tail5_16_r1", CubeListBuilder.create().texOffs(22, 19).addBox(-0.15F, -0.4F, -0.1F, 0.3F, 2.4F, 1.1F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 3.25F, -0.5236F, 0.0F, 0.0F));

        PartDefinition tail5_15_r1 = tail5.addOrReplaceChild("tail5_15_r1", CubeListBuilder.create().texOffs(22, 23).addBox(-0.15F, -0.65F, -0.1F, 0.3F, 2.65F, 1.1F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 2.0F, -0.5236F, 0.0F, 0.0F));

        PartDefinition tail5_14_r1 = tail5.addOrReplaceChild("tail5_14_r1", CubeListBuilder.create().texOffs(22, 16).addBox(-0.15F, -0.9F, -0.1F, 0.3F, 2.9F, 1.1F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 0.75F, -0.5236F, 0.0F, 0.0F));

        PartDefinition tail5_13_r1 = tail5.addOrReplaceChild("tail5_13_r1", CubeListBuilder.create().texOffs(3, 25).addBox(-0.1F, 0.0F, -0.625F, 1.25F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 24).addBox(-0.1F, 0.0F, -2.125F, 1.75F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(27, 23).addBox(-0.1F, 0.0F, 0.875F, 0.75F, 0.1F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.8F, -2.0F, 2.375F, 0.0F, 0.0F, -0.6109F));

        PartDefinition tail5_10_r1 = tail5.addOrReplaceChild("tail5_10_r1", CubeListBuilder.create().texOffs(4, 24).addBox(-1.65F, 0.0F, -0.625F, 1.75F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 25).addBox(-1.15F, 0.0F, 0.875F, 1.25F, 0.1F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(27, 25).addBox(-0.65F, 0.0F, 2.375F, 0.75F, 0.1F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.8F, -2.0F, 0.875F, 0.0F, 0.0F, 0.6109F));

        PartDefinition tail6 = tail5.addOrReplaceChild("tail6", CubeListBuilder.create().texOffs(37, 43).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 6.5F, new CubeDeformation(0.0F))
                .texOffs(0, 33).addBox(-1.0F, -2.0F, 2.75F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(30, 22).addBox(-1.0F, -2.0F, 0.75F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(24, 22).addBox(0.5F, -2.0F, 0.75F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(28, 7).addBox(0.5F, -2.0F, 2.75F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.25F, 6.0F));

        PartDefinition tail6_11_r1 = tail6.addOrReplaceChild("tail6_11_r1", CubeListBuilder.create().texOffs(28, 6).addBox(-0.1F, 0.25F, -0.5F, 0.2F, 1.55F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 5.35F, -0.5236F, 0.0F, 0.0F));

        PartDefinition tail6_10_r1 = tail6.addOrReplaceChild("tail6_10_r1", CubeListBuilder.create().texOffs(30, 22).addBox(-0.1F, 0.0F, -0.5F, 0.2F, 0.9F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 4.2F, -0.5236F, 0.0F, 0.0F));

        PartDefinition tail6_9_r1 = tail6.addOrReplaceChild("tail6_9_r1", CubeListBuilder.create().texOffs(28, 25).addBox(-0.1F, -0.25F, -0.5F, 0.2F, 1.15F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 3.05F, -0.5236F, 0.0F, 0.0F));

        PartDefinition tail6_8_r1 = tail6.addOrReplaceChild("tail6_8_r1", CubeListBuilder.create().texOffs(26, 25).addBox(-0.1F, -0.5F, -0.5F, 0.2F, 1.4F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 1.9F, -0.5236F, 0.0F, 0.0F));

        PartDefinition tail6_7_r1 = tail6.addOrReplaceChild("tail6_7_r1", CubeListBuilder.create().texOffs(8, 24).addBox(-0.1F, -0.85F, -0.5F, 0.2F, 1.75F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.75F, -0.5236F, 0.0F, 0.0F));

        PartDefinition tail7 = tail6.addOrReplaceChild("tail7", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -1.25F, 2.0F, 2.0F, 5.25F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.25F, 5.0F));

        PartDefinition tail7_4_r1 = tail7.addOrReplaceChild("tail7_4_r1", CubeListBuilder.create().texOffs(24, 25).addBox(-0.1F, 0.1F, -0.5F, 0.2F, 0.8F, 1.275F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, 3.375F, -0.5236F, 0.0F, 0.0F));

        PartDefinition tail7_3_r1 = tail7.addOrReplaceChild("tail7_3_r1", CubeListBuilder.create().texOffs(30, 21).addBox(-0.1F, -0.15F, -0.5F, 0.2F, 1.05F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, 2.225F, -0.5236F, 0.0F, 0.0F));

        PartDefinition tail7_2_r1 = tail7.addOrReplaceChild("tail7_2_r1", CubeListBuilder.create().texOffs(28, 21).addBox(-0.1F, -0.4F, -0.5F, 0.2F, 1.3F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, 1.075F, -0.5236F, 0.0F, 0.0F));

        PartDefinition bipedRightArm = partdefinition.addOrReplaceChild("bipedRightArm", CubeListBuilder.create(), PartPose.offset(-4.0F, 2.0F, 0.0F));

        PartDefinition armorRightArm = bipedRightArm.addOrReplaceChild("armorRightArm", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bipedLeftArm = partdefinition.addOrReplaceChild("bipedLeftArm", CubeListBuilder.create(), PartPose.offset(4.0F, 2.0F, 0.0F));

        PartDefinition armorLeftArm = bipedLeftArm.addOrReplaceChild("armorLeftArm", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bipedRightLeg = partdefinition.addOrReplaceChild("bipedRightLeg", CubeListBuilder.create(), PartPose.offset(-2.0F, 12.0F, 0.0F));

        PartDefinition armorRightLeg = bipedRightLeg.addOrReplaceChild("armorRightLeg", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition armorRightBoot = bipedRightLeg.addOrReplaceChild("armorRightBoot", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bipedLeftLeg = partdefinition.addOrReplaceChild("bipedLeftLeg", CubeListBuilder.create(), PartPose.offset(2.0F, 12.0F, 0.0F));

        PartDefinition armorLeftLeg = bipedLeftLeg.addOrReplaceChild("armorLeftLeg", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition armorLeftBoot = bipedLeftLeg.addOrReplaceChild("armorLeftBoot", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    //x: the more positive the further left relative to the players direction it is
    //y: the more positive the lower it is (negative is up)
    //z: the more positive the further back it is relative to the players direction
    //degrees in radians
    //positions: every 16 units is one block
    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if(entity instanceof Player player && player.hasContainerOpen()) return;
        entity.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
            float rotate = entity.yBodyRotO-entity.yBodyRot;
            int spinTicks = cap.getSpinTicks();
            if(spinTicks >= 0){
                if(tail1.yRot > 0.2){
                    rotate = -7f;
                }
                if(tail1.yRot < 0.2){
                    rotate = 7f;
                }
                if(spinTicks >= 12){
                    if(tail1.yRot > -0.2){
                        rotate = -7f;
                    }
                }
            }
            float angle = 1;
            if(spinTicks<=3){
                angle = (3f - (float)spinTicks) / 3f;
            } else if(spinTicks > 3 && spinTicks <= 20){
                angle = 0f;
            } else if(spinTicks > 20){
                angle = ((float)(25-spinTicks)) / 5f;
            }
            if(spinTicks == -1){
                angle = 1;
            }
            angle = Math.abs(angle);
            this.tail1.xRot = (float)Math.toRadians(-35)*angle;
            this.tail2.xRot = ((float)Math.toRadians(10) + Math.abs(tail2.zRot*0.1f))*angle;
            this.tail3.xRot = ((float)Math.toRadians(13) + Math.abs(tail3.zRot*0.8f))*angle;
            this.tail4.xRot = (float)Math.toRadians(5)*angle;
            this.tail5.xRot = (float)Math.toRadians(3)*angle;

            boolean swimOrFly = entity.getPose() == Pose.FALL_FLYING || entity.getPose() == Pose.SWIMMING;
            double rotLimit = swimOrFly ? Math.PI/10 : Math.PI/4;
            boolean isFalling = entity.getDeltaMovement().y < 0.0785;
            int rageArtTicks = cap.getRageArtTicks();
            if(entity.getDeltaMovement().length() > 0.0785) {
                float m = 0.93f;
                float m1 = 0.10f;
                this.tail1.yRot = (float)Mth.clamp(Math.toRadians(rotate)*m1 + this.tail1.yRot*m, -rotLimit, rotLimit);
                this.tail2.yRot = (float)Mth.clamp(Math.toRadians(rotate)*m1 + this.tail2.yRot*m, -rotLimit, rotLimit);
                this.tail3.yRot = (float)Mth.clamp(Math.toRadians(rotate)*m1 + this.tail3.yRot*m, -rotLimit, rotLimit);
                this.tail4.yRot = (float)Mth.clamp(Math.toRadians(rotate)*m1 + this.tail4.yRot*m, -rotLimit, rotLimit);
                this.tail5.yRot = (float)Mth.clamp(Math.toRadians(rotate)*m1 + this.tail5.yRot*m, -rotLimit, rotLimit);
                this.tail6.yRot = (float)Mth.clamp(Math.toRadians(rotate)*m1 + this.tail6.yRot*m, -rotLimit, rotLimit);
                this.tail7.yRot = (float)Mth.clamp(Math.toRadians(rotate)*m1 + this.tail7.yRot*m, -rotLimit, rotLimit);

                //this.tail1.xRot = this.tail1.xRot - (float)Math.toRadians(entity.getDeltaMovement().y*15);
                //this.tail2.xRot = this.tail2.xRot - (float)Math.toRadians(entity.getDeltaMovement().y*15);
                //this.tail3.xRot = this.tail3.xRot - (float)Math.toRadians(entity.getDeltaMovement().y*15);
                //this.tail4.xRot = this.tail4.xRot - (float)Math.toRadians(entity.getDeltaMovement().y*15);
                //this.tail5.xRot = this.tail5.xRot - (float)Math.toRadians(entity.getDeltaMovement().y*15);
                if(Math.abs(entity.getDeltaMovement().y+0.0785) > 0.001) {
                    this.tail1.xRot = (float) Mth.clamp(this.tail1.xRot - Math.toRadians((entity.getDeltaMovement().y + 0.0784) * 15), Math.toRadians(-55), Math.toRadians(25));
                    this.tail2.xRot = (float) Mth.clamp(this.tail2.xRot - Math.toRadians((entity.getDeltaMovement().y + 0.0784) * 15), Math.toRadians(-15), Math.toRadians(40));
                    this.tail3.xRot = (float) Mth.clamp(this.tail3.xRot - Math.toRadians((entity.getDeltaMovement().y + 0.0784) * 15), Math.toRadians(-15), Math.toRadians(30));
                    this.tail4.xRot = (float) Mth.clamp(this.tail4.xRot - Math.toRadians((entity.getDeltaMovement().y + 0.0784) * 15), Math.toRadians(-15), Math.toRadians(20));
                    this.tail5.xRot = (float) Mth.clamp(this.tail5.xRot - Math.toRadians((entity.getDeltaMovement().y + 0.0784) * 15), Math.toRadians(-15), Math.toRadians(20));
                }
            } else if(Math.abs(rotate) > 0){
                float m = 0.05f;
                float m1 = 0.9975f;
                this.tail1.yRot = (float)Mth.clamp(Math.toRadians(rotate)*m + this.tail1.yRot*m1, -rotLimit, rotLimit);
                this.tail2.yRot = (float)Mth.clamp(Math.toRadians(rotate)*m + this.tail2.yRot*m1, -rotLimit, rotLimit);
                this.tail3.yRot = (float)Mth.clamp(Math.toRadians(rotate)*m + this.tail3.yRot*m1, -rotLimit, rotLimit);
                this.tail4.yRot = (float)Mth.clamp(Math.toRadians(rotate)*m + this.tail4.yRot*m1, -rotLimit, rotLimit);
                this.tail5.yRot = (float)Mth.clamp(Math.toRadians(rotate)*m + this.tail5.yRot*m1, -rotLimit, rotLimit);
                this.tail6.yRot = (float)Mth.clamp(Math.toRadians(rotate)*m + this.tail6.yRot*m1, -rotLimit, rotLimit);
                this.tail7.yRot = (float)Mth.clamp(Math.toRadians(rotate)*m + this.tail7.yRot*m1, -rotLimit, rotLimit);
            } else if(rageArtTicks >= 0){
                double rageArtXRot = 0;
                if(rageArtTicks >= 0 && rageArtTicks < 45){
                    rageArtXRot = -2;
                }
                if(rageArtTicks >= 45 && rageArtTicks < 53){
                    rageArtXRot = 2;
                }
                if(rageArtTicks >= 53 && rageArtTicks < 157){
                    rageArtXRot = -2;
                }
                if(rageArtTicks >= 157){
                    rageArtXRot = 0.5;
                }

                this.tail1.xRot = (float) Mth.clamp(this.tail1.xRot - Math.toRadians(rageArtXRot * 15), Math.toRadians(-55), Math.toRadians(25));
                this.tail2.xRot = (float) Mth.clamp(this.tail2.xRot - Math.toRadians(rageArtXRot * 15), Math.toRadians(-15), Math.toRadians(40));
                this.tail3.xRot = (float) Mth.clamp(this.tail3.xRot - Math.toRadians(rageArtXRot * 15), Math.toRadians(-15), Math.toRadians(30));
                this.tail4.xRot = (float) Mth.clamp(this.tail4.xRot - Math.toRadians(rageArtXRot * 15), Math.toRadians(-15), Math.toRadians(20));
                this.tail5.xRot = (float) Mth.clamp(this.tail5.xRot - Math.toRadians(rageArtXRot * 15), Math.toRadians(-15), Math.toRadians(20));
            }
            float m = 1f;
            this.tail2.zRot = this.tail1.yRot*m;
            this.tail3.zRot = this.tail1.yRot*m*0.75f;
            if(swimOrFly){
                this.tail1.xRot -= (float)Math.toRadians(15);
                this.tail2.xRot = -Math.abs(this.tail2.xRot) - (float)Math.toRadians(10);
                this.tail3.xRot = -Math.abs(this.tail3.xRot) - (float)Math.toRadians(5);
                this.tail4.xRot = -Math.abs(this.tail4.xRot) - (float)Math.toRadians(3);
                this.tail5.xRot = -Math.abs(this.tail5.xRot);
                this.tail2.zRot = (float)Mth.clamp(tail2.zRot, -Math.PI/6, Math.PI/6);
                this.tail3.zRot = (float)Mth.clamp(tail3.zRot, -Math.PI/6, Math.PI/6);
            }
        });
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bipedHead.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bipedBody.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bipedRightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bipedLeftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bipedRightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bipedLeftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    private void setXRotStartup(ModelPart part, float angle, float dof){
        if(part.xRot > Math.toRadians(angle + dof)){
            part.xRot = part.xRot*0.995f;
        } else if(part.xRot < Math.toRadians(angle - dof)){
            part.xRot = part.xRot*1.005f;
        }
    }

    //private Vec3 worldPosition(LivingEntity entity, ModelPart part, int num){
    //    Vec3 pos = entity.position();
    //    for(int i = 0; i < num; i++){
    //        Pair<ModelPart, Float> currentPart = tailList.get(i);
    //        //pos.add(currentPart.getA())
    //    }
    //}
}