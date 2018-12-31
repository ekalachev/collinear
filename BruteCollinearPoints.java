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

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("Array of points is null");

        checkOnNullEntrypoint(points);

        Point[] cPoints = points.clone();

        Arrays.sort(cPoints);

        checkDuplicated(cPoints);

        ArrayList<LineSegment> found = new ArrayList<LineSegment>();

        for (int p1 = 0; p1 < cPoints.length - 3; p1++) {
            for (int p2 = p1 + 1; p2 < cPoints.length - 2; p2++) {
                for (int p3 = p2 + 1; p3 < cPoints.length - 1; p3++) {
                    for (int p4 = p3 + 1; p4 < cPoints.length; p4++) {
                        double sloopP1P2 = cPoints[p1].slopeTo(cPoints[p2]);
                        double sloopP1P3 = cPoints[p1].slopeTo(cPoints[p3]);
                        double sloopP1P4 = cPoints[p1].slopeTo(cPoints[p4]);

                        if (Double.compare(sloopP1P2, sloopP1P3) == 0
                                && Double.compare(sloopP1P2, sloopP1P4) == 0) {
                            found.add(new LineSegment(cPoints[p1], cPoints[p4]));
                        }
                    }
                }
            }
        }

        segments = found.toArray(new LineSegment[0]);
    }

    private void checkOnNullEntrypoint(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("Sequence has null entry point");
        }
    }

    private void checkDuplicated(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("Sequence contains duplicates");
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
}
