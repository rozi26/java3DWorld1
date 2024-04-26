package ShapesList.Complex;

import ShapesList.Planes.DemPane;
import ShapesList.ShapeBase;
import Utils.Processioning;

import java.awt.*;

public class Sierpinski extends DemPane {

    private int levels;
    public Sierpinski(int _dem, double _size, Color color, double x, double y, double z, int _levels) {
        super(_dem, _size, color, x, y, z);
        levels = _levels;
    }

    private boolean isIn(double rx, double ry, int level)
    {
        double px = Math.min(rx,1-rx);
        if (px*2 < 1-ry) return false;
        if (level == 0) return true;
        if (ry >= 0.5 && 2*px>ry) return false;
        if (ry < 0.5) return isIn(2*(px-0.25),2*ry,level-1);
        return isIn(px*2,2*(ry-0.5),level-1);
    }

    @Override
    public int getAt(double x, double y, double z, Processioning.ViewVec sourceVec) {
        double px = dem == 0 ? y : x;
        double py = dem == 2 ? y : z;
        if(isIn((px-getLocationDem(pdem1))/size1,(py-getLocationDem(pdem2))/size2,levels)) return color.getRGB();
        else return -1;
    }
}
