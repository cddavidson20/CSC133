package csc133.towerdefense.game.gameobject.tower;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

import csc133.towerdefense.game.GameEngine;
import csc133.towerdefense.game.SoundEngine;
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
    public Bitmap bitmap;

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
        if (willFire && target != null && !GameEngine.youLost) {
            float xx = x + (float) (Math.cos((rotation) * Math.PI / 180) * (width / 2));
            float yy = y + (float) (Math.sin((rotation) * Math.PI / 180) * (height / 2));
            Bullet bullet = new Bullet(xx, yy, target.x, target.y, bulletSize, bulletSpeed, bulletDamage, bulletHealth);
            bullet.lifespan = (attackRadius / bulletSpeed) / GameEngine.MAX_FPS;
            bullets.add(bullet);
            lastFired = GameEngine.framesRan;
            SoundEngine.getSoundEngine().playMachineGun();
        }
    }

    public boolean canFire() {
        return GameEngine.framesRan >= lastFired + (GameEngine.MAX_FPS / attackSpeed);
    }

    public void draw(Canvas canvas) {
        draw(canvas, new Paint());
    }

    public void initBitmap(String bitmapName) {

        Context c = GameEngine.context;

        // Make a resource id out of the string of the file name
        int resID = c.getResources().getIdentifier(bitmapName,
                "drawable", c.getPackageName());

        bitmap = BitmapFactory.decodeResource(c.getResources(), resID);

        bitmap = Bitmap
                .createScaledBitmap(bitmap,
                        (int) width,
                        (int) height,
                        false);

    }

    public void draw(Canvas canvas, Paint paint) {
        int a = paint.getAlpha();
        canvas.save();
        canvas.rotate(rotation, x, y);
        canvas.drawBitmap(bitmap, x - width / 2, y - height / 2, null);
        canvas.restore();
        // draw radius line
        paint.setStrokeWidth(1);
        paint.setColor(Color.GRAY);
        if (target != null && !GameEngine.youLost) paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(a / 3);
        canvas.drawCircle(x, y, attackRadius, paint);
        paint.setAlpha(a);
    }

}
