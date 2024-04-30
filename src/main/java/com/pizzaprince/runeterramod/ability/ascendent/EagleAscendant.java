package com.pizzaprince.runeterramod.ability.ascendent;

import com.pizzaprince.runeterramod.client.gui.WaypointEntry;
import com.pizzaprince.runeterramod.networking.ModPackets;
import com.pizzaprince.runeterramod.networking.packet.AOEParticleS2CPacket;
import com.pizzaprince.runeterramod.util.WaypointNBTEntry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class EagleAscendant extends BaseAscendant{

    private static AttributeModifier HEALTH = new AttributeModifier("eagle_ascendant_health",
            20, AttributeModifier.Operation.ADDITION);

    private static AttributeModifier MOVE_SPEED = new AttributeModifier("eagle_ascendant_move_speed",
            0.5, AttributeModifier.Operation.MULTIPLY_TOTAL);
    private int crouchTicks = 0;
    private int flyTicks = 7;
    private int wasFallFlying = 0;
    private ArrayList<WaypointNBTEntry> waypointList = new ArrayList<>();

    @Override
    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("crouchTicks", crouchTicks);
        nbt.putInt("flyTicks", flyTicks);
        for(int i = 0; i < waypointList.size(); i++){
            CompoundTag tag = new CompoundTag();
            WaypointNBTEntry point = waypointList.get(i);
            tag.putString("name", point.name);
            tag.putString("dimension", point.dimension);
            tag.putInt("x", point.x);
            tag.putInt("z", point.z);
            nbt.put("" + i, tag);
        }
    }

    @Override
    public void loadNBTData(CompoundTag nbt) {
        crouchTicks = nbt.getInt("crouchTicks");
        flyTicks = nbt.getInt("flyTicks");
        int i = 0;
        CompoundTag tag = nbt.getCompound("" + i);
        while(!tag.isEmpty()){
            waypointList.add(new WaypointNBTEntry(tag.getString("name"), tag.getString("dimension"), tag.getInt("x"), tag.getInt("z")));
            i++;
            tag = nbt.getCompound("" + i);
        }
    }

    @Override
    public void tick(ServerPlayer player) {
        if(player.isCrouching() && player.onGround()){
            crouchTicks = Math.min(crouchTicks+1, 20);
        }
        if(player.onGround()) flyTicks = 7;
        else flyTicks = Math.max(0, flyTicks-1);
        if(player.isFallFlying()) wasFallFlying = 5;
        wasFallFlying = Math.max(0, wasFallFlying-1);
    }

    public void shockwave(Player player, float distance){
        if(wasFallFlying == 0 || distance < 20 || player.level().isClientSide()) return;
        Level level = player.level();
        float size = Mth.clampedLerp(5, 10, (distance-20f)/80f);
        level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(size), entity -> !entity.is(player)).forEach(entity -> {
            entity.hurt(level.damageSources().playerAttack(player), size*2);
        });
        ModPackets.sendToClients(new AOEParticleS2CPacket(player.position().x, player.position().z, size));
    }

    public void addWaypoint(String name, String dimension, int x, int z){
        waypointList.add(new WaypointNBTEntry(name, dimension, x, z));
    }

    public void removeWaypoint(String name){
        for(int i = 0; i < waypointList.size(); i++){
            if(waypointList.get(i).name.equals(name)){
                waypointList.remove(i);
                return;
            }
        }
    }

    public ArrayList<WaypointNBTEntry> getWaypointList(){
        return waypointList;
    }

    public int getCrouchTicks(){
        return crouchTicks;
    }

    public void resetCrouchTicks(){
        crouchTicks = 0;
    }

    public int getFlyTicks() {
        return flyTicks;
    }

    @Override
    public void onAscend(Player player) {
        if(!player.getAttribute(Attributes.MAX_HEALTH).hasModifier(HEALTH)) {
            player.getAttribute(Attributes.MAX_HEALTH).addTransientModifier(HEALTH);
            player.heal((float)HEALTH.getAmount());
        }
        if(!player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(MOVE_SPEED)){
            player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(MOVE_SPEED);
        }
    }

    @Override
    public void onDescend(Player player) {
        if(player.getAttribute(Attributes.MAX_HEALTH).hasModifier(HEALTH)) {
            player.getAttribute(Attributes.MAX_HEALTH).removeModifier(HEALTH);
        }
        if(player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(MOVE_SPEED)) {
            player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(MOVE_SPEED);
        }
    }
}
