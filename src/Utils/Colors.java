package Utils;

import java.awt.*;

public class Colors {
    public static interface SmartColor
    {
        public int getAt(double x, double y,double z);
        default public int getAt(double[] res){return getAt(res[0],res[1],res[2]);}
    }

    private static int colorAt(Color c, double x, double y, double z)
    {
        if(c instanceof SmartColor) return ((SmartColor) c).getAt(x,y,z);
        return c.getRGB();
    }
}
