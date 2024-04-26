package ShapesList.ShapesHolder;

import ShapesList.ShapeBase;
import Utils.Processioning;
import com.sun.source.tree.BreakTree;

import java.util.ArrayList;
import java.util.List;

public class ListShapeHolder implements IShapeHolder{

    private List<ShapeBase> shapes;
    public ListShapeHolder()
    {
        shapes = new ArrayList<>();
    }

    @Override
    public boolean isIn(ShapeBase shape) {
        return shapes.contains(shape);
    }

    @Override
    public void addShape(ShapeBase shape) {
        shapes.add(shape);
    }

    @Override
    public List<ShapeBase> getAllShapes() {
        return shapes;
    }

    @Override
    public List<ShapeBase> getPossibleHits(Processioning.ViewVec vec) {
        return shapes;
    }


}
