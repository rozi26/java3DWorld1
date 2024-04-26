import javax.swing.*;

public class Page extends JFrame {
    final int WIDTH = 750;
    final int HEIGHT = 750;

    final Renderer graphics;
    JPanel panel;
    public Page()
    {
        setSize(WIDTH,HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


        panel = new JPanel();
        panel.setSize(WIDTH,HEIGHT);
        this.add(panel);

        graphics = new Renderer(this,panel);
        //graphics = new Recorder(this,panel,"F:\\storge\\test\\3djavaRecord2");
        setVisible(true);
        try
        {
            Thread.sleep(10);
            graphics.update();
        }
        catch (Exception e){}
        graphics.run();
    }
}
