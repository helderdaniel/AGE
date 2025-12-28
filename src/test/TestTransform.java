package test;

/**
 * @author hdaniel@ualg.pt
 * @version 202502040659
 */
import core.eng.Transform;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestTransform {

    @Test
    void testConstructorWithAllParameters() {
        Transform t = new Transform(1.0f, 2.0f, 3, 45.0f, 0.25f, 5);
        assertEquals(1.0f, t.x());
        assertEquals(2.0f, t.y());
        assertEquals(3, t.layer());
        assertEquals(45.0f, t.angle());
        assertEquals(0.25f, t.xScale());
        assertEquals(5, t.yScale());
    }

    @Test
    void testConstructorWithNegativeAngle() {
        Transform t = new Transform(1.0f, 2.0f, 3, -45.0f, 0.25f, 5);
        assertEquals(1.0f, t.x());
        assertEquals(2.0f, t.y());
        assertEquals(3, t.layer());
        assertEquals(315.0f, t.angle());
        assertEquals(0.25f, t.xScale());
        assertEquals(5, t.yScale());
    }

    @Test
    void testConstructorWithAngleGreaterThan360() {
        Transform t = new Transform(1.0f, 2.0f, 3, 405.0f, 0.25f, 5);
        assertEquals(1.0f, t.x());
        assertEquals(2.0f, t.y());
        assertEquals(3, t.layer());
        assertEquals(45.0f, t.angle());
        assertEquals(0.25f, t.xScale());
        assertEquals(5, t.yScale());
    }


    @Test
    void testConstructorWithNegativeAngleGreaterThan360() {
        Transform t = new Transform(1.0f, 2.0f, 3, -445.0f, 0.25f, 5);
        assertEquals(1.0f, t.x());
        assertEquals(2.0f, t.y());
        assertEquals(3, t.layer());
        assertEquals(275.0f, t.angle());
        assertEquals(0.25f, t.xScale());
        assertEquals(5, t.yScale());
    }

    @Test
    void testConstructorWithoutLayer() {
        Transform t = new Transform(1.0f, 2.0f, 45.0f);
        assertEquals(1.0f, t.x());
        assertEquals(2.0f, t.y());
        assertEquals(0, t.layer());
        assertEquals(45.0f, t.angle());
        assertEquals(1.0f, t.xScale());
        assertEquals(1.0f, t.yScale());
    }

    @Test
    void testConstructorWithoutLayerAndAngle() {
        Transform t = new Transform(1.0f, 2.0f);
        assertEquals(1.0f, t.x());
        assertEquals(2.0f, t.y());
        assertEquals(0, t.layer());
        assertEquals(0.0f, t.angle());
        assertEquals(1.0f, t.xScale());
        assertEquals(1.0f, t.yScale());
    }

    @Test
    void testCopyConstructor() {
        Transform original = new Transform(1.0f, 2.0f, 3, 45.0f, 0.25f, 5);
        Transform copy = new Transform(original);
        assertEquals(original, copy);
        assertNotSame(original, copy);
    }

    @Test
    void testMoveWithLayer() {
        Transform t = new Transform(1.0f, 2.0f, 3, 45.0f, 0.25f, 5);
        t.move(1.0f, 1.0f, 1);
        assertEquals(2.0f, t.x());
        assertEquals(3.0f, t.y());
        assertEquals(4, t.layer());
        assertEquals(45.0f, t.angle());
        assertEquals(0.25f, t.xScale());
        assertEquals(5, t.yScale());
    }

    @Test
    void testMoveWithoutLayer() {
        Transform t = new Transform(1.0f, 2.0f, 3, 45.0f, 0.25f, 5);
        t.move(1.0f, 1.0f);
        assertEquals(2.0f, t.x());
        assertEquals(3.0f, t.y());
        assertEquals(3, t.layer());
        assertEquals(45.0f, t.angle());
        assertEquals(0.25f, t.xScale());
        assertEquals(5, t.yScale());
    }

    @Test
    void testRotate00() {
        Transform t = new Transform(1.0f, 2.0f, 3, 45.0f,  0.25f, 5);
        t.rotate(15.0f);
        assertEquals(1.0f, t.x());
        assertEquals(2.0f, t.y());
        assertEquals(3, t.layer());
        assertEquals(60.0f, t.angle());
        assertEquals(0.25f, t.xScale());
        assertEquals(5, t.yScale());
    }

    @Test
    void testRotate01() {
        Transform t = new Transform(1.0f, 2.0f, 3, 45.0f, 0.25f, 5);
        t.rotate(425.0f);
        assertEquals(1.0f, t.x());
        assertEquals(2.0f, t.y());
        assertEquals(3, t.layer());
        assertEquals(110.0f, t.angle());
        assertEquals(0.25f, t.xScale());
        assertEquals(5, t.yScale());
    }

    @Test
    void testRotate02() {
        Transform t = new Transform(1.0f, 2.0f, 3, 45.0f, 0.25f, 5);
        t.rotate(-425.0f);
        assertEquals(1.0f, t.x());
        assertEquals(2.0f, t.y());
        assertEquals(3, t.layer());
        assertEquals(340.0f, t.angle());
        assertEquals(0.25f, t.xScale());
        assertEquals(5, t.yScale());
    }

    @Test
    void testScale() {
        Transform t = new Transform(1.0f, 2.0f, 3, 45.0f, 0.25f, 5);
        t.scale(0.5f, 0.5f);
        assertEquals(0.75f, t.xScale());
        assertEquals(5.5f, t.yScale());
    }

    @Test
    void testScaleNegative() {
        Transform t = new Transform(1.0f, 2.0f, 3, 45.0f, 0.25f, 5);
        t.scale(-0.25f, -1.0f);
        assertEquals(0.0f, t.xScale());
        assertEquals(4.0f, t.yScale());
    }

    @Test
    void testToString() {
        Transform t = new Transform(1.0f, 2.0f, 3, 45.0f, 0.25f, 5);
        //todo reenable xScale for version after POO202425
        //assertEquals("(1.00,2.00), 3, 45.00, 0.25, 5.00", t.toString());
        assertEquals("(1.00,2.00) 3 45.00 0.25", t.toString());
    }

    @Test
    void testClone() throws CloneNotSupportedException {
        Transform t1 = new Transform(1.0f, 2.0f, 3, 45.0f, 0.25f, 5);
        Transform t2 = (Transform) t1.clone();
        assertEquals(t1, t2);
        assertNotSame(t1, t2);
    }

    @Test
    void testEquals00() {
        Transform t1 = new Transform(1.0f, 2.0f, 3, 45.0f, 0.25f, 5);
        Transform t2 = new Transform(1.0f, 2.0f, 3, 45.0f,  0.25f, 5);
        assertEquals(t1, t2);
    }

    void testEquals01() {
        Transform t1 = new Transform(1.0f, 2.0f, 3, 45.0f, 0.25f, 5);
        Transform t2 = new Transform(1.0f, 2.0f, 3, 45.0f,  0.125f, 5);
        assertEquals(t1, t2);
    }

    @Test
    void testHashCode00() {
        Transform t1 = new Transform(1.0f, 2.0f, 3, 45.0f, 0.25f, 5);
        Transform t2 = new Transform(1.0f, 2.0f, 3, 45.0f, 0.25f, 5);
        assertEquals(t1.hashCode(), t2.hashCode());
    }


    @Test
    void testHashCode01() {
        Transform t1 = new Transform(1.1f, 2.0f, 3, 45.0f, 0.25f, 5);
        Transform t2 = new Transform(1.0f, 2.0f, 3, 45.0f, 0.25f, 5);
        assertNotEquals(t1.hashCode(), t2.hashCode());
    }
}
