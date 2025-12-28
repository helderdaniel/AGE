package geometric;


/**
 * @author hdaniel@ualg.pt
 * @version 202502091130
 */
public class Point {
    private double x, y;

    public Point(double x, double y) { this.x = x; this.y = y; }
    public Point(Point p) { this(p.x, p.y); }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public void move(double dx, double dy) {
        x += dx;
        y += dy;
    }

    public void scale(double sx, double sy) {
        x *= sx;
        y *= sy;
    }

    void rotateAround(Point center, double angle) {
        double cx = center.x();
        double cy = center.y();

        //if angle 0 there is no rotation
        //cannot combinations method below, gives NaN
        if (angle != 0) {
            double rads = angle * Math.PI / 180;
            double sin = Math.sin(rads);
            double cos = Math.cos(rads);

            // translate point to origin:
            move(-cx, -cy);

            // rotate point around origin:
            //clockwise if origin if left upper corner
            //counter clockwise if origin if middle of canvas
            double xn = x * cos - y * sin;
            double yn = x * sin + y * cos;

            // translate point back:
            x = xn + cx;
            y = yn + cy;
        }
    }


    public double distance(Point p) {
        return Math.sqrt(Math.pow(p.x() - x, 2) + Math.pow(p.y() - y, 2));
    }

    /**
     * @return Determinant of 2 points:
     *    | this.x  this.y ∣
     *    |    p.x     p.y |
     */
    public double det(Point p) { return (x * p.y - y * p.x); }

    /**
     * @return Dot product of 2 points
     */
    public double dot(Point p) { return x * p.x + y * p.y; }

    public String toString() { return String.format("(%.2f,%.2f)", x, y); }

    public int hashCode() {
        return Double.hashCode(x) ^ Double.hashCode(y);
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Point)) return false;
        Point p = (Point) o;
        return Math.abs(x - p.x) < 1e-9 && Math.abs(y - p.y) < 1e-9;
    }
}
