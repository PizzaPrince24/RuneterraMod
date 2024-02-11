package com.pizzaprince.runeterramod.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
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
import org.objectweb.asm.Opcodes;
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

    @ModifyVariable(method = "Lnet/minecraft/client/renderer/LevelRenderer;renderSnowAndRain(Lnet/minecraft/client/renderer/LightTexture;FDDD)V", at = @At("STORE"), ordinal = 0)
    private Biome.Precipitation renderSandstorm(Biome.Precipitation before){
        return minecraft.level.getBiome(minecraft.cameraEntity.blockPosition()).is(ModBiomes.SHURIMAN_DESERT) ||
                minecraft.level.getBiome(minecraft.cameraEntity.blockPosition()).is(ModBiomes.SHURIMAN_WASTELAND) ? Biome.Precipitation.SNOW : before;
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

    @ModifyArg(method = "renderSnowAndRain",
        at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V"),
        index = 1)
    private ResourceLocation renderSandstorm(int pShaderTexture, ResourceLocation rl){
        return minecraft.level.getBiome(minecraft.cameraEntity.blockPosition()).is(ModBiomes.SHURIMAN_DESERT) ||
                minecraft.level.getBiome(minecraft.cameraEntity.blockPosition()).is(ModBiomes.SHURIMAN_WASTELAND) ?
                SAND_LOCATION : rl;
    }


}
