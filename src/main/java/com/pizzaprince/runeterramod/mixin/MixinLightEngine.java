package com.pizzaprince.runeterramod.mixin;

import com.pizzaprince.runeterramod.world.dimension.ModDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.lighting.LightEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LightEngine.class)
public class MixinLightEngine {

    @Inject(at = @At("HEAD"), method = "Lnet/minecraft/world/level/lighting/LightEngine;getLightValue(Lnet/minecraft/core/BlockPos;)I", cancellable = true)
    private void renderBright(BlockPos pLevelPos, CallbackInfoReturnable<Integer> cir){
        if(Minecraft.getInstance().level != null && Minecraft.getInstance().level.dimension() == ModDimensions.TURTLE_SHELL_SPACE_DIM) cir.setReturnValue(15);
    }
}
