package com.pizzaprince.runeterramod.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.ability.ascendent.AscendantType;
import com.pizzaprince.runeterramod.ability.ascendent.EagleAscendant;
import com.pizzaprince.runeterramod.client.ClientAbilityData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LocalPlayer.class)
public class MixinLocalPlayer {
    @Inject(at = @At("HEAD"), method = "Lnet/minecraft/client/player/LocalPlayer;isControlledCamera()Z", cancellable = true)
    private void isControlledCamera(CallbackInfoReturnable<Boolean> cir) {
        if (ClientAbilityData.getRageArtTicks() >= 0) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "Lnet/minecraft/client/player/LocalPlayer;aiStep()V",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/player/LocalPlayer;wasFallFlying:Z", opcode = Opcodes.PUTFIELD))
    private void freeFly(CallbackInfo ci){
        LocalPlayer player = (LocalPlayer) (Object) this;
        if (player.input.jumping && !player.getAbilities().flying && !player.isPassenger() && !player.onClimbable() && !player.onGround() && !player.isInWater() && !player.hasEffect(MobEffects.LEVITATION)) {
            player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
                if(cap.getAscendantType() == AscendantType.EAGLE){
                    EagleAscendant ascendant = (EagleAscendant) cap.getAscendant();
                    if(ascendant.getFlyTicks() == 0){
                        player.startFallFlying();
                        player.connection.send(new ServerboundPlayerCommandPacket(player, ServerboundPlayerCommandPacket.Action.START_FALL_FLYING));
                    }
                }
            });
        }
    }
}
