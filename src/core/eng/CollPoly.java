package core.eng;

import core.gui.IPainter;
import geometric.Circle;
import geometric.Point;
import geometric.Polygon;

import java.awt.*;
import java.util.List;

/**
 * @author hdaniel@ualg.pt
 * @version 202503081039
 */
public class CollPoly extends Collider {

    public CollPoly(Collider other) { super(other); }
    public CollPoly(IPainter painter, ITransform t, List<Point> vertex, String name) { this(painter, t, vertex, name, Color.RED); }
    public CollPoly(IPainter painter, ITransform t, List<Point> vertex, String name, Color color) {
        super(painter, t, name, color);

        //move figure, rotate and scale according to transform
        figure = new Polygon(vertex);
        figure.onUpdate(t);
    }


    @Override
    public boolean isColliding(ICollider other) { return other.isColliding(this); }

    @Override
    public boolean isColliding(CollCircle other) {
        return ((Polygon) figure).isColliding((Circle) other.figure);
    }

    @Override
    public boolean isColliding(CollPoly other) {
        return ((Polygon) figure).isColliding((Polygon) other.figure);
    }

    @Override
    public ICollider copy() { return new CollPoly(this); }

    @Override
    public void paint() {
        Polygon p = (Polygon) figure();

        painter.drawPolygon(
                p.xcoords(),
                p.ycoords(),
                color, false);
    }

}
