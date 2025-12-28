package core.gui;

import core.eng.ITransform;

import java.awt.*;

/**
 * Represents an image that can be painted on the screen
 * @author hdaniel@ualg.pt
 * @version 202502151124
 *
 * @inv rectangle centroid is at (0,0)
 */
public class ShapeRectangle extends Shape {

    protected Color color;

    public ShapeRectangle(IPainter painter, ITransform t, int width, int height, Color color) { this(painter, t, width, height, color, false); }
    public ShapeRectangle(IPainter painter, ITransform t, int width, int height, Color color, boolean fill) {
        super(painter, t, fill);
        this.width  = width;
        this.height = height;
        this.color  = color;
    }

    public ShapeRectangle(ShapeRectangle other) {
        super(other);
        this.color = other.color;
    }

    @Override
    public void paint() {
        IPainter painter = (IPainter) gameObject.engine().ui();
        painter.drawRectangle(transform, width, height, color, fill);
    }

    @Override
    public IShape copy() { return new ShapeRectangle(this); }
}
