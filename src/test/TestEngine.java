package test;

/**
 * @author hdaniel@ualg.pt
 * @version 202502041133
 */

import core.eng.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.stream.StreamSupport;

class TestEngine {

    private IGameEngine engine;
    private IGameObject gameObject;

    //Convert Iterable to list to access methods like contains and size
    private List<IGameObject> toList(Iterable<IGameObject> iterable) {;
        /*
        List<IGameObject> list = new ArrayList<IGameObject>();
        engine.getEnabled().forEach(list::add);
        return list;
        */

        //or:
        return StreamSupport.stream(iterable.spliterator(), false).toList();
    }


    @BeforeEach
    void setUp() {
        engine = new GameEngine(new GameWorldFlat(), null);
        Transform transform = new Transform(1.0f, 2.0f, 3, 45.0f);
        gameObject = new GameObject(engine, "go", transform, null, null);
    }


    @Test
    void testGetEnabled() {
        engine.addEnabled(gameObject);

        List<IGameObject> enabledObjects = toList(engine.getEnabled());
        assertEquals(1, enabledObjects.size());
        assertTrue(enabledObjects.contains(gameObject));
    }


    @Test
    void testGetDisabled() {
        engine.addDisabled(gameObject);

        List<IGameObject> disabledObjects = toList(engine.getDisabled());
        assertEquals(1, disabledObjects.size());
        assertTrue(disabledObjects.contains(gameObject));
    }


    @Test
    void testAddEnabled() {
        engine.addEnabled(gameObject);
        assertTrue(toList(engine.getEnabled()).contains(gameObject));
    }


    @Test
    void testAddDisabled() {
        engine.addDisabled(gameObject);
        assertTrue(toList(engine.getDisabled()).contains(gameObject));
    }


    @Test
    void testEnable() {
        engine.addDisabled(gameObject);
        engine.enable(gameObject);
        assertTrue(toList(engine.getEnabled()).contains(gameObject));
        assertFalse(toList(engine.getDisabled()).contains(gameObject));
    }


    @Test
    void testDisable() {
        engine.addEnabled(gameObject);
        engine.disable(gameObject);
        assertFalse(toList(engine.getEnabled()).contains(gameObject));
        assertTrue(toList(engine.getDisabled()).contains(gameObject));
    }


    @Test
    void testIsEnabled() {
        engine.addEnabled(gameObject);
        assertTrue(engine.isEnabled(gameObject));
        assertFalse(engine.isDisabled(gameObject));
    }


    @Test
    void testIsDisabled() {
        engine.addDisabled(gameObject);
        assertTrue(engine.isDisabled(gameObject));
        assertFalse(engine.isEnabled(gameObject));
    }

    /**
     * Check that the destroy method will only remove the objects
     * after collision checks are performed.
     */
    @Test
    void testDestroy() {
        engine.addEnabled(gameObject);
        engine.destroy(gameObject);
        //only destroys after collisions are checked
        assertTrue(toList(engine.getEnabled()).contains(gameObject));
        assertFalse(toList(engine.getDisabled()).contains(gameObject));
    }


    /**
     * Check that the destroyAll method will only remove the objects
     * after collision checks are performed.
     */
    @Test
    void testDestroyAll() {
        engine.addEnabled(gameObject);
        GameObject gameObject2 = new GameObject(engine, "go", new Transform(2.0f, 3.0f, 4, 90.0f),  null, null);
        engine.addDisabled(gameObject2);
        engine.destroyAll();
        //only destroys after collisions are checked
        assertTrue(engine.getEnabled().iterator().hasNext());
        assertTrue(engine.getDisabled().iterator().hasNext());
    }


    @Test
    void testUpdate() {
        engine.addEnabled(gameObject);
        //engine.update();
        // Add assertions to verify the update behavior
        //todo
    }


    @Test
    void testCollision() {
        GameObject gameObject2 = new GameObject(engine, "go", new Transform(2.0f, 3.0f, 4, 90.0f), null, null);
        engine.addEnabled(gameObject);
        engine.addEnabled(gameObject2);
        engine.checkCollisions();
        // Add assertions to verify the collision behavior
        //todo
    }
}
