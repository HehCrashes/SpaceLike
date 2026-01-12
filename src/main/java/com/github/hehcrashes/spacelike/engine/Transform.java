package com.github.hehcrashes.spacelike.engine;

import com.github.hehcrashes.spacelike.math.Quaternion;
import com.github.hehcrashes.spacelike.math.Vec3;

public class Transform {
    public Vec3 position = new Vec3(0,0,0);
    public Quaternion rotation = Quaternion.identity();

    public Vec3 toLocal(Vec3 world) {
        Vec3 p = world.sub(position);
        return rotation.inverse().rotate(p);
    }
}
