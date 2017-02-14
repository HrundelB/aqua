package com.spbsu.aqua;

import com.threed.jpct.*;
import com.threed.jpct.util.Light;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import java.util.List;

/**
 * Created by Sergey Afonin on 15.02.2017.
 */
public class WallWorld {

    private static float distance = 50;

    private World world;

    public WallWorld(List<Integer> wallHeights) {
        this.world = createWorld(wallHeights);
    }

    private World createWorld(List<Integer> wallHeights) {
        World world = new World();
        world.setAmbientLight(0, 0, 0);
        Light light = new Light(world);
        light.setIntensity(50, 50, 50);
        light.setPosition(new SimpleVector(-100, -100, 100));

        Wall wall = new Wall(wallHeights);
        wall.rain();
        WallRenderer renderer = new WallRenderer(wall);
        renderer.renderWall(world);
        world.buildAllObjects();
        return world;
    }

    public void drawWorld() throws InterruptedException, LWJGLException {
        Camera cam = world.getCamera();
        cam.moveCamera(Camera.CAMERA_MOVEUP, distance);
        cam.rotateCameraX((float) Math.PI / 2f);
        FrameBuffer buffer = new FrameBuffer(800, 600, FrameBuffer.SAMPLINGMODE_HARDWARE_ONLY);
        buffer.disableRenderer(IRenderer.RENDERER_SOFTWARE);
        buffer.enableRenderer(IRenderer.RENDERER_OPENGL);
        Mouse.create();
        while (!Display.isCloseRequested() && !Mouse.isButtonDown(1)) {
            buffer.clear();
            world.renderScene(buffer);
            world.draw(buffer);
            buffer.update();
            buffer.displayGLOnly();

            int x = Mouse.getDX();
            int y = Mouse.getDY();
            int w = Mouse.getDWheel();

            if (Mouse.isButtonDown(0)) {
                SimpleVector line = new SimpleVector(x, 0, y);
                Matrix m = line.normalize().getRotationMatrix();

                m.rotateAxis(m.getXAxis(), (float) -Math.PI / 2f);
                cam.moveCamera(Camera.CAMERA_MOVEIN, distance);
                cam.rotateAxis(m.invert3x3().getXAxis(), line.length() / 200f);
                cam.moveCamera(Camera.CAMERA_MOVEOUT, distance);
            }
            if (w != 0) {
                float d = w / 20f;
                distance -= d;
                cam.moveCamera(Camera.CAMERA_MOVEIN, d);
            }

            Thread.sleep(10);
        }
        Mouse.setGrabbed(false);
        Mouse.destroy();

        buffer.disableRenderer(IRenderer.RENDERER_OPENGL);
        buffer.dispose();
    }
}
