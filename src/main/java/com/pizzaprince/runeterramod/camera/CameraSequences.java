package com.pizzaprince.runeterramod.camera;

import net.minecraft.world.entity.Entity;
import virtuoel.pehkui.api.ScaleEasings;

public class CameraSequences {

    public static CameraSequence createTestSequence(Entity entity){
        return new CameraSequence(
                new CameraPoint(entity, 0, 0, 0, 0, 0, 1f, 0, ScaleEasings.LINEAR),
                new CameraPoint(entity, 5, 0, 5, 0, 0, 1f, 40, ScaleEasings.CUBIC_IN_OUT)
        );
    }
}
