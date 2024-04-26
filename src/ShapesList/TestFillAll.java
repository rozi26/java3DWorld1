package ShapesList;

import Utils.Processioning;
import Utils.Utils1;

import java.awt.*;

public class TestFillAll extends ShapeBase{
    public TestFillAll() {
        super(0,0,0,Color.RED);
    }

    @Override
    public int getAt(double x, double y, double z, Processioning.ViewVec sourceVec) {
        return Color.RED.getRGB();
    }

    @Override
    public double getLineStep(Processioning.ViewVec line) {
        return 0;
    }

    @Override
    public Utils1.EffectSquare getEffectSquare() {
        return new Utils1.EffectSquare(0,0,0,1,1,1);
    }
}
