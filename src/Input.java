import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Input {
    public static class InputState
    {
        private boolean w, a, s, d,space,shift;
        private boolean[] buttonDown = new boolean[4];
        private int mouseX, mouseY;
        private int lastX=1,lastY=-1;
        public InputState(JFrame frame) {
            frame.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    KeyPress(e.getKeyCode());
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    KeyRelease(e.getKeyCode());
                }
            });

            frame.addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    mouseX = e.getX();
                    mouseY = e.getY();

                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    mouseX = e.getX();
                    mouseY = e.getY();
                }
            });
            frame.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    lastX = -1;
                    lastY = -1;
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    buttonDown[e.getButton()] = true;
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    buttonDown[e.getButton()] = false;
                }
            });
        }
        public boolean isW() { return w; }
        public boolean isA() { return a; }
        public boolean isS() { return s; }
        public boolean isD() { return d; }
        public boolean isSpace() {return space;}
        public boolean isShift() {return shift;}
        public int getMouseX() { return mouseX; }
        public int getMouseY() { return mouseY; }
        public int getMoveX(){int res = (lastX==-1)?0:mouseX-lastX; lastX=mouseX; return res;}
        public int getMoveY(){int res = (lastY==-1)?0:mouseY-lastY; lastY=mouseY; return res;}
        public boolean isCLeft(){return buttonDown[1];}
        public boolean isCRight(){return buttonDown[3];}
        public boolean isCWheel(){return buttonDown[2];}

        public void KeyPress(int keyCode)
        {
            if (keyCode == KeyEvent.VK_W) w = true;
            else if (keyCode == KeyEvent.VK_A) a = true;
            else if (keyCode == KeyEvent.VK_S) s = true;
            else if (keyCode == KeyEvent.VK_D) d = true;
            else if (keyCode == KeyEvent.VK_SPACE) space = true;
            else if (keyCode == KeyEvent.VK_SHIFT) shift = true;
        }
        public void KeyRelease(int keyCode)
        {
            if (keyCode == KeyEvent.VK_W) w = false;
            else if (keyCode == KeyEvent.VK_A) a = false;
            else if (keyCode == KeyEvent.VK_S) s = false;
            else if (keyCode == KeyEvent.VK_D) d = false;
            else if (keyCode == KeyEvent.VK_SPACE) space = false;
            else if (keyCode == KeyEvent.VK_SHIFT) shift = false;
        }

    }
}
