package com.pizzaprince.runeterramod.networking.packet;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.client.ClientAbilityData;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.api.layered.modifier.AbstractFadeModifier;
import dev.kosmx.playerAnim.core.util.Ease;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class PlayerAnimationS2CPacket {

    private final UUID uuid;

    private final String anim;

    public PlayerAnimationS2CPacket(UUID id, String animation) {
        this.uuid = id;
        this.anim = animation;
    }

    public PlayerAnimationS2CPacket(FriendlyByteBuf buf) {
        this.uuid = buf.readUUID();
        this.anim = buf.readNbt().getString("animation");
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(this.uuid);
        CompoundTag nbt = new CompoundTag();
        nbt.putString("animation", this.anim);
        buf.writeNbt(nbt);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {

            Player player = Minecraft.getInstance().level.getPlayerByUUID(this.uuid);

            if(player == null) return;



            var animation = (ModifierLayer<IAnimation>) PlayerAnimationAccess.getPlayerAssociatedData((AbstractClientPlayer) player).get(new ResourceLocation(RuneterraMod.MOD_ID, "animation"));
            if (animation != null) {
                //You can set an animation from anywhere ON THE CLIENT
                //Do not attempt to do this on a server, that will only fail
                animation.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation(RuneterraMod.MOD_ID, "animation.player." + this.anim))));

                //You might use  animation.replaceAnimationWithFade(); to create fade effect instead of sudden change
                //See javadoc for details
            }
        });
        return true;
    }
}
