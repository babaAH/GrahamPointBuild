import java.util.*;

public class GrahamScan
{

    /**
     * An enum denoting a directional-turn between 3 points (vectors).
     */
    protected static enum Turn { CLOCKWISE, COUNTER_CLOCKWISE, COLLINEAR }

    /**
     * Returns true iff all points in <code>points</code> are collinear.
     *
     * @param points the list of points.
     * @return       true iff all points in <code>points</code> are collinear.
     */
    protected static boolean areAllCollinear(List<ColorPoint> points) {

        if(points.size() < 2) {
            return true;
        }

        final ColorPoint a = points.get(0);
        final ColorPoint b = points.get(1);

        for(int i = 2; i < points.size(); i++) {

            ColorPoint c = points.get(i);

            if(getTurn(a, b, c) != Turn.COLLINEAR) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the convex hull of the points created from <code>xs</code>
     * and <code>ys</code>. Note that the first and last point in the returned
     * <code>List&lt;java.awt.Point&gt;</code> are the same point.
     *
     * @param xs the x coordinates.
     * @param ys the y coordinates.
     * @return   the convex hull of the points created from <code>xs</code>
     *           and <code>ys</code>.
     * @throws IllegalArgumentException if <code>xs</code> and <code>ys</code>
     *                                  don't have the same size, if all points
     *                                  are collinear or if there are less than
     *                                  3 unique points present.
     */
    public static List<ColorPoint> getConvexHull(int[] xs, int[] ys) throws IllegalArgumentException {

        if(xs.length != ys.length) {
            throw new IllegalArgumentException("xs and ys don't have the same size");
        }

        List<ColorPoint> points = new ArrayList<ColorPoint>();

        for(int i = 0; i < xs.length; i++) {
            points.add(new ColorPoint(xs[i], ys[i]));
        }

        return getConvexHull(points);
    }

    /**
     * Returns the convex hull of the points created from the list
     * <code>points</code>. Note that the first and last point in the
     * returned <code>List&lt;java.awt.Point&gt;</code> are the same
     * point.
     *
     * @param points the list of points.
     * @return       the convex hull of the points created from the list
     *               <code>points</code>.
     * @throws IllegalArgumentException if all points are collinear or if there
     *                                  are less than 3 unique points present.
     */
    public static List<ColorPoint> getConvexHull(List<ColorPoint> points) throws IllegalArgumentException {

        List<ColorPoint> sorted = new ArrayList<ColorPoint>(getSortedPointSet(points));

        if(sorted.size() < 3) {
            throw new IllegalArgumentException("can only create a convex hull of 3 or more unique points");
        }

        if(areAllCollinear(sorted)) {
            throw new IllegalArgumentException("cannot create a convex hull from collinear points");
        }

        Stack<ColorPoint> stack = new Stack<ColorPoint>();
        stack.push(sorted.get(0));
        stack.push(sorted.get(1));

        for (int i = 2; i < sorted.size(); i++) {

            ColorPoint head = sorted.get(i);
            ColorPoint middle = stack.pop();
            ColorPoint tail = stack.peek();

            Turn turn = getTurn(tail, middle, head);

            switch(turn) {
                case COUNTER_CLOCKWISE:
                    stack.push(middle);
                    stack.push(head);
                    break;
                case CLOCKWISE:
                    i--;
                    break;
                case COLLINEAR:
                    stack.push(head);
                    break;
            }
        }

        // close the hull
        stack.push(sorted.get(0));

        return new ArrayList<ColorPoint>(stack);
    }

    /**
     * Returns the points with the lowest y coordinate. In case more than 1 such
     * point exists, the one with the lowest x coordinate is returned.
     *
     * @param points the list of points to return the lowest point from.
     * @return       the points with the lowest y coordinate. In case more than
     *               1 such point exists, the one with the lowest x coordinate
     *               is returned.
     */
    protected static ColorPoint getLowestPoint(List<ColorPoint> points) {

        ColorPoint lowest = points.get(0);

        for(int i = 1; i < points.size(); i++) {

            ColorPoint temp = points.get(i);

            if(temp.getY() < lowest.getY() ||
                    (temp.getY() == lowest.getY() && temp.getY() < lowest.getY()))
            {
                lowest = temp;
            }
        }

        return lowest;
    }

    /**
     * Returns a sorted set of points from the list <code>points</code>. The
     * set of points are sorted in increasing order of the angle they and the
     * lowest point <tt>P</tt> make with the x-axis. If tow (or more) points
     * form the same angle towards <tt>P</tt>, the one closest to <tt>P</tt>
     * comes first.
     *
     * @param points the list of points to sort.
     * @return       a sorted set of points from the list <code>points</code>.
     * @see GrahamScan#getLowestPoint(java.util.List)
     */
    protected static Set<ColorPoint> getSortedPointSet(List<ColorPoint> points) {

        final ColorPoint lowest = getLowestPoint(points);

        TreeSet<ColorPoint> set = new TreeSet<ColorPoint>(new Comparator<ColorPoint>() {
            @Override
            public int compare(ColorPoint a, ColorPoint b) {

                if(a == b || a.equals(b)) {
                    return 0;
                }

                // use longs to guard against int-underflow
                double thetaA = Math.atan2((long)a.getY() - lowest.getY(), (long)a.getX() - lowest.getX());
                double thetaB = Math.atan2((long)b.getY() - lowest.getY(), (long)b.getX() - lowest.getX());

                if(thetaA < thetaB) {
                    return -1;
                }
                else if(thetaA > thetaB) {
                    return 1;
                }
                else {
                    // collinear with the 'lowest' point, let the point closest to it come first

                    // use longs to guard against int-over/underflow
                    double distanceA = Math.sqrt((((long)lowest.getX() - a.getX()) * ((long)lowest.getX() - a.getX())) +
                            (((long)lowest.getY() - a.getY()) * ((long)lowest.getY() - a.getY())));
                    double distanceB = Math.sqrt((((long)lowest.getX() - b.getX()) * ((long)lowest.getX() - b.getX())) +
                            (((long)lowest.getY() - b.getY()) * ((long)lowest.getY() - b.getY())));

                    if(distanceA < distanceB) {
                        return -1;
                    }
                    else {
                        return 1;
                    }
                }
            }
        });

        set.addAll(points);

        return set;
    }

    /**
     * Returns the GrahamScan#Turn formed by traversing through the
     * ordered points <code>a</code>, <code>b</code> and <code>c</code>.
     * More specifically, the cross product <tt>C</tt> between the
     * 3 points (vectors) is calculated:
     *
     * <tt>(b.x-a.x * c.y-a.y) - (b.y-a.y * c.x-a.x)</tt>
     *
     * and if <tt>C</tt> is less than 0, the turn is CLOCKWISE, if
     * <tt>C</tt> is more than 0, the turn is COUNTER_CLOCKWISE, else
     * the three points are COLLINEAR.
     *
     * @param a the starting point.
     * @param b the second point.
     * @param c the end point.
     * @return the GrahamScan#Turn formed by traversing through the
     *         ordered points <code>a</code>, <code>b</code> and
     *         <code>c</code>.
     */
    protected static Turn getTurn(ColorPoint a, ColorPoint b, ColorPoint c) {

        // use longs to guard against int-over/underflow
        long crossProduct = (((long)b.getX() - a.getX()) * ((long)c.getY() - a.getY())) -
                (((long)b.getY() - a.getY()) * ((long)c.getX() - a.getX()));

        if(crossProduct > 0) {
            return Turn.COUNTER_CLOCKWISE;
        }
        else if(crossProduct < 0) {
            return Turn.CLOCKWISE;
        }
        else {
            return Turn.COLLINEAR;
        }
    }
}

