package com.pizzaprince.runeterramod.util;

public class WaypointNBTEntry {
    public final String name;
    public final String dimension;
    public final int x;
    public final int z;

    public WaypointNBTEntry(String name, String dimension, int x, int z){
        this.name = name;
        this.dimension = dimension;
        this.x = x;
        this.z = z;
    }
}
