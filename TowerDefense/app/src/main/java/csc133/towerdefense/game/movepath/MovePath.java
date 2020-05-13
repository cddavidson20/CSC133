package csc133.towerdefense.game.movepath;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.ArrayList;

import csc133.towerdefense.game.IDrawable;

/**
 * The path that the enemies will travel on.
 */
public class MovePath implements IDrawable {
    public ArrayList<PointF> points;
    public float width;
    public float startX;
    public float startY;
    public float endX;
    public float endY;

    public ArrayList<RectF> rects;

    public MovePath(PointF[] points, float width, float startX, float startY, float endX, float endY) {
        this.points = new ArrayList<>();
        this.width = width;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;

        for (int i = 0; i < points.length; ++i) {
            PointF p = points[i];
            if (p == null) break;
            this.points.add(p);
        }
        initRects();
    }

    private void initRects() {
        PointF prevP = getStartingPoint();
        rects = new ArrayList<RectF>();

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

            rects.add(new RectF(left, top, right, bottom));


            prevP = p;
        }
    }

    private PointF getStartingPoint() {
        return points.get(0);
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);

        for (RectF rect : rects) canvas.drawRect(rect, paint);

        //draw end point
        paint.setColor(Color.RED);
        paint.setAlpha(255 / 2);
        canvas.drawRect(endX - width / 2, endY - width / 2, endX + width / 2, endY + width / 2, paint);
        paint.setAlpha(255);

    }
}
