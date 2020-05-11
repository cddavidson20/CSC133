package csc133.towerdefense.game.gameobject.tower;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

import csc133.towerdefense.game.engine.GameEngine;
import csc133.towerdefense.game.gameobject.Bullet;
import csc133.towerdefense.game.gameobject.GameObject;
import csc133.towerdefense.game.gameobject.enemy.Enemy;
import csc133.towerdefense.game.helpers.Functions;

public class Tower extends GameObject {
    public float attackSpeed;
    public float attackRadius;
    public float buyPrice;
    public float sellPrice;
    public float bulletSpeed;
    public float bulletSize;
    public float bulletDamage;
    public float bulletHealth;

    long lastFired = -9999999;

    float rotation = 0;

    public Enemy target;
    boolean willFire;

    public Tower() {
        super();
    }

    public void setTarget(Enemy target) {
        this.target = target;
    }

    public void update() {
        if (target != null) {
            rotation = (float) ((Functions.getDirectionAngle(x, y, target.x, target.y) * 180) / Math.PI);
        }

        willFire = target != null && canFire();

        target = null;
    }

    public void tryToFire(ArrayList<Bullet> bullets) {
        if (willFire && target != null) {
            float xx = x + (float) (Math.cos((rotation) * Math.PI / 180) * (width / 2));
            float yy = y + (float) (Math.sin((rotation) * Math.PI / 180) * (height / 2));
            bullets.add(new Bullet(xx, yy, target.x, target.y, bulletSize, bulletSpeed, bulletDamage, bulletHealth));
            lastFired = GameEngine.framesRan;
        }
    }

    public boolean canFire() {
        return GameEngine.framesRan >= lastFired + (GameEngine.MAX_FPS / attackSpeed);
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();

        // draw radius line
        paint.setStrokeWidth(1);
        paint.setColor(Color.GRAY);
        if (target != null) paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(255 / 3);
        canvas.drawCircle(x, y, attackRadius, paint);
        paint.setAlpha(255);

        // draw pillbox
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1);
        canvas.drawCircle(x, y, width / 2, paint);
        //canvas.drawRect(x - width / 2, y - height / 2, x + width / 2, y + height / 2, paint);

        paint.setStrokeWidth(2);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(x, y, width / 2, paint);
        //canvas.drawRect(x - width / 2, y - height / 2, x + width / 2, y + height / 2, paint);

        //draw cannon
        paint.setStrokeWidth(1);
        paint.setColor(Color.CYAN);
        paint.setStyle(Paint.Style.FILL);
        canvas.save();
        canvas.rotate(rotation, x, y);
        canvas.drawRect(x, y - height / 15, x + width / 2, y + height / 15, paint);
        canvas.restore();

        //draw dot
        paint.setStrokeWidth(1);
        paint.setColor(Color.DKGRAY);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y, width / 5, paint);
    }


}
