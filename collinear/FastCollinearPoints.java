/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private ArrayList<LineSegment> lineSegments;

    public FastCollinearPoints(Point[] points) {

        checkForNull(points);

        checkForNullPoinst(points);

        checkForDuplicates(points);

        lineSegments = new ArrayList<>();

        Point[] copyPoints = copyArr(points);

        ArrayList<Double> forbiddenSlopes = new ArrayList<>();

        for (int i = 0; i < points.length; i++) {
            forbiddenSlopes.clear();
            int currentIndex = 0;
            int count = 0;
            Arrays.sort(copyPoints);
            Arrays.sort(copyPoints, points[i].slopeOrder());
            for (int j = 1; j < copyPoints.length - 1; j++) {
                double firstSlope = copyPoints[0].slopeTo(copyPoints[j]);
                double secondSlope = copyPoints[0].slopeTo(copyPoints[j + 1]);
                if (firstSlope == secondSlope && checkForForbiddens(forbiddenSlopes, firstSlope)) {
                    if (!checkForAnchor(copyPoints[0], copyPoints[j])) {
                        forbiddenSlopes.add(firstSlope);
                        continue;
                    }

                    count++;
                    currentIndex = j + 1;
                    if (j == copyPoints.length - 2) {
                        checkForCollinear(count, copyPoints, currentIndex);
                    }
                }
                else {
                    checkForCollinear(count, copyPoints, currentIndex);
                    count = 0;
                }
            }
        }
    }

    private boolean checkForForbiddens(ArrayList<Double> forbiddenSlopes, double firstSlope) {
        return !forbiddenSlopes.contains(firstSlope);
    }

    private boolean checkForAnchor(Point a, Point b) {
        if (a.compareTo(b) > 0) {
            return false;
        }
        return true;
    }

    private void checkForNullPoinst(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            checkForNull(points[i]);
        }
    }

    private void checkForNull(Object a) {
        if (a == null) {
            throw new IllegalArgumentException();
        }
    }

    private void checkForDuplicates(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    private void checkForCollinear(int count, Point[] copyPoints, int currentIndex) {
        if (count >= 2) {
            lineSegments.add(new LineSegment(copyPoints[0], copyPoints[currentIndex]));
        }
    }

    private Point[] copyArr(Point[] points) {
        Point[] copyPoints = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            copyPoints[i] = points[i];
        }
        return copyPoints;
    }

    public int numberOfSegments() {
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
