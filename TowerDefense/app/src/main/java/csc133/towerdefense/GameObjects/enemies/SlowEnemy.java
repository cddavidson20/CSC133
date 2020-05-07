package csc133.towerdefense.GameObjects.enemies;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Paint;

import csc133.towerdefense.GameObjects.GameObject;
import csc133.towerdefense.GameState;

public class SlowEnemy extends AbstractEnemy {
    private static int health = 200;
    private static int killedGold = 75;

    private int currPathIndex = 0;

    public SlowEnemy(Context context, GameObject gameObject, GameState gameState, int bs, Point mr) {
        super(context, gameObject, gameState, bs, mr, "SlowEnemy", health, killedGold);
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setAlpha(255);
        super.draw(canvas, paint);
    }

    public void reset(int w, int h) {
        // Reset the heading
        super.reset(w, h);
    }

    public void move() {
        followPath();
    }

    private void followPath() {
        while (true) {
            boolean getout = true;
            if (currPathIndex >= path.length) return;

            int targetDirX = path[currPathIndex].x - headLocation.x;
            int targetDirY = path[currPathIndex].y - headLocation.y;
            if (targetDirX > 0) {
                heading = Heading.RIGHT;
            } else if (targetDirX < 0) {
                heading = Heading.LEFT;
            } else if (targetDirY > 0) {
                heading = Heading.DOWN;
            } else if (targetDirY < 0) {
                heading = Heading.UP;
            } else {
                ++currPathIndex;
                getout = false;
            }
            if (getout) break;
        }

        // Move it appropriately
        switch (heading) {
            case UP:
                headLocation.y--;
                break;

            case RIGHT:
                headLocation.x++;
                break;

            case DOWN:
                headLocation.y++;
                break;

            case LEFT:
                headLocation.x--;
                break;
        }

    }
}
