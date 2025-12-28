package core.gui;

import core.eng.ITransform;
import geometric.Point;
import java.util.List;
import java.awt.*;
import java.util.ArrayList;
import geometric.Polygon;

/**
 * Represents an image that can be painted on the screen
 * @author hdaniel@ualg.pt
 * @version 202502151124
 *
 * @inv polygon centroid is at (0,0)
 */
public class ShapePolygon extends Shape {

    protected List<Point> vertex = new ArrayList<>();
    protected Color color;
    //protected Point centroidOffset;

    public ShapePolygon(IPainter painter, ITransform t, List<Point> vertex, Color color) { this(painter, t, vertex, color, false); }
    public ShapePolygon(IPainter painter, ITransform t, List<Point> vertex, Color color, boolean fill) {
        super(painter, t, fill);

        //move polygon to (0,0)
        Point c = Polygon.centroid(vertex);
        //Polygon.move(vertex, -c.x(), -c.y());

        this.vertex = vertex;
        this.color  = color;
    }


    public ShapePolygon(ShapePolygon other) {
        super(other);
        this.color = other.color;

        //todo: Needed? The shape is immutable?
        //this.vertices = other.vertices;
        this.vertex = new ArrayList<>();
        for (Point p : other.vertex)
            this.vertex.add(p);
    }


    @Override
    public void paint() {
        //Point c = Polygon.centroid(vertex);

        //draw image at center position (0,0) since polygon was moved in constructor
        int[] xCoor = new int[vertex.size()];
        int[] yCoor = new int[vertex.size()];
        for (int i = 0; i < vertex.size(); i++) {
            xCoor[i] = (int) (vertex.get(i).x());//-c.x());
            yCoor[i] = (int) (vertex.get(i).y());//-c.y());
        }

        painter.drawPolygon(transform, xCoor, yCoor, color, fill);
    }

    @Override
    public IShape copy() { return new ShapePolygon(this); }
}
