package test;

import core.eng.*;
import geometric.Point;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author hdaniel@ualg.pt
 * @version 202506281105
 */
public class TestGameObject {

    static protected boolean debug =true;
    IGameEngine engine = new GameEngine(new GameWorldFlat(), null);

    void assertGameObject(IGameObject go, String name, Transform t, IGameEngine engine,
                          ICollider[] colliders) {
        assertNotNull(go);
        assertEquals(name, go.name());
        assertEquals(t, go.transform());
        assertEquals(engine, go.engine());
        assertArrayEquals(colliders, go.colliders());
    }

    void assertGameObjectString(IGameObject go, String name, String t, String c) {
        String expected = name + '\n' + t + '\n' + c;
        assertEquals(expected, go.toString());
        if (debug)
            System.out.println(go);
    }

    void transformGameObject(IGameObject go, double dx, double dy, int dl, double dTheta, double dScale) {

        go.transform().move(dx, dy, dl);
        go.colliders()[0].figure().move(dx, dy);

        go.transform().rotate(dTheta);
        go.colliders()[0].figure().rotate(dTheta);

        go.transform().scale(dScale, dScale);
        dScale = go.transform().xScale();
        go.colliders()[0].figure().scale(dScale, dScale);
    }

    @ParameterizedTest
    @CsvSource({
            "go0, 0,0,2,45,2",
            "go1, -10,-9,-1,-90,3",
            "go2, -1.5,-3.2,0,-45.3,3.5"})
    void testGameObjectCreationNoCollidersNoShape(String name, double x, double y, int layer, double angle, double scale) {

        Transform   t  = new Transform(x, y, layer, angle, scale, scale);
        IGameObject go = new GameObject(engine, name, t, null, null);

        assertGameObject(go, name, t, engine, null);
        assertGameObjectString(go, name, t.toString(), "no colliders");
    }


    @ParameterizedTest
    @CsvSource({
            "Alien01,   1,2,1,0,1,     2,2,3,   0,0,0,0,0",
            "Alien02,   3,7,2,45.6,2,  1,2,3,   0,0,0,0,0",
            "Alien01,   1,2,1,0,1,     2,2,3,   1,1,0,90,0",

            "Alien02,   0,0,2,0,1,     1,2,3,   3,7,2,0,1",
            "Alien01,   1,2,1,0,1,     2,2,3,   1,1,0,90,0",
    })
    void testGameObjectCreationCircleColliderNoShape(
            String name, double x, double y, int layer, double angle, double scale,
            double cx, double cy, double r,
            double dx, double dy, int dl, double dTheta, double dScale) {

        Transform t    = new Transform(x, y, layer, angle, scale, scale);
        //Center collider in transform, ignoring cx yx
        ICollider[] c  = { new CollCircle(null, t, r, "") };
        IGameObject go = new GameObject(engine, name, t, null, c);

        assertGameObject(go, name, t, engine, c);
        transformGameObject(go, dx, dy, dl, dTheta, dScale);
        assertGameObjectString(go, name, t.toString(), c[0].toString());
    }

    //"Alien01\n(2.00,3.00) 1 90.00 1.00\n(2.00,3.00) 3.00"
    //"Alien02\n(3.00,7.00) 4 0.00 2.00\n(3.00,7.00) 6.00"

    @ParameterizedTest
    @CsvSource({
            "PlayerOne, 5,9,0,90,2,     2,2,2,6,4,6,4,2,    0,0,0,0,0",
            "Player2,   6,6,0,0,1,      2,2,2,6,4,6,4,2,    0,0,0,0,0",
            "Player2,   6,6,0,90,1,     2,2,2,6,4,6,4,2,    0,0,0,0,0",
            "Player2,   6,6,0,90,2,     2,2,2,6,4,6,4,2,    0,0,0,0,0",

            "PlayerOne, 7,9,0,0,1,      2,2,2,6,4,6,4,2,    3,7,2,90,1",
            "PlayerOne, 7,9,0,0,1,     -2,0,2,6,4,6,4,2,    3,7,2,90,1",
            "Player2,   3,3,0,0,1,      0,0,0,2,2,2,2,0,    1,1,0,45,0",
            "Monster,   3,6,0,90,1,     0,0,0,2,2,2,2,0,    1,1,0,-90,0"
    })
    void testGameObjectCreationPolyColliderNoShape(
            String name, double x, double y, int layer, double angle, double scale,
            double vx0, double vy0, double vx1, double vy1, double vx2, double vy2, double vx3, double vy3,
            double dx, double dy, int dl, double dTheta, double dScale) {

        Transform t    = new Transform(x, y, layer, angle, scale, scale);

        List<Point> pl = List.of(
            new Point(vx0, vy0),
            new Point(vx1, vy1),
            new Point(vx2, vy2),
            new Point(vx3, vy3)
        );
        ICollider[] c  = { new CollPoly(null, t, pl, "") };
        IGameObject go = new GameObject(engine, name, t, null, c);

        assertGameObject(go, name, t, engine, c);
        transformGameObject(go, dx, dy, dl, dTheta, dScale);
        assertGameObjectString(go, name, t.toString(), c[0].toString());
    }

}
