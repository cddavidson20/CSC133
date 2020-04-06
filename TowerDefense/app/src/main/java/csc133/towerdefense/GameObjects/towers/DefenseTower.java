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
    private Enemy enemy;
    private Rect range;
    private int blockSize;

    private final Point sizeOfDefenseTowerImage = new Point(27,27);
    private Point centerOfTowerImage;


    public DefenseTower() {
        super();
    }

    public DefenseTower(Context context, Point position, Enemy enemy, int bs) {
        super(context, position);
        this.enemy = enemy;
        this.blockSize = bs;
        int positionX = position.x;
        int positionY = position.y;
        int offsetX = (positionX - sizeOfDefenseTowerImage.x /2);
        int offsetY = (positionY - sizeOfDefenseTowerImage.y /2);
        centerOfTowerImage = new Point(offsetX, offsetY);

        range = new Rect(centerOfTowerImage.x - 100, centerOfTowerImage.y - 100,
                centerOfTowerImage.x + 100, centerOfTowerImage.y + 100
        );

        bitmap = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.defense_tower);
    }

    public void draw(Canvas canvas, Paint paint) {
        range(canvas, paint);
        shoot(canvas, paint);
        canvas.drawBitmap(bitmap, centerOfTowerImage.x - sizeOfDefenseTowerImage.x,
                        centerOfTowerImage.y - sizeOfDefenseTowerImage.y, paint);
    }

    private void range(Canvas canvas, Paint paint) {
        if (enemyInRange()) {
            paint.setColor(Color.argb(50, 255, 0, 0));
            canvas.drawRect(range, paint);
        } else {
            paint.setColor(Color.argb(50, 255, 255, 255));
        }
        canvas.drawRect(range, paint);
        paint.setAlpha(255);
    }

    private void shoot(Canvas canvas, Paint paint) {
        if (enemyInRange()) {
            paint.setColor(Color.rgb(255, 255, 0));
            canvas.drawLine(centerOfTowerImage.x, centerOfTowerImage.y,
                    enemy.headLocation.x * blockSize,
                    enemy.headLocation.y * blockSize + (int)(blockSize * 2.5), paint);
        }
    }

    //change headLocation to pixels
    private boolean enemyInRange() {
        return range.contains((enemy.headLocation.x * blockSize) ,
                              (enemy.headLocation.y * blockSize + (int)(blockSize * 2.5)) );
    }
}
