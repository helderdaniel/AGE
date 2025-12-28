package geometric;

import core.eng.ITransform;

/**
 * Represents a geometric figure
 * @author hdaniel@ualg.pt
 * @version 202503051124
 */
public interface IGeometricFigure {

    /**
     * @return the width of this shape
     */
    //int width();

    /**
     * @return the height of this shape
     */
    //int height();

    /**
     * @return the centroid of this shape
     */
    Point centroid();

    /**
     * moves this IGeometricFigure by dx, dy
     *
     * @param dx move distance in x
     * @param dy move distance in y
     */
    void move(double dx, double dy);

    /**
     * rotates this IGeometricFigure clockwise by angle around its centroid
     *
     * @param angle clockwise rotation angle in radians
     */
    void rotate(double angle);

    /**
     * scale this IGeometricFigure
     *
     * @param sx x-axis scale factor
     * @param sy y-axis scale factor
     */
    void scale(double sx, double sy);

    /**
     * Transform this figure following t
     * @param t transform to follow
     */
    void onUpdate(ITransform t);

    /**
     * @return a copy of this figure
     */
    IGeometricFigure copy();
}

