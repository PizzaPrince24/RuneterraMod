package com.pizzaprince.runeterramod.mixin;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.particle.ModParticles;
import com.pizzaprince.runeterramod.world.biome.ModBiomes;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LevelRenderer.class)
public class MixinLevelRenderer {

    @Shadow
    @Final
    private Minecraft minecraft;

    private static final ResourceLocation SAND_LOCATION = new ResourceLocation(RuneterraMod.MOD_ID, "textures/environment/sandstorm.png");
    @ModifyArg(method = "tickRain",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"),
        index = 0)
    private ParticleOptions renderSandOnGround(ParticleOptions particleoptions, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed){
        return minecraft.level.getBiome(new BlockPos((int)x, (int)y, (int)z)).is(ModBiomes.SHURIMAN_DESERT) ||
                minecraft.level.getBiome(new BlockPos((int)x, (int)y, (int)z)).is(ModBiomes.SHURIMAN_WASTELAND) ?
                ModParticles.SAND_PARTICLE.get() : particleoptions;
    }

    @Inject(method = "Lnet/minecraft/client/renderer/LevelRenderer;tickRain(Lnet/minecraft/client/Camera;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;playLocalSound(Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V"),
            cancellable = true)
    private void nullifyRainSound(Camera pCamera, CallbackInfo ci){
        if(minecraft.level.getBiome(minecraft.cameraEntity.blockPosition()).is(ModBiomes.SHURIMAN_DESERT) ||
                minecraft.level.getBiome(minecraft.cameraEntity.blockPosition()).is(ModBiomes.SHURIMAN_WASTELAND)){
            ci.cancel();
        }
    }

    @ModifyVariable(method = "Lnet/minecraft/client/renderer/LevelRenderer;renderSnowAndRain(Lnet/minecraft/client/renderer/LightTexture;FDDD)V", at = @At("STORE"), ordinal = 3)
    private float moveSandTest(float before){
        return minecraft.level.getBiome(minecraft.cameraEntity.blockPosition()).is(ModBiomes.SHURIMAN_DESERT) ||
                minecraft.level.getBiome(minecraft.cameraEntity.blockPosition()).is(ModBiomes.SHURIMAN_WASTELAND) ?
                before*2f : before;
    }

    @ModifyVariable(method = "Lnet/minecraft/client/renderer/LevelRenderer;renderSnowAndRain(Lnet/minecraft/client/renderer/LightTexture;FDDD)V", at = @At("STORE"), ordinal = 2)
    private float moveSandTest2(float before){
        return minecraft.level.getBiome(minecraft.cameraEntity.blockPosition()).is(ModBiomes.SHURIMAN_DESERT) ||
                minecraft.level.getBiome(minecraft.cameraEntity.blockPosition()).is(ModBiomes.SHURIMAN_WASTELAND) ?
                before*20f : before;
    }

    /*
    @ModifyVariable(method = "Lnet/minecraft/client/renderer/LevelRenderer;renderSnowAndRain(Lnet/minecraft/client/renderer/LightTexture;FDDD)V", at = @At("STORE"), ordinal = 15)
    private int moveSandTest(int before){
        return minecraft.level.getBiome(minecraft.cameraEntity.blockPosition()).is(ModBiomes.SHURIMAN_DESERT) ||
                minecraft.level.getBiome(minecraft.cameraEntity.blockPosition()).is(ModBiomes.SHURIMAN_WASTELAND) ?
                before*2 : before;
    }

     */

    /*
    @ModifyArg(method = "renderSnowAndRain",
            at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;uv(FF)Lcom/mojang/blaze3d/vertex/VertexConsumer;"),
            index = 0)
    private float yes(float pU, float pV){
        return pV;
    }

     */

    /*
    @Inject(method = "renderSnowAndRain",
            at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;begin(Lcom/mojang/blaze3d/vertex/VertexFormat$Mode;Lcom/mojang/blaze3d/vertex/VertexFormat;)V", ordinal = 1),
            locals = LocalCapture.CAPTURE_FAILHARD)
    private void renderSandstorm(LightTexture pLightTexture, float pPartialTick, double pCamX, double pCamY, double pCamZ, CallbackInfo ci, float f,
                                 Level level, int i, int j, int k, Tesselator tesselator, BufferBuilder bufferbuilder, int l, int i1, float f1,
                                 BlockPos.MutableBlockPos blockpos$mutableblockpos, int j1, int k1, int l1, double d0, double d1, Biome biome, int i2,
                                 int j2, int k2, int l2, RandomSource randomsource, Biome.Precipitation biome$precipitation, int i3, float f3, double d2,
                                 double d4, float f3, float f4, int j3, float f5, float f6, float f7, double d3, double d5, float f8, float f9, )


     */
    @ModifyArg(method = "renderSnowAndRain",
        at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V"),
        index = 1)
    private ResourceLocation renderSandstorm(int pShaderTexture, ResourceLocation rl){
        return minecraft.level.getBiome(minecraft.cameraEntity.blockPosition()).is(ModBiomes.SHURIMAN_DESERT) ||
                minecraft.level.getBiome(minecraft.cameraEntity.blockPosition()).is(ModBiomes.SHURIMAN_WASTELAND) ?
                SAND_LOCATION : rl;
    }


}
