package csc133.towerdefense.game.movepath;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

import java.util.ArrayList;

import csc133.towerdefense.game.IDrawable;

/**
 * The path that the enemies will travel on.
 */
public class MovePath implements IDrawable {
    public ArrayList<PointF> points;
    public float width;

    public MovePath(PointF[] points, float width) {
        this.points = new ArrayList<PointF>();
        this.width = width;
        for (int i = 0; i < points.length; ++i) {
            PointF p = points[i];
            if (p == null) break;
            this.points.add(p);
        }
    }

    public PointF getStartingPoint() {
        return points.get(0);
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);

        PointF prevP = getStartingPoint();

        for (int i = 1; i < points.size(); ++i) {
            PointF p = points.get(i);
            float left, right, top, bottom;
            left = prevP.x - width / 2;
            right = p.x + width / 2;
            top = prevP.y - width / 2;
            bottom = p.y + width / 2;

            // if go from right to left swap
            if (prevP.x > p.x) {
                left = p.x - width / 2;
                right = prevP.x + width / 2;
            }
            // if go from left to right swap
            if (prevP.y > p.y) {
                bottom = prevP.y + width / 2;
                top = p.y - width / 2;
            }

            canvas.drawRect(left, top, right, bottom, paint);
            prevP = p;
        }
    }
}
