package com.pizzaprince.runeterramod.ability.ascendent;

public enum AscendantType {
    NONE,
    CROCODILE,
    TURTLE;

    public static AscendantType fromInt(int x) {
        switch(x) {
            case 1:
                return CROCODILE;
            case 2:
                return TURTLE;
        }
        return NONE;
    }
}
