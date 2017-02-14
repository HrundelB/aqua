package com.spbsu.aqua;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Sergey Afonin on 15.02.2017.
 */
public class AquaApp {


    public static final String USAGE = "Usage: aquaapp [wallSize1] [wallSize2] ...";

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println(USAGE);
            return;
        }

        List<Integer> heights;
        try {
            heights = Stream.of(args).map(Integer::parseInt).collect(Collectors.toList());
        } catch (NumberFormatException e) {
            System.out.println(String.format("Invalid arguments: %s\n%s", e.getMessage(), USAGE));
            return;
        }
        // = Arrays.asList(2, 5, 1, 3, 1, 2, 1, 7, 7, 6);
        WallWorld wallWorld = new WallWorld(heights);
        wallWorld.drawWorld();
    }
}