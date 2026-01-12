package com.github.hehcrashes.spacelike.engine;

public class Spaceship extends Entity {
    public Camera cockpitCamera = new Camera();

    public Spaceship() {
        cockpitCamera.transform = this.transform;
    }
}
