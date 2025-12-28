package core.gui;

import core.eng.IGameObject;
import core.eng.ITransform;
import core.eng.Transform;
import geometric.Point;
import java.awt.*;

/**
 * Represents a shape that can be painted on the screen
 * @author hdaniel@ualg.pt
 * @version 202502151124
 *
 * @inv shape centroid is at (0,0)
 */
abstract public class Shape implements IShape {

    protected IPainter painter;
    protected IGameObject gameObject;
    protected ITransform transform;
    protected int width    = 0;
    protected int height   = 0;
    protected int centerX  = 0;
    protected int centerY  = 0;

    //Todo: move fill for driver
    protected boolean fill = false;

    protected Shape(IPainter painter, ITransform t, boolean fill) {
        this.painter   = painter;
        this.transform = t;
        this.fill      = fill;
    }

    protected Shape(Shape other) {
        this.painter    = other.painter;
        this.gameObject = other.gameObject;
        this.transform  = new Transform(other.transform);
        this.width      = other.width;
        this.height     = other.height;
        this.centerX    = other.centerX;
        this.centerY    = other.centerY;
        this.fill       = other.fill;
    }

    @Override
    public void transform(ITransform t) { transform = t; }

    @Override
    public IGameObject gameObject() { return gameObject; }

    @Override
    public void gameObject(IGameObject go) { gameObject = go; }

    @Override
    public int width()  { return width; }

    @Override
    public int height() { return height; }

    @Override
    public Point centroid() { return new Point(centerX, centerY); }

    @Override
    public String toString() {
        return width + "x" + height + " C(" + centerX + "," + centerY + ")";
    }

}
