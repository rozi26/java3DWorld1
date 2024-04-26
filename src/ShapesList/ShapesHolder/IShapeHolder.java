package ShapesList.ShapesHolder;

import ShapesList.ShapeBase;
import Utils.Processioning;

import java.awt.*;
import java.util.List;

public interface IShapeHolder {
    public boolean isIn(ShapeBase shape);
    public void addShape(ShapeBase shape);
    public List<ShapeBase> getAllShapes();
    public List<ShapeBase> getPossibleHits(Processioning.ViewVec vec);
}
