package test;

import geometric.Point;
import geometric.Polygon;
import geometric.Rectangle;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author hdaniel@ualg.pt
 * @version 202502211847
 */
class TestRectangle {

    // parallel to axis rectangle
    @org.junit.jupiter.api.Test
    void testConstructor00() {
        Point a = new Point(0, 0);
        Point b = new Point(0, 1);
        Point c = new Point(1, 1);
        Point d = new Point(1, 0);
        new Rectangle(a, b, c, d);
    }

    // Square rotated 45º
    @org.junit.jupiter.api.Test
    void testConstructor01() {
        Point a = new Point(0, 0);
        Point b = new Point(1, 1);
        Point c = new Point(2, 0);
        Point d = new Point(1, -1);
        new Rectangle(a, b, c, d);
    }

    // elongated diamond
    @org.junit.jupiter.api.Test
    void testConstructorFail02() {
        Point a = new Point(0, 0);
        Point b = new Point(2, 1);
        Point c = new Point(4, 0);
        Point d = new Point(2, -1);
        assertThrows(IllegalArgumentException.class, () -> new Rectangle(a, b, c, d));
    }

    // parallel to axis rectangle
    @org.junit.jupiter.api.Test
    void testCentroid00() {
        Point a = new Point(0, 0);
        Point b = new Point(0, 1);
        Point c = new Point(1, 1);
        Point d = new Point(1, 0);
        Point expected = new Point(0.5, 0.5);
        Rectangle r =  new Rectangle(a, b, c, d);
        assertEquals(expected, r.centroid());

        //Compare to Polygon centroid general method
        Polygon p = new Polygon(a, b, c, d);
        assertEquals(expected, p.centroid());
    }

    // Square rotated 45º
    @org.junit.jupiter.api.Test
    void testCentroid01() {
        Point a = new Point(0, 0);
        Point b = new Point(1, 1);
        Point c = new Point(2, 0);
        Point d = new Point(1, -1);
        Point expected = new Point(1, 0);
        Rectangle r =  new Rectangle(a, b, c, d);
        assertEquals(expected, r.centroid());

        //Compare to Polygon centroid general method
        Polygon p = new Polygon(a, b, c, d);
        assertEquals(expected, p.centroid());
    }

    // Square rotated 45º
    @org.junit.jupiter.api.Test
    void testCentroid02() {
        Point a = new Point(-1, -1);
        Point b = new Point(-1, -2);
        Point c = new Point(-2, -2);
        Point d = new Point(-2, -1);
        Point expected = new Point(-1.5, -1.5);
        Rectangle r =  new Rectangle(a, b, c, d);
        assertEquals(expected, r.centroid());

        //Compare to Polygon centroid general method
        Polygon p = new Polygon(a, b, c, d);
        assertEquals(expected, p.centroid());
    }

}