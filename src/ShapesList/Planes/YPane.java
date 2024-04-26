package ShapesList.Planes;

import ShapesList.ShapeBase;
import Utils.Processioning;

import java.awt.*;

public class YPane extends ShapeBase {
    protected double size;
    public YPane(double _size, Color color, double x, double y, double z) {super(x,y,z,color);size = _size;}
    public int getAt(double x,double y, double z, Processioning.ViewVec sourceVec) {return color.getRGB();}
    public double getLineStep(Processioning.ViewVec line)
    {
        if(line.MY == 0) return -1;
        double move = (Y-line.SY)/line.MY;
        if(move < 0) return -1;
        double px = line.SX+line.MX*move;
        if(px < X || px > X + size) return -1;
        double pz = line.SZ+line.MZ*move;
        return (pz < Z || pz > Z + size)?-1:move;
    }
}
