import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }                     // constructs the point (x, y)

    public void draw() {
        StdDraw.point(x, y);
    }                  // draws this point

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }                 // draws the line segment from this point to that point

    public String toString() {
        return "(" + x + ", " + y + ")";
    }               // string representation

    public int compareTo(Point that) {
        if (this.y == that.y && this.x == that.x) {
            return 0;
        }
        if (this.y < that.y || this.y == that.y && this.x < that.x) {
            return -1;
        }
        return 1;
    }   // compare two points by y-coordinates, breaking ties by x-coordinates

    public double slopeTo(Point that) {
        if (compareTo(that) == 0) {
            return Double.NEGATIVE_INFINITY;
        }
        if (that.x - this.x == 0) {
            return Double.POSITIVE_INFINITY;
        }
        if (that.y - this.y == 0) {
            return 0;
        }
        return (that.y - this.y) / (double) (that.x - this.x);
    }    // the slope between this point and that point

    public Comparator<Point> slopeOrder() {
        Comparator<Point> comparator = new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return Double.compare(slopeTo(o1), slopeTo(o2));
            }
        };
        return comparator;
    }      // compare two points by slopes they make with this point
}