package core.eng;

import core.gui.IShape;
import geometric.Point;

/**
 * Interface for a IGameObject that defines the event methods called by the IGameEngine
 * @author hdaniel@ualg.pt
 * @version 202501310841
 */

public interface IGameObject {

    /**
     * @return the name of the GameObject
     */
    String name();

    /**
     * @return the GameEngine where the GameObject is running
     */
    IGameEngine engine();

    /**
     * @return the Shape of the GameObject
     */
    public IShape shape();

    /**
     * @return the Transform of the GameObject
     */
    ITransform transform();

    /**
     * @return the Collider of the GameObject
     */
    ICollider[] colliders();

    /**
     * @return the Behaviours of the GameObject
     */
    IBehaviour[] behaviours();

    /**
     * @return the centroid of the GameObject in world coordinates
     *         relative to current scale
     */
    Point centroid();

    /**
     * @return the width of the GameObject relative to current scale
     */
    double width();

    /**
     * @return the height of the GameObject relative to current scale
     */
    double height();

    /**
     * @return a copy of the IGameObject
     */
    IGameObject copy();
}
