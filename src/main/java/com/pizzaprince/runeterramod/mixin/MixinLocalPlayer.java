package com.pizzaprince.runeterramod.mixin;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.client.ClientAbilityData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
public class MixinLocalPlayer {
    @Inject(at = @At("HEAD"), method = "Lnet/minecraft/client/player/LocalPlayer;isControlledCamera()Z", cancellable = true)
    private void isControlledCamera(CallbackInfoReturnable<Boolean> cir){
        if(ClientAbilityData.getRageArtTicks() >= 0){
            cir.setReturnValue(true);
        }
    }
}
