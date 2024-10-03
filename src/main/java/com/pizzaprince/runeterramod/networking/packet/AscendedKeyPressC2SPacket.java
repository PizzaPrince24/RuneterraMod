package com.pizzaprince.runeterramod.networking.packet;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.ability.ascendent.*;
import com.pizzaprince.runeterramod.effect.ModEffects;
import com.pizzaprince.runeterramod.effect.ModPotions;
import com.pizzaprince.runeterramod.item.ModItems;
import com.pizzaprince.runeterramod.networking.ModPackets;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.UUID;
import java.util.function.Supplier;

public class AscendedKeyPressC2SPacket {

    private final int entityID;

    public AscendedKeyPressC2SPacket(int eID) {
        this.entityID = eID;
    }

    public AscendedKeyPressC2SPacket(FriendlyByteBuf buf) {
        this.entityID = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.entityID);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();
            player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(abilities -> {
                if(abilities.getAscendantType() == AscendantType.CROCODILE){
                    CrocodileAscendant ascendant = (CrocodileAscendant) abilities.getAscendant();
                    if(player.isShiftKeyDown()){
                        if(ascendant.getRage() >= 50){
                            ModPackets.sendToClients(new PlayerAnimationS2CPacket(player.getUUID(), "spin"));
                            ascendant.addRage(-50, player);
                            ascendant.startSpin();
                            UUID randId = UUID.randomUUID();
                            player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(new AttributeModifier(randId,"spin_slow", -0.8, AttributeModifier.Operation.MULTIPLY_TOTAL));
                            ascendant.setSlowUUID(randId);
                        }
                    } else {
                        if(ascendant.getRage() == 100 && this.entityID != -1){
                            if(level.getEntity(this.entityID) instanceof LivingEntity target){
                                ascendant.startRageArt(this.entityID);
                                ModPackets.sendToClients(new PlayerAnimationS2CPacket(player.getUUID(), "rage_art"));
                                ModPackets.sendToPlayer(new RageArtCameraSyncS2CPacket(player, target, true), player);
                                if(target instanceof ServerPlayer player1){
                                    ModPackets.sendToPlayer(new RageArtCameraSyncS2CPacket(player, target, false), player1);
                                }
                                target.addEffect(new MobEffectInstance(ModEffects.STUN.get(), 172, 0, true, true, true));
                                ascendant.addRage(-100, player);
                            }
                        } else if(ascendant.getRage() >= 25){
                            if(abilities.removeTempHitEffect("rage_stun")){
                                abilities.addTempHitEffect("rage_stun", e -> {
                                    e.getEntity().addEffect(new MobEffectInstance(ModEffects.STUN.get(),
                                            100, 1, true, true, true));
                                    e.setAmount(e.getAmount() * 2f);
                                });
                            } else {
                                ascendant.addRage(-25, player);
                                abilities.addTempHitEffect("rage_stun", e -> {
                                    e.getEntity().addEffect(new MobEffectInstance(ModEffects.STUN.get(),
                                            100, 1, true, true, true));
                                    e.setAmount(e.getAmount() * 2f);
                                });
                            }
                        }
                    }
                }
                if(abilities.getAscendantType() == AscendantType.TURTLE){
                    TurtleAscendant ascendant = (TurtleAscendant) abilities.getAscendant();
                    ascendant.changeDimension(player);
                }
                if(abilities.getAscendantType() == AscendantType.SCORPION){
                    ScorpionAscendant ascendant = (ScorpionAscendant) abilities.getAscendant();
                    if(player.getMainHandItem().getItem() instanceof BottleItem && ascendant.getVenom() >= 400){
                        level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
                        ItemStack newStack = PotionUtils.setPotion(new ItemStack(Items.POTION), ModPotions.SCORPION_POISON.get());
                        newStack.getTag().putInt("CustomPotionColor", 6950317);
                        ItemUtils.createFilledResult(player.getMainHandItem(), player, newStack);
                        ascendant.addVenom(-400);
                    } else if(entityID != -1) {
                        ascendant.startFreeze(entityID);
                    } else if(ascendant.getVenom() >= ascendant.venomForSelectedPoison()){
                        abilities.addTempHitEffect("scorpion_custom_poison", event -> {
                            if(event.getSource().getEntity() instanceof Player player1){
                                player1.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
                                    if(cap.getAscendantType() == AscendantType.SCORPION){
                                        ScorpionAscendant as = (ScorpionAscendant) cap.getAscendant();
                                        as.applySelectedPoisonEffectsToEntity(event.getEntity(), player1);
                                    }
                                });
                            }
                        });
                        ascendant.addVenom(-ascendant.venomForSelectedPoison());
                    }
                }
            });
        });
        return true;
    }

}
