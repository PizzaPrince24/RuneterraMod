package com.pizzaprince.runeterramod.ability.ascendent;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.effect.ModAttributes;
import com.pizzaprince.runeterramod.effect.ModDamageTypes;
import com.pizzaprince.runeterramod.networking.ModPackets;
import com.pizzaprince.runeterramod.networking.packet.CrocRageS2CPacket;
import com.pizzaprince.runeterramod.networking.packet.RageArtCapSyncS2CPacket;
import com.pizzaprince.runeterramod.networking.packet.RageArtTickSyncS2CPacket;
import com.pizzaprince.runeterramod.particle.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.UUID;

public class CrocodileAscendant extends BaseAscendant{

    private static AttributeModifier HEALTH = new AttributeModifier("crocodile_ascendant_health",
            20, AttributeModifier.Operation.ADDITION);

    private static AttributeModifier DAMAGE = new AttributeModifier("crocodile_ascendant_damage",
            0.5, AttributeModifier.Operation.MULTIPLY_TOTAL);

    private static AttributeModifier LETHALITY = new AttributeModifier("crocodile_ascendant_lethality",
            6, AttributeModifier.Operation.ADDITION);
    private int spinTicks = -1;
    private int rageArtTicks = -1;
    private int rageArtTargetId = -1;
    private boolean canGainRage = true;
    private UUID spinSlow = null;
    private Vec3 rageArtTargetPos;
    private boolean canRageArtCrash = false;
    private int rage;
    private boolean overdrive = false;
    @Override
    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("spinTicks", this.spinTicks);
    }

    @Override
    public void loadNBTData(CompoundTag nbt) {
        spinTicks = nbt.getInt("spinTicks");
    }

    @Override
    public void tick(ServerPlayer player) {
        if(tickCount % 4 == 0 && outOfCombat == 0){
            addRage(-1, player);
        }
        if(rage == 0 && overdrive){
            overdrive = false;
            player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
                cap.removePermaHitEffect("overdrive");
            });
        }
        updateSpin(player);
        updateRageArt(player);
    }

    @Override
    public void onAscend(Player player) {
        if(!player.getAttribute(Attributes.MAX_HEALTH).hasModifier(HEALTH)) {
            player.getAttribute(Attributes.MAX_HEALTH).addTransientModifier(HEALTH);
            player.heal((float)HEALTH.getAmount());
        }
        if(!player.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(DAMAGE)) {
            player.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(DAMAGE);
        }
        if(!player.getAttribute(ModAttributes.LETHALITY.get()).hasModifier(LETHALITY)) {
            player.getAttribute(ModAttributes.LETHALITY.get()).addTransientModifier(LETHALITY);
        }
    }

    @Override
    public void onDescend(Player player) {
        if(player.getAttribute(Attributes.MAX_HEALTH).hasModifier(HEALTH)) {
            player.getAttribute(Attributes.MAX_HEALTH).removeModifier(HEALTH);
        }
        if(player.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(DAMAGE)) {
            player.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(DAMAGE);
        }
        if(player.getAttribute(ModAttributes.LETHALITY.get()).hasModifier(LETHALITY)) {
            player.getAttribute(ModAttributes.LETHALITY.get()).removeModifier(LETHALITY);
        }
    }

    public void addRage(int rage, ServerPlayer player){
        if(rage > 0){
            if(canGainRage){
                this.rage = Mth.clamp(overdrive ? (this.rage+rage)*2 : this.rage+rage, 0, 100);
                ModPackets.sendToPlayer(new CrocRageS2CPacket(this.rage), player);
            }
        } else {
            this.rage = Mth.clamp(this.rage+rage, 0, 100);
            ModPackets.sendToPlayer(new CrocRageS2CPacket(this.rage), player);
        }
    }

    private void updateRageArt(ServerPlayer player){
        if(rageArtTicks >= 0){
            rageArtTicks++;
            canGainRage = false;
            LivingEntity target = (LivingEntity) player.level().getEntity(this.rageArtTargetId);
            if(target == null){
                rageArtTicks = -1;
            }
            if(rageArtTicks == 1){
                this.rageArtTargetPos = target.position();
            }
            if(this.rageArtTargetPos != null && target != null && rageArtTicks < 90){
                target.teleportTo(rageArtTargetPos.x, rageArtTargetPos.y, rageArtTargetPos.z);
            }
            if(rageArtTicks == 90){
                this.canRageArtCrash = true;
            }
            if(player.onGround() && canRageArtCrash){
                float scale = ScaleTypes.BASE.getScaleData(player).getBaseScale();
                player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(7 + ((scale-1)*7))).forEach(entity -> {
                    entity.hurt(ModDamageTypes.getEntityDamageSource(player.level(), ModDamageTypes.RAGE_ART, player), (20 + (float)(player.getAttributeValue(Attributes.ATTACK_DAMAGE)*2))*scale);
                    //entity.knockback(0.5, entity.getX() - player.getX(), entity.getZ() - player.getZ());
                    entity.addDeltaMovement(new Vec3(entity.getX() - player.getX(), 1, entity.getZ() - player.getZ()).normalize().scale(2));
                });

                float radius = 13 + (scale-1)*7;
                float angle = 0f;
                float radiusStep = radius * 0.05f;
                float angleStep = (float)Math.PI / (radius*15);

                while(angle < (Math.PI*2)){
                    for(float r = radius; r > 0; r -= radiusStep){
                        double velY = (Math.cos(Math.min(3*Math.PI, r) / 3.0)*5.0) + 5.0;
                        double x = player.getX() + (Math.cos(angle)*r);
                        double z = player.getZ() + (Math.sin(angle)*r);
                        int y = Minecraft.getInstance().level.getHeight(Heightmap.Types.WORLD_SURFACE, (int)x, (int)z);

                        player.serverLevel().sendParticles(ModParticles.SAND_PARTICLE.get(), x + Math.random()*0.4, y + Math.random()*(radius/2.5),
                                z + Math.random()*0.4, 1, 0, 0, 0, velY / 15.0);
                    }
                    angle += angleStep;
                }
                //ModPackets.sendToNearbyPlayers(new AOEParticleS2CPacket(player.getX(), player.getZ(), 7 + (scale-1)*7), player.serverLevel(), player.getOnPos());
                this.canRageArtCrash = false;
            }
            if(rageArtTicks >= 172){
                this.rageArtTicks = -1;
                this.rageArtTargetId = -1;
                this.canGainRage = true;
                this.overdrive = true;
                player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
                    cap.addPermaHitEffect("overdrive", event -> event.setAmount(event.getAmount()*2));
                });
            }
            ModPackets.sendToPlayer(new RageArtTickSyncS2CPacket(this.rageArtTicks), player);
            ModPackets.sendToClients(new RageArtCapSyncS2CPacket(this.rageArtTicks, player.getId()));
        } else {
            this.rageArtTicks = -1;
            this.rageArtTargetId = -1;
            this.canGainRage = true;
        }
    }

    private void updateSpin(ServerPlayer player){
        if(spinTicks >= 0){
            canGainRage = false;
            spinTicks++;
            if(spinTicks == 2){
                float scale = ScaleTypes.BASE.getScaleData(player).getBaseScale();
                player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(4 + ((scale-1)*2), 0, 4 + ((scale-1)*2))).forEach(target -> {
                    float angle = 140f;
                    float entityHitAngle = (float) ((Math.atan2(target.getZ() - player.getZ(), target.getX() - player.getX()) * (180 / Math.PI) - 90) % 360);
                    float entityAttackingAngle = player.yBodyRot % 360;
                    if (entityHitAngle < 0) {
                        entityHitAngle += 360;
                    }
                    if (entityAttackingAngle < 0) {
                        entityAttackingAngle += 360;
                    }
                    float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
                    if ((entityRelativeAngle <= angle && entityRelativeAngle >= -angle / 2) || (entityRelativeAngle >= 360 - angle || entityRelativeAngle <= -360 + angle / 2)) {
                        target.hurt(player.level().damageSources().playerAttack(player), (float)player.getAttribute(Attributes.ATTACK_DAMAGE).getValue() + 4f);
                        player.heal((float)player.getAttributeValue(Attributes.ATTACK_DAMAGE)*0.5f);
                    }
                });
            }
            if(spinTicks == 13){
                float scale = ScaleTypes.BASE.getScaleData(player).getBaseScale();
                player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(4 + ((scale-1)*2), 0, 4 + ((scale-1)*2))).forEach(target -> {
                    float angle = 140f;
                    float entityHitAngle = (float) ((Math.atan2(target.getZ() - player.getZ(), target.getX() - player.getX()) * (180 / Math.PI) - 90) % 360);
                    float entityAttackingAngle = player.yBodyRot % 360;
                    if (entityHitAngle < 0) {
                        entityHitAngle += 360;
                    }
                    if (entityAttackingAngle < 0) {
                        entityAttackingAngle += 360;
                    }
                    float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
                    if ((entityRelativeAngle <= angle && entityRelativeAngle >= -angle / 2) || (entityRelativeAngle >= 360 - angle || entityRelativeAngle <= -360 + angle / 2)) {
                        target.hurt(player.level().damageSources().playerAttack(player), (float)player.getAttribute(Attributes.ATTACK_DAMAGE).getValue() + 4f);
                        player.heal((float)player.getAttributeValue(Attributes.ATTACK_DAMAGE)*0.5f);
                        target.knockback(1, -Math.sin(Math.toRadians((player.yBodyRot % 360) - 90)), Math.cos(Math.toRadians((player.yBodyRot % 360) - 90)));
                    }
                });
            }
            if(spinTicks >= 25){
                spinTicks = -1;
                canGainRage = true;
                player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(this.spinSlow);
                this.spinSlow = null;
            }
        }
    }

    public int getRage(){
        return this.rage;
    }

    public float getDamageMultiplierFromRage(){
        return ((float)this.rage)*0.005f;
    }

    public void startSpin(){
        spinTicks = 0;
    }

    public int getSpinTicks(){
        return spinTicks;
    }

    public void setSlowUUID(UUID set){
        this.spinSlow = set;
    }

    public void startRageArt(int targetId){
        this.rageArtTicks = 0;
        this.rageArtTargetId = targetId;
    }

    public int getRageArtTargetID(){
        return this.rageArtTargetId;
    }

    public int getRageArtTicks(){
        return this.rageArtTicks;
    }

    public void setRageArtTicks(int ticks){
        this.rageArtTicks = ticks;
    }
}
