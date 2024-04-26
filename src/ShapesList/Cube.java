package ShapesList;

import ShapesList.Planes.DemPane;
import ShapesList.Planes.XPane;
import ShapesList.Planes.YPane;
import ShapesList.Planes.ZPane;
import Utils.Processioning;
import Utils.Utils1;

import java.awt.*;

public class Cube extends ShapeBase {
    ShapeBase[] Panes;
    Color lastColor;
    public Cube(double size, double x, double y, double z)
    {
        super(x,y,z,Color.WHITE);
        final double h = size/2;
        //Panes = new ShapeBase[]{new XPane(size,Color.RED,x+h,y,z),new XPane(size,Color.GREEN,x-h,y,z),new ZPane(size,Color.BLUE,x-h,y,z),new ZPane(size,Color.YELLOW,x-h,y,z+size), new YPane(size,Color.CYAN,x-h,y,z),new YPane(size,Color.MAGENTA,x-h,y+size,z)};
        Panes = new ShapeBase[]{new DemPane(0,size,Color.RED,x+h,y,z),new DemPane(0,size,Color.GREEN,x-h,y,z),new DemPane(2,size,Color.BLUE,x-h,y,z),new DemPane(2,size,Color.YELLOW,x-h,y,z+size), new DemPane(1,size,Color.CYAN,x-h,y,z),new DemPane(1,size,Color.MAGENTA,x-h,y+size,z)};
        lastColor = Panes[0].color;
    }
    public int getAt(double x, double y, double z, Processioning.ViewVec sourceVec){return lastColor.getRGB();}
    public double getLineStep(Processioning.ViewVec line)
    {
        double v1 = Panes[line.MX < 0 ? 0 : 1].getLineStep(line);
        double v2 = Panes[line.MZ > 0 ? 2 : 3].getLineStep(line);
        double v3 = Panes[line.MY > 0 ? 4 : 5].getLineStep(line);
        if (v1 < 0) v1 = Integer.MAX_VALUE;
        if (v2 < 0) v2 = Integer.MAX_VALUE;
        if (v3 < 0) v3 = Integer.MAX_VALUE;
        int m = 0;
        if (v2 < v1) m = 1;
        if (v3 < Math.min(v1,v2)) m = 2;
        if (m == 0 && v1 == Integer.MAX_VALUE) return -1;
        if (m == 0) {lastColor = Panes[line.MX < 0 ? 0 : 1].color; return v1;}
        else if (m == 1) {lastColor = Panes[line.MZ > 0 ? 2 : 3].color; return v2;}
        else {lastColor = Panes[line.MY > 0 ? 4 : 5].color; return v3;}
    }

    @Override
    public Utils1.EffectSquare getEffectSquare() {
        Utils1.EffectSquare effectSquare = Panes[0].getEffectSquare();
        for (int i = 1; i < Panes.length; i++) effectSquare.mergeOther(Panes[i].getEffectSquare());
        return effectSquare;
    }
}
