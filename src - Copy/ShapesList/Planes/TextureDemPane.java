package ShapesList.Planes;

import Utils.Processioning;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TextureDemPane extends DemPane{
    protected BufferedImage img;

    public int rotateMode;
    public int viewSide = 1; //if -1 can see only from negative vec, if 0 from each if 1 from positive vec

    public TextureDemPane(int dem, int size,double x, double y, double z, BufferedImage _img, Color color)
    {
        super(dem,size, color,x,y,z);
        img = _img;
        rotateMode = 2;
    }

    @Override
    public int getAt(double x, double y, double z, Processioning.ViewVec sourceVec) {
        if ((viewSide == 1 && sourceVec.getAt(dem) < 0) || (viewSide == -1 && sourceVec.getAt(dem) > 0)) return color.getRGB();
        double[] locs = new double[]{x,y,z};
        if (rotateMode % 4 == 1) {double t = locs[pdem1]; locs[pdem1] = locs[pdem2]; locs[pdem2] = t;}
        if (rotateMode % 4 == 2) {locs[pdem1] = 2*getLocationDem(pdem1)+size1-locs[pdem1];}
        double r1 = (locs[pdem1]-getLocationDem(pdem1)) / size1;
        double r2 = (locs[pdem2]-getLocationDem(pdem2)) / size2;
        try
        {
            return img.getRGB((int)(r2*img.getWidth()),(int)(r1*img.getHeight()));
        }
        catch (Exception e)
        {
            return -1;
        }
    }
}
