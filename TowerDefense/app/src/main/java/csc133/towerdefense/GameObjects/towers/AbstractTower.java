package csc133.towerdefense.GameObjects.towers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public abstract class AbstractTower {

    public AbstractTower() {}

    public AbstractTower(Context context, Point position) {}

    public void draw(Canvas canvas, Paint paint){}
}
