package ShapesList.Planes;

import ShapesList.ShapeBase;
import Utils.Processioning;
import Utils.Utils1;

import java.awt.*;

public class DemPane extends ShapeBase {
    protected double size1;
    protected double size2;
    protected int dem;
    protected int pdem1;
    protected int pdem2;
    public DemPane(int _dem, double _size, Color color, double x, double y, double z)
    {
        super(x,y,z,color);
        size1 = _size;
        size2 = _size;
        dem = _dem;
        pdem1 = dem == 0 ? 1 : 0;
        pdem2 = dem == 2 ? 1 : 2;
    }

    public int getAt(double x,double y, double z, Processioning.ViewVec sourceVec) {return color.getRGB();}
    public double getLineStep(Processioning.ViewVec line)
    {
        if(line.getAt(dem) == 0) return -1;
        double move = (getLocationDem(dem) - line.getSourceAt(dem))/line.getAt(dem);
        if(move < 0) return -1;
        double p1 = line.getMoveDem(pdem1,move);
        if(p1 < getLocationDem(pdem1) || p1 > getLocationDem(pdem1) + size1) return -1;
        double p2 = line.getMoveDem(pdem2,move);
        return (p2 < getLocationDem(pdem2) || p2 > getLocationDem(pdem2) + size2)?-1:move;
    }

    @Override
    public Utils1.EffectSquare getEffectSquare() {
        return new Utils1.EffectSquare(X,Y,Z,X + (dem == 0 ? 0 : size1),Y + (dem == 1 ? 0 : (dem == 0 ? size2 : size1)),Z + (dem == 2 ? 0 : size2));
    }
}
