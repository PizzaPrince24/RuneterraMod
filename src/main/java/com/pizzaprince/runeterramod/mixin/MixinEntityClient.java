package com.pizzaprince.runeterramod.mixin;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.client.ClientAbilityData;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class MixinEntityClient {

    @Shadow
    private int id;

    @Inject(at = @At("HEAD"), method = "Lnet/minecraft/world/entity/Entity;getTeamColor()I", cancellable = true)
    private void getTeamColor(CallbackInfoReturnable<Integer> cir){
        Player player = Minecraft.getInstance().player;
        if(player != null){
            player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
                if(this.id == ClientAbilityData.getLookAtEntityID()){
                    cir.setReturnValue(16711680);
                }
            });
        }

    }

}
