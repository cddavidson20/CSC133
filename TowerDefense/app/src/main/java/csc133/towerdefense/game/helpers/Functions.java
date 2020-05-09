package csc133.towerdefense.game.helpers;

import android.graphics.PointF;

/**
 * Functions (Methods) to help make coding easier. They just work man.
 */
public class Functions {
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

    public static boolean oppositeSigns(float x, float y) {
        return !((x < 0) == (y < 0));
    }

    public static boolean rectInRect(float Ax, float Ay, float Aw, float Ah,
                                     float Bx, float By, float Bw, float Bh) {
        return Bx + Bw > Ax &&
                By + Bh > Ay &&
                Ax + Aw > Bx &&
                Ay + Ah > By;
    }

    public static boolean rectInCircle(float cx, float cy, float cr, float rx, float ry, float rw, float rh) {
        float closestX = clamp(cx, rx, rx + rw);
        float closestY = clamp(cy, ry - rh, ry);

        float distanceX = cx - closestX;
        float distanceY = cy - closestY;

        return Math.pow(distanceX, 2) + Math.pow(distanceY, 2) < Math.pow(cr, 2);
    }

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
