package csc133.towerdefense;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

public class Path {

    private Point[] path;
    private int blockSize;
    private Point hudOffset;
    private ArrayList<Rect> rectPath;

    public Path(int blockSize) {

        this.blockSize = blockSize;
        path = new Point[]{
                new Point(0, 0),
                new Point(25, 0),
                new Point(25, 15),
                new Point(50, 15)
        };

        hudOffset = new Point(0, (int) (2.5 * blockSize));
        rectPath = new ArrayList<>();
    }

    public void drawPath(Canvas canvas, Paint paint) {
        if (rectPath.isEmpty()) {
            for (int i = 0; i < path.length - 1; ++i) {
                int x1 = blockSize * path[i].x;
                int x2 = blockSize * path[i + 1].x;
                int y1 = blockSize * path[i].y;
                int y2 = blockSize * path[i + 1].y;
                int left, top, right, bottom;
                if (x1 < x2) {
                    left = x1 - blockSize / 2;
                    right = x2 + blockSize / 2;
                } else {
                    left = x1 + blockSize / 2;
                    right = x2 - blockSize / 2;
                }
                if (y1 < y2) {
                    bottom = y1 - blockSize / 2;
                    top = y2 + blockSize / 2;
                } else {
                    bottom = y1 + blockSize / 2;
                    top = y2 - blockSize / 2;
                }
                int xOffset = blockSize / 2;
                int yOffset = blockSize / 2;

                Rect rect = new Rect(left + xOffset, top + yOffset + hudOffset.y, right + xOffset, bottom + yOffset + hudOffset.y);
                paint.setColor(Color.argb(50, 255, 255, 150));
                canvas.drawRect(rect, paint);
                rectPath.add(rect);
            }
        } else {
            for (Rect rect : rectPath) {
                paint.setColor(Color.argb(50, 255, 255, 150));
                canvas.drawRect(rect, paint);
            }
        }
        paint.setAlpha(255);
    }

    public boolean hittingPath(Point p) {
        Point sizeOfDefenseTowerImage = new Point(27, 27);
        int offsetX = (p.x - sizeOfDefenseTowerImage.x / 2);
        int offsetY = (p.y - sizeOfDefenseTowerImage.y / 2);

        if (!rectPath.isEmpty()) {
            for (Rect rect : rectPath) {
                if (rect.contains(offsetX, offsetY))
                    return true;
            }
        }
        return false;
    }
}