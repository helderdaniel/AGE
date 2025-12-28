package core.eng;

import core.gui.IPainter;
import geometric.IGeometricFigure;
import geometric.Point;

import java.awt.*;

/**
 * Represents a collider that can be drawn on the screen
 * @author hdaniel@ualg.pt
 * @version 202501311039
 */
public abstract class Collider implements ICollider {

    protected ITransform transform;
    protected IGeometricFigure figure;
    protected Color color;
    protected String name;
    protected IGameObject gameObject;
    protected IPainter painter;

    public Collider(IPainter painter, ITransform t, String name) { this(painter, t, name, Color.RED); }
    public Collider(IPainter painter, ITransform t, String name, Color c) {
        this.painter   = painter;
        this.transform = t;
        this.name      = name;
        this.color     = c;

    }
    public Collider(Collider other) {
        this.painter    = other.painter;
        this.transform  = new Transform(other.transform);
        this.name       = other.name;
        this.color      = other.color;
        this.figure     = other.figure.copy();
        this.gameObject = other.gameObject;
    }

    @Override
    public void transform(ITransform t) { transform = t; }

    @Override
    public IGameObject gameObject() { return gameObject; }

    @Override
    public void gameObject(IGameObject go) { gameObject = go; }

    @Override
    public String name() { return name; }

    @Override
    public Point centroid() { return figure.centroid(); }

    @Override
    public IGeometricFigure figure() { return figure; }

    //todo: testing
    //@Override
    //public ITransform transform() { return transform; }

    @Override
    public Color color() { return color; }

    @Override
    public void onUpdate() { figure.onUpdate(transform); }

    @Override
    public String toString() {
        String out = "";
        if (name != "") out = name + "\n";
        return out + figure.toString();
    }

}


