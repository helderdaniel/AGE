package core.eng;

import core.gui.IShape;

/**
 * Interface for a Game Object factory
 * @author hdaniel@ualg.pt
 * @version 202506181108
 */
public interface IGOFactory {

    /**
     * Create a new IGameObject with the given ITransform
     * @param name  the name to be used for the new IGameObject
     * @param t     the transform to be used for the new IGameObject
     * @return      a new IGameObject instance
     */
    IGameObject create(String name, ITransform t);

    /**
     * @return the model IShape of this IGOFactory
     */
    IShape shape();

    /**
     * @return the colliders of this IGOFactory
     */
    ICollider[] colliders();

    /**
      * @return the Behaviours of this IGOFactory
     */
    IBehaviour[] behaviours();

}
