package ShapesList.ShapesHolder;

import ShapesList.ShapeBase;
import Utils.Processioning;
import Utils.Utils1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GridShapeHolder1 implements IShapeHolder{

    private final int xSpan, ySpan, zSpan;
    private final double squareSize;
    protected Utils1.RangeArray<Utils1.RangeArray<Utils1.RangeArray<IShapeHolder>>> grid;
    protected List<ShapeBase> out;
    protected List<ShapeBase> all;
    protected final Utils1.EffectSquare gridEffectSquare;

    private int temp = 0;

    public GridShapeHolder1(double _squareSize, int _xSpan, int _ySpan, int _zSpan)
    {
        xSpan = _xSpan; ySpan = _ySpan; zSpan = _zSpan;
        squareSize = _squareSize;
        grid = new Utils1.RangeArray<Utils1.RangeArray<Utils1.RangeArray<IShapeHolder>>>(-zSpan,zSpan);
        for (int z = grid.start; z < grid.end; z++)
        {
            grid.set(z,new Utils1.RangeArray<Utils1.RangeArray<IShapeHolder>>(-ySpan,ySpan));
            for (int y = -ySpan; y < ySpan; y++)
            {
                grid.get(z).set(y,new Utils1.RangeArray<IShapeHolder>(-xSpan,xSpan));
                for (int x = -xSpan; x < xSpan; x++) grid.get(z).get(y).set(x,new ListShapeHolder());
            }
        }
        out = new ArrayList<>();
        all = new ArrayList<>();
        gridEffectSquare = new Utils1.EffectSquare(-xSpan*squareSize,-ySpan*squareSize,-zSpan*squareSize,xSpan*squareSize,ySpan*squareSize,zSpan*squareSize);
    }
    public GridShapeHolder1(int girdSize, int span)
    {
        this(girdSize,span,span,span);
    }

    @Override
    public boolean isIn(ShapeBase shape) {
        return all.contains(shape);
    }

    @Override
    public void addShape(ShapeBase shape) {
        all.add(shape);
        Utils1.EffectSquare effectSquare = shape.getEffectSquare();
        if (effectSquare == null || !gridEffectSquare.isContain(effectSquare))
        {
            out.add(shape);
            return;
        }
        int pt = temp;
        int x1 = (int)(effectSquare.x1 / squareSize); int x2 = (int)Math.ceil(effectSquare.x2 / squareSize);
        int y1 = (int)(effectSquare.y1 / squareSize); int y2 = (int)Math.ceil(effectSquare.y2 / squareSize);
        int z1 = (int)(effectSquare.z1 / squareSize); int z2 = (int)Math.ceil(effectSquare.z2 / squareSize);
        for (int z = z1; z < z2; z++) for (int y = y1; y < y2; y++) for (int x = x1; x < x2; x++)
        {
            grid.get(z).get(y).get(x).addShape(shape);
            System.out.println("add at (" + x + "," + y + "," + z + ")");
            temp++;
        }
        //System.out.println("add the shape is " + (temp - pt) + " places sum " + temp + " (from x " + x1 + " - " + x2 + ",)");
    }


    @Override
    public List<ShapeBase> getAllShapes() {
        return all;
    }

    @Override
    public List<ShapeBase> getPossibleHits(Processioning.ViewVec vec) {
        if (vec.MX == 0 || vec.MY == 0 || vec.MZ == 0) return all;
        final int dx = vec.MX > 0 ? 1 : -1;
        final int dy = vec.MY > 0 ? 1 : -1;
        final int dz = vec.MZ > 0 ? 1 : -1;
        double m = 0;
        double[] loc = vec.getMove(0);
        List<ShapeBase> shapes = new ArrayList<>();
        for (int i = 0; i < 20; i++)
        {
            int x = (int)(loc[0] / squareSize);
            int y = (int)(loc[1] / squareSize);
            int z = (int)(loc[2] / squareSize);
            //System.out.println("at (" + x + "," + y + "," + z + ")");
            if (Math.abs(x) >= xSpan || Math.abs(y) >= ySpan || Math.abs(z) >= zSpan) break;
            shapes.addAll(grid.get(z).get(y).get(x).getAllShapes());
            double tx = ((x+dx)*squareSize - loc[0]) / vec.MX;
            double ty = ((y+dy)*squareSize - loc[1]) / vec.MY;
            double tz = ((z+dz)*squareSize - loc[2]) / vec.MZ;
            m  += Math.min(tx,Math.min(ty,tz));
            /*double tx = ((x+dx)*squareSize - vec.SX) / vec.MX;
            double ty = ((y+dy)*squareSize - vec.SY) / vec.MY;
            double tz = ((z+dz)*squareSize - vec.SZ) / vec.MZ;
            m  = Math.min(tx,Math.min(ty,tz));*/
            //m += 0.1;
            loc = vec.getMove(m);
        }
        //System.out.println("done\n");

        shapes = new HashSet<ShapeBase>(shapes).stream().toList();
        List<ShapeBase> res = new ArrayList<>();
        res.addAll(shapes);
        res.addAll(out);
        return res;
    }

}
