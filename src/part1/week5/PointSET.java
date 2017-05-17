package part1.week5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.LinkedList;

/**
 * A mutable data type PointSET.java that represents a set of points in the unit square. Implement the following API by
 * using a red-black BST (using either SET from algs4.jar or java.util.TreeSet).
 */
public class PointSET {

    private SET<Point2D> pointSet;

    public PointSET() {
        pointSet = new SET<Point2D>();
    }

    public boolean isEmpty() {
        return  pointSet.isEmpty();
    }

    public int size() {
        return pointSet.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException("null arguments passed");
        pointSet.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException("null arguments passed");
        return pointSet.contains(p);
    }

    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        for (Point2D point: pointSet) {
            point.draw();
        }

        StdDraw.show();
    }

    public Iterable<Point2D> range(RectHV rect) {

        if (rect == null) throw new NullPointerException("null arguments passed");
        LinkedList<Point2D> pointInRange = new LinkedList<>();
        for (Point2D point: pointSet) {
            if (rect.contains(point)) {
                pointInRange.add(point);
            }
        }
        return pointInRange;
    }

    public  Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException("null arguments passed");
        if (isEmpty()) return null;
        Point2D nearestPoint = null;
        double nearestDistance = Double.POSITIVE_INFINITY;

        for (Point2D point: pointSet) {
            double distanceToPoint = point.distanceSquaredTo(p);
            if (distanceToPoint < nearestDistance) {
                nearestDistance = distanceToPoint;
                nearestPoint = point;
            }
        }
        return nearestPoint;
    }
}
