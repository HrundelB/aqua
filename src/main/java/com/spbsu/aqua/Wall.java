package com.spbsu.aqua;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey Afonin on 15.02.2017.
 */
public class Wall {

    private List<SimpleWall> walls;
    private List<Integer> heights;
    private List<Integer> waterLevels;
    private boolean isRained = false;

    public Wall(List<Integer> heights) {
        if (heights.size() > 100)
            throw new IllegalArgumentException("Size of wall can not be greater than 100");

        for (Integer h : heights) {
            if (h > 100)
                throw new IllegalArgumentException("Size of wall can not be greater than 100");
        }

        this.heights = heights;
        this.waterLevels = new ArrayList<>();
        this.walls = new ArrayList<>();

        makeWalls(heights);
    }

    private void makeWalls(List<Integer> heights) {
        int start = 0;
        for (int i = 0; i < heights.size(); i++) {
            if (heights.get(i) <= 0) {
                if (start == i) {
                    start++;
                } else {
                    this.walls.add(new SimpleWall(heights.subList(start, i)));
                    start = i + 1;
                }
            }
        }
        if (start < heights.size()) {
            this.walls.add(new SimpleWall(heights.subList(start, heights.size())));
        }
    }

    public void rain() {
        this.walls.forEach(SimpleWall::rain);

        int n = 0;
        for (int i = 0; i < heights.size(); i++) {
            if (heights.get(i) <= 0) {
                waterLevels.add(0);
            } else {
                List<Integer> levels = this.walls.get(n).getWaterLevels();
                this.waterLevels.addAll(levels);
                n++;
                i += levels.size() - 1;
            }
        }

        this.isRained = true;
    }

    public List<Integer> getHeights() {
        return heights;
    }

    public List<Integer> getWaterLevels() {
        return waterLevels;
    }

    public boolean isRained() {
        return isRained;
    }
}
