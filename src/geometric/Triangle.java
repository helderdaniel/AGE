package geometric;

import geometric.Point;

/**
 * @author hdaniel@ualg.pt
 * @version 202502091140
 *
 * @inv The vertices must not be collinear
 */
public class Triangle extends Polygon {

    /**
     * Constructs a triangle from 3 points sorted anti-clockwise
     * @param a first vertex
     * @param b next anti-clockwise vertex to a
     * @param c next anti-clockwise vertex to b     */
    public Triangle(Point a, Point b, Point c) {
        super(a, b, c);

        if (areCollinear()) {
            throw new IllegalArgumentException("Triangle:iv");
        }
    }

    /**
     * @return true if the 3 vertex are not on the same line
     */
    private boolean areCollinear() {
        Point a = vertex.get(0);
        Point b = vertex.get(1);
        Point c = vertex.get(2);
        return a.x() * (b.y() - c.y()) + b.x() * (c.y() - a.y()) + c.x() * (a.y() - b.y()) == 0;
    }

    /**
     * @return the centroid of a triangles from the average of the vertex coordinates
     *         works for convex figures, not for concave
     *
     *         optimezed
     */
    @Override
    public Point centroid() {
        //return Polygon.centroidConvex(vertex);

        //optimized version without loop
        double x= vertex.get(0).x() + vertex.get(1).x() + vertex.get(2).x();
        double y= vertex.get(0).y() + vertex.get(1).y() + vertex.get(2).y();
        return new Point(x/3, y/3);
    }

}
