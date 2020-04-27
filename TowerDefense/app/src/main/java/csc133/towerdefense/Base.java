package csc133.towerdefense;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import csc133.towerdefense.GameObjects.enemies.Enemy;

public class Base {
    private Rect base;
    private int bs;

    public Base(int blockSize) {
        bs = blockSize;
    }

    public void drawBase(Canvas mCanvas, Paint mPaint) {
        mPaint.setColor(Color.argb(100, 255, 0, 0));
        base = new Rect(1700, 650, 1800, 950);
        mCanvas.drawRect(base, mPaint);
    }

    //check if an enemy is hitting the base
    public boolean hittingBase(Enemy enemy) {
        return base.contains((enemy.headLocation.x * bs),
                (enemy.headLocation.y * bs + (int) (bs * 2.5)));
    }

    public boolean hittingBase(Point p) {
        return base.contains(p.x, p.y);
    }
}
