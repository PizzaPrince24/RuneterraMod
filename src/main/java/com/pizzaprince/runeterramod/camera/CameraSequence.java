package com.pizzaprince.runeterramod.camera;

import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import net.minecraft.client.CameraType;
import net.minecraft.world.phys.Vec3;

import java.util.List;

import static com.pizzaprince.runeterramod.camera.CinematicCamera.MC;

public class CameraSequence {

    List<CameraPoint> sequence;

    private long startTime = -1;

    private CameraPoint toTarget;

    private CameraPoint fromTarget;

    private CinematicCamera camera;

    public CameraSequence(CameraPoint... points) {
        if (points.length < 2) {
            this.sequence = null;
        } else {
            this.sequence = List.of(points);
        }
    }


    public void play(){
        if(sequence != null){
            camera = new CinematicCamera(this);
            CameraPoint start = sequence.get(0);
            camera.setCameraPoint(start);
            MC.setCameraEntity(camera);
            startTime = System.currentTimeMillis();
            fromTarget = sequence.get(0);
            toTarget = sequence.get(1);
            MC.options.setCameraType(CameraType.FIRST_PERSON);
        }
    }

    public void updateCamera(CinematicCamera camera){
        if(startTime == -1) return;
        long currentTime = System.currentTimeMillis();
        long endTime = startTime + toTarget.timeToPoint*50;
        double x = getValueForEasing(fromTarget.x, toTarget.x, currentTime, endTime);
        double y = getValueForEasing(fromTarget.y, toTarget.y, currentTime, endTime);
        double z = getValueForEasing(fromTarget.z, toTarget.z, currentTime, endTime);
        float pitch = getValueForEasing(fromTarget.pitch, toTarget.pitch, currentTime, endTime);
        float yaw = getValueForEasing(fromTarget.yaw, toTarget.yaw, currentTime, endTime);
        float fov = getValueForEasing(fromTarget.fov, toTarget.fov, currentTime, endTime);
        camera.setCameraPoint(new CameraPoint(new Vec3(x, y, z), 0, 0, 0, yaw, pitch, fov, 0, null));
        if(currentTime >= endTime){
            if(sequence.indexOf(toTarget) >= sequence.size()-1){
                camera.despawn();
            } else {
                fromTarget = toTarget;
                toTarget = sequence.get(sequence.indexOf(toTarget)+1);
                startTime = currentTime;
            }
        }
        MC.options.setCameraType(CameraType.FIRST_PERSON);
    }

    public double getValueForEasing(double start, double end, long currentTime, long endTime){
        return start + ((end - start)*toTarget.easingType.apply((float)(currentTime - startTime) / (float)(endTime - startTime)));
    }

    public float getValueForEasing(float start, float end, long currentTime, long endTime){
        return start + ((end - start)*toTarget.easingType.apply((float)(currentTime - startTime) / (float)(endTime - startTime)));
    }
}
