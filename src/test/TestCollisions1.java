package test;

/**
 * @author hdaniel@ualg.pt
 * @version 202501310756
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

import core.eng.*;
import geometric.Circle;
import geometric.Point;
import geometric.Polygon;


/*
    Create 2 objects
 */

public class TestCollisions1 {

    ITransform t0, t1;
    Point[] v0, v1;

    @BeforeEach
    void setUp() {
        t0 = new Transform(new Point(0, 0), 0, 0, 1, 1);
        t1 = new Transform(new Point(4, 2), 0, 0, 1, 1);
        v0 = new Point[]{new Point(3, 0), new Point(2, 1), new Point(2, 2)};
        v1 = new Point[] {new Point(3,0), new Point(-4,1), new Point(2,2) };
    }

    @Test
    void testCollider01() {
        CollCircle cc1 = new CollCircle(null, t0, 2, "");
        CollPoly cp1 = new CollPoly(null, t1, new ArrayList<>(List.of(v0)), "");
        assertFalse(cc1.isColliding(cp1));
    }

    @Test
    void testGeometricCollision01() {
        Circle c1 = new Circle(new Point(0, 0), 2);
        Polygon p1 = new Polygon(v0);
        assertFalse(c1.isColliding(p1));
    }

    @Test
    void testCollider02() {
        CollCircle cc2 = new CollCircle(null, t0, 2, "");
        CollPoly   cp2 = new CollPoly(null, t1, new ArrayList<>(List.of(v1)), "");
        assertTrue(cc2.isColliding(cp2));  //true
    }

    @Test
    void testGeometricCollision02() {
        Circle  c2 = new Circle(new Point(0,0), 2);
        Polygon p2 = new Polygon(v1);
        assertTrue(c2.isColliding(p2));  //true
    }

}