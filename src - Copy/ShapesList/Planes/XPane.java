package ShapesList.Planes;

import ShapesList.ShapeBase;
import Utils.Processioning;

import java.awt.*;

public class XPane extends ShapeBase {
    protected double size;
    public XPane(double _size, Color color, double x, double y, double z) {super(x,y,z,color);size = _size;}
    public int getAt(double x,double y, double z, Processioning.ViewVec sourceVec) {return color.getRGB();}
    public double getLineStep(Processioning.ViewVec line)
    {
        if(line.MX == 0) return -1;
        double move = (X-line.SX)/line.MX;
        if(move < 0) return -1;
        double py = line.SY+line.MY*move;
        if(py < Y || py > Y + size) return -1;
        double pz = line.SZ+line.MZ*move;
        return (pz < Z || pz > Z + size)?-1:move;
    }
}
