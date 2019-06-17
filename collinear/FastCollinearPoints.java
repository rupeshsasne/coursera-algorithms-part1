/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private final LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
        Utils utils = new Utils();

        utils.requiresNonNull(points, "null argument");
        utils.requiresNonNullAndUniqueValues(points);

        ArrayList<LineSegment> lineSegments = new ArrayList<>();

        points = Arrays.copyOf(points, points.length);
        Arrays.sort(points);

        for (int i = 0; i < points.length; i++) {
            Point p = points[i];

            Point[] copy = Arrays.copyOf(points, points.length);
            Arrays.sort(copy, p.slopeOrder());

            double refSlope;

            for (int j = 1; j < copy.length;) {

                refSlope = p.slopeTo(copy[j]);

                int position = last(refSlope, p, copy, 1, copy.length - 1);

                if (position - j >= 2 && p.compareTo(copy[j]) < 0) {
                    lineSegments.add(new LineSegment(p, copy[position]));
                }

                j = position + 1;
            }
        }

        segments = lineSegments.toArray(new LineSegment[0]);
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segments.length);
    }

    private int last(double refSlope, Point point, Point[] copy, int lo, int hi) {
        if (lo > hi) return -1;

        int mid = lo + (hi - lo) / 2;

        if ((mid == copy.length - 1 || refSlope < point.slopeTo(copy[mid + 1]))
                && refSlope == point.slopeTo(copy[mid]))
            return mid;
        else if (refSlope < point.slopeTo(copy[mid]))
            return last(refSlope, point, copy, lo, (mid - 1));
        else
            return last(refSlope, point, copy, (mid + 1), hi);
    }
}
