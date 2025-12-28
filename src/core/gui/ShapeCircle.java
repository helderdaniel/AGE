package core.gui;

import core.eng.ITransform;

import java.awt.*;

/**
 * Represents a circle that can be painted on the screen
 * @author hdaniel@ualg.pt
 * @version 202502151124
 *
 * @inv circle centroid is at (0,0)
 */
public class ShapeCircle extends Shape {

    protected int radius;
    protected Color color;

    public ShapeCircle(IPainter painter, ITransform t, double radius, Color color) { this(painter, t, radius, color, false); }
    public ShapeCircle(IPainter painter, ITransform t, double radius, Color color, boolean fill) {
        super(painter, t, fill);
        this.radius = (int) radius;
        this.color  = color;
        width = (int) radius*2;
        height = width;
        //centerX = (int) radius;
        //centerY = (int) radius;
    }

    public ShapeCircle(ShapeCircle other) {
        super(other);
        this.radius = other.radius;
        this.color  = other.color;
    }

    @Override
    public void paint() {
        painter.drawCircle(transform, radius, color, fill);
    }

    @Override
    public IShape copy() { return new ShapeCircle(this); }
}
