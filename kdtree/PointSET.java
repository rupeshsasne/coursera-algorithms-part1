import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public final class PointSET {

    private final SET<Point2D> set;

    // construct an empty set of points
    public PointSET() {
        set = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();
    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        requiresNonNull(p);

        set.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        requiresNonNull(p);
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : set) {
            StdDraw.point(p.x(), p.y());
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        requiresNonNull(rect);

        Stack<Point2D> resultSet = new Stack<>();

        for (Point2D point2D : set) {
            if (rect.contains(point2D)) {
                resultSet.push(point2D);
            }
        }

        return resultSet;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        requiresNonNull(p);

        Point2D winner = null;

        double winnerDistance = Double.POSITIVE_INFINITY;
        double dist;

        for (Point2D point2D : set) {
            dist = point2D.distanceSquaredTo(p);

            if (winnerDistance > dist) {
                winner = point2D;
                winnerDistance = dist;
            }
        }

        return winner;
    }

    private void requiresNonNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException();
        }
    }
}
