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

    public static CameraSequence getRageArtSequence(double x, double y, double z, float yaw, float pitch, double targetX, double targetY, double targetZ, float targetBbWidth, float targetBbHeight){
        return new CameraSequence(
                new CameraPoint(targetX, targetY, targetZ, yaw, pitch, -(targetBbWidth/2)-3, 0, 2, 160, 0, 1f, 0, ScaleEasings.LINEAR),
                new CameraPoint(targetX, targetY, targetZ, yaw, pitch, -(targetBbWidth/2)-3, 0, 2, 130, 0, 1f, 15, ScaleEasings.CUBIC_IN_OUT),
                new CameraPoint(targetX, targetY, targetZ, yaw, pitch, 0, targetBbHeight - 1, targetBbWidth/2 + 1, 180, -10, 1f, 1, ScaleEasings.LINEAR),
                new CameraPoint(targetX, targetY, targetZ, yaw, pitch, 0, targetBbHeight - 1, targetBbWidth/2 + 1, 180, -10, 0.8f, 14, ScaleEasings.CUBIC_IN_OUT),
                new CameraPoint(targetX, targetY, targetZ, yaw, pitch, 0, targetBbHeight, targetBbWidth/2 + 2, 180, -60, 1f, 21, ScaleEasings.CUBIC_OUT),
                new CameraPoint(targetX, targetY, targetZ, yaw, pitch, -0.5, targetBbHeight + 11.3, targetBbWidth/2 + 0.5, 150, 0, 1f, 1, ScaleEasings.LINEAR),
                new CameraPoint(targetX, targetY, targetZ, yaw, pitch, -0.5, targetBbHeight + 11.3, targetBbWidth/2 + 0.5, 150, 0, 0.4f, 12, ScaleEasings.CUBIC_IN_OUT),
                new CameraPoint(targetX, targetY, targetZ, yaw, pitch, 0.65, targetBbHeight + 11.6, targetBbWidth/2 + 0.5, 210, 0, 0.4f, 18, ScaleEasings.CUBIC_IN_OUT),
                new CameraPoint(targetX, targetY, targetZ, yaw, pitch, 5, targetBbHeight + 13, targetBbWidth/2 - 5, 320, 40, 0.9f, 1, ScaleEasings.LINEAR),
                new CameraPoint(targetX, targetY, targetZ, yaw, pitch, 5, targetBbHeight + 13, targetBbWidth/2 - 5, 320, 70, 0.7f, 50, ScaleEasings.CUBIC_IN_OUT),
                new CameraPoint(targetX, targetY, targetZ, yaw, pitch, 2, 0.5, -3, 320, 30, 1.1f, 30, ScaleEasings.CUBIC_IN_OUT),
                new CameraPoint(targetX, targetY, targetZ, yaw, pitch, 2, 0.5, -3 , 320, 30, 1.1f, 8, ScaleEasings.CUBIC_IN_OUT)
        );
    }
}
