package ShapesList.Planes;

import ShapesList.ShapeBase;
import Utils.Processioning;

import java.awt.*;

public class SidePane extends ShapeBase {

    protected int dem;
    protected double size1;
    protected double size2;
    public SidePane(Processioning.Vec v1, int _dem, double _size1, double _size2, double x, double y, double z, Color color)
    {
        super(x,y,z,color);
        dem = _dem;
        size1 = _size1;
        size2 = _size2;
    }

    @Override
    public int getAt(double x, double y, double z, Processioning.ViewVec sourceVec) {
        return color.getRGB();
    }

    @Override
    public double getLineStep(Processioning.ViewVec line) {
        return 0;
    }
}
