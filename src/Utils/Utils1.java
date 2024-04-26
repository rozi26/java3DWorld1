package Utils;

import java.util.ArrayList;
import java.util.List;

public class Utils1 {

    public static class EffectSquare
    {
        public double x1,y1,z1,x2,y2,z2;
        public EffectSquare(double _x1, double _y1, double _z1, double _x2, double _y2, double _z2)
        {
            x1 = Math.min(_x1,_x2); x2 = Math.max(_x1,_x2);
            y1 = Math.min(_y1,_y2); y2 = Math.max(_y1,_y2);
            z1 = Math.min(_z1,_z2); z2 = Math.max(_z1,_z2);
        }

        public EffectSquare getWithMargin(double margin)
        {
            return new EffectSquare(x1-margin,y1-margin,z1-margin,x2+margin,y2+margin,z2+margin);
        }

        public void mergeOther(EffectSquare other)
        {
            x1 = Math.min(x1,other.x1); x2 = Math.max(x2, other.x2);
            y1 = Math.min(y1,other.y1); y2 = Math.max(y2, other.y2);
            z1 = Math.min(z1,other.z1); z2 = Math.max(z2, other.z2);
        }

        public boolean isTouch(EffectSquare other)
        {
            if (x2 < other.x1 || x1 > other.x2) return false;
            if (y2 < other.y1 || y1 > other.y2) return false;
            if (z2 < other.z1 || z1 > other.z2) return false;
            return true;
        }

        public boolean isContain(EffectSquare other) //is the currect square containe other square
        {
            if (other.x1 < x1 || other.x2 > x2) return false;
            if (other.y1 < y1 || other.y2 > y2) return false;
            if (other.z1 < z1 || other.z2 > z2) return false;
            return true;
        }

        @Override
        public String toString() {
            return "qube from (" + x1 + "," + y1 + "," + z1 + ") to (" + x2 + "," + y2 + "," + z2 + ")";
        }
    }

    public static class RangeArray<T>
    {
        public final int start;
        public final int end;
        private final List<T> vals;
        public RangeArray(int _start, int _end)
        {
            start = _start;
            end = _end;
            vals = new ArrayList<T>();
            for (int i = start; i < end; i++) vals.add(null);
        }

        public T get(int inx)
        {
            return vals.get(inx - start);
        }

        public void set(int inx, T val)
        {
            vals.set(inx - start, val);
        }

    }
}
