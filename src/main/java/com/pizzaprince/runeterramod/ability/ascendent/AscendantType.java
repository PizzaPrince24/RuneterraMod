package com.pizzaprince.runeterramod.ability.ascendent;

public enum AscendantType {
    NONE,
    CROCODILE,
    TURTLE,
    EAGLE;

    public static AscendantType fromInt(int x) {
        switch(x) {
            case 1: return CROCODILE;
            case 2: return TURTLE;
            case 3: return EAGLE;
        }
        return NONE;
    }
}
