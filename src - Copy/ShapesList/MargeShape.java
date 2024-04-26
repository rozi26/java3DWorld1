package ShapesList;

import Utils.Processioning;

import java.awt.*;

public class MargeShape extends ShapeBase{
    public ShapeBase[] shapes;
    public MargeShape(ShapeBase... _shapes)
    {
        super(0,0,0,Color.WHITE);
        shapes = new ShapeBase[_shapes.length];
        int i = 0;
        for(ShapeBase s:_shapes){shapes[i] = s; i++;}
    }
    public int getAt(double x, double y, double z, Processioning.ViewVec sourceVec)
    {
        for(ShapeBase s:shapes)
        {
            int c = s.getAt(x,y,z,sourceVec);
            if(c != -1) return c;
        }
        return -1;
    }
    public double getLineStep(Processioning.ViewVec line)
    {
        double bestV = -1; int bestI = -1; int i = 0;
        for(ShapeBase s: shapes)
        {
            double v = s.getLineStep(line);
            if(v == -2) continue;
            if(v > 0 && (bestI == -1 || bestV > v)) {bestV = v; bestI = i;}
            i++;
        }
        return bestV;
    }

}
