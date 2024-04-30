package com.pizzaprince.runeterramod.mixin;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.ability.ascendent.AscendantType;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class MixinPlayer {
    @Inject(at = @At("HEAD"), method = "Lnet/minecraft/world/entity/player/Player;tryToStartFallFlying()Z", cancellable = true)
    private void eagleFlight(CallbackInfoReturnable<Boolean> cir){
        Player player = (Player) (Object)this;
        if (!player.onGround() && !player.isInWater() && !player.hasEffect(MobEffects.LEVITATION)) {
            player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
                if (cap.getAscendantType() == AscendantType.EAGLE) {
                    player.startFallFlying();
                    cir.setReturnValue(true);
                }
            });
        }
    }
}
