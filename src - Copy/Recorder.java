import ShapesList.Circle;
import ShapesList.Cube;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

public class Recorder extends Renderer{

    protected int frame = 0;
    protected String saveFolder;

    public Recorder(JFrame page, JPanel panel, String _saveFolder) {
        super(page, panel);
        saveFolder = _saveFolder;
        speed = 1;
        X = -20;
        Y = 10;
        SHAPES.addShape(new Circle(10, Color.BLUE,0,10,40));
        SHAPES.addShape(new Cube(10,100,0,0)); 
    }

    @Override
    public void runFrame() {
        super.runFrame();
        frame++;
        /*if (frame == 1) {INPUT.KeyPress(KeyEvent.VK_A); INPUT.KeyPress(KeyEvent.VK_SPACE);}
        if (frame == 10) {INPUT.KeyRelease(KeyEvent.VK_SPACE);}
        if (frame == 60) {INPUT.KeyRelease(KeyEvent.VK_A);}
        if (frame == 61) {INPUT.KeyPress(KeyEvent.VK_S);}
        if (frame == 100) {INPUT.KeyRelease(KeyEvent.VK_S);}
        if (frame > 90 && frame < 100) YE += 0.02;
        if (frame > 100 && frame < 120) YE -= 0.02;
        if (frame > 120 && frame < 130) {YE += 0.02; XE += 0.02;};
        if (frame > 130 && frame < 150) {XE -= 0.02;}
        if (frame > 150 && frame < 160) {XE += 0.02;}
        if (frame == 160) PAGE.setVisible(false);*/
        try{
            ImageIO.write(IMG,"png", new File(saveFolder + "\\frame" + frame + ".png"));
        }
        catch (Exception e) {System.out.println("fail to save frame " + frame + " with error " + e);}
    }
}
