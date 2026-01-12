package com.github.hehcrashes.spacelike.scene;

import com.github.hehcrashes.spacelike.engine.Entity;
import com.github.hehcrashes.spacelike.math.Vec3;

public class SpaceStation extends Entity {

    public Vec3[] vertices = {
            new Vec3(-1, -1, -1), new Vec3(1, -1, -1),
            new Vec3(1, 1, -1), new Vec3(-1, 1, -1),
            new Vec3(-1, -1, 1), new Vec3(1, -1, 1),
            new Vec3(1, 1, 1), new Vec3(-1, 1, 1)
    };

    // 每个面用顶点索引表示，顺时针或逆时针都行
    public int[][] faces = {
            {0, 1, 2, 3}, // 后
            {4, 5, 6, 7}, // 前
            {0, 1, 5, 4}, // 下
            {2, 3, 7, 6}, // 上
            {0, 3, 7, 4}, // 左
            {1, 2, 6, 5}  // 右
    };
}
