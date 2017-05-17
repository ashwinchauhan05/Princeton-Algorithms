package part1.week3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ashwinch on 26/03/17.
 *
 * BruteCollinearPoints.java examines 4 points at a time and checks whether they all lie on the same line segment,
 * returning all such line segments. To check whether the 4 points p, q, r, and s are collinear, check whether the
 * three slopes between p and q, between p and r, and between p and s are all equal.
 *
 */
public class BruteCollinearPoints {

    private List<LineSegment> lineSegments = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {

        if (points == null) throw new NullPointerException("input received is null");

        int numberOfPoints = points.length;

        Point[] cloneArray = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            cloneArray[i] = points[i];
        }

        Arrays.sort(cloneArray);
        for (int i = 0; i < numberOfPoints-1; i++) {
            if (cloneArray[i] == null) throw new NullPointerException("null point passed in the array");

            if (cloneArray[i].compareTo(cloneArray[i+1]) == 0)
                throw new IllegalArgumentException("Given input contains repeated point");
        }

        for (int i = 0; i < numberOfPoints-3; i++) {
            for (int j = i+1; j < numberOfPoints-2; j++) {
                for (int k = j+1; k < numberOfPoints-1; k++) {

                    double slopeIJ = points[i].slopeTo(points[j]);
                    double slopeIK = points[i].slopeTo(points[k]);

                    if (slopeIJ == slopeIK) {

                        for (int l = k+1; l < numberOfPoints; l++) {
                            double slopeIL = points[i].slopeTo(points[l]);
                            if (slopeIJ == slopeIL) {

                                Point[] tempPoints = new Point[4];
                                tempPoints[0] = points[i];
                                tempPoints[1] = points[j];
                                tempPoints[2] = points[k];
                                tempPoints[3] = points[l];
                                Arrays.sort(tempPoints);

                                lineSegments.add(new LineSegment(tempPoints[0], tempPoints[3]));
                            }
                        }
                    }

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

