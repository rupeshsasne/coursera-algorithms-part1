/* *****************************************************************************
 *  Name: Rupesh Sasne
 *  Date: 16th June 2019
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private final LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        Utils utils = new Utils();

        utils.requiresNonNull(points, "null argument");
        utils.requiresNonNullAndUniqueValues(points);

        points = Arrays.copyOf(points, points.length);

        ArrayList<LineSegment> lineSegments = new ArrayList<>();

        Arrays.sort(points);

        for (int p = 0; p < points.length - 3; p++) {
            for (int q = p + 1; q < points.length - 2; q++) {
                double s1 = points[p].slopeTo(points[q]);

                for (int r = q + 1; r < points.length - 1; r++) {
                    double s2 = points[p].slopeTo(points[r]);

                    for (int s = r + 1; s < points.length; s++) {
                        double s3 = points[p].slopeTo(points[s]);

                        if (s1 == s2 && s2 == s3) {
                            lineSegments.add(new LineSegment(points[p], points[s]));
                        }
                    }
                }
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
}
