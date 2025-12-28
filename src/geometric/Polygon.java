package geometric;

import core.eng.ITransform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author hdaniel@ualg.pt
 * @version 202502091140
 *
 * Collision detection according to the: Separating Axis Theorem
 * @see: https://research.ncl.ac.uk/game/mastersdegree/gametechnologies/previousinformation/physics4collisiondetection/2017%20Tutorial%204%20-%20Collision%20Detection.pdf
 */
public class Polygon implements IGeometricFigure {
    protected final List<Point> baseVertex = new ArrayList<>();
    protected final List<Point> vertex = new ArrayList<>();

    public Polygon(Point... points)    { this(Arrays.asList(points)); }
    public Polygon(List<Point> vertex) {
        this.vertex.addAll(vertex);

        //Deep copy to baseVertex
        for (Point p : vertex)
            baseVertex.add(new Point(p));

        //Move baseVertex to (0,0)
        Point c = centroid();
        Polygon.move(this.baseVertex, -c.x(), -c.y());

    }

    public List<Point> vertex() { return vertex; }

    public Polygon(Polygon other) {
        for (Point p : other.baseVertex)
            baseVertex.add(new Point(p));
        for (Point p : other.vertex)
            vertex.add(new Point(p));
    }

    public int size() { return vertex.size(); }
    public double width() {
        double min = Double.MAX_VALUE, max = 0;
        for (Point p : vertex) {
            if (p.x() < min) min = p.x();
            if (p.x() > max) max = p.x();
        }
        return max-min;
    }
    public double height() {
        double min = Double.MAX_VALUE, max = 0;
        for (Point p : vertex) {
            if (p.y() < min) min = p.y();
            if (p.y() > max) max = p.y();
        }
        return max-min;
    }

    public int[] xcoords() {
        int[] xc = new int[vertex.size()];
        for (int i = 0; i < vertex.size(); i++)
            xc[i] = (int) (vertex.get(i).x());
        return xc;
        }

    public int[] ycoords() {
        int[] yc = new int[vertex.size()];
        for (int i = 0; i < vertex.size(); i++)
            yc[i] = (int) (vertex.get(i).y());
        return yc;
        }


    static public void move(List<Point> vertex, double dx, double dy) {
        for (Point p : vertex)
            p.move(dx, dy);
    }

    @Override
    public void move(double dx, double dy) {
        move(this.vertex, dx, dy);
    }

    @Override
    public void rotate(double angle) {
        Point c = centroid();
        for (Point p : vertex)
            p.rotateAround(c, angle);
    }

    @Override
    public void scale(double sx, double sy) {
        Point c = centroid();
        move(-c.x(), -c.y());
        for (Point p : vertex)
            p.scale(sx, sy);
        move(c.x(), c.y());
    }

    @Override
    public void onUpdate(ITransform t) {
        reset();
        scale(t.xScale(), t.yScale());
        move(t.x(), t.y());
        rotate(t.angle());
    }

    protected void reset() {
        vertex.clear();
        for (Point p : baseVertex)
            vertex.add(new Point(p));
    }

    @Override
    public IGeometricFigure copy() { return new Polygon(this); }

    /**
     * @return the area of the polygon
     * @see: https://en.wikipedia.org/wiki/Shoelace_formula
     */
    static private double signedArea(List<Point> vertex) {
        /* Compute the area of the polygon using Shoelace formula:

            Area = 1/2 abs(det|x1 y1∣+∣x2 y2∣+...+∣xn yn|)
                              |x2 y2| |x3 y3|     |x1 y1|
        */
        double sum=0;

        for(int i = 0; i < vertex.size()-1; ++i)
            sum += vertex.get(i).det(vertex.get(i+1));

        //close polygon
        sum += vertex.getLast().det(vertex.getFirst());

        return sum/2;  //Note the unsigned area is given by Math.abs(sum/2)
    }


    /**
     * @return the centroid of a convex figure from the average of the vertex coordinates
     *         does not works for concave figures
     */
    static public Point centroidConvex(List<Point> vertex) {
        double cx=0, cy=0;
        int size = vertex.size();
        for (int i=0; i<size; ++i) {
            cx += vertex.get(i).x();
            cy += vertex.get(i).y();
        }
        //centroid point
        return new Point(cx/size,cy/size);
    }


    @Override
    public Point centroid() { return centroid(vertex); }

    /**
     * @param vertex List of points that form the polygon ordered anti-clockwise
     * @return the centroid of a convex or concave polygon
     */
    static public Point centroid(List<Point> vertex) {
        //Static to be called from other classes without needing to alocate memory for a new instance
        //https://en.wikipedia.org/wiki/Centroid#Of_a_polygon
        double x=0, y=0;

        for(int i = 0; i < vertex.size()-1; ++i)  {
            double det = vertex.get(i).det(vertex.get(i+1));
            x += (vertex.get(i).x() + vertex.get(i+1).x()) * det;
            y += (vertex.get(i).y() + vertex.get(i+1).y()) * det;
        }

        //close figure
        double det = vertex.getLast().det(vertex.getFirst());
        x += (vertex.getLast().x() + vertex.getFirst().x()) * det;
        y += (vertex.getLast().y() + vertex.getFirst().y()) * det;

        double area = signedArea(vertex);
        if (area == 0)
            throw new RuntimeException("Polygon area is zero, ie. a line segment");
        double f = 1/(6*signedArea(vertex));
        return new Point(x*f, y*f);
    }


