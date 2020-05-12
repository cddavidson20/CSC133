package csc133.towerdefense.game.movepath;

import android.graphics.PointF;

import csc133.towerdefense.game.movepath.MovePath;

/**
 * The path that the enemy units follow. Each index is a path
 * for a level. The points are the enemies' destination. Logic is
 * done in the Enemy class.
 */
public class Paths {
    public static final MovePath[] Paths =  new MovePath[] {
        new MovePath(
            new PointF[] {
                new PointF(0, 300),
                new PointF(500, 300),
                new PointF(1000, 300),
                new PointF(1000, 500),
                new PointF(250, 500),
                new PointF(250, 700),
                new PointF(1000, 700),
                new PointF(1000, 900),
                new PointF(1900, 900),
            },
                80,
                -30, 300,
                1800, 900),
        new MovePath(
            new PointF[] {
                new PointF(60, 1000),
                new PointF(60, 300),
                new PointF(300, 300),
                new PointF(300, 500),
                new PointF(800, 500),
                new PointF(800, 700),
                new PointF(1900, 700),
            },
                80,
                60 ,1040,
                1800, 700),
        new MovePath(
            new PointF[] {
                new PointF(0, 500),
                new PointF(200, 500),
                new PointF(200, 600),
                new PointF(1000, 600),
                new PointF(1000, 700),
                new PointF(1900, 700),
                },
                80,
                -30, 500,
                1800, 700)
    };
}
