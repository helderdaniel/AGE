package core.eng;

import core.gui.IPainter;
import geometric.Circle;
import geometric.Point;
import java.awt.*;

/**
 * @author hdaniel@ualg.pt
 * @version 202503081039
 */
public class CollCircle extends Collider {

    public CollCircle(Collider other) { super(other); }
    public CollCircle(IPainter painter, ITransform t, double radius, String name) { this(painter, t, radius, name, Color.RED); }
    public CollCircle(IPainter painter, ITransform t, double radius, String name, Color color) {
        super(painter, t, name, color);
        figure = new Circle(new Point(0,0),  radius);

        //move figure, rotate and scale according to transform
        figure.onUpdate(t);
    }

    @Override
    public boolean isColliding(ICollider other) { return other.isColliding(this);  }

    @Override
    public boolean isColliding(CollCircle other) {
        return ((Circle) figure).isColliding((Circle) other.figure);
    }

    @Override
    public boolean isColliding(CollPoly other) {
        return other.isColliding(this);
    }

    @Override
    public ICollider copy() { return new CollCircle(this); }

    @Override
    public void paint() {
        Circle c = (Circle) figure();

        painter.drawCircle(
                (int) c.centroid().x(),
                (int) c.centroid().y(),
                (int) c.radius(),
                color, false);

        //Alternate version using oval
        /*
        painter.drawOval(
                (int) c.centroid().x(),
                (int) c.centroid().y(),
                (int) c.radius() * 2,
                (int) c.radius() * 2,
                color, false);
        */

        /*  OLD version using Graphics2D
            g.setColor(color());
            g.drawOval(
                    (int) (c.centroid().x() - c.radius()),
                    (int) (c.centroid().y() - c.radius()),
                    (int) c.radius() * 2,
                    (int) c.radius() * 2
            );

         */
    }

}
