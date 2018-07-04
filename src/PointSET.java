import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Iterator;

public class PointSET {

    private Set<Point2D> pointSet = null;

    public PointSET() {
        pointSet = new TreeSet<Point2D>();
    }                    // construct an empty set of points

    public boolean isEmpty() {
        return pointSet.isEmpty();
    }                   // is the set empty?

    public int size() {
        return pointSet.size();
    }     // number of points in the set

    public void insert(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }
        pointSet.add(new Point2D(p.x(), p.y()));
    }    // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }
        return pointSet.contains(p);
    }   // does the set contain point p?

    public void draw() {
        StdDraw.setScale(0, 1);
        StdDraw.setPenRadius(0.02);
        for (Point2D point : pointSet) {
            StdDraw.point(point.x(), point.y());
        }
    }        // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new java.lang.IllegalArgumentException();
        }

        final List<Point2D> pointInRect = new ArrayList<Point2D>();
        for (Point2D point : pointSet) {
            if (rect.contains(point)) {
                pointInRect.add(point);
            }
        }
        return new Iterable<Point2D>() {
            @Override
            public Iterator<Point2D> iterator() {
                return pointInRect.iterator();
            }
        };
    }      // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }
        Point2D nearestPoint = null;
        double nearestDistance = -1;
        for (Point2D point : pointSet) {
            double dist = point.distanceSquaredTo(p);
            if (nearestDistance == -1 || dist < nearestDistance && !point.equals(p)) {
                nearestDistance = dist;
                nearestPoint = point;
            }
        }
        return nearestPoint;
    }       // a nearest neighbor in the set to point p; null if the set is empty
}
