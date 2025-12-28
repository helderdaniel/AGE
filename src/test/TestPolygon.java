package test;

import geometric.Point;
import geometric.Polygon;
import geometric.Rectangle;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author hdaniel@ualg.pt
 * @version 202502211847
 */
class TestPolygon {

    // Convex polygon
    @org.junit.jupiter.api.Test
    void testCentroid00() {
        List<Point> v = new ArrayList<>();
        v.add(new Point(2, 2));
        v.add(new Point(3, 3));
        v.add(new Point(4, 3));
        v.add(new Point(5, 2));
        v.add(new Point(5, 1));
        v.add(new Point(4, 0));
        v.add(new Point(3, 0));
        v.add(new Point(2, 1));
        Polygon p =  new Polygon(v);

        Point expectedCentroid = new Point(3.5, 1.5);

        //Test centroid
        assertEquals(expectedCentroid, p.centroid());

        //Test centroid convex
        assertEquals(expectedCentroid, Polygon.centroidConvex(v));
    }


    // Concave polygon
    @org.junit.jupiter.api.Test
    void testCentroid01() {
        List<Point> v = new ArrayList<>();
        v.add(new Point(2, 2));
        v.add(new Point(3, 3));
        v.add(new Point(4, 3));
        v.add(new Point(5, 2));
        v.add(new Point(5, 1));
        v.add(new Point(4, 2));
        v.add(new Point(3, 2));
        v.add(new Point(2, 1));
        Polygon p =  new Polygon(v);

        //Test centroid
        Point expectedCentroid = new Point(3.5, 13.0/6);  //2.166 ...
        assertEquals(expectedCentroid, p.centroid());

        //Test centroid convex
        Point wrongExpectedCentroid = new Point(3.5, 2);
        assertEquals(wrongExpectedCentroid, Polygon.centroidConvex(v));
    }


    /*Polygon with 2 concave parts

        /\_
        | _|
        \/
     */
    @org.junit.jupiter.api.Test
    void testCentroid02() {
        List<Point> v = new ArrayList<>();
        v.add(new Point(1, 2));
        v.add(new Point(1, 3));
        v.add(new Point(2, 4));
        v.add(new Point(3, 3));
        v.add(new Point(4, 3));
        v.add(new Point(4, 2));
        v.add(new Point(3, 2));
        v.add(new Point(2, 1));
        Polygon p =  new Polygon(v);

        //Test centroid
        Point expectedCentroid = new Point(2.3, 2.5);  //2.166 ...
        assertEquals(expectedCentroid, p.centroid());

        //Test centroid convex
        Point wrongExpectedCentroid = new Point(2.5, 2.5);
        assertEquals(wrongExpectedCentroid, Polygon.centroidConvex(v));
    }


}