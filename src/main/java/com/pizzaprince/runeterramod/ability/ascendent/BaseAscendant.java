package com.pizzaprince.runeterramod.ability.ascendent;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public abstract class BaseAscendant {

    public int outOfCombat = 0;
    public int tickCount = 0;

    public abstract void saveNBTData(CompoundTag nbt);

    public abstract void loadNBTData(CompoundTag nbt);

    public abstract void tick(ServerPlayer player);

    public abstract void onAscend(Player player);

    public abstract void onDescend(Player player);

    public void setOutOfCombat(int set){
        this.outOfCombat = set;
    }

    public void setTickCount(int set){
        this.tickCount = set;
    }

}
