package Alienoid;

import geometric.Point;

import java.util.Iterator;

/**
 * @author hdaniel@ualg.pt
 * @version 202502091130
 */
public class SquadIterator implements Iterator<Point> {
    private double x, y, xoff, yoff, rows, cols;
    private int c = 0, l = 0;

    public SquadIterator(int rows, int cols, double x, double y, double xoff, double yoff) {
        this.x = x;
        this.y = y;
        this.xoff = xoff;
        this.yoff = yoff;
        this.rows = rows;
        this.cols = cols;
    }

    @Override
    public boolean hasNext() {
        return c < cols && l < rows;
    }

    @Override
    public Point next() {
        Point p = new Point(c * xoff + x, l * yoff + y);
        if (++l >= rows) {
            l = 0;
            c++;
        }
        return p;
    }
}
