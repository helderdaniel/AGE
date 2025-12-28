package geometric;

import core.eng.ITransform;

/**
 * @author hdaniel@ualg.pt
 * @version 202502091143
 *
 * @inv radius > 0
 */
public class Circle implements IGeometricFigure {
    private Point  baseCenter, center;
    private double baseRadius, radius;

    public Circle(Point center, double radius) {
        this.baseCenter = this.center = center;
        this.baseRadius = this.radius = radius;

        if (radius <= 0) {
            throw new IllegalArgumentException("Circle:iv");
        }
    }

    public Circle(Circle other) {
        this.baseCenter = new Point(other.baseCenter);
        this.center     = new Point(other.center);
        this.baseRadius = this.radius = other.baseRadius;
    }

    public Point centroid() { return center; }

    @Override
    public void move(double dx, double dy) {
        center.move(dx, dy);
    }

    @Override
    public void rotate(double angle) {
        // does nothing for Circle
    }

    @Override
    // uses just x scale, to prevent this circle to be transformed into an ellipse
    public void scale(double sx, double sy) {
        this.radius = baseRadius * sx;
    }

    public double radius() { return radius; }

    @Override
    public void onUpdate(ITransform t) {
        center = new Point(baseCenter);
        move(t.x(), t.y());
        scale(t.xScale(), t.yScale());
    }

    @Override
    public IGeometricFigure copy() { return new Circle(this); }


    public boolean isColliding(Circle other) {
        double dCenter = center.distance(other.center);
        double rSum = radius + other.radius;
        return dCenter <= rSum;
    }

    public boolean isColliding(Polygon other) {
        return other.isColliding(this);
    }

    @Override
    public String toString() {
        return String.format("%s %.2f", center, radius);
    }
}

