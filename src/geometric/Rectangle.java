package geometric;

import geometric.Point;

/**
 * @author hdaniel@ualg.pt
 * @version 202502091140
 *
 * @inv: all 4 angles must have 90º
 */
public class Rectangle extends Polygon {

    /**
     * Constructs a rectangle from 4 points sorted anti-clockwise
     * @param a first vertex
     * @param b next anti-clockwise vertex to a
     * @param c next anti-clockwise vertex to b
     * @param d next anti-clockwise vertex to c
     */
    public Rectangle(Point a, Point b, Point c, Point d) {
        super(a, b, c, d);
        if (!isRectangle())
            throw new IllegalArgumentException("Rectangle:iv");
    }

    /**
     * @return true if the 4 vertex form a rectangle
     */
    private boolean isRectangle() {
        if( vertex.get(0).distance(vertex.get(2)) ==
            vertex.get(1).distance(vertex.get(3))) return true;
        return false;
    }

    /**
     * @return the centroid of a rectangle from the average of the vertex coordinates
     *         works for convex figures, not for concave
     */
    @Override
    public Point centroid() {
        //return Polygon.centroidConvex(vertex);

        //optimized version without loop
        double x= vertex.get(0).x() + vertex.get(1).x() + vertex.get(2).x() + vertex.get(3).x();
        double y= vertex.get(0).y() + vertex.get(1).y() + vertex.get(2).y() + vertex.get(3).y();
        return new Point(x/4, y/4);
    }



}
