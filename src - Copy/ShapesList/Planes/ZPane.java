package ShapesList.Planes;

import ShapesList.ShapeBase;
import Utils.Processioning;

import java.awt.*;

public class ZPane extends ShapeBase {
    protected double size;
    public ZPane(double _size, Color color, double x, double y, double z) {super(x,y,z,color);size = _size;}
    public int getAt(double x,double y, double z, Processioning.ViewVec sourceVec) {return color.getRGB();}
    public double getLineStep(Processioning.ViewVec line)
    {
        if(line.MZ == 0) return -1;
        double move = (Z-line.SZ)/line.MZ;
        if(move < 0) return -1;
        double py = line.SY+line.MY*move;
        if(py < Y || py > Y + size) return -1;
        double px = line.SX+line.MX*move;
        return (px < X || px > X + size)?-1:move;
    }
}
