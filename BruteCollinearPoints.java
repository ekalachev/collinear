/******************************************************************************
 *  Compilation:  javac BruteCollinearPoints.java
 *  Execution:    java BruteCollinearPoints
 *  Dependencies: LineSegment.java, Point.java
 *
 *  Examines 4 points at a time and checks whether they all lie on the same line
 *  segment, returning all such line segments. To check whether the 4 points
 *  p, q, r, and s are collinear, check whether the three slopes between
 *  p and q, between p and r, and between p and s are all equal.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("Array of points is null");

        checkDuplicated(points);

        Arrays.sort(points);

        ArrayList<LineSegment> found = new ArrayList<LineSegment>();

        for (int p1 = 0; p1 < points.length - 3; p1++) {
            for (int p2 = p1 + 1; p2 < points.length - 2; p2++) {
                for (int p3 = p2 + 1; p3 < points.length - 1; p3++) {
                    for (int p4 = p3 + 1; p4 < points.length; p4++) {
                        double sloopP1P2 = points[p1].slopeTo(points[p2]);
                        double sloopP1P3 = points[p1].slopeTo(points[p3]);
                        double sloopP1P4 = points[p1].slopeTo(points[p4]);

                        if (sloopP1P2 == sloopP1P3 && sloopP1P2 == sloopP1P4) {
                            found.add(new LineSegment(points[p1], points[p4]));
                        }
                    }
                }
            }
        }

        segments = found.toArray(new LineSegment[0]);
    }

    private void checkDuplicated(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Sequence contains duplicates");
                }
            }
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

    public static void main(String[] args) {
        Point[] points = new Point[] {
                new Point(1, 1),
                new Point(2, 4),
                new Point(4, 2),
                new Point(43, 70),
                new Point(3, 3),
                new Point(1, 5),
                new Point(6, 30),
                new Point(2, 2),
                new Point(4, 4),
                new Point(4, 7),
                new Point(7, 20)
        };

        BruteCollinearPoints bcp = new BruteCollinearPoints(points);

        for (LineSegment ls : bcp.segments()) {
            StdOut.println(ls.toString());
        }
    }
}
