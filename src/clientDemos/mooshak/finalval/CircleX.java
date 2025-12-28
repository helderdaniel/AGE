package clientDemos.mooshak.finalval;

/**
 * @author hdaniel@ualg.pt
 * @version 202505241343
 */
public class CircleX {

    PointX center;
    double radius;

    public CircleX(PointX p, double r) {
        center = p;
        radius = r;
    }

    public boolean collides(CircleX other) {
       double distance = center.distance(other.center);
       return distance <= (radius + other.radius);
    }
}
