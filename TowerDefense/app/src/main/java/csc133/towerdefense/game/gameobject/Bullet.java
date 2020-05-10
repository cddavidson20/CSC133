package csc133.towerdefense.game.gameobject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

import csc133.towerdefense.game.helpers.Functions;

public class Bullet extends GameObject {
    public float speed;
    public float unitDirX;
    public float unitDirY;

    public float timeAlive = 0;
    public float damage;
    public float health;

    public float prevX;
    public float prevY;

    public Bullet(float x, float y, float targetX, float targetY, float size, float speed, float damage, float maxHealth) {
        super(x, y, size, damage);
        prevX = x;
        prevY = y;
        this.speed = speed;
        this.damage = damage;
        PointF unitDir = Functions.getUnitDirection(x, y, targetX, targetY);
        this.unitDirX = unitDir.x;
        this.unitDirY = unitDir.y;
        this.health = maxHealth;
    }

    public void update() {
        ++timeAlive;

        prevX = x;
        prevY = y;

        this.x += unitDirX * speed;
        this.y += unitDirY * speed;

        if (timeAlive >= 3 * (60) || this.health <= 0) {
            toDestroy = true;
        }

    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1);
        canvas.drawCircle(x, y, width / 2, paint);
        //canvas.drawRect(x - width / 2, y - height / 2, x + width / 2, y + height / 2, myPaint);
    }
}
