package core.eng;

/**
 * Represents the position, rotation and scale
 * @author hdaniel@ualg.pt
 * @version 202502032256
 * @inv 0 <= angle() < 360
 */
public interface ITransform  {

    /**
     * @return the x coordinate
     */
    public double x();

    /**
     * @return the y coordinate
     */
    public double y();

    /**
     * @return the angle in degrees
     */
    public double angle();

    /**
     * @return the layer
     */
    public int layer();

    /**
     * @return the current x scale factor
     */
    public double xScale();

    /**
     * @return the current y scale factor
     */
    public double yScale();

    /**
     * Move this ITransform by dx, dy and dlayer
     * @param dx      the x increment
     * @param dy      the y increment
     * @param dlayer  the layer increment
     */
    public void move(double dx, double dy, int dlayer);

    /**
     * Move this ITransform by dx, dy
     * @param dx      the x increment
     * @param dy      the y increment
     */
    public void move(double dx, double dy);

    /**
     * Rotate this ITransform by dTheta
     * @param dTheta
     * pos: 0 <= this.angle() < 360
     */
    public void rotate(double dTheta);

    /**
     * increment the Transform scale by dsx, dsy
     * @param dsx the x scale increment
     * @param dsy the y scale increment
     */
    public void scale(double dsx, double dsy);
}

