package geometric;

import geometric.Point;

/**
 * @author hdaniel@ualg.pt
 * @version 202502091141
 */
public class LineSegment {
    protected Point p1, p2;

    public LineSegment(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public double length() { return p1.distance(p2);  }

    public double slope() {
        return (p2.y() - p1.y()) / (p2.x() - p1.x());
    }

    public double yIntercept() {
        return p1.y() - slope() * p1.x();
    }

    public Point intersection(LineSegment other) {
        double x = (other.yIntercept() - yIntercept()) / (slope() - other.slope());
        double y = slope() * x + yIntercept();
        return new Point(x, y);
    }

}
