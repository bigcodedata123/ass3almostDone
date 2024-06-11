// Name: Yonatan Omer
// ID: 322624693

/**
 * Represents a point in a 2D coordinate system.
 */
public class Point {
    // Variables to hold the coordinates of the point
    private final double x; // The x-coordinate of the point
    private final double y; // The y-coordinate of the point

    /**
     * Constructor to initialize a point with given x and y coordinates.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Method to return the distance from this point to another point.
     *
     * @param other The other point.
     * @return The distance between this point and the other point.
     */
    public double distance(Point other) {
        // Using the threshold method for double comparison
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    /**
     * Method to check if this point is equal to another point.
     *
     * @param other The other point.
     * @return True if this point is equal to the other point, false otherwise.
     */
    public boolean equals(Point other) {
        if (other == null) {
            return false;
        }
        // Using the threshold method for double comparison
        return Line.threshold(this.x, other.x, Line.EPSILON) && Line.threshold(this.y, other.y, Line.EPSILON);
    }

    /**
     * Getter method to return the x-coordinate of this point.
     *
     * @return The x-coordinate of this point.
     */
    public double getX() {
        return x;
    }

    /**
     * Getter method to return the y-coordinate of this point.
     *
     * @return The y-coordinate of this point.
     */
    public double getY() {
        return y;
    }
}