package csc133.towerdefense.GameObjects.towers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public abstract class AbstractTower {

    AbstractTower() {}

    AbstractTower(Context context, Point position) {}

    public abstract void draw(Canvas canvas, Paint paint);
    public abstract Point getPosition();
}
