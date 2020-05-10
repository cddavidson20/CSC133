package csc133.towerdefense.game;

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
                new PointF(1760, 900),
            },
        80),
        new MovePath(
            new PointF[] {
                new PointF(0, 0),
                new PointF(20, 0),
                new PointF(20, 20),
                new PointF(50, 20),
                new PointF(50, 100),
            }, 100
        )
    };
}
