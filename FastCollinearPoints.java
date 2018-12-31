import java.util.ArrayList;
import java.util.Arrays;

/******************************************************************************
 *
 *  Compilation:  javac FastCollinearPoints.java
 *  Execution:    java FastCollinearPoints
 *  Dependencies: LineSegment.java, Point.java
 *
 *  Given a point p, the following method determines whether p participates
 *  in a set of 4 or more collinear points.
 *
 ******************************************************************************/

public class FastCollinearPoints {
    private final LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("Array of points is null");

        checkOnNullEntrypoint(points);

        Point[] cPoints = points.clone();

        // sort points by XY
        Arrays.sort(cPoints);

        checkDuplicated(cPoints);

        segments = findSegments(cPoints);
    }

    // find segments without duplicates
    private LineSegment[] findSegments(Point[] cPoints) {
        ArrayList<LineSegment> foundSegments = new ArrayList<LineSegment>();

        for (int i = 0; i < cPoints.length - 3; i++) {
            Arrays.sort(cPoints);

            Arrays.sort(cPoints, cPoints[i].slopeOrder());

            for (int p = 0, first = 1, last = 2; last < cPoints.length;) {
                while (last < cPoints.length &&
                        Double.compare(cPoints[p].slopeTo(cPoints[first]),
                                       cPoints[p].slopeTo(cPoints[last])) == 0) {
                    last++;
                }

                if (last - first >= 3 && cPoints[p].compareTo(cPoints[first]) < 0) {
                    foundSegments.add(new LineSegment(cPoints[p], cPoints[last - 1]));
                }

                first = last;
                last++;
            }
        }

        return foundSegments.toArray(new LineSegment[0]);
    }

    // checks that sequence has not null entry point
    private void checkOnNullEntrypoint(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("Sequence has null entry point");
        }
    }

    // checks duplicates in array
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
