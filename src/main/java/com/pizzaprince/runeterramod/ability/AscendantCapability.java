package com.pizzaprince.runeterramod.ability;

import com.pizzaprince.runeterramod.ability.ascendent.AscendantType;
import com.pizzaprince.runeterramod.ability.ascendent.BaseAscendant;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class AscendantCapability {

    private BaseAscendant ascendant;

    public int outOfCombat = 0;
    public int tickCount = 0;

    public void tick(ServerPlayer player) {
        if(ascendant == null) return;
        outOfCombat = Math.max(0, --outOfCombat);
        tickCount = ++tickCount % 20;
        ascendant.setTickCount(tickCount);
        ascendant.setOutOfCombat(outOfCombat);
        ascendant.tick(player);
    }

    public void saveNBTData(CompoundTag nbt) {
        if(ascendant == null) return;
        ascendant.saveNBTData(nbt);
    }

    public void loadNBTData(CompoundTag nbt) {
        if(ascendant == null) return;
        ascendant.loadNBTData(nbt);
    }

    public void copyFrom(AscendantCapability source) {
        ascendant = source.getAscendant();
    }

    public BaseAscendant getAscendant(){
        return ascendant;
    }

    public void ascend(Player player, AscendantType type){

    }

    public void descent(Player player){

    }

    public void setInCombat(){
        this.outOfCombat = 240;
    }
}
