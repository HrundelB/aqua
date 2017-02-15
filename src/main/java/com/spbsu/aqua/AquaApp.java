package com.spbsu.aqua;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Sergey Afonin on 15.02.2017.
 */
public class AquaApp {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter wall sizes separated by ',' (e.g. \"2,3,4,5\"):\n");
        String s = br.readLine();
        br.close();

        List<Integer> heights;
        try {
            heights = Stream.of(s.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        } catch (NumberFormatException e) {
            System.out.println(String.format("Invalid arguments: %s", e.getMessage()));
            return;
        }

        WallWorld wallWorld = new WallWorld(heights);
        wallWorld.drawWorld();
    }
}