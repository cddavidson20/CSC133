package csc133.towerdefense.game.gameobject.enemy;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

import csc133.towerdefense.game.gameobject.GameObject;
import csc133.towerdefense.game.helpers.Functions;
import csc133.towerdefense.game.movepath.MovePath;

/**
 * Anemy
 */
public class Enemy extends GameObject {
    public static float ENEMY_SIZE = 25;
    public static float ENEMY_HEALTH_MAX = 1;
    public static float ENEMY_SPEED = 5;

    public float healthMax;
    public float health;
    public float speed;

    public float targetX;
    public float targetY;

    public float offsetX;
    public float offsetY;

    MovePath path;
    int pathIndex;

    public Enemy(float x, float y, MovePath path, float offsetX, float offsetY) {
        super(x, y, ENEMY_SIZE);
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.path = path;
        this.pathIndex = -1;
        nextPoint();
        this.healthMax = ENEMY_HEALTH_MAX;
        this.speed = ENEMY_SPEED;

        this.health = healthMax;
    }

    public Enemy(float x, float y, MovePath path) {
        this(x, y, path, 0, 0);
    }

    public Enemy(MovePath path) {
        this(path.getStartingPoint().x, path.getStartingPoint().y, path);
    }

    private void nextPoint() {
        if (pathIndex + 1 >= path.points.size()) {
            // TODO niga died
        } else {
            ++pathIndex;
            this.targetX = path.points.get(pathIndex).x + offsetX;
            this.targetY = path.points.get(pathIndex).y + offsetY;
        }

    }

    public void update() {
        move();

        checkDeath();
    }

    public void checkDeath() {
        if (health <= 0) {
            toDestroy = true;
        }
    }

    private void move() {
        PointF unitDir = Functions.getUnitDirection(x, y, targetX, targetY);
        float postX = x + unitDir.x * speed;
        float postY = y + unitDir.y * speed;

        // clamp that pdick
        if (Functions.oppositeSigns(targetX - this.x, targetX - postX)) {
            this.x = targetX;
        } else {
            this.x = postX;
        }
        if (Functions.oppositeSigns(targetY - this.y, targetY - postY)) {
            this.y = targetY;
        } else {
            this.y = postY;
        }

        // if my dude at the point then get to next point u slackin bro
        if ((Math.abs(x - targetX) == 0) && (Math.abs(y - targetY) == 0)) {
            nextPoint();
        }
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1);
        canvas.drawRect(x - width / 2, y - height / 2, x + width / 2, y + height / 2, paint);

        paint.setStrokeWidth(1);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(x - width / 2, y - height / 2, x + width / 2, y + height / 2, paint);
    }

}
