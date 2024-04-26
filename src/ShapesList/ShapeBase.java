package ShapesList;

import Utils.Processioning;
import Utils.Utils1;

import java.awt.*;

public abstract class ShapeBase {
    protected double X,Y,Z;
    protected Color color;
    public ShapeBase(double x, double y, double z, Color _color)
    {
        X=x;Y=y;Z=z;
        color = _color;
    }
    public abstract int getAt(double x, double y, double z, Processioning.ViewVec sourceVec); // return -1 if there is no color else return the rgb of the color
    public int getAt(double[] pos, Processioning.ViewVec sourceVec){return getAt(pos[0],pos[1],pos[2],sourceVec);}

    public abstract double getLineStep(Processioning.ViewVec line); //for some shapes use prepossess to get if some line will interact with the shape (-1 will not) (-2 unknown)

    protected double getLocationDem(int dem)
    {
        return switch (dem)
        {
            case 0 -> X;
            case 1 -> Y;
            default -> Z;
        };
    }

    public Utils1.EffectSquare getEffectSquare()
    {
        return null;
    }
}
