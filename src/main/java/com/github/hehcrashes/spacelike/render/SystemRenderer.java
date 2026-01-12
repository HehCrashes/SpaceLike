package com.github.hehcrashes.spacelike.render;

import com.github.hehcrashes.spacelike.engine.Camera;
import com.github.hehcrashes.spacelike.engine.Entity;
import com.github.hehcrashes.spacelike.scene.SpaceStation;
import com.github.hehcrashes.spacelike.engine.StarSystem;
import com.github.hehcrashes.spacelike.math.Vec3;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SystemRenderer {

    private static class RenderFace {
        double[] screenX;
        double[] screenY;
        double depth;
        Color color;

        public RenderFace(double[] screenX, double[] screenY, double depth, Color color) {
            this.screenX = screenX;
            this.screenY = screenY;
            this.depth = depth;
            this.color = color;
        }
    }

    public void render(StarSystem system, Camera cam,
                       GraphicsContext gc, double w, double h) {

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, w, h);

        List<RenderFace> facesToDraw = new ArrayList<>();

        for (Entity e : system.entities) {
            if (e instanceof SpaceStation s) {
                collectStationFaces(s, cam, w, h, facesToDraw);
            }
        }

        // Painter's Algorithm：先画远的
        facesToDraw.sort(Comparator.comparingDouble(f -> -f.depth));

        for (RenderFace f : facesToDraw) {
            gc.setStroke(f.color);
            gc.strokePolygon(f.screenX, f.screenY, f.screenX.length);
        }
    }

    private void collectStationFaces(SpaceStation s, Camera cam,
                                     double w, double h,
                                     List<RenderFace> facesToDraw) {

        for (int[] face : s.faces) {
            Vec3[] verts = new Vec3[face.length];
            double depthSum = 0;
            boolean behindCamera = false;

            for (int i = 0; i < face.length; i++) {
                Vec3 world = s.transform.rotation.rotate(s.vertices[face[i]])
                        .add(s.transform.position);
                Vec3 view = cam.transform.toLocal(world);

                if (view.z <= 0) behindCamera = true; // 相机后面，丢弃
                depthSum += view.z;

                double f = cam.scale / (view.z + 6);
                verts[i] = new Vec3(
                        view.x * f + w / 2,
                        -view.y * f + h / 2,
                        view.z
                );
            }

            if (behindCamera) continue;

            double[] sx = new double[verts.length];
            double[] sy = new double[verts.length];
            for (int i = 0; i < verts.length; i++) {
                sx[i] = verts[i].x;
                sy[i] = verts[i].y;
            }

            double depth = depthSum / verts.length;
            facesToDraw.add(new RenderFace(sx, sy, depth, Color.WHITE));
        }
    }
}
