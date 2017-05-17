package part1.week5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.LinkedList;


/**
 * A mutable data type KdTree.java that uses a 2d-tree to implement the same API (but replace PointSET with KdTree).
 * A 2d-tree is a generalization of a BST to two-dimensional keys. The idea is to build a BST with points in the nodes,
 * using the x- and y-coordinates of the points as keys in strictly alternating sequence.
 */
public class KdTree {

    private int size;
    private Node root;
    private RectHV rectHV;

    private static class Node {
        private Point2D p;      // the point
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private RectHV rect;

        Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
        }
    }

    public KdTree() {
        root = null;
        size = 0;
        rectHV = new RectHV(0, 0, 1, 1);
    }

    public boolean isEmpty() {
        return  size == 0;
    }

    public int size() {
        return this.size;
    }

    public void insert(Point2D point) {
        if (point == null) throw new NullPointerException("null arguments passed");
        RectHV defaultRect = new RectHV(0, 0, 1, 1);
        root = insert(root, point, defaultRect, true);
    }

    private Node insert(Node head, Point2D point, RectHV rect, boolean isVertical) {

        if (head == null) {
            size++;
            return new Node(point, rect);
        }

        if (head.p.equals(point)) return head;

        int compare;

        if (isVertical) compare = Point2D.X_ORDER.compare(point, head.p);
        else compare = Point2D.Y_ORDER.compare(point, head.p);


        if (isVertical) {
            if (compare == -1) {
                if (head.lb != null) rectHV = head.lb.rect;
                else rectHV =  new RectHV(rect.xmin(), rect.ymin(), head.p.x(), rect.ymax());
                head.lb = insert(head.lb, point, rectHV, !isVertical);
            } else {
                if (head.rt != null) rectHV = head.rt.rect;
                else rectHV = new RectHV(head.p.x(), rect.ymin(), rect.xmax(), rect.ymax());
                head.rt = insert(head.rt, point, rectHV, !isVertical);
            }

        }
        else {
            if (compare == -1) {
                if (head.lb != null) rectHV = head.lb.rect;
                else rectHV = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), head.p.y());
                head.lb = insert(head.lb, point, rectHV, !isVertical);
            }
            else {

                if (head.rt != null) rectHV = head.rt.rect;
                else rectHV = new RectHV(rect.xmin(), head.p.y(), rect.xmax(), rect.ymax());
                head.rt = insert(head.rt, point, rectHV, !isVertical);
            }
        }

        return  head;
    }

    public boolean contains(Point2D point) {
        if (point == null) throw new NullPointerException("null arguments passed");
        return contains(root, point, true) != null;
    }

    private Node contains(Node head, Point2D point, boolean isVertical) {

        if (head == null) return null;
        if (head.p.equals(point)) return head;

        int compare;

        if (isVertical) compare = Point2D.X_ORDER.compare(point, head.p);
        else compare = Point2D.Y_ORDER.compare(point, head.p);

        if (compare == -1) return contains(head.lb, point, !isVertical);
        else return contains(head.rt, point, !isVertical);
    }

    public void draw() {
        StdDraw.rectangle(0.0, 0.0, 1.0, 1.0);
        if (isEmpty()) return;
        draw(root, true);
    }

    private void draw(Node head, boolean isVertical) {

        if (head.lb != null) draw(head.lb, !isVertical);
        if (head.rt != null) draw(head.rt, !isVertical);

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        head.p.draw();

        double xMin, yMin, xMax, yMax;

        if (isVertical) {

            StdDraw.setPenColor(StdDraw.RED);
            xMin = head.p.x();
            yMin = head.rect.ymin();
            xMax = head.p.x();
            yMax = head.rect.ymax();
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            xMin = head.rect.xmin();
            yMin = head.p.y();
            xMax = head.rect.xmax();
            yMax = head.p.y();
        }

        StdDraw.setPenRadius();
        StdDraw.line(xMin, yMin, xMax, yMax);

    }

    public Iterable<Point2D> range(RectHV rect) {

        if (rect == null) throw new NullPointerException("null arguments passed");
        LinkedList<Point2D> pointsInRange = new LinkedList<>();
        if (isEmpty()) return  pointsInRange;
        range(root, rect, pointsInRange);
        return pointsInRange;
    }

    private void range(Node head, RectHV rect, LinkedList<Point2D> pointIterable) {

        if (head == null) return;
        if (rect.contains(head.p)) pointIterable.add(head.p);
        if (head.lb != null && head.lb.rect.intersects(rect)) range(head.lb, rect, pointIterable);
        if (head.rt != null && head.rt.rect.intersects(rect)) range(head.rt, rect, pointIterable);
    }

    public Point2D nearest(Point2D p) {

        if (p == null) throw new NullPointerException("null arguments passed");
        if (isEmpty()) return  null;

        Point2D minPoint = root.p;
        minPoint = nearest(p, root, minPoint, true);

        return minPoint;
    }

    private Point2D nearest(Point2D point, Node head, Point2D minPoint, boolean isVertical) {

        if (head == null) return minPoint;

        double distanceToHead = point.distanceSquaredTo(head.p);
        double distanceToMinPoint = point.distanceSquaredTo(minPoint);

        if (distanceToHead < distanceToMinPoint) minPoint = head.p;

        int compare;

        if (isVertical) compare = Point2D.X_ORDER.compare(point, head.p);
        else compare = Point2D.Y_ORDER.compare(point, head.p);


        if (compare == -1) {
            minPoint = nearest(point, head.lb, minPoint, !isVertical);
            if (head.rt != null && point.distanceSquaredTo(minPoint) > head.rt.rect.distanceSquaredTo(point))
                minPoint = nearest(point, head.rt, minPoint, !isVertical);
        }
        else {
            minPoint = nearest(point, head.rt, minPoint, !isVertical);
            if (head.lb != null && point.distanceSquaredTo(minPoint) > head.lb.rect.distanceSquaredTo(point))
                minPoint = nearest(point, head.lb, minPoint, !isVertical);
        }

        return minPoint;
    }
}
