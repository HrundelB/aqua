package com.spbsu.aqua;

import com.threed.jpct.*;

import javax.swing.*;
import java.awt.*;

/**
 * Hello world!
 *
 */
public class App 
{
    private World world;
    private FrameBuffer buffer;
    private Object3D box;
    private JFrame frame;

    public static void main(String[] args) throws Exception {
        new App().loop();
    }

    public App() throws Exception {

        frame=new JFrame("Hello world");
        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        world = new World();
        world.setAmbientLight(0, 0, 0);

        TextureManager.getInstance().addTexture("box", new Texture("C:\\Users\\afonin.s\\Downloads\\jpctapi\\jpct\\examples\\helloworld\\box.jpg"));

        box = Primitives.getSphere(2f);
        //box.rotateX(-10f);
        //box.build();

        Object3D[] object3Ds = new Object3D[10];
        for (int i = 0; i < 10; i++) {
            object3Ds[i] = Primitives.getCube(2f);
            object3Ds[i].setTexture("box");
            object3Ds[i].setEnvmapped(Object3D.ENVMAP_ENABLED);
            object3Ds[i].setAdditionalColor(Color.DARK_GRAY);
            object3Ds[i].translate(0,5f*i,10f);
            //object3Ds[i].build();

            //box.addChild(obj);
        }

        world.addObject(box);
        world.addObjects(object3Ds);
        world.buildAllObjects();


        world.getCamera().setPosition(50f, 0, 0);
        world.getCamera().lookAt(box.getTransformedCenter());
    }

    private void loop() throws Exception {
        buffer = new FrameBuffer(800, 600, FrameBuffer.SAMPLINGMODE_NORMAL);

        while (frame.isShowing()) {
            box.rotateY(0.01f);
            buffer.clear(java.awt.Color.BLUE);
            world.renderScene(buffer);
            world.draw(buffer);
            buffer.update();
            buffer.display(frame.getGraphics());
            Thread.sleep(10);
        }
        buffer.disableRenderer(IRenderer.RENDERER_OPENGL);
        buffer.dispose();
        frame.dispose();
        System.exit(0);
    }
}
