package csc133.towerdefense.game.gameobject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

import csc133.towerdefense.game.helpers.Functions;

public class Bullet extends GameObject {
    static final float BULLET_SIZE = 10;
    static final float BULLET_SPEED = 5;
    public float speed;
    public float unitDirX;
    public float unitDirY;

    public float timeAlive = 0;

    public Bullet(float x, float y, float targetX, float targetY, float speed) {
        super(x, y, BULLET_SIZE);
        this.speed = speed;
        PointF unitDir = Functions.getUnitDirection(x, y, targetX, targetY);
        this.unitDirX = unitDir.x;
        this.unitDirY = unitDir.y;
    }

    public Bullet(float x, float y, float targetX, float targetY) {
        this(x, y, targetX, targetY, BULLET_SPEED);
    }

    public void update() {
        ++timeAlive;

        this.x += unitDirX * speed;
        this.y += unitDirY * speed;

        if (timeAlive >= 3 * (60)) {
            toDestroy = true;
        }
    }

    public void draw(Canvas canvas) {
        Paint myPaint = new Paint();
        myPaint.setColor(Color.RED);
        myPaint.setStyle(Paint.Style.FILL);
        myPaint.setStrokeWidth(1);
        canvas.drawRect(x - width / 2, y - height / 2, x + width / 2, y + height / 2, myPaint);
    }
}
