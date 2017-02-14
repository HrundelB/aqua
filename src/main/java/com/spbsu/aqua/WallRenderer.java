package com.spbsu.aqua;

import com.threed.jpct.*;

import java.awt.*;
import java.util.List;

/**
 * Created by Sergey Afonin on 15.02.2017.
 */
public class WallRenderer {

    private static final float INTERVAL = 4f;
    private static final float SIZE = 2f;
    private static final String WATER_JPG = "src/main/java/resources/water.jpg";
    private static final String BOX_JPG = "src/main/java/resources/box.jpg";
    private static final String WATER = "water";
    private static final String BOX = "box";

    private Wall wall;

    public WallRenderer(Wall wall) {
        this.wall = wall;
    }

    public void renderWall(World world) {
        if (!this.wall.isRained())
            return;

        TextureManager.getInstance().addTexture(WATER, new Texture(WATER_JPG));
        TextureManager.getInstance().addTexture(BOX, new Texture(BOX_JPG));

        List<Integer> heights = wall.getHeights();
        List<Integer> waterLevels = wall.getWaterLevels();
        float offsetX = -INTERVAL * heights.size() / 2f;
        for (int i = 0; i < heights.size(); i++) {
            Object3D[] object3Ds = createOneColumn(heights.get(i), waterLevels.get(i), offsetX + i * INTERVAL);
            world.addObjects(object3Ds);
        }
    }

    private Object3D[] createOneColumn(int height, int waterLevel, float x) {
        if (height <= 0 || waterLevel < 0)
            return new Object3D[0];

        Object3D[] object3Ds = new Object3D[height + waterLevel];
        for (int i = 0; i < height + waterLevel; i++) {
            object3Ds[i] = Primitives.getCube(SIZE);
            object3Ds[i].setTexture(i >= height ? WATER : BOX);
            object3Ds[i].setEnvmapped(Object3D.ENVMAP_ENABLED);
            object3Ds[i].setAdditionalColor(i >= height ? Color.BLUE : Color.DARK_GRAY);
            object3Ds[i].translate(x, 0, INTERVAL * i);
            object3Ds[i].rotateY((float) Math.PI / 4f);
        }
        return object3Ds;
    }
}
