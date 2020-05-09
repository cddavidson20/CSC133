package csc133.towerdefense.game.helpers;

import android.graphics.PointF;

/**
 * Functions (Methods) to help make coding easier. They just work man.
 */
public class Functions {
    /**
     * Basically an atan2 method used to find the unit vector.
     * @param x Current x
     * @param y Current y
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
     * @param x number 1
     * @param y number 2
     * @return if the numbers are opposite signs.
     */
    public static boolean oppositeSigns(float x, float y) {
        return !((x < 0) == (y < 0));
    }

    /**
     *
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
        return Bx + Bw > Ax &&
                By + Bh > Ay &&
                Ax + Aw > Bx &&
                Ay + Ah > By;
    }

    /**
     *
     * @param cx
     * @param cy
     * @param cr
     * @param rx
     * @param ry
     * @param rw
     * @param rh
     * @return
     */
    public static boolean rectInCircle(float cx, float cy, float cr, float rx, float ry, float rw, float rh) {
        float closestX = clamp(cx, rx, rx + rw);
        float closestY = clamp(cy, ry - rh, ry);

        float distanceX = cx - closestX;
        float distanceY = cy - closestY;

        return Math.pow(distanceX, 2) + Math.pow(distanceY, 2) < Math.pow(cr, 2);
    }

    /**
     *
     * @param value
     * @param min
     * @param max
     * @return
     */
    public static float clamp(float value, float min, float max) {
        float x = value;
        if (x < min) {
            x = min;
        } else if (x > max) {
            x = max;
        }
        return x;
    }
}
