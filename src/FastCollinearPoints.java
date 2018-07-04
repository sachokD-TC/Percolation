import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;


public class FastCollinearPoints {

    private Object[] segmentObjects;
    private final LineSegment[] segments;
    private int segmentId;

    public FastCollinearPoints(Point[] points) {
        // check corner cases
        if (points == null)
            throw new IllegalArgumentException("duplicate arguments given");
        checkForDuplicates(points);

        int n = points.length;
        Point[] pointsCopy = new Point[n];
        System.arraycopy(points, 0, pointsCopy, 0, n);
        Arrays.sort(pointsCopy);

        segmentId = 0;
        segmentObjects = new LineSegment[n];

        for (int i = 0; i < n - 3; i++) {
            Point p = pointsCopy[i];
            Point[] sortedPoints = new Point[n];  // content length = n - 1, last = null
            System.arraycopy(pointsCopy, 0, sortedPoints, 0, i);
            System.arraycopy(pointsCopy, i + 1, sortedPoints, i, n - i - 1);
            Arrays.sort(sortedPoints, 0, n - 1, p.slopeOrder());

            int currentStartIndex = 0;
            for (int j = 1; j < n; j++) {
                Point currentStartPoint = sortedPoints[currentStartIndex];
                Point currentPoint = sortedPoints[j];
                if (currentPoint == null || currentPoint.slopeTo(p) != currentStartPoint.slopeTo(p)) {
                    int len = j - currentStartIndex;
                    /* critical - if current starting point of this segment is smaller than p,
                       then this line segment is already in the collection */
                    if (len >= 3 && currentStartPoint.compareTo(p) > 0) {
                        segmentObjects[segmentId] = new LineSegment(p, sortedPoints[j - 1]);
                        segmentId++;
                        if (segmentId == segmentObjects.length) {
                            resize(segmentId * 2);
                        }
                    }
                    currentStartIndex = j;
                }
            }
        }

        segments = new LineSegment[segmentId];

        for (int i = 0; i < segmentId; i++) {
            segments[i] = (LineSegment) segmentObjects[i];
        }
    }


    private void resize(int capacity) {
        assert capacity >= segmentId;
        if (capacity > segmentId) {
            Object[] temp = new Object[capacity];
            System.arraycopy(segmentObjects, 0, temp, 0, segmentId);
            segmentObjects = temp;
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segments.length);
    }

    private void checkForDuplicates(Point[] points) {
        for (int i = 0; i != points.length - 1; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("element is null");
            for (int j = i + 1; j < points.length; j++) {
                if (points[j] == null)
                    throw new IllegalArgumentException("element is null");
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("duplicate arguments given");
                }
            }
        }
    }


    public static void main(String[] args) {

        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32477);
        StdDraw.setYscale(0, 32457);
        for (Point p : points) {
            StdOut.println(p);
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}