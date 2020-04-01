package csc133.towerdefense.GameObjects.towers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import csc133.towerdefense.GameObjects.enemies.Enemy;
import csc133.towerdefense.R;

public class DefenseTower extends AbstractTower {

    private Bitmap bitmap;
    private int positionX;
    private int positionY;
    private int blockSize;
    private Enemy enemy;


    public DefenseTower() {
        super();
    }

    public DefenseTower(Context context, Point position, int blockSize, Enemy enemy) {
        super(context, position);
        this.blockSize = blockSize;
        this.enemy = enemy;
        positionX = position.x;
        positionY = position.y;


        bitmap = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.defense_tower);
    }

    public void draw(Canvas canvas, Paint paint) {
        range(canvas, paint);
        canvas.drawBitmap(bitmap, positionX, positionY, paint);
    }

    public void range(Canvas canvas, Paint paint) {
        Rect rect = new Rect(positionX / 2, positionY / 2,
                positionX + 250, positionY + 250
        );
        if (enemyInRange()) {
            paint.setColor(Color.argb(50, 255, 0, 0));
        } else {
            paint.setColor(Color.argb(50, 255, 255, 255));
        }
        canvas.drawRect(rect, paint);
        paint.setAlpha(255);
    }

    private boolean enemyInRange() {
        int left = positionX / (blockSize * 2);
        int right = (positionX + 250) / blockSize;

        if ((enemy.headLocation.x >=  left) && (enemy.headLocation.x <=  right)) {
            return true;
        } else {
            return false;
        }
    }

}
