package csc133.towerdefense.GameObjects.enemies;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public abstract class AbstractEnemy {

    public Point headLocation;

    AbstractEnemy() {}

    public abstract void draw(Canvas canvas, Paint paint);
    public abstract void move();
    public abstract void reset(int w, int h);
    public abstract void enemyShot(int healthTaken, int indexOfEnemy);
}
