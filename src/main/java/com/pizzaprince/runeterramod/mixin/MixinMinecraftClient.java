package com.pizzaprince.runeterramod.mixin;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.client.ClientAbilityData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(Minecraft.class)
public class MixinMinecraftClient {

    @Shadow
    @Nullable
    public LocalPlayer player;

    @Inject(at = @At("HEAD"), method = "Lnet/minecraft/client/Minecraft;shouldEntityAppearGlowing(Lnet/minecraft/world/entity/Entity;)Z", cancellable = true)
    private void shouldEntityAppearGlowing(Entity pEntity, CallbackInfoReturnable<Boolean> cir){
        if(player != null) {
            player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
                if (pEntity instanceof LivingEntity entity) {
                    if (cap.isCrocodileAscended() && ClientAbilityData.getRage() == 100) {
                        if (player.hasLineOfSight(entity) && player.distanceTo(entity) <= 15) {
                            cir.setReturnValue(true);
                        }
                    }
                }
            });
        }
    }

}
