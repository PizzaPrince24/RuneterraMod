package com.pizzaprince.runeterramod.camera;

import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animation.EasingType;

public class CameraPoint {
    public final double x;
    public final double y;
    public final double z;
    public final float yaw;
    public final float pitch;

    public final float fov;
    public final int timeToPoint;

    public final Float2FloatFunction easingType;

    //usage for setting the camera off of already existing Camera Points
    public CameraPoint(Vec3 pos, double xOffset, double yOffset, double zOffset, float yaw, float pitch, float fov, int time, Float2FloatFunction easing){
        this.x = pos.x + xOffset;
        this.y = pos.y + yOffset;
        this.z = pos.z + zOffset;
        this.yaw = yaw;
        this.pitch = pitch;
        this.fov = fov;
        this.timeToPoint = time;
        this.easingType = easing;
    }

    //main usage
    public CameraPoint(Entity start, double rightOffset, double heightOffset, double forwardOffset, float yawOffset, float pitchOffset, float fov, int timeForTravel, Float2FloatFunction easing){
        float yaw = start.getYRot();
        System.out.println(yaw);
        this.x = start.position().x + Math.sin(Math.toRadians((yaw-90) % 360))*rightOffset - Math.sin(Math.toRadians(yaw % 360))*forwardOffset;
        this.y = start.position().y + heightOffset;
        this.z = start.position().z + Math.cos(Math.toRadians((yaw+90) % 360))*rightOffset + Math.cos(Math.toRadians(yaw % 360))*forwardOffset;
        this.yaw = start.getYRot() + yawOffset;
        this.pitch = start.getXRot() + pitchOffset;
        this.fov = fov;
        this.timeToPoint = timeForTravel;
        this.easingType = easing;
    }
}
