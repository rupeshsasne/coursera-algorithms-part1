import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.util.ArrayList;

public final class KdTree {

    private Node treeRoot;

    private static final class Node {
        private final Point2D point;

        private int size = 1;
        private int level = 0;

        private Node lb = null;
        private Node rt = null;

        private RectHV rect;

        private Node(Point2D p) {
            point = p;
        }
    }

    public boolean isEmpty() {
        return treeRoot == null;
    }

    public int size() {
        return isEmpty() ? 0 : treeRoot.size;
    }

    public void insert(final Point2D p) {
        requiresNonNull(p);
        treeRoot = insert(treeRoot, null, p, 0);
    }

    public boolean contains(final Point2D p) {
        requiresNonNull(p);
        return contains(treeRoot, p) != null;
    }

    public void draw() {
        draw(treeRoot);
    }

    public Iterable<Point2D> range(final RectHV rect) {
        requiresNonNull(rect);

        ArrayList<Point2D> iterable = new ArrayList<>();
        range(treeRoot, rect, iterable);

        return iterable;
    }

    public Point2D nearest(final Point2D p) {
        requiresNonNull(p);

        if (isEmpty()) return null;

        return nearest(treeRoot, p, treeRoot.point);
    }

    private Point2D nearest(final Node node, final Point2D queryPoint,
                            final Point2D nearestPointFoundSoFar) {
        Point2D winner = nearestPointFoundSoFar;

        if (node == null) {
            return winner;
        }

        double winningDistance = winner.distanceSquaredTo(queryPoint);

        if (winningDistance < node.rect.distanceSquaredTo(queryPoint)) {
            return winner;
        }

        if (node.point.distanceSquaredTo(queryPoint) < winningDistance) {
            winner = node.point;
        }

        if (node.lb == null && node.rt == null) {
            return winner;
        }

        if (node.lb == null) {
            return nearest(node.rt, queryPoint, winner);
        }

        if (node.rt == null) {
            return nearest(node.lb, queryPoint, winner);
        }

        if (node.lb.rect.distanceSquaredTo(queryPoint) < node.rt.rect
                .distanceSquaredTo(queryPoint)) {
            winner = nearest(node.lb, queryPoint, winner);
            winner = nearest(node.rt, queryPoint, winner);
        }
        else {
            winner = nearest(node.rt, queryPoint, winner);
            winner = nearest(node.lb, queryPoint, winner);
        }

        return winner;
    }

    private void range(final Node root, final RectHV rect, final ArrayList<Point2D> list) {
        if (root == null) {
            return;
        }

        if (rect.intersects(root.rect)) {

            if (rect.contains(root.point)) {
                list.add(root.point);
            }

            range(root.lb, rect, list);
            range(root.rt, rect, list);
        }
    }

    private void draw(final Node node) {
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(Color.BLACK);
        node.point.draw();

        StdDraw.setPenRadius();
        StdDraw.setPenColor(node.level % 2 == 0 ? Color.BLUE : Color.RED);
        node.rect.draw();

        if (node.lb != null) {
            draw(node.lb);
        }

        if (node.rt != null) {
            draw(node.rt);
        }
    }

    private Node insert(final Node root, final Node parent, final Point2D point, final int level) {
        if (root == null) {

            Node nodeToInsert = new Node(point);
            nodeToInsert.size = 1;
            nodeToInsert.lb = null;
            nodeToInsert.rt = null;
            nodeToInsert.level = level;
            nodeToInsert.rect = getRect(parent, point);

            return nodeToInsert;
        }

        int cmp = root.level % 2 == 0 ? Double.compare(root.point.x(), point.x())
                                      : Double.compare(root.point.y(), point.y());

        if (cmp >= 0) {
            if (cmp == 0 && root.point.equals(point)) {
                return root;
            }

            root.lb = insert(root.lb, root, point, level + 1);
        }
        else {
            root.rt = insert(root.rt, root, point, level + 1);
        }

        root.size = 1 + (root.lb != null ? root.lb.size : 0) + (root.rt != null ? root.rt.size : 0);

        return root;
    }

    private RectHV getRect(final Node parent, final Point2D point) {
        if (parent == null) {
            return new RectHV(0, 0, 1, 1);
        }

        if (parent.level % 2 == 0) {
            double parentX = parent.point.x();

            return getLRRect(
                    Double.compare(parentX, point.x()),
                    parent,
                    parentX,
                    parent.rect
            );
        }

        double parentY = parent.point.y();

        return getTBRect(
                Double.compare(parentY, point.y()),
                parent,
                parentY,
                parent.rect
        );
    }

    private RectHV getTBRect(final int cmp, final Node root, double rootY,
                             final RectHV defaultRect) {
        if (cmp < 0) {
            return new RectHV(root.rect.xmin(), rootY, root.rect.xmax(), root.rect.ymax());
        }
        else if (cmp > 0) {
            return new RectHV(root.rect.xmin(), root.rect.ymin(), root.rect.xmax(), rootY);
        }

        return defaultRect;
    }

    private RectHV getLRRect(final int cmp, final Node root, double rootX,
                             final RectHV defaultRect) {
        if (cmp > 0) {
            return new RectHV(root.rect.xmin(), root.rect.ymin(), rootX, root.rect.ymax());
        }
        else if (cmp < 0) {
            return new RectHV(rootX, root.rect.ymin(), root.rect.xmax(), root.rect.ymax());
        }

        return defaultRect;
    }

    private Node contains(final Node root, final Point2D point) {
        if (root == null) {
            return null;
        }

        int cmp = root.level % 2 == 0
                  ? Double.compare(root.point.x(), point.x())
                  : Double.compare(root.point.y(), point.y());

        if (cmp >= 0) {
            return root.point.equals(point) ? root : contains(root.lb, point);
        }
        else {
            return contains(root.rt, point);
        }
    }

    private void requiresNonNull(final Object object) {
        if (object == null) {
            throw new IllegalArgumentException();
        }
    }
}
