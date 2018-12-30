import java.util.Arrays;
import java.util.HashMap;

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
    private LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("Array of points is null");

        checkDuplicated(points);

        HashMap<Double, LineSegment> found = new HashMap<Double, LineSegment>();

        // sort points by XY
        Arrays.sort(points);

        for (int i = 0; i < points.length; i++) {
            findSegments(points[i], Arrays.copyOfRange(points, i + 1, points.length), found);
        }

        segments = found.values().toArray(new LineSegment[0]);
    }

    private void findSegments(Point p, Point[] points, HashMap<Double, LineSegment> found) {
        Arrays.sort(points, p.slopeOrder());

        int slopesOfSegmentLength = 1;
        double previousSlope = 0.0;

        for (int i = 0; i < points.length; i++) {
            double slopePQ = p.slopeTo(points[i]);

            // if end of array or PQ1 != PQ2
            if (i == points.length - 1 || slopePQ != p.slopeTo(points[i + 1])) {
                if (slopesOfSegmentLength >= 3 && !found.containsKey(previousSlope)) {
                    found.put(previousSlope, new LineSegment(p, points[i]));
                }

                slopesOfSegmentLength = 1;
            }
            else {
                slopesOfSegmentLength++;
                previousSlope = slopePQ;
            }
        }
    }

    // checks duplicates in array
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
}