    /*
     * todo: alternate functions to be used in the collision detection
     */

    public boolean isColliding(Circle circle) {
        Point center = circle.centroid();
        double radius = circle.radius();

        // Check if the circle's center is inside the polygon
        if (this.contains(center)) {
            return true;
        }

        // Check if any of the polygon's edges intersect with the circle
        List<Point> vertices = this.vertex;
        int numVertices = vertices.size();
        for (int i = 0; i < numVertices; i++) {
            Point p1 = vertices.get(i);
            Point p2 = vertices.get((i + 1) % numVertices);
            if (isCircleIntersectingLineSegment(center, radius, p1, p2)) {
                return true;
            }
        }

        return false;
    }

    private boolean contains(Point center) {
        List<Point> vertices = this.vertex;
        int numVertices = vertices.size();
        boolean contains = false;
        for (int i = 0, j = numVertices - 1; i < numVertices; j = i++) {
            if ((vertices.get(i).y() > center.y()) != (vertices.get(j).y() > center.y()) &&
                    (center.x() < (vertices.get(j).x() - vertices.get(i).x()) * (center.y() - vertices.get(i).y()) / (vertices.get(j).y() - vertices.get(i).y()) + vertices.get(i).x())) {
                contains = !contains;
            }
        }
        return contains;
    }

    private boolean isCircleIntersectingLineSegment(Point center, double radius, Point p1, Point p2) {
        double dx = p2.x() - p1.x();
        double dy = p2.y() - p1.y();
        double fx = p1.x() - center.x();
        double fy = p1.y() - center.y();

        double a = dx * dx + dy * dy;
        double b = 2 * (fx * dx + fy * dy);
        double c = (fx * fx + fy * fy) - radius * radius;

        double discriminant = b * b - 4 * a * c;
        if (discriminant < 0) {
            return false;
        }

        discriminant = Math.sqrt(discriminant);
        double t1 = (-b - discriminant) / (2 * a);
        double t2 = (-b + discriminant) / (2 * a);

        if (t1 >= 0 && t1 <= 1 || t2 >= 0 && t2 <= 1) {
            return true;
        }

        return false;
    }


    /*
     * todo: Move to collider (?)
     */

    /**
     * Detect if circle c is colliding with this polygon
     *
     * @param c a circle
     * @return true if this Polygon and Circle c are colliding,
     *         false otherwise
     */
    public boolean isColliding2(Circle c) {
        List<Point> axes = getAxes();
        axes.addAll(getCircleAxes(c.centroid()));

        for (Point axis : axes) {
            if (!overlap(project(axis), projectCircle(axis, c.centroid(), c.radius()))) {
                return false;
            }
        }
        return true;
    }


    private List<Point> getCircleAxes(Point center) {
        List<Point> axes = new ArrayList<>();
        for (Point vertex : vertex) {
            Point edge = new Point(vertex.x() - center.x(), vertex.y() - center.y());
            Point normal = new Point(-edge.y(), edge.x());
            axes.add(normal);
        }
        return axes;
    }


    private double[] projectCircle(Point axis, Point center, double radius) {
        //double centerProjection = dotProduct(center, axis);
        double centerProjection = center.dot(axis);
        return new double[]{centerProjection - radius, centerProjection + radius};
    }


    /**
     * Detect if Polygon other is colliding with this polygon
     *
     * @param other a Polygon
     * @return true if this Polygon and Polygon other are colliding,
     *         false otherwise
     */
    public boolean isColliding(Polygon other) {
        List<Point> axes = getAxes();
        axes.addAll(other.getAxes());

        for (Point axis : axes) {
            if (!overlap(project(axis), other.project(axis))) {
                return false;
            }
        }
        return true;
    }

    private List<Point> getAxes() {
        List<Point> axes = new ArrayList<>();
        for (int i = 0; i < vertex.size(); i++) {
            Point p1 = vertex.get(i);
            Point p2 = vertex.get((i + 1) % vertex.size());
            Point edge = new Point(p2.x() - p1.x(), p2.y() - p1.y());
            Point normal = new Point(-edge.y(), edge.x());
            axes.add(normal);
        }
        return axes;
    }

    private double[] project(Point axis) {
        //double min = dotProduct(vertex.get(0), axis);
        double min = vertex.get(0).dot(axis);
        double max = min;
        for (Point v : vertex) {
            //double projection = dotProduct(vertex, axis);
            double projection = v.dot(axis);
            if (projection < min)         min = projection;
            else if (projection > max)    max = projection;
        }
        return new double[]{min, max};
    }

    private boolean overlap(double[] proj1, double[] proj2) {
        return !(proj1[1] < proj2[0] || proj2[1] < proj1[0]);
    }

    public String toString() {
        StringJoiner sj = new StringJoiner(" ");
        for (Point p : vertex)
            sj.add(p.toString());
        String s = sj.toString();
        return s;
    }
}
