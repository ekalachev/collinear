/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class BruteCollinearPoints {
    private final LineSegment[] segments;
    private int segmentsLength = 0;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null || points.length < 4)
            throw new IllegalArgumentException("Array of points should contain 4 or more points");

        sort(points);

        segments = new LineSegment[0];

        for (int p1 = 0; p1 < points.length - 3; p1++) {
            for (int p2 = p1 + 1; p2 < points.length - 2; p2++) {
                for (int p3 = p2 + 1; p3 < points.length - 1; p3++) {
                    for (int p4 = p3 + 1; p4 < points.length; p4++) {
                        Point point1 = points[p1];
                        Point point2 = points[p2];
                        Point point3 = points[p3];
                        Point point4 = points[p4];

                        double sloopP1P2 = point1.slopeTo(point2);
                        double sloopP1P3 = point1.slopeTo(point2);
                        double sloopP1P4 = point1.slopeTo(point4);

                        if (sloopP1P2 == sloopP1P3 && sloopP1P2 == sloopP1P4) {
                            //TODO add to array of segments
                            new LineSegment(point1, point4);
                        }
                    }
                }
            }
        }
    }

    private static void merge(Point[] a, Point[] aux, int lo, int mid, int hi) {
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid)
                aux[k] = a[j++];
            else if (j > hi)
                aux[k] = a[i++];
            else if (less(a[j], a[i]))
                aux[k] = a[j++];
            else
                aux[k] = a[i++];
        }
    }

    private static boolean less(Point p1, Point p2) {
        if (p1 == null || p2 == null)
            throw new IllegalArgumentException();

        return p1.compareTo(p2) < 0;
    }

    private static void sort(Point[] a, Point[] aux, int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;

        sort(aux, a, lo, mid);
        sort(aux, a, mid + 1, hi);

        merge(a, aux, lo, mid, hi);
    }

    private static void sort(Point[] a) {
        Point[] aux = new Point[a.length];
        sort(a, aux, 0, a.length - 1);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segmentsLength;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments;
    }

    public static void main(String[] args) {

    }
}
