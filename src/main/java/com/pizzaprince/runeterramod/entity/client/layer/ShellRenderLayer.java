package com.pizzaprince.runeterramod.entity.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.GeckoLib;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.renderer.GeoObjectRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

import java.util.Optional;

public class ShellRenderLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> implements GeoAnimatable {

    private GeoObjectRenderer shellRenderer;

    AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private HumanoidModel<?> baseModel;

    private RenderLayerParent<T, M> pRenderer;

    boolean retract = false;

    private static final ResourceLocation SHELL_LOCATION = new ResourceLocation(RuneterraMod.MOD_ID, "textures/entity/layer/shell.png");

    private GeoAnimatable animatable;
    public ShellRenderLayer(RenderLayerParent<T, M> pRenderer, EntityModelSet pModelSet) {
        super(pRenderer);
        this.shellRenderer = new GeoObjectRenderer(new ShellModel());
        /*
        animatable = new GeoAnimatable() {

            AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

            public boolean shouldRetract = false;
            @Override
            public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
                controllerRegistrar.add(new AnimationController(this, "controller", 0, state -> {
                    if(shouldRetract){
                        state.setAnimation(RawAnimation.begin().then("animation.shell.retract", Animation.LoopType.PLAY_ONCE));
                    }
                    return PlayState.CONTINUE;
                }));
            }

            public void setShouldRetract(boolean y){
                shouldRetract = y;
            }

            @Override
            public AnimatableInstanceCache getAnimatableInstanceCache() {
                return cache;
            }

            @Override
            public double getTick(Object o) {
                return RenderUtils.getCurrentTick();
            }
        };

         */

        if(pRenderer.getModel() instanceof HumanoidModel<?> model){
            this.baseModel = model;
        }
        this.pRenderer = pRenderer;
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        pLivingEntity.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
            if(cap.isTurtleAscended()){
                retract = cap.isRetracting();
                //if(pRenderer.getModel() instanceof HumanoidModel<?> model) {
                    //ModelPart body = model.body;
                    //Optional<GeoBone> shellOptional = shellRenderer.getGeoModel().getBone("shell");
                    //shellOptional.ifPresent(shell -> {
                    //    RenderUtils.matchModelPartRot(body, shell);
                    //    shell.updatePosition(body.x, -body.y, body.z);
                    //    System.out.println("" + body.x + ", " + body.y + ", " + body.z);
                    //});
                    pPoseStack.translate(-0.55, -1.4, -0.5);
                    pPoseStack.scale(1.1f, 1.1f, 1.1f);
                    VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.entitySolid(SHELL_LOCATION));
                    this.shellRenderer.render(pPoseStack, this, pBuffer, RenderType.entitySolid(SHELL_LOCATION), vertexconsumer, pPackedLight);
                }
            //}
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController(this, "controller", 0, state -> {
            if(retract){
                state.setAnimation(RawAnimation.begin().then("animation.shell.retract", Animation.LoopType.PLAY_ONCE));
            }
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getTick(Object o) {
        return RenderUtils.getCurrentTick();
    }
}
