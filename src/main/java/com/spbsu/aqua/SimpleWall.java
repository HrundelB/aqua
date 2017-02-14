package com.spbsu.aqua;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey Afonin on 15.02.2017.
 */
public class SimpleWall {

    private List<Integer> heights;
    private List<Integer> waterLevels;

    public SimpleWall(List<Integer> heights) {
        this.heights = heights;
        this.waterLevels = new ArrayList<>();
        heights.forEach(h -> this.waterLevels.add(0));
    }

    public void rain() {
        int leftMax = 0;
        int rightMax = 0;
        int left = 0;
        int right = heights.size() - 1;

        while(left < right) {
            if(heights.get(left) > leftMax) {
                leftMax = heights.get(left);
            }
            if(heights.get(right) > rightMax) {
                rightMax = heights.get(right);
            }
            if(leftMax >= rightMax) {
                waterLevels.set(right, rightMax - heights.get(right));
                right--;
            } else {
                waterLevels.set(left, leftMax - heights.get(left));
                left++;
            }
        }
    }

    public List<Integer> getHeights() {
        return heights;
    }

    public List<Integer> getWaterLevels() {
        return waterLevels;
    }
}
