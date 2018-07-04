import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

    private final LineSegment[] lineSegments;

    public BruteCollinearPoints(final Point[] points) {
        if (points == null)
            throw new java.lang.IllegalArgumentException();
        checkForDuplicates(points);
        List<LineSegment> lineSegmentsArrayList = new ArrayList<>();
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy, Point::compareTo);
        for (int p = 0; p < pointsCopy.length - 3; p++) {
            for (int q = p + 1; q < pointsCopy.length - 2; q++) {
                for (int r = q + 1; r < pointsCopy.length - 1; r++) {
                    for (int s = r + 1; s < pointsCopy.length; s++) {
                        if (pointsCopy[p].slopeTo(pointsCopy[q]) == pointsCopy[p].slopeTo(pointsCopy[r]) &&
                                pointsCopy[p].slopeTo(pointsCopy[q]) == pointsCopy[p].slopeTo(pointsCopy[s])) {
                            lineSegmentsArrayList.add(new LineSegment(pointsCopy[p], pointsCopy[s]));
                        }
                    }
                }
            }
        }
        lineSegments = lineSegmentsArrayList.toArray(new LineSegment[lineSegmentsArrayList.size()]);
    }  // finds all line segments containing 4 points


    public int numberOfSegments() {
        return lineSegments.length;
    }


    public LineSegment[] segments() {
        return Arrays.copyOf(lineSegments, numberOfSegments());
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
            p.draw();
            StdOut.println(p);
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
