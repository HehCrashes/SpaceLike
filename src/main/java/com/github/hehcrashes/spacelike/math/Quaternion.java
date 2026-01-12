package com.github.hehcrashes.spacelike.math;

public class Quaternion {
    public double w, x, y, z;

    public Quaternion(double w, double x, double y, double z) {
        this.w = w; this.x = x; this.y = y; this.z = z;
    }

    public static Quaternion identity() {
        return new Quaternion(1, 0, 0, 0);
    }

    public Quaternion inverse() {
        return new Quaternion(w, -x, -y, -z);
    }

    public Vec3 rotate(Vec3 v) {
        Quaternion qv = new Quaternion(0, v.x, v.y, v.z);
        Quaternion r = this.multiply(qv).multiply(this.inverse());
        return new Vec3(r.x, r.y, r.z);
    }

    public Quaternion multiply(Quaternion o) {
        return new Quaternion(
                w * o.w - x * o.x - y * o.y - z * o.z,
                w * o.x + x * o.w + y * o.z - z * o.y,
                w * o.y - x * o.z + y * o.w + z * o.x,
                w * o.z + x * o.y - y * o.x + z * o.w
        );
    }
}

