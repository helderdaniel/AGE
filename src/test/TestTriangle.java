package test;

import geometric.Point;
import geometric.Polygon;
import geometric.Rectangle;
import geometric.Triangle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author hdaniel@ualg.pt
 * @version 202502211847
 */
class TestTriangle {

    // parallel to axis triangle rectangle
    @org.junit.jupiter.api.Test
    void testConstructor00() {
        Point a = new Point(0, 0);
        Point b = new Point(0, 1);
        Point c = new Point(1, 0);
        new Triangle(a, b, c);
    }

    // rotated triangle
    @org.junit.jupiter.api.Test
    void testConstructor01() {
        Point a = new Point(1, 1);
        Point b = new Point(2, 2);
        Point c = new Point(2, 3);
        new Triangle(a, b, c);
    }

    // coolinear points
    @org.junit.jupiter.api.Test
    void testConstructorFail02() {
        Point a = new Point(0, 0);
        Point b = new Point(1, 1);
        Point c = new Point(2, 2);
        assertThrows(IllegalArgumentException.class, () -> new Triangle(a, b, c));
    }

    // rotated triangle
    @org.junit.jupiter.api.Test
    void testCentroid01() {
        Point a = new Point(1, 1);
        Point b = new Point(2, 2);
        Point c = new Point(2, 3);
        Triangle t = new Triangle(a, b, c);
        Point expected = new Point(5.0/3, 2);
        assertEquals(expected, t.centroid());

        //Compare to Polygon centroid general method
        Polygon p = new Polygon(a, b, c);
        assertEquals(expected, p.centroid());
    }

}