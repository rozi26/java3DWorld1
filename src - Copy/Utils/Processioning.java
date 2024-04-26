package Utils;

import ShapesList.ShapeBase;

import java.awt.*;
import java.util.List;

public class Processioning {

    final static double MAX_DIS = 1000;
    final static double VIEW_STEP = 0.1;

    final public static Colors.SmartColor BACKGROUND_COLOR = new Colors.SmartColor() {@Override
    public int getAt(double x, double y, double z) {
            y -= 10;
            //int b = (int)((y/(10+Math.abs(y))+1)*127);
            //int b = Math.max(0,(int)(2*255*Math.atan(y/1000)/Math.PI));
            //System.out.println(b);
           // return Math.max(Math.min(255,((int)y)>>1),0);
        return 0;
    }};

    public static class Line
    {
        public final double MY,MZ,BY,BZ;
        public Line(double my, double by, double mz, double bz)
        {
            MY = my; BY = by;
            MZ = mz; BZ = bz;
        }
        public Line(double x1, double y1, double z1, double x2, double y2, double z2)
        {
            MY = (y2-y1)/(x2-x1);
            BY = y1-MY*x1;
            MZ = (z2-z1)/(x2-x1);
            BZ = z1-MZ*x1;
        }

        public double[] getMove(double sx, double sy, double sz, double d)
        {
            final double step = Math.sqrt(d*d/(1+MY*MY+MZ*MZ));
            return new double[]{sx+step,sy+MY*step,sz+MZ*step};
        }
    }
    public static class ViewLine extends Line
    {
        final double SX,SY,SZ;
        public ViewLine(Line line, double sx, double sy, double sz)
        {
            super(line.MY,line.BY,line.MZ,line.BZ);
            SX = sx; SY = sy; SZ = sz;
        }
        public double[] getMove(double d)
        {
            return super.getMove(SX,SY,SZ,d);
        }
    }

    public static class Vec
    {
        final public double MX,MY,MZ;
        public Vec(double mx, double my, double mz){MX=mx;MY=my;MZ=mz;}
        public double[] getMove(double sx, double sy, double sz, double d){return new double[]{sx+MX*d,sy+MY*d,sz+MZ*d};}
        public boolean isNormal(){return Math.abs(MX*MX+MY*MY+MZ*MZ-1)<0.0000001;}
        public Vec getNormal()
        {
            final double n = Math.sqrt(1/(MX*MX+MY*MY+MZ*MZ));
            return new Vec(MX*n,MY*n,MZ*n);
        }
        public Vec multiply(double r){return new Vec(MX*r,MY*r,MZ*r);}
        public String toString(){return "(" + MX +"," + MY + "," + MZ +")";}
        public double getAt(int index)
        {
            if (index == 0) return MX;
            else if (index == 1) return  MY;
            return MZ;
        }
    }
    public static class ViewVec extends Vec
    {
        final public double SX,SY,SZ;
        public ViewVec(Vec vec, double sx, double sy, double sz){super(vec.MX,vec.MY,vec.MZ); SX=sx; SY=sy; SZ=sz;}
        public ViewVec(Vec vec, double[] loc){this(vec,loc[0],loc[1],loc[2]);}
        public double[] getMove(double d){return super.getMove(SX,SY,SZ,d);}
        public double getSourceAt(int index)
        {
            if (index == 0) return SX;
            else if (index == 1) return SY;
            return SZ;
        }
        public double getMoveDem(int dem, double move)
        {
            return  getSourceAt(dem) + getAt(dem)*move;
        }
    }

    public static double getDisSquare(double[] p1, double[] p2)
    {
        return (p1[0]-p2[0])*(p1[0]-p2[0])+(p1[1]-p2[1])*(p1[1]-p2[1])+(p1[2]-p2[2])*(p1[2]-p2[2]);
    }
    public static double closestMoveToPoint(ViewVec line, double x, double y, double z) //return how much to move on vec to get closest to some point
    {
        return  -1*(line.MX*(line.SX-x)+line.MY*(line.SY-y)+line.MZ*(line.SZ-z))/(line.MX*line.MX+line.MY*line.MY+line.MZ*line.MZ);
    }

    public static int getColor(List<ShapeBase> shapes, ViewVec line){return getColor(shapes,line,MAX_DIS);}
    public static int getColor(List<ShapeBase> shapes, ViewVec line, double maxDis)
    {
        double best = maxDis;
        int color = -1;
        for(ShapeBase shape:shapes)
        {
            final double lineV = shape.getLineStep(line);
            if(lineV == -1 || lineV >= maxDis) continue;
            if(lineV > 0)
            {
                if (best <= lineV) continue;
                int at = shape.getAt(line.getMove(lineV),line);
                if (at == -1) continue;
                best = lineV; color = at;
                continue;
            }
            //System.out.println("calc 2");
            int step = 0; double dis = 0;
            while (dis < best)
            {
                int c = shape.getAt(line.getMove(dis),line);
                if(c != -1) {color = c;best=dis; break;}
                step++;
                dis += step*VIEW_STEP;
            }
        }
        if(color != -1) //make the color dimmer
        {
            final double rat = 1-best/MAX_DIS;
            //Color c = new Color(color);
            //color = new Color((int)(c.getRed()*rat),(int)(c.getGreen()*rat),(int)(c.getBlue()*rat)).getRGB();
            //final int f = (int)color;
            //return color&255+(((int)(((color>>8)&255)*rat))<<8)+(((int)((color>>16)*rat))<<16);
            return color;
        }
        return color;
    }

}
