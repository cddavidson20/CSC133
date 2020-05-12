package csc133.towerdefense.game.helpers;

import android.graphics.PointF;

/**
 * Functions (Methods) to help make coding easier. They just work man.
 */
public class Functions {
    public static float getDirectionAngle(float x, float y, float targetX, float targetY) {

        float xDiff = targetX - x;
        float yDiff = targetY - y;

        return (float) Math.atan2(yDiff, xDiff);
    }

    /**
     * Basically an atan2 method used to find the unit vector.
     *
     * @param x       Current x
     * @param y       Current y
     * @param targetX target or destination x
     * @param targetY target or destination y
     * @return the unit vector in the direction of targetX and targetY
     */
    public static PointF getUnitDirection(float x, float y, float targetX, float targetY) {
        float resX;
        float resY;

        float xDiff = targetX - x;
        float yDiff = targetY - y;

        float angle = (float) Math.atan2(yDiff, xDiff);

        resX = (float) Math.cos(angle);
        resY = (float) Math.sin(angle);

        return new PointF(resX, resY);
    }

    /**
     * Check for opposite signs between 2 numbers.
     *
     * @param x number 1
     * @param y number 2
     * @return if the numbers are opposite signs.
     */
    public static boolean oppositeSigns(float x, float y) {
        return !((x < 0) == (y < 0));
    }

    /**
     * @param Ax point x of rect A
     * @param Ay point y of rect A
     * @param Aw width
     * @param Ah
     * @param Bx
     * @param By
     * @param Bw
     * @param Bh
     * @return
     */
    public static boolean rectInRect(float Ax, float Ay, float Aw, float Ah,
                                     float Bx, float By, float Bw, float Bh) {
        Ax -= Aw / 2;
        Ay -= Ah / 2;
        Bx -= Bw / 2;
        By -= Bh / 2;
        return Bx + Bw > Ax &&
                By + Bh > Ay &&
                Ax + Aw > Bx &&
                Ay + Ah > By;
    }

    // LINE/LINE
    public static boolean lineInLine(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {

        // calculate the direction of the lines
        float uA = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / ((y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1));
        float uB = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / ((y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1));

        // if uA and uB are between 0-1, lines are colliding
        if (uA >= 0 && uA <= 1 && uB >= 0 && uB <= 1) {

            // optionally, draw a circle where the lines meet
            float intersectionX = x1 + (uA * (x2 - x1));
            float intersectionY = y1 + (uA * (y2 - y1));

            return true;
        }
        return false;
    }

    public static boolean lineInRect(float x1, float y1, float x2, float y2, float rx, float ry, float rw, float rh) {

        rx -= rw / 2;
        ry -= rh / 2;
        // check if the line has hit any of the rectangle's sides
        // uses the Line/Line function below
        boolean left = lineInLine(x1, y1, x2, y2, rx, ry, rx, ry + rh);
        boolean right = lineInLine(x1, y1, x2, y2, rx + rw, ry, rx + rw, ry + rh);
        boolean top = lineInLine(x1, y1, x2, y2, rx, ry, rx + rw, ry);
        boolean bottom = lineInLine(x1, y1, x2, y2, rx, ry + rh, rx + rw, ry + rh);

        // if ANY of the above are true, the line
        // has hit the rectangle
        return left || right || top || bottom;
    }

    public static boolean circleInRect(float cx, float cy, float radius, float rx, float ry, float rw, float rh) {
        // temporary variables to set edges for testing
        float testX = cx;
        float testY = cy;

        rx -= rw / 2;
        ry -= rh / 2;

        // which edge is closest?
        if (cx < rx) testX = rx;      // test left edge
        else if (cx > rx + rw) testX = rx + rw;   // right edge
        if (cy < ry) testY = ry;      // top edge
        else if (cy > ry + rh) testY = ry + rh;   // bottom edge

        // get distance from closest edges
        float distX = cx - testX;
        float distY = cy - testY;
        float distance = (float) Math.sqrt((distX * distX) + (distY * distY));
        // if the distance is less than the radius, collision!
        return distance <= radius;
    }

    public static boolean circleInCircle(float x1, float y1, float radius1, float x2, float y2, float radius2) {
        float xDif = x1 - x2;
        float yDif = y1 - y2;
        float distanceSquared = xDif * xDif + yDif * yDif;
        return distanceSquared < (radius1 + radius2) * (radius1 + radius2);
    }

    public static boolean pointInRect(float x, float y, float objx, float objy, float objWidth, float objHeight) {
       objx -= objWidth / 2;
       objy -= objHeight / 2;
        return (x > objx && y > objy &&
                x < objx + objWidth &&
                y < objy + objHeight);
    }

}
