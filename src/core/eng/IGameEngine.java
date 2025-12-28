package core.eng;

import core.gui.IUserInterface;

/**
 * Interface for a Game engine where only enabled IGameObjects are displayed in the UI
 * @author hdaniel@ualg.pt
 * @version 202501310808
 */
public interface IGameEngine {

    /**
     * @return the IUserInterface assigned to this IGameEngine
     */
    public IUserInterface ui();

    /**
     * add and enable IGameObject go
     * pre: go != null
     * pos: go.onInit()
     */
    public void addEnabled(IGameObject go);

    /**
     * add and disable IGameObject go
     * pre: go != null
     * pos: go.onInit()
     */
    public void addDisabled(IGameObject go);

    /**
     * Add the GameObject go to the destroy list
     * whether it is enabled or disabled
     * Objects in the destroy list will be handled after all collisions have been detected
     * @param go: a GameObject to add to the destroy list
     * pre: go != null
     */
    public void destroy(IGameObject go);

    /**
     * Add all the GameObjects to the destroy list
     * Objects in the destroy list will be handled after all collisions have been detected
     */
    public void destroyAll();

    /**
     * return true if IGameObject go is enabled
     * pre: go != null
     */
    public boolean isEnabled(IGameObject go);

    /**
     * return true if IGameObject go is disabled
     * pre: go != null
     */
    public boolean isDisabled(IGameObject go);

    /**
     * If IGameObject go is disabled move it to enabled
     * pre: go != null
     */
    public void enable(IGameObject go);

    /**
     * If IGameObject go is enabled move it to disabled
     * pre: go != null
     */
    public void disable(IGameObject go);

    /**
     * return an Iterable for all the enabled GameObjects
     */
    public Iterable<IGameObject> getEnabled();

    /**
     * return an Iterable for all the disabled GameObjects
     */
    public Iterable<IGameObject> getDisabled();

    /**
     * Update all the enabled GameObjects
     * pre: dt > 0
     * pos: calls Behaviour.onUpdate() for all enabled objects
     *      calls Collider.onUpdate() for all enabled objects
     */
    public void update(double dt, IInputEvent ie);

    /**
     * Check collisions for all the enabled objects
     * pos: calls Behaviour.onCollision(gol) for all enabled GameObjects
     *      passing in the list of all the objects that collided with each IGameObject
     */
    public void checkCollisions();

    /**
     * Generates a new frame:
     * Get user input from UI
     * update all the enabled GameObjects
     * check collisions and send info to GameObjects
     * update UI
     * pos: UI.input() &&
     *      calls Behaviour.onUpdate() for all enabled objects &&
     *      Behaviour.checkCollisions() &&
     *      UI.draw()
     */
    public void run();
}
