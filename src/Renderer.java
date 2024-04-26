import ShapesList.Circle;
import ShapesList.Complex.Sierpinski;
import ShapesList.Cube;
import ShapesList.Planes.TextureDemPane;
import ShapesList.ShapeBase;
import ShapesList.ShapesHolder.GridShapeHolder1;
import ShapesList.ShapesHolder.IShapeHolder;
import ShapesList.ShapesHolder.ListShapeHolder;
import ShapesList.TestFillAll;
import Utils.Processioning;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Renderer {

    protected final JFrame PAGE;
    protected final JPanel PANEL;
    protected final BufferedImage IMG;
    private  Graphics2D PANELG;
    protected final Input.InputState INPUT;

    protected final IShapeHolder SHAPES = new ListShapeHolder();
    //private final IShapeHolder SHAPES = new GridShapeHolder1(10,100);

    protected double XE = 0;
    protected double YE = 0;
    protected double X=0,Y=0,Z=0;

    protected double RANGE_X = Math.PI/8;
    protected double RANGE_Y = Math.PI/8;
    protected double speed = 1;
    private double VIEW_R = 0.5;

    private int FRAMES = 0;
    private final long START;

    private String test_messsage = "test";


    public Renderer(JFrame page, JPanel panel)
    {
        PAGE = page;
        PANEL = panel;
        PANELG = (Graphics2D) PANEL.getGraphics();
        PANELG.setFont(new Font("arial",Font.BOLD,20));
        PANELG.setColor(Color.YELLOW);
        IMG = new BufferedImage(page.getWidth(),page.getHeight(),BufferedImage.TYPE_INT_RGB);
        INPUT = new Input.InputState(PAGE);
        //for(Shapes.Shape s:Shapes.getCube(20,40,0,0).shapes) SHAPES.add(s);
        SHAPES.addShape(new Cube(20,40,0,0));
        System.out.println(SHAPES.getAllShapes().get(0).getEffectSquare());
        //SHAPES.addShape(new Cube(10,0,20,40));
        SHAPES.addShape(new Circle(15,Color.DARK_GRAY, 0,40,0));

        try{SHAPES.addShape(new TextureDemPane(0,50,100,0,0, ImageIO.read(new File("F:\\storge\\test\\images\\Leonhard_Euler.jpg")),Color.GRAY));}
        catch (Exception e) {System.out.println("fail");}
        SHAPES.addShape(new Sierpinski(0,20,Color.YELLOW,0,0,0,5));
        //SHAPES.addShape(new TestFillAll());
        //for (int i = 0; i < 100; i++) SHAPES.addShape(new Cube(10,i*20,0,0));

        RANGE_X *= IMG.getWidth()/(double)IMG.getHeight();
        START = System.currentTimeMillis();
        update();
    }

    public void run()
    {
        final int MAX_FPS = 120;
        final double MAX_MILS = MAX_FPS + MAX_FPS*0.2;
        long last = System.currentTimeMillis();
        while (true)
        {
            runFrame();
            long pass = System.currentTimeMillis()-last;
            int wait = (int)(Math.round((1000-MAX_MILS*pass)/MAX_MILS));
            if(wait > 0)
            {
                try {Thread.sleep(wait);}
                catch (Exception e){}
            }
            last = System.currentTimeMillis();
            FRAMES++;
            //break;
            System.out.print("\ravarage fps is " + (1000.0*FRAMES/(System.currentTimeMillis()-START)) + "   ");
        }
    }

    //rendering methods
    private double[][] getHorValues(int x1, int x2, Processioning.Vec vec)
    {
        final double XC = Math.cos(XE);
        final double ZC = Math.sin(XE);
        Processioning.ViewVec horVec = new Processioning.ViewVec(new Processioning.Vec(ZC,0,-XC),0,0,0);
        double[][] horValues = new double[x2-x1][2];
        for(int px = x1; px < x2; px++)
        {
            double ratio_x = (px/(double)IMG.getWidth()-0.5)*2;
            final double ratio_n = ratio_x*RANGE_X;
            horValues[px-x1][0] = horVec.MX*ratio_n;
            horValues[px-x1][1] = horVec.MZ*ratio_n;
        }
        return  horValues;
    }
    private double[][] getVerValues(int y1, int y2, Processioning.Vec vec)
    {
        final double XC = Math.cos(XE);
        final double ZC = Math.sin(XE);
        final double CC = Math.cos(YE);
        final double YC = Math.sin(YE);
        Processioning.ViewVec varVec = new Processioning.ViewVec(new Processioning.Vec(YC*XC,-CC,ZC*YC),0,0,0);

        double[][] verValues = new double[y2-y1][3];
        for (int py = y1; py < y2; py++)
        {
            double ratio_y = ((py/(double)IMG.getHeight())-0.5)*2*RANGE_Y;
            verValues[py-y1][0] = ratio_y*varVec.MX;
            verValues[py-y1][1] = ratio_y*varVec.MY;
            verValues[py-y1][2] = ratio_y*varVec.MZ;
        }
        return  verValues;
    }

    public void gridRender(int x1, int y1, int x2, int y2, int gridSize)
    {
        x2 -= x2 % gridSize;
        y2 -= y2 % gridSize ;
        final double XC = Math.cos(XE);
        final double ZC = Math.sin(XE);
        final double CC = Math.cos(YE);
        final double YC = Math.sin(YE);
        Processioning.Vec vec = new Processioning.Vec(XC*CC,YC,ZC*CC).getNormal();

        double[][] horValues = getHorValues(x1,x2,vec);
        double[][] verValues = getVerValues(y1,y2,vec);

        int[][] yColors = new int[(y2-y1)/gridSize][(x2-x1)/gridSize];
        int x = 0; int y =0;
        for (int py = y1; py < y2; py += gridSize)
        {
            x = 0;
            for (int px = x1; px < x2; px += gridSize)
            {
                Processioning.Vec derVec = new Processioning.Vec(vec.MX+verValues[py][0]+horValues[px][0],vec.MY+verValues[py][1],vec.MZ+verValues[py][2]+horValues[px][1]);
                Processioning.ViewVec pixVec = new Processioning.ViewVec(derVec,X,Y,Z);
                yColors[y][x] = Processioning.getColor(SHAPES.getPossibleHits(pixVec),pixVec);
                x++;
            }
            y++;
        }
        int c;
        for (int gy = 0; gy < yColors.length - 2; gy++)
        {
            for (int gx = 0; gx < yColors[0].length - 2; gx++)
            {
                c = yColors[gy][gx];
                if (c == yColors[gy][gx + 1] && c == yColors[gy+1][gx] && c == yColors[gy+1][gx+1])
                {
                    c = c == -1 ? 0 : c;
                    for (int py = gy*gridSize; py < gy*gridSize+gridSize; py++) for (int px = gx*gridSize; px < gx*gridSize+gridSize; px++) IMG.setRGB(px,py,c);
                }
                else fullRender(gx*gridSize,gy*gridSize,gx*gridSize+gridSize,gy*gridSize+gridSize,vec,horValues,verValues,0,0);
            }
        }
    }

    public void fullRender()
    {
        fullRender(0,0,IMG.getWidth(),IMG.getHeight());
    }

    public void fullRender(int x1, int y1, int x2, int y2, Processioning.Vec vec, double[][] horValues, double[][] verValues, int bx, int by)
    {
        for (int py = y1; py < y2; py++) for (int px = x1; px < x2; px++)
        {
            double[] YS = verValues[py - by];
            double[] HS = horValues[px - bx];
            Processioning.Vec derVec = new Processioning.Vec(vec.MX+YS[0]+HS[0],vec.MY+YS[1],vec.MZ+YS[2]+HS[1]);
            Processioning.ViewVec pixVec = new Processioning.ViewVec(derVec,X,Y,Z);
            int c = Processioning.getColor(SHAPES.getPossibleHits(pixVec),pixVec);
            if(c == -1) IMG.setRGB(px,py,Processioning.BACKGROUND_COLOR.getAt(pixVec.getMove(100)));
            else IMG.setRGB(px,py,c);
        }
    }
    public void fullRender(int x1, int y1, int x2, int y2)
    {
        final double XC = Math.cos(XE);
        final double ZC = Math.sin(XE);
        final double CC = Math.cos(YE);
        final double YC = Math.sin(YE);

        Processioning.Vec vec = new Processioning.Vec(XC*CC,YC,ZC*CC).getNormal();

        double[][] horValues = getHorValues(x1,x2,vec);
        double[][] verValues = getVerValues(y1,y2,vec);
        fullRender(x1,y1,x2,y2,vec,horValues,verValues,x1,y1);
        /*for(int py = y1; py < y2; py++)
        {
            double ratio_y = ((py/(double)IMG.getHeight())-0.5)*2*RANGE_Y;
            final double[] YS = new double[]{ratio_y*varVec.MX,ratio_y*varVec.MY,ratio_y*varVec.MZ};

            for(int px = x1; px < x2; px++)
            {
                Processioning.Vec derVec = new Processioning.Vec(vec.MX+YS[0]+horValues[px-x1][0],vec.MY+YS[1],vec.MZ+YS[2]+horValues[px-x1][1]);
                //Utils.Processioning.Vec derVec = new Utils.Processioning.Vec(x_cos_sin[px][0]*VYC,VY,x_cos_sin[px][1]*VYC);
                //Utils.Processioning.Vec derVec = new Utils.Processioning.Vec(x_cos_sin[px][0],VY,x_cos_sin[px][1]).getNormal();
                Processioning.ViewVec pixVec = new Processioning.ViewVec(derVec,X,Y,Z);


                //int c = -1;
                int c = Processioning.getColor(SHAPES,pixVec);
                if(c == -1) IMG.setRGB(px,py,Processioning.BACKGROUND_COLOR.getAt(pixVec.getMove(100)));
                else IMG.setRGB(px,py,c);
                //IMG.setRGB(px,py,Color.GRAY.getRGB());
            }
        }*/
    }


    public void runFrame()
    {
        final int moveX = INPUT.getMoveX();
        final int moveY = INPUT.getMoveY();
        if(INPUT.isCLeft())
        {
            XE += 2*RANGE_X*moveX/IMG.getWidth();
            YE += 2*RANGE_Y*moveY/IMG.getHeight();
        }

        final double XC = Math.cos(XE);
        final double ZC = Math.sin(XE);

        if(INPUT.isW()) {X += XC*speed; Z += ZC*speed;}
        if(INPUT.isS()) {X -= XC*speed; Z -= ZC*speed;}
        if(INPUT.isA()) {X -= ZC*speed; Z += XC*speed;}
        if(INPUT.isD()) {X += ZC*speed; Z -= XC*speed;}
        if(INPUT.isSpace()) {Y += speed;}
        if(INPUT.isShift()) {Y -= speed;}

        //gridRender(0,0,IMG.getWidth(),IMG.getHeight(),9);
        fullRender();
        //double[][] x_cos_sin = new double[IMG.getWidth()][2];

        //System.out.print("\r" + vec + " <-> " + horVec +" [" + XE + "]   ");

        test_messsage = "(" + X + "," + Y + "," + Z + ")";
        update();
    }

    public void update()
    {
        //PANELG = (Graphics2D)PANEL.getGraphics();
        PANELG.drawImage(IMG,0,0,PANEL);
        //PANELG.drawString(test_messsage,20,20);
    }
}
