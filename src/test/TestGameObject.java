package test;

import core.eng.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author hdaniel@ualg.pt
 * @version 202506281105
 */
public class TestGameObject {

    @ParameterizedTest
    @CsvSource({
            "go0, 0,0,2,45,2",
            "go1, -10,-9,-1,-90,3",
            "go2, -1.5,-3.2,0,-45.3,3.5"})
    void testGameObjectCreationNoShapeNoColliders(String name, double x, double y, int layer, double angle, double scale) {
        IGameEngine engine = new GameEngine(new GameWorldFlat(), null);
        Transform transform = new Transform(x, y, layer, angle, scale, scale);
        IGameObject go = new GameObject(engine, name, transform, null, null);

        assertNotNull(go);
        assertEquals(name, go.name());
        assertEquals(transform, go.transform());
        assertEquals(engine, go.engine());

        String out = go.toString();
        String expected = name + '\n' + transform.toString() + "\nno colliders";
        assertEquals(expected, out);
    }

}
