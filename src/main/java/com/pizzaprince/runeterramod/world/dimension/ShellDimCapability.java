package com.pizzaprince.runeterramod.world.dimension;

import net.minecraft.nbt.CompoundTag;

public class ShellDimCapability {

    private int currentNumPlayers = 0;

    public int callNewHolder(){
        this.currentNumPlayers++;
        return this.currentNumPlayers;
    }

    public void serializeNBT(CompoundTag tag) {
        tag.putInt("numPlayers", currentNumPlayers);
    }

    public void deserializeNBT(CompoundTag nbt) {
        this.currentNumPlayers = nbt.getInt("numPlayers");
    }
}
