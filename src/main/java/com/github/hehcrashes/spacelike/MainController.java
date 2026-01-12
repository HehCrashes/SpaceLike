package com.github.hehcrashes.spacelike;

import com.github.hehcrashes.spacelike.engine.Spaceship;
import com.github.hehcrashes.spacelike.engine.StarSystem;
import com.github.hehcrashes.spacelike.engine.Universe;
import com.github.hehcrashes.spacelike.math.Quaternion;
import com.github.hehcrashes.spacelike.render.SystemRenderer;
import com.github.hehcrashes.spacelike.scene.SpaceStation;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class MainController {

    @FXML
    private Canvas canvas;

    private Universe universe;
    private Spaceship player;
    private final SystemRenderer renderer = new SystemRenderer();

    @FXML
    public void initialize() {
        initWorld();
        startLoop();
    }

    private void initWorld() {
        universe = new Universe();
        StarSystem system = new StarSystem();

        player = new Spaceship();
        player.transform.position.z = -5;

        SpaceStation station = new SpaceStation();
        station.transform.position.z = 5;

        system.entities.add(station);
        universe.activeSystem = system;
    }

    private void startLoop() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        new AnimationTimer() {
            double a = 0;
            @Override
            public void handle(long now) {
                a += 0.01;
                universe.activeSystem.entities.forEach(e -> {
                    e.transform.rotation =
                            new Quaternion(
                                    Math.cos(a / 2), 0,
                                    Math.sin(a / 2), 0);
                });

                renderer.render(
                        universe.activeSystem,
                        player.cockpitCamera,
                        gc,
                        canvas.getWidth(),
                        canvas.getHeight()
                );
            }
        }.start();
    }
}
