package core.eng;

import geometric.Point;

import java.util.Objects;

/**
 * @author hdaniel@ualg.pt
 * @version 202502032256
 */
public class Transform implements ITransform, Cloneable {

    protected double x=0, y=0, theta=0;   // consider zero degrees at North
    protected int   layer=0;
    protected double xScale=1, yScale=1;

    /**
     * Initializes a Transform at position x, y and layer layer, with rotation theta
     * pos: this.x() == x &&
     *      this.y() == y &&
     *      this.layer() == layer &&
     *      0 <= this.angle() < 360
     */
    public Transform(double x, double y, int layer, double theta, double xScale, double yScale) {
        this.x = x;
        this.y = y;
        this.layer = layer;
        this.theta = normAngle(theta);
        this.xScale = xScale;
        this.yScale = yScale;
    }

    public Transform(Point pos, int layer, double theta, double xScale, double yScale) {
        this(pos.x(), pos.y(), layer, theta, xScale, yScale);
    }

    /**
     * Copy constructor to create a new Transform with the same values as the given Transform
     */
    public Transform(ITransform other) {
        this(other.x(), other.y(), other.layer(), other.angle(), other.xScale(), other.yScale());
    }

    /**
     * Initializes a Transform at position x, y, and layer layer with rotation theta and scale (1,1)
     */
    public Transform(double x, double y, int layer, double theta) {
        this(x,y,layer,theta,1,1);
    }

    /**
     * Initializes a Transform at position x, y and layer 0, with rotation theta and scale (1,1)
     */
    public Transform(double x, double y, double theta) {
        this(x,y,0,theta,1,1);
    }

    /**
     * Initializes a Transform at position x, y and layer 0, with rotation 0º and scale (1,1)
     */
    public Transform(double x, double y) {
        this(x,y,0,0,1,1);
    }

    public double x() { return x; }
    public double y() { return y; }
    public double angle() { return theta; }
    public int layer() { return layer; }
    public double xScale() { return xScale; }
    public double yScale() { return yScale; }

    /**
     * @param theta is an angle in degrees
     * @return theta normalized to: 0 <= theta < 360
     */
    private double normAngle(double theta) {
        theta = theta % 360;
        if (theta < 0) theta += 360;
        return theta;
    }

    public void move(double dx, double dy, int dlayer) {
        x += dx;
        y += dy;
        layer += dlayer;
    }

    public void move(double dx, double dy) {
        move(dx, dy, 0);
    }

    public void rotate(double dTheta) {
        theta = normAngle(theta + dTheta);
    }

    public void scale(double dsx, double dsy) {
        xScale += dsx;
        yScale += dsy;
    }

    /**
     * Return the current state with format: (x, y, layer, angle)
     */
    @Override
    public String toString() {
        //todo reenable xScale for version after POO202425
        //return String.format("(%.2f,%.2f), %d, %.2f, %.2f, %.2f", x, y, layer, theta, xScale, yScale);
        return String.format("(%.2f,%.2f) %d %.2f %.2f", x, y, layer, theta, xScale);
    }

    /**
     * Return a new Transform with the same values
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transform transform = (Transform) o;
        return Double.compare(x, transform.x) == 0 && Double.compare(y, transform.y) == 0 &&
               Double.compare(theta, transform.theta) == 0 && layer == transform.layer &&
               Double.compare(xScale, transform.xScale) == 0 && Double.compare(yScale, transform.yScale) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, theta, layer, xScale, yScale);
    }
}

