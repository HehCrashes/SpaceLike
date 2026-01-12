package com.github.hehcrashes.spacelike;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MainController {

    @FXML
    private Canvas canvas;

    private GraphicsContext gc;

    // 旋转角度
    private double spinAngle = 0;


    // 正十二面体顶点（单位球上，已标准化）
    private static final double PHI = (1 + Math.sqrt(5)) / 2;

    private final double[][] vertices = {
            { 1,  1,  1}, { 1,  1, -1}, { 1, -1,  1}, { 1, -1, -1},
            {-1,  1,  1}, {-1,  1, -1}, {-1, -1,  1}, {-1, -1, -1},

            {0,  1/PHI,  PHI}, {0,  1/PHI, -PHI},
            {0, -1/PHI,  PHI}, {0, -1/PHI, -PHI},

            { 1/PHI,  PHI, 0}, { 1/PHI, -PHI, 0},
            {-1/PHI,  PHI, 0}, {-1/PHI, -PHI, 0},

            { PHI, 0,  1/PHI}, {-PHI, 0,  1/PHI},
            { PHI, 0, -1/PHI}, {-PHI, 0, -1/PHI}
    };

    // 边（顶点索引对）
    private final int[][] edges = {
            {0,8},{0,12},{0,16},
            {1,9},{1,12},{1,18},
            {2,10},{2,13},{2,16},
            {3,11},{3,13},{3,18},

            {4,8},{4,14},{4,17},
            {5,9},{5,14},{5,19},
            {6,10},{6,15},{6,17},
            {7,11},{7,15},{7,19},

            {8,10},{9,11},
            {12,14},{13,15},
            {16,18},{17,19}
    };

    @FXML
    public void initialize() {
        gc = canvas.getGraphicsContext2D();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                render();
            }
        }.start();
    }

    private void update() {
        spinAngle += 0.01;
    }


    private void render() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1.2);

        double[][] projected = new double[vertices.length][2];

        for (int i = 0; i < vertices.length; i++) {
            double[] v = rotate(vertices[i]);
            projected[i] = project(v);
        }

        for (int[] e : edges) {
            double[] p1 = projected[e[0]];
            double[] p2 = projected[e[1]];
            gc.strokeLine(p1[0], p1[1], p2[0], p2[1]);
        }
    }

    private double[] rotate(double[] v) {
        double x = v[0];
        double y = v[1];
        double z = v[2];

        double c = Math.cos(spinAngle);
        double s = Math.sin(spinAngle);

        // 绕 Z 轴
        double x2 = x * c - y * s;
        double y2 = x * s + y * c;

        return new double[]{x2, y2, z};
    }



    private double[] project(double[] v) {
        double scale = 420;
        double distance = 4.0;

        double factor = scale / (distance + v[2]);

        double x = v[0] * factor + canvas.getWidth() / 2;
        double y = v[1] * factor + canvas.getHeight() / 2;

        return new double[]{x, y};
    }


}
