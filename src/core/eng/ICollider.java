package core.eng;

import geometric.IGeometricFigure;
import geometric.Point;

import java.awt.*;

/**
 * @author hdaniel@ualg.pt
 * @version 202503081039
 */
public interface ICollider {

    /**
     * Set this ICollider ITransform
     * @param t the transform to associate
     * @pre t != null
     */
    void transform(ITransform t);

    /**
     * @return the IGameObject associated with this ICollider
     */
    public IGameObject gameObject();

    /**
     * Set this IShape's GameObject
     * @param go the IGameObject to associate
     * @pre t != null
     */
    public void gameObject(IGameObject go);

    /**
     * @return the name of this collider
     */
    String name();

    /**
     * @return the centroid of this collider
     */
    Point centroid();

    /**
     * @return the geometric figure of this collider
     */
    IGeometricFigure figure();

    //todo: testing
    //ITransform transform();

    Color color();

    /**
     * Update this ICollider based on the transform
     */
    void onUpdate();


    //Needed to perform double dispatching in java
    /**
     * Check if this collider is colliding with another collider
     * @param other the other collider
     * @return true if colliding, false otherwise
     */
    boolean isColliding(ICollider other);
    boolean isColliding(CollPoly other);
    boolean isColliding(CollCircle other);

    /**
     * @return a copy of this shape
     */
    ICollider copy();

    /**
     * Paint the collider on a IUserInterface
     */
    public void paint();
}




