package clientDemos.mooshak.finalval;

/**
 * @author hdaniel@ualg.pt
 * @version 202505011705
 */
public class PointX {
    static private double toNorth = 90;
    private double x,y;

    public PointX(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public PointX(PointX p) {
        this.x = p.x;
        this.y = p.y;
    }

    public double x() { return x; }
    public double y() { return y; }

    /**
     * Move this point by offset in the direction of rot.
     *
     * @param offset to move in the direction nof rot
     * @param rot    angle of rotation in degrees, where 0º is up and increases counterclockwise
     *
     * pos: x += offset . cos(rot)
     *      y += offset . sin(rot)
     */
    public void move(double offset, double rot) {
        x += Math.cos(Math.toRadians(rot+toNorth)) * offset;
        y += Math.sin(Math.toRadians(rot+toNorth)) * offset;
    }

    public double distance(PointX other) {
        double dx = x - other.x;
        double dy = y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public String toString() { return String.format("(%.2f; %.2f)", x, y); }
}
