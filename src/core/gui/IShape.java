package core.gui;

import core.eng.IGameObject;
import geometric.Point;
import java.awt.*;
import core.eng.ITransform;

/**
 * Represents a shape that can be painted on the screen
 * @author hdaniel@ualg.pt
 * @version 202502151124
 */
public interface IShape {

    /**
     * Set this IShape's ITransform
     * @param t the transform to associate
     * @pre t != null
     */
    void transform(ITransform t);

    /**
     * @return the IGameObject associated with this ICollider
     */
    IGameObject gameObject();

    /**
     * Set this IShape's GameObject
     * @param go the IGameObject to associate
     * @pre t != null
     */
    void gameObject(IGameObject go);

    /**
     * @return the width of this shape
     */
    int width();

    /**
     * @return the height of this shape
     */
    int height();

    /**
     * Paint this IShape on a IUserInterface
     */
    void paint();

    /**
     * @return the centroid of this shape
     */
    Point centroid();

    /**
     * @return a copy of this shape
     */
    IShape copy();


}

