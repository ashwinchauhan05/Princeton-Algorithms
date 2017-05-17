package part1.week3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * FastCollinearPoints - Sort the array based on the slope they make with a point. Find all the points, if any, that are
 * collinear to the given points. Add all these to a map with slope,arrayList as the key,value pair. Iterator over these
 * value pairs, sort each array in natural order and return lineSegments with the first and last points in the array.
 * That should do.
 */
public class FastCollinearPoints {

    private List<LineSegment> lineSegments = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {

        if (points == null) throw new NullPointerException("input received is null");

        ArrayList<Point> collinearPoints;
        ArrayList<Point[]> cordinates = new ArrayList<>();
        boolean shouldInsert;

        Point[] cloneArray = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            cloneArray[i] = points[i];
        }

        Arrays.sort(cloneArray);

        for (int i = 0; i < cloneArray.length; i++)
            if (cloneArray[i] == null)
                throw new NullPointerException("input received is null");

        for (int i = 0; i < cloneArray.length-1; i++) {
            if (cloneArray[i].compareTo(cloneArray[i+1]) == 0)
                throw new IllegalArgumentException("Duplicate Element found");
        }


        for (int j = 0; j < points.length; j++) {

            shouldInsert = true;

            Point p = points[j];
            Arrays.sort(cloneArray, p.slopeOrder());
            int startIndex = 0;
            int endIndex = 0;

            collinearPoints = new ArrayList<>();
            collinearPoints.add(p);

            for (int i = 1; i < cloneArray.length-1; i++) {
                if (p.slopeTo(cloneArray[i]) == p.slopeTo(cloneArray[i+1])) {
                    if (startIndex == 0) startIndex = i;
                    endIndex = i+1;
                } else if (endIndex - startIndex >= 2)
                    break;
                else {
                    startIndex = 0;
                    endIndex = 0;
                }
            }

            for (int i = startIndex; i <= endIndex; i++) {
                collinearPoints.add(cloneArray[i]);
            }

            if (collinearPoints.size() >= 4) {
                Point[] pointArray = collinearPoints.toArray(new Point[collinearPoints.size()]);
                Arrays.sort(pointArray);

                Point startPoint = pointArray[0];
                Point endPoint = pointArray[pointArray.length-1];

                for (int i = 0; i < cordinates.size(); i++) {
                    Point[] ls = cordinates.get(i);

                    if (ls[0].compareTo(startPoint) == 0 && ls[1].compareTo(endPoint) == 0) {
                        shouldInsert = false;
                        break;
                    }
                }

                if (shouldInsert) {
                    Point[] fp = new Point[2];
                    fp[0] = startPoint;
                    fp[1] = endPoint;
                    cordinates.add(fp);

                    lineSegments.add(new LineSegment(startPoint, endPoint));
                }
            }
        }
    }

    public int numberOfSegments() {
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        return  lineSegments.toArray(new LineSegment[numberOfSegments()]);
    }

}

