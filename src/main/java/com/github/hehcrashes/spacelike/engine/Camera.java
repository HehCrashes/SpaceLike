package com.github.hehcrashes.spacelike.engine;

public class Camera {
    public Transform transform = new Transform();
    public double fov = Math.toRadians(70);
    public double near = 0.1;
    public double scale = 800;
}