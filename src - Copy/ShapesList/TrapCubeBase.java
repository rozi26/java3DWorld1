package ShapesList;

import Utils.Processioning;

import java.awt.*;

public abstract class TrapCubeBase extends Cube{

    public TrapCubeBase(double size,double x, double y, double z) {
        super(size,x,y,z);
    }

    abstract public int getAt(double x, double y, double z, Processioning.ViewVec sourceVec);
}
