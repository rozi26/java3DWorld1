package ShapesList;

import Utils.Processioning;

import java.awt.*;

public class Circle extends ShapeBase {
    protected double radius;
    protected double radiusS;
    public Circle(double _radius, Color _color, double x, double y, double z)
    {
        super(x,y,z,_color);
        radius = _radius;
        radiusS = radius*radius;
    }

    public int getAt(double x, double y, double z, Processioning.ViewVec sourceVec)
    {
        if((x-X)*(x-X)+(y-Y)*(y-Y)+(z-Z)*(z-Z) > radiusS) return -1;
        return color.getRGB();
    }

    public double getLineStep(Processioning.ViewVec line) //for some shapes use prepossess to get if some line will interact with the shape (-1 will not) (-2 unknown)
    {
        final double min = Processioning.closestMoveToPoint(line,X,Y,Z);
        //if(min < 0) return -1;
        final double dis = Processioning.getDisSquare(new double[]{X,Y,Z},line.getMove(min));
        return (dis>radiusS)?-1:min;
    }
}
